package com.example.api_techforb.Modules.auth.Jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
       
        // Extrae el token de autenticación de la solicitud
        final String token = getTokenFromRequest(request);
        final String username;

        // Si no hay token, continúa con el siguiente filtro
        if (token==null)
        {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtiene el nombre de usuario del token JWT
        username=jwtService.getUsernameFromToken(token);
        // Si hay un usuario y aún no está autenticado, procede a validar el token
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            // Verifica si el token es válido para el usuario
            if (jwtService.isTokenValid(token, userDetails))
            {
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

                // Configura los detalles de autenticación y los establece en el contexto de seguridad
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continúa con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }

    // Extrae el token del encabezado de autorización de la solicitud
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
    // Verifica si el encabezado está presente y comienza con "Bearer "
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))
        {
            return authHeader.substring(7); // Extrae solo el token
        }
        return null; // Retorna null si no se encuentra el token
    }
}