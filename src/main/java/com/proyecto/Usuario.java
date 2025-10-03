package com.proyecto;

import java.util.*;

/**
 * Clase Usuario - Representa un usuario del sistema
 */
public class Usuario {

    private String email;
    private String password;
    private String nombre;
    private Date fechaCreacion;

    /**
     * Constructor de Usuario
     * 
     * @param email    Email único del usuario
     * @param password Contraseña del usuario
     * @param nombre   Nombre completo del usuario
     */
    public Usuario(String email, String password, String nombre) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.fechaCreacion = new Date();
    }

    // ========== GETTERS ==========

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    // ========== SETTERS ==========

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ========== MÉTODOS DE OBJECT ==========

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Usuario usuario = (Usuario) obj;
        return Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}