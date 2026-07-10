package com.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import com.auth.util.AuthConstants;


@Component
public class JwtUtil {

    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + AuthConstants.DAY_TIME_IN_MILLIS))
                .signWith(
                        Keys.hmacShaKeyFor(
                                AuthConstants.SECRET.getBytes()))
                .compact();
    }
}
