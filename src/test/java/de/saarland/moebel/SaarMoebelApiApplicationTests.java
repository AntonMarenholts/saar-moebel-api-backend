package de.saarland.moebel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SaarMoebelApiApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void generateBcryptPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "YhT1r3xc8dL0aM5nBfj7sZ1gR2oU4pW3x"; // Ваш пароль из .env
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("Hashed Password: " + encodedPassword);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}
