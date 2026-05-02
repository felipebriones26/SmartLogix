package cl.smartlogix.api_gateway.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
 
@Component
public class JwtUtil {

    private final String SECRET_KEY = "SmartLogixSecretKeyParaDesarrolloFullstack2026!";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public void validateToken(final String token) {
        // Esto lanzará una excepción si el token expiró o fue adulterado
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}