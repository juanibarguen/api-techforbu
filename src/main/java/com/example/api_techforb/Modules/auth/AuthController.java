package com.example.api_techforb.Modules.auth;


import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);

        
        if (authResponse.getUser() == null) {
            return ResponseEntity.badRequest().body(null); 
        }

        UserSession userSession = new UserSession(
            authResponse.getUser().getMail(),
            authResponse.getUser().getUsername(),
            authResponse.getUser().lastname,
            authResponse.getUser().getFirstname(),
            authResponse.getUser().getRole().toString()  
        );
        userSessionService.setCurrentUser(userSession);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
