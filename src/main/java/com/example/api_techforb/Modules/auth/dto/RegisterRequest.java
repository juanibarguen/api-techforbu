package com.example.api_techforb.Modules.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String username;
    String password;
    String mail;

    @JsonProperty("firstName")
    String firstname;

    @JsonProperty("lastName")
    String lastname;
}
