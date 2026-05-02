package cl.smartlogix.ms_usuarios.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    
    // Clave estática de al menos 256 bits (32 caracteres)
    private final String SECRET_KEY = "SmartLogixSecretKeyParaDesarrolloFullstack2026!";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    
    private final long EXPIRATION_TIME = 86400000;

    public String generateToken(String email, String tipoPerfil) {
        return Jwts.builder()
                .setSubject(email)
                .claim("perfil", tipoPerfil)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}