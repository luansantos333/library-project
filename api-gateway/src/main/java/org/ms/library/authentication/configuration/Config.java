package org.ms.library.authentication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class Config {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .authorizeExchange(exchange -> exchange
                // Allow requests to the authorization server's endpoints to pass through
                .pathMatchers("/oauth2/**", "/login", "/api/auth/**").permitAll()
                // All other requests must be authenticated
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())); // Validate tokens as JWTs

        // Disable CSRF for now as it's complex with SPAs, can be enabled later
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }
}
