package com.lti.flipfit.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

@Service
public class JwtService {

 private final SecretKey key;
 private final long expirationMs;

 public JwtService(
     @Value("${jwt.secret}") String secret,
     @Value("${jwt.expiration}") long expirationMs
 ) {
     this.key = Keys.hmacShaKeyFor(secret.getBytes());
     this.expirationMs = expirationMs;
 }

 @SuppressWarnings("deprecation")
public String generateToken(GymUser user, Map<String, Object> extraClaims) {
     long now = System.currentTimeMillis();

     Map<String, Object> claims = (extraClaims != null) ? new HashMap<>(extraClaims) : new HashMap<>();

        // Put GymUser.id in the JWT
        claims.put("id", user.getId());           // numeric claim
        claims.put("roleid", user.getRoleid());   // optional if useful
        // If you already map authorities, you can keep your "roles" claim too.

     return Jwts.builder()
             .claims(extraClaims)
             .subject(user.getUsername())
             .issuedAt(new Date(now))
             .expiration(new Date(now + expirationMs))
             .signWith(key,Jwts.SIG.HS256)
             .compact();
 }
 public String extractUsername(String token) {
     return parseAllClaims(token).getSubject();
 }

 public boolean isTokenValid(String token, String expectedUsername) {
     try {
         Claims claims = parseAllClaims(token);
         return expectedUsername.equals(claims.getSubject()) &&
                claims.getExpiration().after(new Date());
     } catch (JwtException | IllegalArgumentException e) {
         return false;
     }
 }

 private Claims parseAllClaims(String token) {
     return Jwts.parser().verifyWith(key).build()
             .parseSignedClaims(token)
             .getPayload();
 }
}
