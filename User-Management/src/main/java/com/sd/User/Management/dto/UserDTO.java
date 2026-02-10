package com.sd.User.Management.dto;

import com.sd.User.Management.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID idUser;

    private String name;

    private String password;

    private Role role;
}

