package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void postConstruct() {
        String roleAdmin = "ROLE_ADMIN";
        String roleUser = "ROLE_USER";

        Role admin = new Role(roleAdmin);
        Role user = new Role(roleUser);

        roleService.save(admin);
        roleService.save(user);

        Role newAdmin = roleService.findRoleByName(roleAdmin);
        Role newUser = roleService.findRoleByName(roleUser);

        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword("admin");
        user1.setName("adminName");
        user1.setSurname("adminSurname");
        user1.setEmail("admin@gmail.com");
        user1.setRoles(Set.of(newAdmin, newUser));
        userService.save(user1);

        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword("user");
        user2.setName("userName");
        user2.setSurname("userSurname");
        user2.setEmail("user@gmail.com");
        user2.setRoles(Set.of(newUser));
        userService.save(user2);
    }
}
