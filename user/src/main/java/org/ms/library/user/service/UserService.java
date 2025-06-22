package org.ms.library.user.service;

import org.ms.library.user.dto.UserDTO;
import org.ms.library.user.entity.Role;
import org.ms.library.user.entity.User;
import org.ms.library.user.repository.UserRepository;
import org.ms.library.user.repository.projections.UserDetailsProjection;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO findUserByUsernameAndPassword(String username) {


        List<UserDetailsProjection> userDetailsByUsername = userRepository.findUserDetailsByUsername(username);
        if (userDetailsByUsername.isEmpty()) {

            throw new NoSuchElementException("User not found");

        }

        User user = new User();
        user.setUsername(userDetailsByUsername.get(0).getEmail());
        user.setPassword(userDetailsByUsername.get(0).getPassword());

        for (UserDetailsProjection p : userDetailsByUsername) {

            user.getRoles().add(new Role(p.getRoleId(), p.getRole(), p.getRoleDescription()));

        }
        return new UserDTO(user);

    }


}
