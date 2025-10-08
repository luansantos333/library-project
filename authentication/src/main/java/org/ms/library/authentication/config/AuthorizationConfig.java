package org.ms.library.authentication.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.ms.library.authentication.config.customprovider.CustomOauth2AuthenticationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity

public class AuthorizationConfig {
    @Value("${library.client.id}")
    private String client_id;
    @Value("${library.client.secret}")
    private String client_secret;
    @Value("${jwt.token.duration.in.seconds}")
    private Long tokenDuration;
    private final CustomOauth2AuthenticationProvider customOauth2AuthenticationProvider;


    public AuthorizationConfig(CustomOauth2AuthenticationProvider customOauth2AuthenticationProvider) {
        this.customOauth2AuthenticationProvider = customOauth2AuthenticationProvider;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfigurer oAuth2AuthorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        oAuth2AuthorizationServerConfigurer.tokenEndpoint((tokenEndpoint) ->
                tokenEndpoint.authenticationProvider(customOauth2AuthenticationProvider));
        http.with(oAuth2AuthorizationServerConfigurer, Customizer.withDefaults());
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt());
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(authorize -> authorize.requestMatchers("/oauth2/**", "/oauth/**", "/.well-known/**").permitAll().anyRequest().authenticated());
        http.httpBasic(Customizer.withDefaults());
        http.formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }


    // IN MEMORY CLIENT REPOSITORY BEAN FOR TESTING

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder encoder) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(client_id)
                .clientSecret(encoder.encode(client_secret))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("catalog.read")
                .scope("catalog.write")
                .scope("user.read")
                .scope("user.write")
                .scope("rental.read")
                .scope("rental.write")
                .scope("client.read")
                .scope("client.write")
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofSeconds(tokenDuration)).
                        refreshTokenTimeToLive(Duration.ofSeconds(tokenDuration)).
                        reuseRefreshTokens(false).build())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }


    // JWK SOURCE BEAN
    @Bean
    JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {

        KeyPair keyPair = generateKeyPairRSAKeys();
        RSAKey rsakey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).
                privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString()).build();
        JWKSet jwkSet = new JWKSet(rsakey);

        return new ImmutableJWKSet<>(jwkSet);


    }


    // GENERATE RSA KEY PAIR FOR JWK
    private KeyPair generateKeyPairRSAKeys() throws NoSuchAlgorithmException {

        KeyPair keypar;
        try {

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keypar = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new NoSuchAlgorithmException(e.getMessage());
        }

        return keypar;


    }

    // IMPLEMENTS CUSTOM AUTHENTICATION MANAGER
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(customOauth2AuthenticationProvider);
    }

    @Bean
    OAuth2TokenCustomizer<JwtEncodingContext> mappingRoleToScope() {

        return context -> {

            var principalAuthorities = context.getPrincipal().getAuthorities();
            Set<String> tokenRoles = principalAuthorities.stream().
                    map(x -> x.getAuthority()).
                    filter(x -> x.startsWith("ROLE_")).collect(Collectors.toSet());

            Set<String> mapScopes = new HashSet<>();
            String username = context.getPrincipal().getName();
            if (tokenRoles.isEmpty()) {

                throw new AuthenticationCredentialsNotFoundException("No roles assigned to the user");

            }

            if (tokenRoles.contains("ROLE_ADMIN")) {

                mapScopes.addAll(Arrays.asList("catalog.read", "catalog.write", "client.read", "client.write", "user.read", "user.write", "rental.read", "rental.write"));

            } else
                mapScopes.addAll(Arrays.asList("catalog.read")); // Analisar depois quais autorizações o perfil de usuário deve ter

            context.getClaims().claim("roles", tokenRoles);
            context.getClaims().claim("scope", mapScopes);
            context.getClaims().claim("username", username);


        };

    }




}


