package org.ms.library.authentication.configuration.JWTAuthenticationConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class JWTAuthenticationConverter implements Converter<Jwt, Mono<CustomJWTAuthenticationObject>> {



    @Override
    public Mono<CustomJWTAuthenticationObject> convert(Jwt source) {

        List<String> roles = (List<String>) source.getClaims().getOrDefault("roles", null);
        List<String> scopes = (List<String>) source.getClaims().getOrDefault("scope", null);

        if (roles == null || scopes == null) {


            throw new IllegalArgumentException("Missing roles and scopes");


        }

        var username = source.getClaims().get("username");
        List<GrantedAuthority> grantedAuthority = new ArrayList<>();

        for (String role : roles) {


            grantedAuthority.add(new SimpleGrantedAuthority(role));


        }

        return Mono.just(new CustomJWTAuthenticationObject(source, grantedAuthority, scopes));

    }
}
