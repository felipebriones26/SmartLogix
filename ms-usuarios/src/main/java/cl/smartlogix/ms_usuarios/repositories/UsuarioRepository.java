package cl.smartlogix.ms_usuarios.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.smartlogix.ms_usuarios.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Spring crea automáticamente la query "SELECT * FROM usuarios WHERE email = ?"
    Optional<Usuario> findByEmail(String email);
}
