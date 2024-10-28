package com.example.api_techforb.Modules.auth.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.api_techforb.Modules.user.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // Llave secreta para firmar y verificar tokens
    private static final String SECRET_KEY="586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    // Genera un token JWT para un usuario con detalles básicos
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }
     // Genera un token JWT para un usuario, con la posibilidad de incluir claims adicionales
    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts.builder()
            .setClaims(extraClaims)// Establece claims adicionales si existen   
            .setSubject(((User) user).getMail()) // Usa el correo como identificador principal
            .setIssuedAt(new Date(System.currentTimeMillis()))// Establece la fecha de emisión del token
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))// Establece la fecha de expiración
            .signWith(getKey(), SignatureAlgorithm.HS256) // Firma el token con la llave y algoritmo HS256
            .compact();
    }


    // Decodifica la llave secreta para firmar y verificar los tokens
    private Key getKey() {
       byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
       return Keys.hmacShaKeyFor(keyBytes);
    }

    // Obtiene el nombre de usuario (correo) desde el token JWT
    public String getUsernameFromToken(String token) {
        System.out.println("getUsernameFromToken: "+token);
        return getClaim(token, Claims::getSubject);
    }
    
    // Verifica si el token es válido para el usuario especificado
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
// Extrae todos los claims del token JWT
    private Claims getAllClaims(String token){
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())// Usa la llave secreta para decodificar
            .build()
            .parseClaimsJws(token)
            .getBody();// Devuelve el cuerpo del token (claims)
    }

    // Método genérico para obtener un claim específico del token usando una función de resolución
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Obtiene la fecha de expiración del token
    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }
    // Verifica si el token ha expirado
    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
    
}