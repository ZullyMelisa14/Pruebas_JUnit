package com.proyecto;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Clase GestorUsuarios - Sistema de gestión de usuarios
 */
public class GestorUsuarios {

    // ========== ATRIBUTOS ==========

    private Map<String, Usuario> usuarios;

    // Expresión regular para validar emails
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // ========== CONSTRUCTOR ==========

    /**
     * Constructor que inicializa el gestor de usuarios
     */
    public GestorUsuarios() {
        this.usuarios = new HashMap<>();
    }

    // ========== MÉTODOS PRINCIPALES ==========

    /**
     * Registra un nuevo usuario en el sistema
     * @param email Email del usuario (debe ser único y válido)
     * @param password Contraseña (mínimo 6 caracteres)
     * @param nombre Nombre del usuario (no puede estar vacío)
     * @return true si el registro fue exitoso, false si el usuario ya existe
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    public boolean registrarUsuario(String email, String password, String nombre) {

        // Verificar si el usuario ya existe
        if (usuarios.containsKey(email)) {
            return false; // Usuario ya existe
        }
        // Validar email
        if (!validarEmail(email)) {
            throw new IllegalArgumentException("Email inválido");
        }

        // Validar password
        if (password == null || password.length() < 5) {
            throw new IllegalArgumentException("Password debe tener al menos 5 caracteres");
        }

        // Validar nombre
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede estar vacío");
        }

        // Crear y registrar el usuario
        Usuario usuario = new Usuario(email, password, nombre);
        usuarios.put(email, usuario);
        return true;
    }

    /**
     * Autentica un usuario con email y contraseña
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario si la autenticación es exitosa, null si falla
     */
    public Usuario autenticar(String email, String password) {
        // Verificar si el usuario existe
        if (!usuarios.containsKey(email)) {
            return null;
        }

        Usuario usuario = usuarios.get(email);

        // Verificar contraseña
        if (usuario.getPassword().equals(password)) {
            return usuario;
        }

        return null; // Contraseña incorrecta
    }

    /**
     * Elimina un usuario del sistema
     * @param email Email del usuario a eliminar
     * @return true si el usuario fue eliminado, false si no existía
     */
    public boolean eliminarUsuario(String email) {
        return usuarios.remove(email) != null;
    }

    /**
     * Busca un usuario por su email
     * @param email Email del usuario a buscar
     * @return Usuario encontrado o null si no existe
     */
    public Usuario buscarUsuario(String email) {
        return usuarios.get(email);
    }

    /**
     * Obtiene una lista de todos los usuarios registrados
     * @return Lista de todos los usuarios
     */
    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios.values());
    }

    /**
     * Actualiza el nombre de un usuario existente
     * @param email Email del usuario a actualizar
     * @param nuevoNombre Nuevo nombre del usuario
     * @return true si la actualización fue exitosa, false si el usuario no existe
     * @throws IllegalArgumentException si el nuevo nombre es inválido
     */
    public boolean actualizarUsuario(String email, String nuevoNombre) {
        Usuario usuario = usuarios.get(email);

        // Verificar que el usuario existe
        if (usuario == null) {
            return false;
        }

        // Validar nuevo nombre
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nuevo nombre no puede estar vacío");
        }

        // Actualizar nombre
        usuario.setNombre(nuevoNombre);
        return true;
    }

    /**
     * Cambia la contraseña de un usuario
     * @param email Email del usuario
     * @param passwordActual Contraseña actual (para verificación)
     * @param nuevaPassword Nueva contraseña
     * @return true si el cambio fue exitoso, false si falla la verificación
     * @throws IllegalArgumentException si la nueva contraseña es inválida
     */
    public boolean cambiarPassword(String email, String passwordActual, String nuevaPassword) {
        Usuario usuario = usuarios.get(email);

        // Verificar que el usuario existe y la contraseña actual es correcta
        if (usuario == null || !usuario.getPassword().equals(passwordActual)) {
            return false;
        }

        // Validar nueva contraseña
        if (nuevaPassword == null || nuevaPassword.length() < 5) {
            throw new IllegalArgumentException("Nueva password debe tener al menos 6 caracteres");
        }

        // Cambiar contraseña
        usuario.setPassword(nuevaPassword);
        return true;
    }

    // ========== MÉTODOS DE UTILIDAD ==========

    /**
     * Valida el formato del email usando expresión regular
     * @param email Email a validar
     * @return true si el formato es válido, false en caso contrario
     */
    private boolean validarEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Obtiene el número total de usuarios registrados
     * @return Cantidad de usuarios en el sistema
     */
    public int obtenerCantidadUsuarios() {
        return usuarios.size();
    }

    /**
     * Verifica si un email ya está registrado
     * @param email Email a verificar
     * @return true si el email existe, false en caso contrario
     */
    public boolean existeUsuario(String email) {
        return usuarios.containsKey(email);
    }

    /**
     * Limpia todos los usuarios del sistema (útil para testing)
     */
    public void limpiarTodos() {
        usuarios.clear();
    }

    /**
     * Obtiene estadísticas básicas del sistema
     * @return String con información del estado del sistema
     */
    public String obtenerEstadisticas() {
        return String.format(
                "Sistema de Usuarios - Total: %d usuarios registrados",
                usuarios.size()
        );
    }
}