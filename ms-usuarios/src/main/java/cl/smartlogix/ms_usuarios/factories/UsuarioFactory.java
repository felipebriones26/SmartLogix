package cl.smartlogix.ms_usuarios.factories;

import org.springframework.stereotype.Component;

import cl.smartlogix.ms_usuarios.model.AdminGlobal;
import cl.smartlogix.ms_usuarios.model.OperadorPyme;
import cl.smartlogix.ms_usuarios.model.Usuario;
@Component
public class UsuarioFactory {

    public Usuario crearUsuario(String tipoPerfil) {
        if (tipoPerfil == null) {
            throw new IllegalArgumentException("El tipo de perfil no puede ser nulo");
        }

        return switch (tipoPerfil.toUpperCase()) {
            case "ADMIN" -> new AdminGlobal();
            case "PYME" -> new OperadorPyme();
            default -> throw new IllegalArgumentException("Tipo de perfil desconocido: " + tipoPerfil);
        };
    }
}
