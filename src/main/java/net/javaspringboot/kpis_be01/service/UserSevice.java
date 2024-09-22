package net.javaspringboot.kpis_be01.service;

import net.javaspringboot.kpis_be01.entity.User;
import net.javaspringboot.kpis_be01.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSevice {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser(){
        return  userRepository.findAll();
    }
}
