package org.ms.library.user.repository.projections;

public interface UserDetailsProjection {


    String getEmail();
    String getRole();
    Long getRoleId();
    String getPassword();
    String getRoleDescription();

}
