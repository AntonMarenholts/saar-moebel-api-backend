package de.saarland.moebel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
// ++ ДОБАВЬТЕ ЭТИ ИМПОРТЫ ++
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
// ++ ДОБАВЬТЕ ЭТИ ДВЕ АННОТАЦИИ ++
// Явно указываем Spring, где искать все ваши компоненты, сервисы и репозитории.

@EnableJpaRepositories(basePackages = {"de.saarland.moebel.repository"})
public class SaarMoebelApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaarMoebelApiApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}