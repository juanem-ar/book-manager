package com.complejolapasionaria.reservation.security.service.impl;

import com.complejolapasionaria.reservation.model.User;
import com.complejolapasionaria.reservation.repository.IUserRepository;
import com.complejolapasionaria.reservation.security.service.IJwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtilsImpl implements IJwtUtils {
    private String SECRET_KEY = "secret";
    private IUserRepository userRepository;
    public JwtUtilsImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String extractUsername (String token){ return extractClaim(token, Claims::getSubject);}

    @Override
    public boolean isEnable(String token) {
        return Boolean.parseBoolean(extractAllClaims(token).get("enable").toString());
    }

    @Override
    public Date extractExpiration(String token){ return extractClaim(token, Claims::getExpiration);}
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    @Override
    public String getJwt(String token){
        String jwt = token.substring(7);
        return jwt;
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token){ return extractExpiration(token).before(new Date());}

    @Override
    public String generateToken(UserDetails userDetails){
        User user = userRepository.findByEmail(userDetails.getUsername());
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId",user.getId());
        claims.put("enable", user.isEnabled());
        return createToken(claims, userDetails.getUsername());
    }
    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 100 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    @Override
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public Long extractUserId(String token) {
        return Long.valueOf(extractAllClaims(token).get("userId").toString());
    }
}
