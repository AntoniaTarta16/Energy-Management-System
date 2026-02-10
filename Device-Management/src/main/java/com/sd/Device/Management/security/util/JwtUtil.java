package com.sd.Device.Management.security.util;


import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.Properties;

@Slf4j
@UtilityClass
public class JwtUtil {

    public static String secretKey;
    public static Integer tokenExpirationDays;

    static {
        try (InputStream inputStream = JwtUtil.class.getResourceAsStream("/application.yaml")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            JwtUtil.secretKey = properties.getProperty("secret-key");
            JwtUtil.tokenExpirationDays = Integer.parseInt(properties.getProperty("token-expiration-days"));
        } catch (IOException | NumberFormatException e) {
            log.error(e.getMessage());
        }
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization") == null
                ? null
                : request.getHeader("Authorization").split("Bearer ")[1];
    }


    public Key getSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}
