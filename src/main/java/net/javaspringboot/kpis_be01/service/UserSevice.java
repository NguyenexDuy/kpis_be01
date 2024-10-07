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

    public User getUserByUsername(String username){
        return  userRepository.findByUsername(username).orElse(null);
    }

    public User SaveorUpdate(User object){
     return  userRepository.save(object);
    }
    public User getUserNameByUniqueName(String unique_name){
        return userRepository.getUserNameByUniqueName(unique_name);
    }
    public User getUserById(Long id){
        return  userRepository.findById(id).get();
    }



}
