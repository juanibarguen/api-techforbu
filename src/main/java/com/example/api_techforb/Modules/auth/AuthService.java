package com.example.api_techforb.Modules.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api_techforb.Modules.auth.Jwt.JwtService;
import com.example.api_techforb.Modules.auth.dto.AuthResponse;
import com.example.api_techforb.Modules.auth.dto.LoginRequest;
import com.example.api_techforb.Modules.auth.dto.RegisterRequest;
import com.example.api_techforb.Modules.user.model.Role;
import com.example.api_techforb.Modules.user.model.User;
import com.example.api_techforb.Modules.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;




@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private UserDetails getUserDetails(String mail) {
        User user = userRepository.findByMail(mail)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            return user; 
    }
    

public AuthResponse login(LoginRequest request) {
    // Autentica el usuario
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getMail(), request.getPassword())
    );

    // Obtiene los detalles del usuario
    UserDetails user = getUserDetails(request.getMail());
    
    // Genera el token
    String token = jwtService.getToken(user);

    // Crea la respuesta
    return AuthResponse.builder()
        .token(token)
        .user((User) user) 
        .build();
}



public AuthResponse register(RegisterRequest request) {
    System.out.println("Contenido de RegisterRequest: " + request);
    
    User user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .username(request.getUsername())
        .mail(request.getMail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();

    userRepository.save(user);

    return AuthResponse.builder()
        .token(jwtService.getToken(user))
        .user(user) // Devuelve el usuario completo
        .build();
}




public User getUserDataByEmail(String email) {
    return userRepository.findByMail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + email));
}
}
