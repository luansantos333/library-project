package org.ms.library.user.repository;

import org.ms.library.user.entity.User;
import org.ms.library.user.repository.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

     @Query
     Optional<User> findUserByUsername(String username);

     @Query (value = "SELECT u.username AS email, u.password AS password, r.id AS roleId, r.authority AS role, r.description AS roleDescription " +
             "FROM tb_user u JOIN u.roles r WHERE u.username = :username")
     List<UserDetailsProjection> findUserDetailsByUsername(String username);


}
