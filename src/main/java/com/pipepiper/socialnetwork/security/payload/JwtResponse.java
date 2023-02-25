package com.pipepiper.socialnetwork.security.payload;

import io.jsonwebtoken.Jwt;

public class JwtResponse {
    private String username;

    public JwtResponse(String username ) {
        this.username = username;

    }
    public JwtResponse() {}
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
