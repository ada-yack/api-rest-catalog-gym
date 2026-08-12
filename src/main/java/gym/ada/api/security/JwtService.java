package gym.ada.api.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import gym.ada.api.enums.Rol;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey secretKey;

    private final long expiracion;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiracion) {

        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes()
        );

        this.expiracion = expiracion;
    }

    public String generarToken(String email, Rol rol) {

        Date ahora = new Date();

        Date expiracionFecha =
                new Date(ahora.getTime() + expiracion);

        return Jwts.builder()
                .subject(email)
                .claim("rol", rol.name())
                .issuedAt(ahora)
                .expiration(expiracionFecha)
                .signWith(secretKey)
                .compact();
    }
    public String obtenerEmail(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    
    public String obtenerRol(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("rol", String.class);
    }
}