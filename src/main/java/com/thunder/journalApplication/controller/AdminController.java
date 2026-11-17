package com.thunder.journalApplication.controller;

import com.thunder.journalApplication.Dto.UserDto;
import com.thunder.journalApplication.cache.AppCache;
import com.thunder.journalApplication.entity.user;
import com.thunder.journalApplication.services.userServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name= "Admin APIs")
public class AdminController {
    @Autowired
    userServices UserServices;
    @Autowired
    AppCache appCache;

    @GetMapping("/all-users")
    @Operation(summary = "Get All Users")
    public ResponseEntity<List<user>> getAllUsers() {
        List<user> all = UserServices.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    @Operation(summary = "Create Admin-USER")
    public void createUser(@RequestBody UserDto User) {
        user newUser = new user();
        newUser.setEmail(User.getEmail());
        newUser.setPassword(User.getPassword());
        newUser.setUserName(User.getUserName());
        newUser.setSentimentAnalysis(User.getSentimentAnalysis());
        UserServices.saveAdmin(newUser);
    }


    @GetMapping("/clear-app-cache")
    @Operation(summary = "Clear App Cache")
    public void clearAppCache() {
        appCache.init();
    }
}