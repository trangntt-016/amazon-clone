package com.canada.aws.repo;

import com.canada.aws.model.Role;
import com.canada.aws.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query("SELECT r FROM Role r WHERE r.roleName=:roleName")
    Optional<Role> findByRoleName(String roleName);
}
