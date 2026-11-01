package com.thunder.journalApplication.controller;
import com.thunder.journalApplication.api.response.WeatherResponse;
import com.thunder.journalApplication.entity.user;
import com.thunder.journalApplication.repository.userRepository;
import com.thunder.journalApplication.services.WeatherServices;
import com.thunder.journalApplication.services.userServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private userServices UserServices;
    @Autowired
    private userRepository UserRepository;
    @Autowired
    private WeatherServices WeatherServices;

    @GetMapping
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
    public ResponseEntity<?> updateUser(@RequestBody user User){
    Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
        user userInDb = UserServices.findByUserName(userName);
        if (userInDb != null) {
            userInDb.setUserName(User.getUserName());
            userInDb.setPassword(User.getPassword());
            UserServices.saveNewUser(userInDb);
        } return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        UserRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}  