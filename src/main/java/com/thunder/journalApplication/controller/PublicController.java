package com.thunder.journalApplication.controller;

import com.thunder.journalApplication.entity.user;
import com.thunder.journalApplication.services.UserDetailsServicesIMPL;
import com.thunder.journalApplication.services.userServices;
import com.thunder.journalApplication.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/public")
@Tag(name="Public APIs")
public class PublicController {

    @Autowired
    private userServices UserServices;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServicesIMPL userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping("/health-check")
    @Operation(summary = "Check Server Connection")
    public String healthcheck(){
    return "ok";
}

    @PostMapping("/signup")
    @Operation(summary = "Create Account")
    public void signup(@RequestBody user User){
        UserServices.saveNewUser(User);
    }
    @PostMapping("/login")
    @Operation(summary = "Login Account, Result(JWT Token)")
    public ResponseEntity<String> login(@RequestBody user User){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(User.getUserName(),User.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(User.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt , HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occured while createAuthenticationToken ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

