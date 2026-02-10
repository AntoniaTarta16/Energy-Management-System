package com.sd.User.Management.controller;

import com.sd.User.Management.dto.UserDTO;
import com.sd.User.Management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://angular.localhost", allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(
                userService.findAll(),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new-user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        return new ResponseEntity<>(
                userService.save(userDTO, request),
                HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteUser(@PathVariable("name") String name, HttpServletRequest request) {
        userService.deleteByName(name, request);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{name}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("name") String name, @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(
                userService.update(name, userDTO),
                HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<UserDTO> getLoggedUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = (String) authentication.getPrincipal();

        return new ResponseEntity<>(
                userService.findByName(name),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{name}")
    public ResponseEntity<UserDTO> getUserByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(
                userService.findByName(name),
                HttpStatus.OK
        );
    }

}
