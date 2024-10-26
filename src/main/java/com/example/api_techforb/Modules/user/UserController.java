package com.example.api_techforb.Modules.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_techforb.Modules.user.session.UserSession;
import com.example.api_techforb.Modules.user.session.UserSessionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserSessionService userSessionService;

    @GetMapping("/dataUser")
    public ResponseEntity<UserSession> getUserInfo() {
        UserSession currentUser = userSessionService.getCurrentUser();
        
        if (currentUser == null) {
            return ResponseEntity.status(404).body(null); // Devuelve 404 si no hay sesión activa
        }

        return ResponseEntity.ok(currentUser); // Devuelve los datos del usuario en la sesión
    }
}
