package org.ms.library.catalog.configuration;

import org.ms.library.catalog.configuration.JWTAuthenticationConverter.JWTAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ResourceServerConfiguration {

    private final JWTAuthenticationConverter jwtAuthenticationConverter;

    public ResourceServerConfiguration(JWTAuthenticationConverter jwtAuthenticationConverter) {
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



