package com.thunder.journalApplication.Services;

import com.thunder.journalApplication.services.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@SpringBootTest
public class EmailServiceTest {
        @Autowired
        EmailService emailService;
    @Disabled
        @Test
    public void testSendEmail() {
            emailService.sendEmail("" , "testing Email " , "hello hello hello");
        }
}
