package org.ms.library.client.config.JWTAuthenticationConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JWTAuthenticationConverter implements Converter<Jwt,JWTAuthenticationObject> {

    @Override
    public JWTAuthenticationObject convert(Jwt source) {

        List<String> scopes = (List<String>) source.getClaims().getOrDefault("scope", null);
        List<String> authorities = (List<String>) source.getClaims().getOrDefault("roles", null);

        if (scopes == null || authorities == null) {

            throw new IllegalArgumentException("scope or roles is null");

        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();


        for (String authority : authorities) {


            grantedAuthorities.add(new SimpleGrantedAuthority(authority));



        }

        return new JWTAuthenticationObject(source, grantedAuthorities, scopes);
    }
}
