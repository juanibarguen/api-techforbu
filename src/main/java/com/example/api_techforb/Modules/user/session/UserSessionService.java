package com.example.api_techforb.Modules.user.session;

import org.springframework.stereotype.Service;

@Service
public class UserSessionService {
    private UserSession currentUserSession;

    public void setCurrentUser(UserSession userSession) {
        this.currentUserSession = userSession;
    }

    public UserSession getCurrentUser() {
        return currentUserSession;
    }

    public void clearCurrentUser() {
        this.currentUserSession = null;
    }
}