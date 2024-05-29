package com.desafio.desafio_backend.domain.usuario;

public enum Papel {
    ADMIN("admin"),
    USER("user");

    private String role;

    Papel(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
