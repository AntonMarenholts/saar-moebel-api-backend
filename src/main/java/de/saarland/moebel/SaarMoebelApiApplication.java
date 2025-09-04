package de.saarland.moebel;

import de.saarland.moebel.model.ERole;
import de.saarland.moebel.model.User;
import de.saarland.moebel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SaarMoebelApiApplication {

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    public static void main(String[] args) {
        SpringApplication.run(SaarMoebelApiApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        return args -> {
            // Создание администратора, если его нет
            if (!userRepository.existsByUsername(adminUsername)) {
                User adminUser = new User();
                adminUser.setUsername(adminUsername);
                adminUser.setEmail(adminEmail);
                adminUser.setPassword(passwordEncoder.encode(adminPassword));
                adminUser.setRole(ERole.ROLE_ADMIN);
                userRepository.save(adminUser);
                System.out.println(">>> Default administrator created from .env file!");
            }
        };
    }
}

