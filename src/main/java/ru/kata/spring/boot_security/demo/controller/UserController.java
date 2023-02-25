package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserDetailsService userDetailsService, UserService userService, RoleService roleService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getUserInfo(Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User chosenUser = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("chosenUser", chosenUser);
        model.addAttribute("roleList", roleService.roleList());

        model.addAttribute("userPrincipal", userDetailsService.loadUserByUsername(principal.getName()));
        return "user";
    }
}
