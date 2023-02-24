package com.pipepiper.socialnetwork.dto;

import com.pipepiper.socialnetwork.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    String username;
    String email;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public String getUsername() {
        return username;
    }




    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}


