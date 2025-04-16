package com.example.demo.serviceImplement;

import com.example.demo.entities.UserEntity;
import com.example.demo.repository.UserRepo;
import com.example.demo.services.UserInterface;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserImplement implements UserInterface {
    @Autowired
    UserRepo userRepo;
    @Override
    public UserEntity addUser(UserEntity user) {

        return userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public List<UserEntity> AddListUsers(List<UserEntity> users) {
        return userRepo.saveAll(users);

    }

    @Override
    public String addUserWTCP(UserEntity user) {
        String ch ="";
        if(user.getPassword().equals(user.getConfirmPassword())){
            userRepo.save(user);
            ch="user added successfully";
        }
        else ch="user password does not match";

        return ch;
    }

    @Override
    public String adduserWTUN(UserEntity user) {
        String ch ="";
        if(userRepo.existsByUsername(user.getUsername())){
            ch="user already exists";
        }
        else {
            userRepo.save(user);
            ch="user added successfully";
        }
        return ch;
    }

    @Override
    public UserEntity UpdateUser(UserEntity user, Long id) {
        UserEntity u = getUserById(id);
           u.setFirstName(user.getFirstName());
           u.setLastName(user.getLastName());
        return userRepo.save(u);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<UserEntity> getUsersSWT(String un) {
        return userRepo.getUserSW(un);
    }

    @Override
    public List<UserEntity> getUsersByEmail(String un) {
        return userRepo.getUsersByEmail(un);
    }


}
