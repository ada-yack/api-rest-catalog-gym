package gym.ada.api.service.implement;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gym.ada.api.dto.Auth.LoginResponseDto;
import gym.ada.api.dto.UsuarioDto;
import gym.ada.api.dto.Auth.LoginUsuarioDto;
import gym.ada.api.dto.Auth.RegistroUsuarioDto;
import gym.ada.api.enums.Rol;
import gym.ada.api.model.Usuario;
import gym.ada.api.repository.IUsuarioRepository;
import gym.ada.api.security.JwtService;

import com.google.firebase.auth.FirebaseToken;
import gym.ada.api.security.firebase.FirebaseService;

@Service
public class UsuarioServiceImplement {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final FirebaseService firebaseService;


    public UsuarioServiceImplement(
            IUsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            FirebaseService firebaseService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.firebaseService = firebaseService;
    }

    // ==========================================
    // REGISTRAR
    // ==========================================

    public UsuarioDto registrar(RegistroUsuarioDto dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException(
                    "Ya existe un usuario con ese correo");
        }

        Usuario usuario = new Usuario();

        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());

        // HASH DE CONTRASEÑA
        usuario.setPasswordHash(
                passwordEncoder.encode(dto.getPassword())
        );

        usuario.setRol(Rol.CLIENTE);
        usuario.setActivo(true);

        Usuario guardado = usuarioRepository.save(usuario);

        return convertirAUsuarioDto(guardado);
    }

    
    public LoginResponseDto login(LoginUsuarioDto dto) {

        Usuario usuario = usuarioRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Credenciales incorrectas")
                );

        if (!usuario.isActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        boolean passwordCorrecta =
                passwordEncoder.matches(
                        dto.getPassword(),
                        usuario.getPasswordHash()
                );

        if (!passwordCorrecta) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtService.generarToken(
                usuario.getEmail(),
                usuario.getRol()
        );

        UsuarioDto usuarioDto =
                convertirAUsuarioDto(usuario);

        return new LoginResponseDto(
                token,
                usuarioDto
        );
    }
    

    // ==========================================
    // BUSCAR POR EMAIL
    // ==========================================

    public Usuario buscarPorEmail(String email) {

        return usuarioRepository.findByEmail(email)
                .orElse(null);
    }


    // ==========================================
    // LISTAR
    // ==========================================

    public List<UsuarioDto> listarTodos() {

        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirAUsuarioDto)
                .toList();
    }


    // ==========================================
    // BUSCAR POR ID
    // ==========================================

    public UsuarioDto buscarPorId(Long id) {

        Usuario usuario = usuarioRepository
                .findById(id)
                .orElse(null);

        if (usuario == null) {
            return null;
        }

        return convertirAUsuarioDto(usuario);
    }


    // ==========================================
    // CONVERTIR A DTO
    // ==========================================

    private UsuarioDto convertirAUsuarioDto(Usuario usuario) {

        UsuarioDto dto = new UsuarioDto();

        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setActivo(usuario.isActivo());
        dto.setRol(usuario.getRol());
        dto.setAvatarUrl(usuario.getAvatarUrl());

        return dto;
    }
    
    public LoginResponseDto loginGoogle(String token) {

        // ==========================================
        // 1. VERIFICAR TOKEN CON FIREBASE
        // ==========================================

        FirebaseToken firebaseToken =
                firebaseService.verificarToken(token);


        // ==========================================
        // 2. DATOS DE FIREBASE
        // ==========================================

        String email = firebaseToken.getEmail();
        String nombre = firebaseToken.getName();
        String avatarUrl = firebaseToken.getPicture();


        // ==========================================
        // 3. BUSCAR USUARIO EN MYSQL
        // ==========================================

        Usuario usuario =
                usuarioRepository.findByEmail(email)
                        .orElse(null);


        // ==========================================
        // 4. SI NO EXISTE → CREAR CLIENTE
        // ==========================================

        if (usuario == null) {

            usuario = new Usuario();

            usuario.setNombre(nombre);
            usuario.setEmail(email);

            // Google maneja la contraseña
            usuario.setPasswordHash(null);

            usuario.setActivo(true);

            // Todo usuario nuevo de Google será CLIENTE
            usuario.setRol(Rol.CLIENTE);

            usuario.setAvatarUrl(avatarUrl);

            usuario = usuarioRepository.save(usuario);
        }


        // ==========================================
        // 5. VERIFICAR QUE ESTÉ ACTIVO
        // ==========================================

        if (!usuario.isActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }


        // ==========================================
        // 6. GENERAR NUESTRO JWT
        // ==========================================

        String jwt =
                jwtService.generarToken(
                        usuario.getEmail(),
                        usuario.getRol()
                );


        // ==========================================
        // 7. CONVERTIR USUARIO A DTO
        // ==========================================

        UsuarioDto usuarioDto =
                convertirAUsuarioDto(usuario);


        // ==========================================
        // 8. DEVOLVER LA MISMA RESPUESTA
        // ==========================================

        return new LoginResponseDto(
                jwt,
                usuarioDto
        );
    }
}