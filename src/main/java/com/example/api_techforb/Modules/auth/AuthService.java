package com.example.api_techforb.Modules.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    // Verifica si el usuario existe en la base de datos
    User user = userRepository.findByMail(request.getMail())
        .orElseThrow(() -> new UsernameNotFoundException("Correo no registrado"));

    try {
        // Intenta autenticar con el correo y la contraseña
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getMail(), request.getPassword())
        );
    } catch (BadCredentialsException e) {
        // Lanza excepción si la contraseña es incorrecta
        throw new BadCredentialsException("Contraseña incorrecta");
    }

    // Obtiene los detalles del usuario después de autenticar
    UserDetails userDetails = getUserDetails(request.getMail());
    String token = jwtService.getToken(userDetails);

    // Devuelve la respuesta con el token y los detalles del usuario
    return AuthResponse.builder()
        .token(token)
        .user(user) 
        .build();
}


public boolean existsByEmail(String email) {
    return userRepository.existsByMail(email);
}

public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
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
