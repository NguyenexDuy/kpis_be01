package net.javaspringboot.kpis_be01.controller;


import lombok.extern.slf4j.Slf4j;
import net.javaspringboot.kpis_be01.dto.response.ApiResponse;
import net.javaspringboot.kpis_be01.entity.Staffs;
import net.javaspringboot.kpis_be01.entity.User;
import net.javaspringboot.kpis_be01.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserSevice userService;

    @GetMapping
    List<User> getAllUser(){
        return  userService.getAllUser();
    }

    @GetMapping("/getUserInfomation")
     ApiResponse<User>  getUserInfomation(){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=userService.getUserByUsername(authentication.getName());
        log.info("Username:{}",authentication.getName());
        return ApiResponse.<User>builder()
                .code(1000)
                .result(user)
                .message("SUCCESS")
                .build();
    }
}
