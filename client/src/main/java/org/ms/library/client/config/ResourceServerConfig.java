package org.ms.library.client.config;

import org.ms.library.client.config.JWTAuthenticationConverter.JWTAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig {

    private final JWTAuthenticationConverter jwtAuthenticationConverter;

    public ResourceServerConfig(JWTAuthenticationConverter jwtAuthenticationConverter) {
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }


    @Bean
    public SecurityFilterChain applyRolesValidation (HttpSecurity http) throws Exception {


        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.
                        jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));
        http.csrf(c -> c.disable());




        return http.build();

    }
}
