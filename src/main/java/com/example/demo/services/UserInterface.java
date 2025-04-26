package com.example.demo.services;

import com.example.demo.entities.UserEntity;

import java.util.List;

public interface UserInterface {
   UserEntity addUser(UserEntity user);
   void deleteUser(Long id);
   List<UserEntity> AddListUsers(List<UserEntity> users);
   String addUserWTCP(UserEntity user);
   String adduserWTUN(UserEntity user);
   UserEntity UpdateUser(UserEntity user,Long id);
   List<UserEntity> getAllUsers();
   UserEntity getUserById(Long id);
   UserEntity getUserByUsername(String username);
   List<UserEntity> getUsersSWT(String un);
   List<UserEntity> getUsersByEmail(String un);
}
