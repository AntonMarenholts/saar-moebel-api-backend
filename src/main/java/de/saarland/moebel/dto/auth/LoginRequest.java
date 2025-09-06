package de.saarland.moebel.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    // Для reCAPTCHA, как в проекте Events
    private String recaptchaToken;
}