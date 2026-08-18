package gym.ada.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtService jwtService;

    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http) throws Exception {

        JwtAuthenticationFilter jwtFilter =
                new JwtAuthenticationFilter(jwtService);

        http
        .csrf(csrf -> csrf.disable())

        .cors(cors -> {})

        .sessionManagement(session ->
            session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
            )
        )

            .authorizeHttpRequests(auth -> auth

            	    // ==========================================
            	    // PÚBLICO
            	    // ==========================================

            	    .requestMatchers(
            	        "/api/auth/**",
            	        "/swagger-ui/**",
            	        "/v3/api-docs/**"
            	    ).permitAll()


            	    // ==========================================
            	    // PRODUCTOS → PÚBLICO
            	    // ==========================================

            	    .requestMatchers(
            	        HttpMethod.GET,
            	        "/api/productos/listarProductoData",
            	        "/api/productos/listarActivos"
            	    ).permitAll()


            	    // ==========================================
            	    // CATEGORÍAS → PÚBLICO PARA CONSULTAR
            	    // ==========================================

            	    .requestMatchers(
            	        HttpMethod.GET,
            	        "/api/categorias/**"
            	    ).permitAll()


            	    // ==========================================
            	    // TALLAS → PÚBLICO PARA CONSULTAR
            	    // ==========================================

            	    .requestMatchers(
            	        HttpMethod.GET,
            	        "/api/tallas/**"
            	    ).permitAll()


            	    // ==========================================
            	    // IMÁGENES → PÚBLICO PARA CONSULTAR
            	    // ==========================================

            	    .requestMatchers(
            	        HttpMethod.GET,
            	        "/api/imagenes/**"
            	    ).permitAll()


            	    // ==========================================
            	    // PRODUCTOS → SOLO ADMIN
            	    // ==========================================

            	    .requestMatchers(
            	        HttpMethod.POST,
            	        "/api/productos/**"
            	    ).hasRole("ADMIN")

            	    .requestMatchers(
            	        HttpMethod.PATCH,
            	        "/api/productos/**"
            	    ).hasRole("ADMIN")

            	    .requestMatchers(
            	        HttpMethod.DELETE,
            	        "/api/productos/**"
            	    ).hasRole("ADMIN")


            	    // ==========================================
            	    // CATEGORÍAS → SOLO ADMIN
            	    // ==========================================

            	    .requestMatchers(
            	        HttpMethod.POST,
            	        "/api/categorias/**"
            	    ).hasRole("ADMIN")

            	    .requestMatchers(
            	        HttpMethod.DELETE,
            	        "/api/categorias/**"
            	    ).hasRole("ADMIN")


            	    // ==========================================
            	    // TALLAS → SOLO ADMIN
            	    // ==========================================

            	    .requestMatchers(
            	        HttpMethod.POST,
            	        "/api/tallas/**"
            	    ).hasRole("ADMIN")

            	    .requestMatchers(
            	        HttpMethod.DELETE,
            	        "/api/tallas/**"
            	    ).hasRole("ADMIN")


            	    // ==========================================
            	    // IMÁGENES → SOLO ADMIN PARA MODIFICAR
            	    // ==========================================

            	    .requestMatchers(
            	        HttpMethod.POST,
            	        "/api/imagenes/**"
            	    ).hasRole("ADMIN")

            	    .requestMatchers(
            	        HttpMethod.DELETE,
            	        "/api/imagenes/**"
            	    ).hasRole("ADMIN")


            	    // ==========================================
            	    // TODO LO DEMÁS
            	    // ==========================================

            	    .anyRequest().authenticated()
            	)

            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}