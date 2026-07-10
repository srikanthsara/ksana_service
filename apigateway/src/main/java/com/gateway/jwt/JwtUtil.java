package com.gateway.jwt;

import com.gateway.utils.ApiGatewayConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {


    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()
                        + ApiGatewayConstants.DAY_TIME_IN_SEC))
                .signWith(Keys.hmacShaKeyFor(ApiGatewayConstants.SECRET.getBytes()))
                .compact();
    }
}
