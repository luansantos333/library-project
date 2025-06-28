package org.ms.library.user.service;

import org.ms.library.user.dto.RoleDTO;
import org.ms.library.user.dto.UserDTO;
import org.ms.library.user.entity.Role;
import org.ms.library.user.entity.User;
import org.ms.library.user.repository.RolesRepository;
import org.ms.library.user.repository.UserRepository;
import org.ms.library.user.repository.projections.UserDetailsProjection;
import org.ms.library.user.service.exceptions.ExistentUserException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(UserRepository userRepository, RolesRepository rolesRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    public UserDTO findUserRoleByUsername(String username) {


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

    @Transactional(readOnly = true)
    public UserDTO findUserByUsername(String username) {

        User userByUsername = userRepository.findUserByUsername(username).orElseThrow(() -> new NoSuchElementException("No user found with this username"));

        return new UserDTO(userByUsername);

    }

    @Transactional(readOnly = true)
    public UserDTO findUserByUUID(UUID uuid) {

        User user = userRepository.findUserById(uuid).orElseThrow(() -> new NoSuchElementException("No user found with this userid"));

        return new UserDTO(user);

    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {

        if (userRepository.findUserByUsername(userDTO.getUsername()).isPresent()) {

            throw new ExistentUserException("The information you provided could not be processed. Please verify your data and try again.");

        }
        User user = new User();
        copyDTOToEntity(userDTO, user);
        User save = userRepository.save(user);
        return new UserDTO(save);
    }


    private void copyDTOToEntity(UserDTO userDTO, User user) {

        Role roleUser = rolesRepository.findByRoleByAuthority("ROLE_USER").orElseThrow(() -> new NoSuchElementException());
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.getRoles().add(new Role(roleUser.getId(), roleUser.getAuthority(),  roleUser.getDescription()));


    }

}
