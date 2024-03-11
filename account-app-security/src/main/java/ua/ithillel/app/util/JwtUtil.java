package ua.ithillel.app.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ua.ithillel.app.service.UserDetailsImpl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@PropertySource("classpath:security.properties")
public class JwtUtil {
    public static final long JWT_TOKEN_VALIDITY = 24 * 1000 * 60 * 60;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public Claims getClaimsFromToken(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build();

        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String token){
        List<LinkedHashMap<String, String>> roles = getClaimsFromToken(token).get("roles", List.class);
        return roles.stream()
                .flatMap(map -> map.values().stream())
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaimsFromToken(token).getId());
    }

    public String generateToken(UserDetailsImpl userDetails) {
        return Jwts.builder()
                .setId(userDetails.getUserId() + "")
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .claim("roles", userDetails.getAuthorities())
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }
}
