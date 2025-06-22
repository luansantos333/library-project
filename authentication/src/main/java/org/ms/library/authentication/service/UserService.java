package org.ms.library.authentication.service;

import org.ms.library.authentication.dto.UserDTO;
import org.ms.library.authentication.feign.UserFeignClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserFeignClient userFeignClient;

    public UserService(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTO userDetailsByUsername = userFeignClient.findUserRoleByUsername(username).getBody();

        if (userDetailsByUsername == null) {

            throw new UsernameNotFoundException(username);
        }

        return new User(userDetailsByUsername.getUsername(), userDetailsByUsername.getPassword(), (Collection<? extends GrantedAuthority>) userDetailsByUsername.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet()));

    }
}
