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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

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

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()).
                authorizeHttpRequests(authorize -> authorize.requestMatchers("/oauth2/**", "/oauth/**", "/.well_known/**").permitAll().anyRequest().authenticated());
        http.httpBasic(Customizer.withDefaults());
        http.formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }


    // IN MEMORY CLIENT REPOSITORY BEAN FOR TESTING

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(client_id)
                .clientSecret("{noop}" + client_secret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("book.read")
                .scope("book.write")
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofSeconds(tokenDuration)).build())
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


}


