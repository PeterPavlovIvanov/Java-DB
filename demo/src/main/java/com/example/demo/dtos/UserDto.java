package com.example.demo.dtos;

import com.example.demo.entities.Game;
import com.example.demo.entities.Order;
import com.example.demo.entities.Role;

import java.util.Set;

public class UserDto {
    private String email;
    private String fullName;
    private String password;
    private Role role;

    public UserDto() {
    }

    public UserDto(String email, String fullName, String password, Role role) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
