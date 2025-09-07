package de.saarland.moebel.controller;

import de.saarland.moebel.dto.auth.JwtResponse;
import de.saarland.moebel.dto.auth.LoginRequest;
import de.saarland.moebel.dto.auth.MessageResponse;
import de.saarland.moebel.dto.auth.SignupRequest;
import de.saarland.moebel.model.ERole;
import de.saarland.moebel.model.User;
import de.saarland.moebel.repository.UserRepository;
import de.saarland.moebel.security.jwt.JwtUtils;
import de.saarland.moebel.security.services.UserDetailsImpl;
import de.saarland.moebel.service.EmailService;
import de.saarland.moebel.service.RecaptchaService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RecaptchaService recaptchaService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils, EmailService emailService, RecaptchaService recaptchaService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
        this.recaptchaService = recaptchaService;
    }

    @PostMapping("/signin")
    @RateLimiter(name = "loginLimiter")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                ERole.ROLE_USER);

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    // ++ НАЧАЛО ИЗМЕНЕНИЙ: Добавляем два новых эндпоинта ++
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = UUID.randomUUID().toString();
            user.setResetPasswordToken(token);
            user.setTokenCreationDate(ZonedDateTime.now());
            userRepository.save(user);

            // Ссылка будет вести на фронтенд, где мы создадим страницу для сброса
            String resetLink = frontendUrl + "/reset-password?token=" + token;
            emailService.sendPasswordResetEmail(user, resetLink);
        }

        // Мы всегда возвращаем один и тот же ответ, чтобы не раскрывать, есть ли email в базе
        return ResponseEntity.ok(new MessageResponse("If your email is in our system, you will receive a password reset link."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token, @RequestBody Map<String, String> request) {
        String newPassword = request.get("password");
        if (newPassword == null || newPassword.length() < 6) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Password must be at least 6 characters long!"));
        }

        Optional<User> userOptional = userRepository.findByResetPasswordToken(token);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid token!"));
        }

        User user = userOptional.get();

        // Проверяем, что токен не истек (например, 24 часа)
        if (user.getTokenCreationDate() != null && user.getTokenCreationDate().plusHours(24).isBefore(ZonedDateTime.now())) {
            user.setResetPasswordToken(null);
            user.setTokenCreationDate(null);
            userRepository.save(user);
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Token expired!"));
        }

        user.setPassword(encoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setTokenCreationDate(null);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Password has been reset successfully."));
    }

}