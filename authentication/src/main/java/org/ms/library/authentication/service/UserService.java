package org.ms.library.authentication.service;

import org.ms.library.authentication.dto.RoleDTO;
import org.ms.library.authentication.dto.UserDTO;
import org.ms.library.authentication.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;
    private static Map<String, Set<String>> ROLE_TO_SCOPES = Map.of("ROLE_ADMIN", Set.of("catalog.read", "catalog.write", "client.read", "client.write",
            "rental.read", "rental.write", "user.read", "user.write"), "ROLE_USER", Set.of("catalog.read", "client.read", "client.write", "rental.read", "rental.write", "user.write"));


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTO userDetailsByUsername = userFeignClient.findUserRoleByUsernameInternal(username).getBody();

        if (userDetailsByUsername == null) {

            throw new UsernameNotFoundException(username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (RoleDTO roleDTO : userDetailsByUsername.getRoles()) {

            grantedAuthorities.add(new SimpleGrantedAuthority(roleDTO.getName()));
            Set<String> scopesLinkedToRole = ROLE_TO_SCOPES.getOrDefault(roleDTO.getName(), null);
            for (String scope : scopesLinkedToRole) {

                grantedAuthorities.add(new SimpleGrantedAuthority("SCOPE_" + scope));

            }

        }


        return new User(userDetailsByUsername.getUsername(), userDetailsByUsername.getPassword(), grantedAuthorities);

    }
}
