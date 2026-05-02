package cl.smartlogix.ms_usuarios.dto;

import lombok.Data;

@Data
public class UsuarioRequest {
    private String email;
    private String password;
    private String nombre;
    private String tipoPerfil; // "ADMIN" o "PYME"
}