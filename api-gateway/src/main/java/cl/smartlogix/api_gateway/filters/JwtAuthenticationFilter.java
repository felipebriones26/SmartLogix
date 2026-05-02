package cl.smartlogix.api_gateway.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import cl.smartlogix.api_gateway.utils.JwtUtil;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 1. Dejamos pasar las rutas públicas (Login y Registro)
        if (path.contains("/api/usuarios/login") || path.contains("/api/usuarios/registrar")) {
            return chain.filter(exchange);
        }

        // 2. Verificamos si existe el encabezado "Authorization"
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 3. Verificamos que tenga el formato correcto ("Bearer <token>")
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                // 4. Validamos el token matemáticamente
                jwtUtil.validateToken(token);
            } catch (Exception e) {
                // Si la firma no coincide o expiró, rechazamos con 401
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 5. Si todo está en orden, dejamos que la petición continúe hacia el microservicio
        return chain.filter(exchange);
    }

    // Le damos alta prioridad para que sea lo primero que se ejecute
    @Override
    public int getOrder() {
        return -1;
    }
}