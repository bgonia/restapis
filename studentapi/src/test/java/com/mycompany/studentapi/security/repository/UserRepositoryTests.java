package com.mycompany.studentapi.security.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.studentapi.repository.User;
import com.mycompany.studentapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testAddFirstUser(){
        User user1 = new User();
        user1.setUsername("namhm");
        user1.setRole("read");

        String password = "mhman";
        String encodedPassword = passwordEncoder.encode(password);

        user1.setPassword(encodedPassword);

        User savedUser = userRepository.save(user1);

        assertThat(savedUser).isNotNull();

    }

    @Test
    public void testAddSecondUser(){
        User user2 = new User();
        user2.setUsername("admin");
        user2.setRole("write");

        String password = "nimda";
        String encodedPassword = passwordEncoder.encode(password);
        user2.setPassword(encodedPassword);
        User savedUser = userRepository.save(user2);
        assertThat(savedUser).isNotNull();
    }

    @Test
    public void testFindUserNotFound(){
        Optional<User> findByUserName = userRepository.findByUsername("xxxx");
        assertThat(findByUserName).isNotPresent();
    }

    @Test
    public void testFindUserFound(){
        String username = "namhm";
        Optional<User> findByUserName = userRepository.findByUsername(username);
        assertThat(findByUserName).isPresent();

        User user = findByUserName.get();
        assertThat(user.getUsername()).isEqualTo(username);
    }


}
