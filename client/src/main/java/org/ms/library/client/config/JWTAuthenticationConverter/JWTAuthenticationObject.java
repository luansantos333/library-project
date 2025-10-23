package org.ms.library.client.config.JWTAuthenticationConverter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.List;

public class JWTAuthenticationObject extends JwtAuthenticationToken {

    private final List<String> scopes;

    public JWTAuthenticationObject(Jwt jwt, Collection<? extends GrantedAuthority> authorities, List<String> scopes) {
        super(jwt, authorities);
        this.scopes = scopes;
    }


    public List<String> getScopes() {
        return scopes;
    }


}
