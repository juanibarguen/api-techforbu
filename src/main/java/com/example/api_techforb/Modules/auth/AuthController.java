package com.example.api_techforb.Modules.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.api_techforb.Modules.auth.dto.AuthResponse;
import com.example.api_techforb.Modules.auth.dto.LoginRequest;
import com.example.api_techforb.Modules.auth.dto.RegisterRequest;
import com.example.api_techforb.Modules.user.session.UserSession;
import com.example.api_techforb.Modules.user.session.UserSessionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final UserSessionService userSessionService;
    
@PostMapping("/login") // Endpoint para el inicio de sesión
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
        // Autentica al usuario y obtiene la respuesta con el token y los detalles del usuario
        AuthResponse authResponse = authService.login(request);
        // Crea una nueva sesión de usuario después de autenticarse
        UserSession userSession = new UserSession(
            authResponse.getUser().getMail(),
            authResponse.getUser().getUsername(),
            authResponse.getUser().getLastname(),
            authResponse.getUser().getFirstname(),
            authResponse.getUser().getRole().toString()  
        );
        userSessionService.setCurrentUser(userSession);

        // Devuelve la respuesta exitosa con el token y los detalles del usuario
        return ResponseEntity.ok(authResponse);

    } catch (UsernameNotFoundException e) {
        // Maneja el caso de un correo electrónico no registrado
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("El correo electrónico no está registrado");
    } catch (BadCredentialsException e) {
        // Maneja el caso de una contraseña incorrecta
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("La contraseña es incorrecta");
    } catch (Exception e) {
        // Maneja errores no específicos durante el inicio de sesión
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al procesar la solicitud de inicio de sesión");
    }
}



@PostMapping(value = "/register") // Endpoint para el registro de usuario
public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    System.out.println("Registro solicitado: " + request); // Log de la solicitud de registro

    // Verifica si el email o username ya estan registrados
    if (authService.existsByEmail(request.getMail())) {
        return ResponseEntity.badRequest().body("Error: El email ya está registrado");
    }
    // Verifica si existe el username ya esta registrado
    if (authService.existsByUsername(request.getUsername())) {
        return ResponseEntity.badRequest().body("Error: El nombre de usuario ya está registrado");
    }
    // Si no están registrados, procede con el registro
    AuthResponse authResponse = authService.register(request);        
    
    // Devuelve la respuesta de registro con el token y detalles del usuario
    return ResponseEntity.ok(authResponse);
    }
}
