package com.sd.User.Management.service;

import com.sd.User.Management.dto.UserDTO;
import com.sd.User.Management.entity.UserEntity;
import com.sd.User.Management.exception.ExceptionCode;
import com.sd.User.Management.exception.NotFoundException;
import com.sd.User.Management.mapper.UserMapper;
import com.sd.User.Management.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    @Value(value = "${device.management.url}")
    private String deviceManagementUrl;

    @Override
    @Transactional
    public UserDTO save(UserDTO userDTO, HttpServletRequest request) {
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(hashedPassword);

        UserEntity user = userMapper.toUser(userDTO);
        UserEntity userAdded = userRepository.save(user);
        sendUserToPersonReference(userAdded.getIdUser(), request);

        return userMapper.toUserDTO(userAdded);
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserEntity> users = userRepository.findAll();
        return userMapper.toUserDTOs(users);
    }

    @Override
    @Transactional
    public void deleteByName(String name, HttpServletRequest request) {
        UserEntity user = userRepository.findByName(name).orElseThrow(() -> new RuntimeException(String.format(ExceptionCode.ERR001_USER_NOT_FOUND.getMessage(), name)));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String jwtToken = extractJwtToken(request);

        if (jwtToken != null) {
            headers.set("Authorization", "Bearer " + jwtToken);
        } else {
            throw new RuntimeException("JWT token is missing for the request.");
        }

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(deviceManagementUrl + "/" + user.getIdUser(), HttpMethod.DELETE, requestEntity, Void.class);
        } catch (HttpStatusCodeException ex) {
            throw new RuntimeException("Transaction rolled back due to external service error.", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Transaction rolled back due to an unexpected error.", ex);
        }

        userRepository.delete(user);
    }

    @Override
    public UserDTO update(String name, UserDTO userDTO) {
        UserEntity user = userRepository.findByName(name).orElseThrow(() -> new RuntimeException(String.format(ExceptionCode.ERR001_USER_NOT_FOUND.getMessage(), name)));
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getRole() != null) {
            user.setRole(userDTO.getRole());
        }
        /*Do not update password*/
        //userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toUserDTO(updatedUser);
    }

    @Override
    public UserDTO findByName(String name) {
        return userRepository.findByName(name)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new NotFoundException(String
                        .format(ExceptionCode.ERR001_USER_NOT_FOUND.getMessage(), name)));
    }

    public void sendUserToPersonReference(UUID idUser, HttpServletRequest request) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("idUser", idUser.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String jwtToken = extractJwtToken(request);

        if (jwtToken != null) {
            headers.set("Authorization", "Bearer " + jwtToken);
        } else {
            throw new RuntimeException("JWT token is missing for the request.");
        }

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(deviceManagementUrl, HttpMethod.POST, requestEntity, Void.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to add user in PersonReference!");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Transaction rolled back.", ex);
        }
    }

    private String extractJwtToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // extract the token
        }
        return null;
    }
}
