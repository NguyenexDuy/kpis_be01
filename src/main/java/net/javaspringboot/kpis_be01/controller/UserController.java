package net.javaspringboot.kpis_be01.controller;


import net.javaspringboot.kpis_be01.entity.User;
import net.javaspringboot.kpis_be01.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserSevice userService;

    @GetMapping
    List<User> getAllUser(){
        return  userService.getAllUser();
    }
}
