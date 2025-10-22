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
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.*;
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
    @Value("${authorization-code.redirect-uri}")
    private String redirectURI;


    public AuthorizationConfig(CustomOauth2AuthenticationProvider customOauth2AuthenticationProvider) {
        this.customOauth2AuthenticationProvider = customOauth2AuthenticationProvider;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        http.getConfigurer(authorizationServerConfigurer.getClass()).oidc(Customizer.withDefaults());
        http.authenticationProvider(customOauth2AuthenticationProvider);
        http.exceptionHandling((c) -> {

            c.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
        });

        return http.build();
    }

    @Bean
    @Order (2)
    public SecurityFilterChain endpointAuthorizationFilterChain (HttpSecurity http) throws Exception {

        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/oauth2/**", "/.well-known/**").permitAll().anyRequest().authenticated());
        http.cors(cors -> {
            CorsConfigurationSource corsConfigurationSource = request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("http://localhost:8085", "http://example.com", "http://example2.com"));
                corsConfiguration.setAllowedMethods(List.of("POST", "GET"));
                corsConfiguration.setAllowedHeaders(List.of("*"));
                return corsConfiguration;
            };

            cors.configurationSource(corsConfigurationSource);
        });

        http.csrf(AbstractHttpConfigurer::disable);


        return http.build();
    }


    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }


    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder encoder) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString()).
                clientId(client_id).clientSecret("{noop}" + client_secret).
                clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC).
                authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE).
                authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN).
                redirectUri(redirectURI).
                scope("catalog.read").
                scope("catalog.write").
                scope("user.read").
                scope("user.write").
                scope("rental.read").
                scope("rental.write").
                scope("client.read").
                scope("client.write").
                tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofSeconds(tokenDuration)).build()).
                build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }


    @Bean
    JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {

        KeyPair keyPair = generateKeyPairRSAKeys();
        RSAKey rsakey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).privateKey((RSAPrivateKey) keyPair.getPrivate()).keyID(UUID.randomUUID().toString()).build();
        JWKSet jwkSet = new JWKSet(rsakey);

        return new ImmutableJWKSet<>(jwkSet);


    }

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


    @Bean
    OAuth2TokenCustomizer<JwtEncodingContext> mappingRoleToScope() {

        return context -> {

            var principalAuthorities = context.getPrincipal().getAuthorities();
            Set<String> tokenRoles = principalAuthorities.stream().map(x -> x.getAuthority()).filter(x -> x.startsWith("ROLE_")).collect(Collectors.toSet());

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


