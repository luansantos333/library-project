package org.ms.library.authentication.service;

import feign.FeignException;
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
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        try {
            UserDTO userDetailsByUsername = userFeignClient.findUserRoleByUsernameInternal(username).getBody();

            if (userDetailsByUsername == null) {

                throw new UsernameNotFoundException(username);
            }

            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

            for (RoleDTO roleDTO : userDetailsByUsername.getRoles()) {


                grantedAuthorities.add(new SimpleGrantedAuthority(roleDTO.getName()));


            }

            return new User(userDetailsByUsername.getUsername(), userDetailsByUsername.getPassword(), grantedAuthorities);

        } catch (FeignException e) {

            throw new InternalError(e.getMessage());


        }


    }
}
