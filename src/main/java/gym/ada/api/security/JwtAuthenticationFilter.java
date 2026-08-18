package gym.ada.api.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("====================================");
        System.out.println("🔥 JWT FILTER");
        System.out.println("Método: " + request.getMethod());
        System.out.println("URI: " + request.getRequestURI());
        System.out.println(
            "Authorization: " +
            request.getHeader("Authorization")
        );

        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader == null ||
            !authorizationHeader.startsWith("Bearer ")) {

            System.out.println(
                "🟢 JWT → SIN TOKEN → filterChain.doFilter()"
            );

            filterChain.doFilter(request, response);

            System.out.println(
                "🟢 JWT → REGRESÓ DEL filterChain"
            );

            System.out.println(
                "Status actual: " +
                response.getStatus()
            );

            System.out.println("====================================");

            return;
        }

        System.out.println(
            "🟡 JWT → ENCONTRÓ BEARER"
        );

        String token =
                authorizationHeader.substring(7);

        try {

            System.out.println(
                "🟡 JWT → intentando validar token"
            );

            String email =
                    jwtService.obtenerEmail(token);

            String rol =
                    jwtService.obtenerRol(token);

            System.out.println(
                "🟢 JWT → token válido"
            );

            System.out.println(
                "Email: " + email
            );

            System.out.println(
                "Rol: " + rol
            );

            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority(
                        "ROLE_" + rol
                    );

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        java.util.List.of(authority)
                    );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

        } catch (Exception e) {

            System.out.println(
                "🔴 JWT → TOKEN INVÁLIDO"
            );

            System.out.println(
                "Error: " + e.getClass().getName()
            );

            System.out.println(
                "Mensaje: " + e.getMessage()
            );

            SecurityContextHolder.clearContext();
        }

        System.out.println(
            "➡️ JWT → pasando al siguiente filtro"
        );

        filterChain.doFilter(request, response);

        System.out.println(
            "⬅️ JWT → regresó del siguiente filtro"
        );

        System.out.println(
            "Status final: " +
            response.getStatus()
        );

        System.out.println("====================================");
    }
}