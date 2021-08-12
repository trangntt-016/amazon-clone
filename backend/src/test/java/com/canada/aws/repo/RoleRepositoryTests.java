package com.canada.aws.repo;

import com.canada.aws.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RoleRepositoryTests {
    @Autowired
    RoleRepository roleRepository;

    @Test
    public void testCreateRoles(){
        Role roleCustomer = Role.builder().roleName("CUSTOMER").code("C").build();

        Role roleSeller = Role.builder().roleName("SELLER").code("S").build();

        Role roleAdmin = Role.builder().roleName("ADMIN").code("AD").build();

        roleRepository.saveAll(List.of(roleAdmin,roleSeller));
    }

    @Test
    public void testFindRoleByRoleName(){
        String roleName = "CUSTOMER";

        Optional<Role> role = roleRepository.findByRoleName(roleName);

        assertThat(role.get().getRoleName()).isEqualTo(roleName);
    }
}
