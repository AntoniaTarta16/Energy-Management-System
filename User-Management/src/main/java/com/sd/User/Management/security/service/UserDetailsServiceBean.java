package com.sd.User.Management.security.service;

import com.sd.User.Management.entity.UserEntity;
import com.sd.User.Management.exception.ExceptionCode;
import com.sd.User.Management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class UserDetailsServiceBean implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository
                .findByName(name)
                .map(this::getUserDetails)
                .orElseThrow(() -> new BadCredentialsException(ExceptionCode.ERR099_INVALID_CREDENTIALS.getMessage()));
    }

    private UserDetails getUserDetails(UserEntity userEntity) {
        return User.builder()
                .username(userEntity.getName())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().name())
                .build();
    }
}
