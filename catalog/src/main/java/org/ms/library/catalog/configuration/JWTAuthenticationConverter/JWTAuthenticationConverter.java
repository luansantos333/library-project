package org.ms.library.catalog.configuration.JWTAuthenticationConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class JWTAuthenticationConverter implements Converter<Jwt,JWTAuthenticationObject> {

    @Override
    public JWTAuthenticationObject convert(Jwt source) {

        Set<String> scopes = (Set<String>) source.getClaims().get("scopes");
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) source.getClaims().get("roles");

        return new JWTAuthenticationObject(source, authorities, scopes);
    }
}
