package org.ms.library.authentication.configuration;

import org.ms.library.authentication.configuration.JWTAuthenticationConverter.JWTAuthenticationConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfiguration {

    private final JWTAuthenticationConverter jwtCustomAuthenticationConverter;
    @Value("${jwk.url}")
    private String jwkURI;

    public ResourceServerConfiguration(JWTAuthenticationConverter jwtCustomAuthenticationConverter) {
        this.jwtCustomAuthenticationConverter = jwtCustomAuthenticationConverter;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http.authorizeExchange(authorize -> authorize.pathMatchers("/oauth2/**", "/oauth/**", "/.well-known/**").
                permitAll().anyExchange().authenticated()).oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
            jwt.jwtAuthenticationConverter(jwtCustomAuthenticationConverter).jwkSetUri(jwkURI);
        }));

        return http.build();


    }



}
