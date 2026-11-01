package com.thunder.journalApplication.services;
import com.thunder.journalApplication.entity.user;
import com.thunder.journalApplication.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.List;

@Component
public class userServices {

    @Autowired
    private userRepository UserRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(user User) {
        User.setPassword(passwordEncoder.encode(User.getPassword()));
        User.setRoles(Arrays.asList("USER"));
        UserRepository.save(User);
    }
    public void saveAdmin(user User){
        User.setPassword(passwordEncoder.encode(User.getPassword()));
        User.setRoles(Arrays.asList("USER", "ADMIN"));
        UserRepository.save(User);

    }
    public void saveUser(user User){
        UserRepository.save(User);
    }
    public List<user> getAll(){
        return UserRepository.findAll();
    }

    public user findByUserName(String userName){
        return UserRepository.findByUserName(userName);
    }
}
