package cl.smartlogix.ms_usuarios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.smartlogix.ms_usuarios.factories.UsuarioFactory;
import cl.smartlogix.ms_usuarios.model.Usuario;
import cl.smartlogix.ms_usuarios.repositories.UsuarioRepository;
import cl.smartlogix.ms_usuarios.utils.JwtUtil;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioFactory usuarioFactory;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // Agregamos la utilidad de JWT

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, 
                          UsuarioFactory usuarioFactory, 
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioFactory = usuarioFactory;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil; // Lo inicializamos
    }

    public Usuario registrarUsuario(String email, String password, String nombre, String tipoPerfil) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }

        Usuario nuevoUsuario = usuarioFactory.crearUsuario(tipoPerfil);
        nuevoUsuario.setEmail(email);
        
        // Encriptamos la contraseña antes de setearla
        String passwordEncriptada = passwordEncoder.encode(password);
        nuevoUsuario.setPassword(passwordEncriptada); 
        
        nuevoUsuario.setNombre(nombre);

        return usuarioRepository.save(nuevoUsuario);
    }

    public String login(String email, String passwordRaw) {
        // 1. Buscamos el usuario por email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        // 2. Comparamos la contraseña en texto plano con el hash de PostgreSQL
        if (!passwordEncoder.matches(passwordRaw, usuario.getPassword())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        // 3. Si todo está correcto, generamos y retornamos el JWT
        String tipoPerfil = usuario.getClass().getSimpleName().toUpperCase(); 
        
        return jwtUtil.generateToken(usuario.getEmail(), tipoPerfil);
    }
}