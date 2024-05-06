package com.kanban.kanban.auth;

import com.kanban.kanban.entity.UserEntity;
import com.sun.source.util.JavacTask;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.util.Date;

@Component
public class JwtUtil {

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    private final String secretKey = "laterDude";

    public String generateToken(UserEntity user){

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer("bhxnusingh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token) throws AuthenticationException {

        try{

            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            // if it does not throw error token was verified

            return true;
        } catch (Exception e){

            throw new RuntimeException(e.getMessage());
        }
    }

    public String getUsernameFromToken(String token){

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
