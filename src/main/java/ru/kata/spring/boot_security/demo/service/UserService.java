package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.Set;

public interface UserService {
    Set<User> userList();
    void save(User user);
    User getUser(long id);
    void update(User user);
    void delete(Long id);
    User findByUsername(String username);
}
