package com.sd.User.Management.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd.User.Management.dto.LoginDTO;
import com.sd.User.Management.exception.ExceptionBody;
import com.sd.User.Management.security.util.JwtUtil;
import com.sd.User.Management.security.util.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;

    public LoginFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        this.objectMapper = objectMapper;
        this.setAuthenticationManager(authenticationManager);
        this.setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            LoginDTO authenticationRequest = objectMapper.readValue(
                    request.getInputStream(),
                    LoginDTO.class
            );
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getName(),
                    authenticationRequest.getPassword()
            );

            return super.getAuthenticationManager().authenticate(authentication);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        try {
            String accessToken = JwtUtil.generateJwtToken(
                    ((User) authResult.getPrincipal()).getUsername(),
                    authResult.getAuthorities()
            );

            // Adăugăm token-ul în corpul răspunsului
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.OK.value());

            // Creăm un obiect JSON cu token-ul JWT și îl trimitem în răspuns
            String jsonResponse = "{\"jwtToken\": \"" + accessToken + "\"}";

            // Trimitem răspunsul cu token-ul în corp
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            // Logăm eroarea
            log.error("IOException during authentication response: {}", e.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        objectMapper.writeValue(response.getWriter(), new ExceptionBody(failed.getMessage()));
    }
}
