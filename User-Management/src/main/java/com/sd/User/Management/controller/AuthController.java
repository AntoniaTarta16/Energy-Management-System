package com.sd.User.Management.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://angular.localhost", allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<Void> login() {
        log.info("Login request detected...");
        return ResponseEntity.ok().build();
    }
}
