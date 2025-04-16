package com.example.demo.controllers;

import com.example.demo.entities.UserEntity;
import com.example.demo.services.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserInterface userInterface;
    @GetMapping("/afficher")

    public String test(){
        return " Hey user";
    }
    @GetMapping("user")
    public ResponseEntity<Map<String,Object> >getUser(){
        Map<String,Object> response = new HashMap<>();
        List<String> users = new ArrayList<>();
        Set<String> userSet = new HashSet<>();
        userSet.add("John");
        userSet.add("John");
        userSet.add("Jane");
        userSet.add("Mary");
        users.add("John");
        users.add("John");
        users.add("Jane");
        users.add("Mary");
        response.put("status","success");
        response.put("data",userSet);

        return ResponseEntity.ok(response) ;
    }
    @PostMapping("add")
    public UserEntity addUser(@RequestBody UserEntity user){

        return userInterface.addUser(user);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id){
          userInterface.deleteUser(id);
    }
    @DeleteMapping("deleteuser")
    public void deleteUsers(@RequestParam("id")Long id){
        userInterface.deleteUser(id);
    }
    @PostMapping("saveall")
    public List<UserEntity> addListUsers(@RequestBody List<UserEntity> users){
        return userInterface.AddListUsers(users);
    }
    @PostMapping("addwithconfpassword")
    public String addUserWithConfPassword(@RequestBody UserEntity user){
        return userInterface.addUserWTCP(user);
    }
    @PostMapping("addWTUN")
    public String addUserWTUN(@RequestBody UserEntity user){
        return userInterface.adduserWTUN(user);
    }
   @PutMapping("updateuser/{id}")
    public UserEntity updateUser(@PathVariable("id")Long id,@RequestBody UserEntity user){
        return userInterface.UpdateUser(user,id);
   }
   @GetMapping("all")
    public List<UserEntity>getAllUsers(){
        return userInterface.getAllUsers();
   }
   @GetMapping("findbyid/{id}")
    public UserEntity getUserById(@PathVariable("id") Long id){
        return  userInterface.getUserById(id);
   }
   @GetMapping("findbyusername/{abc}")
    public UserEntity getUserByUsername(@PathVariable("abc") String u){
        return userInterface.getUserByUsername(u);

   }
   @GetMapping("getuserswt/{cle}")
    public List<UserEntity> getUsersSw(@PathVariable String cle){
        return userInterface.getUsersSWT(cle);
   }
   @GetMapping("getusersbyemaildomaine")
    public List<UserEntity> getUsersByEmaildomaine(@RequestParam String domaine){
        return userInterface.getUsersByEmail(domaine);
   }
}