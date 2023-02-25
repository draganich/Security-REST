package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.Set;

public interface RoleService {
    Set<Role> roleList();
    void save(Role role);
    void setRoles(User user);
    Role findRoleByName(String name);
}

