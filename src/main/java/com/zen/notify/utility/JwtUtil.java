package com.zen.notify.utility;

import io.jsonwebtoken.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.zen.notify.filters.ZenUserDetails;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "G9MzZjTz2Mm89rL1RL9Jb9vP0rf4pKukwH3XzOvQduE="; // use env variable in production
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
    	System.out.println("Validate token");
    	System.out.println("username" + username);
    	System.out.println("token" + token);
        return (extractUsername(token).equals(username) && !isTokenExpired(token));
    }
    
    public String generateRefreshToken(ZenUserDetails userDetails) {
    	System.out.println("Validate token");
    	System.out.println("username" + userDetails.getUsername());
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            //.claim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7 days
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }
}
