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
    
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
        AuthResponse authResponse = authService.login(request);
        UserSession userSession = new UserSession(
            authResponse.getUser().getMail(),
            authResponse.getUser().getUsername(),
            authResponse.getUser().getLastname(),
            authResponse.getUser().getFirstname(),
            authResponse.getUser().getRole().toString()  
        );
        userSessionService.setCurrentUser(userSession);

        return ResponseEntity.ok(authResponse);

    } catch (UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("El correo electrónico no está registrado");
    } catch (BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("La contraseña es incorrecta");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al procesar la solicitud de inicio de sesión");
    }
}



    @PostMapping(value = "/register")
        public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Verifica si el email o username ya estan registrados
        if (authService.existsByEmail(request.getMail())) {
        return ResponseEntity.badRequest().body("Error: El email ya está registrado");
    }

    if (authService.existsByUsername(request.getUsername())) {
        return ResponseEntity.badRequest().body("Error: El nombre de usuario ya está registrado");
    }

    // Si no están registrados, procede con el registro
    AuthResponse authResponse = authService.register(request);
    return ResponseEntity.ok(authResponse);
}
}
