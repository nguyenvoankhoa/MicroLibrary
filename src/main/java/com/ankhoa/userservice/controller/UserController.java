package com.ankhoa.userservice.controller;

import com.ankhoa.userservice.data.User;
import com.ankhoa.userservice.model.UserDTO;
import com.ankhoa.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping
    public UserDTO addUser(@RequestBody UserDTO dto) {
        return userService.saveUser(dto);
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody UserDTO dto) {
        return userService.login(dto.getUsername(), dto.getPassword());
    }
}
