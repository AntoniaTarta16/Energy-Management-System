package com.sd.User.Management.service;

import com.sd.User.Management.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {
    UserDTO save(UserDTO userDTO, HttpServletRequest request);

    List<UserDTO> findAll();

    void deleteByName(String name, HttpServletRequest request);

    UserDTO update(String name, UserDTO userDTO);

    UserDTO findByName(String name);
}
