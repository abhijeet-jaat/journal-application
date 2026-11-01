package com.thunder.journalApplication;
import com.mongodb.assertions.Assertions;
import com.thunder.journalApplication.entity.user;
import com.thunder.journalApplication.repository.userRepository;
import com.thunder.journalApplication.services.UserDetailsServicesIMPL;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserDetailsServicesIMPLTesl {
    @Autowired
    private UserDetailsServicesIMPL  userDetailsServices;

    @MockBean
    private userRepository userRepository;
    @Test
    void loadUserByUsernameTest() {
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn((user) User.builder()
                .username("ram")
                .password("ram")
                .roles("USER")
                .build());
    UserDetails user = userDetailsServices.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }
}
