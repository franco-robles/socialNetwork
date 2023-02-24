package com.pipepiper.socialnetwork.security.payload;

import io.jsonwebtoken.Jwt;

public class JwtResponse {
    private String username;
    //private String email;

    public JwtResponse(String username ) {
        this.username = username;
       // this.email = email;
    }
    public JwtResponse() {}
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

   // public String getEmail() {
   //     return email;
   // }
//
   // public void setEmail(String email) {
   //     this.email = email;
   // }
}
