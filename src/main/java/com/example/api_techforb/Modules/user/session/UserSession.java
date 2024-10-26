package com.example.api_techforb.Modules.user.session;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSession {
    private String mail;
    private String username;
    private String lastname;
    private String firstname;
    private String role;
}
