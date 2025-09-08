package de.saarland.moebel.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    private final RestTemplate restTemplate;
    private final String supabaseUrl;
    private final String serviceKey;

    public FileUploadController(
            @Value("${supabase.url}") String supabaseUrl,
            @Value("${supabase.service_key}") String supabaseServiceKey) {
        this.supabaseUrl = supabaseUrl;
        this.serviceKey = supabaseServiceKey;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/image")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "File must not be empty"));
        }

        String bucketName = "product-images";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String uploadPath = "/storage/v1/object/" + bucketName + "/" + fileName;
        String fullUrl = supabaseUrl + uploadPath;


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + serviceKey);
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));

        headers.set("apikey", serviceKey);

        try {
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);


            ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String publicUrl = supabaseUrl + uploadPath;
                return ResponseEntity.ok(Map.of("imageUrl", publicUrl));
            } else {

                return ResponseEntity.status(response.getStatusCode()).body(Map.of("error", "Supabase error: " + response.getBody()));
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Could not read file bytes: " + e.getMessage()));
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "Error uploading file: " + e.getMessage()));
        }
    }
}