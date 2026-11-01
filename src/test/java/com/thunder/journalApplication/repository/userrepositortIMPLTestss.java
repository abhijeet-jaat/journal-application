package com.thunder.journalApplication.repository;

import com.thunder.journalApplication.entity.user;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class userrepositortIMPLTestss {

    @Autowired
    private UserRepositoryIMPL userRepository;

    @Test
    public void findAllUsers() {
        userRepository.getUserForSA();
    }

}
