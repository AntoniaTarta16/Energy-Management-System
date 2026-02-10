package com.sd.User.Management.mapper;

import com.sd.User.Management.dto.UserDTO;
import com.sd.User.Management.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserDTO toUserDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .idUser(userEntity.getIdUser())
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .build();
    }

    public UserEntity toUser(UserDTO userDTO) {
        return UserEntity.builder()
                .idUser(userDTO.getIdUser())
                .name(userDTO.getName())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .build();
    }

    public List<UserDTO> toUserDTOs(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(this::toUserDTO)
                .toList();
    }
}
