package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public ApiController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<Set<User>> getAllUsers() { return new ResponseEntity<>(userService.userList(), HttpStatus.OK); }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) { return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK); }

    @GetMapping("/user")
    public ResponseEntity<User> getLoggedUser(Principal principal) {
        User loggedUser = userService.findByUsername(principal.getName());
        return new ResponseEntity<>(loggedUser, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user) {
        roleService.setRoles(user);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*  create user JSON
    {
        "username": "userNew",
        "password": "userNew",
        "name": "userNameNew",
        "surname": "userSurnameNew",
        "email": "userNew@gmail.com",
        "roles": [
           {
                "id": 2,
                "name": "ROLE_USER",
                "authority": "ROLE_USER"
           }
        ]
    }
    */

    @PatchMapping("/users/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        roleService.setRoles(user);
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

     /*  update user JSON
    {
        "id": 2,
        "username": "userNew",
        "password": "userNew",
        "name": "userNameNew",
        "surname": "userSurnameNew",
        "email": "userNew@gmail.com",
        "roles": [
           {
                "id": 2,
                "name": "ROLE_USER",
                "authority": "ROLE_USER"
           }
        ]
    }
    */

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

