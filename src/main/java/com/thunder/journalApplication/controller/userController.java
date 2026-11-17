package com.thunder.journalApplication.controller;
import com.thunder.journalApplication.Dto.UserDto;
import com.thunder.journalApplication.api.response.WeatherResponse;
import com.thunder.journalApplication.entity.user;
import com.thunder.journalApplication.repository.userRepository;
import com.thunder.journalApplication.services.WeatherServices;
import com.thunder.journalApplication.services.userServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name ="User APIs", description = "Read, Update, & Delete User")
public class userController {

    @Autowired
    private userServices UserServices;
    @Autowired
    private userRepository UserRepository;
    @Autowired
    private WeatherServices WeatherServices;

    @GetMapping
    @Operation(summary = "Get User info")
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = WeatherServices.getWeather("Mumbai");
        String greetings = "";
        if (weatherResponse != null) {
            greetings = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greetings ,  HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "Update User Details")
    public ResponseEntity<?> updateUser(@RequestBody UserDto User){

        user newUser = new user();
        newUser.setUserName(User.getUserName());
        newUser.setPassword(User.getPassword());
        newUser.setEmail(User.getEmail());
        newUser.setSentimentAnalysis(User.getSentimentAnalysis());

    Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
        user userInDb = UserServices.findByUserName(userName);
        if (userInDb != null) {
            userInDb.setUserName(newUser.getUserName());
            userInDb.setPassword(newUser.getPassword());
            userInDb.setEmail(newUser.getEmail());
            userInDb.setSentimentAnalysis(newUser.getSentimentAnalysis());
            UserServices.saveNewUser(userInDb);
        } return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Operation(summary = "Delete User Account")
    public ResponseEntity<?> deleteById(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        UserRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}  