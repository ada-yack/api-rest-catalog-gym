package gym.ada.api.security;

import java.util.List;

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

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
            "http://localhost:4200",
            "https://gyselmood.netlify.app"
        ));

        configuration.setAllowedMethods(List.of(
            "GET",
            "POST",
            "PUT",
            "PATCH",
            "DELETE",
            "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http) throws Exception {

        JwtAuthenticationFilter jwtFilter =
                new JwtAuthenticationFilter(jwtService);

        http
            .csrf(csrf -> csrf.disable())

            .cors(cors ->
                cors.configurationSource(corsConfigurationSource())
            )

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

            		.requestMatchers(
            			    "/api/auth/**",
            			    "/api/prueba-login/**",
            			    "/api/prueba-auth/**",
            			    "/api/diagnostico/**",
            			    "/swagger-ui/**",
            			    "/v3/api-docs/**"
            			).permitAll()

            		.requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

            		.requestMatchers(HttpMethod.GET,
            		    "/api/productos/listarProductoData",
            		    "/api/productos/listarActivos",
            		    "/api/categorias/**",
            		    "/api/tallas/**",
            		    "/api/imagenes/**"                 //recuperacion de esta vaina
            		).permitAll()

            		.requestMatchers(HttpMethod.POST,
            		    "/api/productos/**",
            		    "/api/categorias/**",
            		    "/api/tallas/**",
            		    "/api/imagenes/**"
            		).hasRole("ADMIN")

            		.requestMatchers(HttpMethod.PATCH, "/api/productos/**").hasRole("ADMIN")

            		.requestMatchers(HttpMethod.DELETE,
            		    "/api/productos/**",
            		    "/api/categorias/**",
            		    "/api/tallas/**",
            		    "/api/imagenes/**"
            		).hasRole("ADMIN")

            		.anyRequest().authenticated()
            )

           .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class //filtro 
            );

        return http.build();
    }
}