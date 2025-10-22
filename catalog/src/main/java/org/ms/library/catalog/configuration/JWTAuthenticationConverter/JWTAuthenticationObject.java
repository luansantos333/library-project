package org.ms.library.catalog.configuration.JWTAuthenticationConverter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Set;

public class JWTAuthenticationObject extends JwtAuthenticationToken {

    private final Set<String> scopes;

    public JWTAuthenticationObject(Jwt jwt, Collection<? extends GrantedAuthority> authorities, Set<String> scopes) {
        super(jwt, authorities);
        this.scopes = scopes;
    }


    public Set<String> getScopes() {
        return scopes;
    }


}
