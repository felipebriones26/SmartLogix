package cl.smartlogix.ms_usuarios.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.smartlogix.ms_usuarios.dto.LoginRequest;
import cl.smartlogix.ms_usuarios.dto.UsuarioRequest;
import cl.smartlogix.ms_usuarios.model.Usuario;
import cl.smartlogix.ms_usuarios.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody UsuarioRequest request) {
        try {
            Usuario usuarioCreado = usuarioService.registrarUsuario(
                    request.getEmail(),
                    request.getPassword(),
                    request.getNombre(),
                    request.getTipoPerfil()
            );
            return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = usuarioService.login(request.getEmail(), request.getPassword());
            // Devolvemos el token en un formato JSON estándar
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}