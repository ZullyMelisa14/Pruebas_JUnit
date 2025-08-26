package com.proyecto;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Casos de prueba para la clase GestorUsuarios
 * Ubicación: src/test/java/com/proyecto/GestorUsuariosTest.java
 *
 * PROYECTO DE AULA - MÍNIMO 5 CASOS DE PRUEBA UNITARIA
 * Este archivo contiene 8 casos de prueba completos
 */
class GestorUsuariosTest {

    private GestorUsuarios gestor;

    @BeforeEach
    void setUp() {
        // Se ejecuta antes de cada prueba
        gestor = new GestorUsuarios();
    }

    @AfterEach
    void tearDown() {
        // Se ejecuta después de cada prueba
        gestor = null;
    }

    // ========== CASO DE PRUEBA 1: REGISTRO EXITOSO ==========

    @Test
    @DisplayName("CP001 - Registrar usuario con datos válidos")
    void testRegistrarUsuarioValido() {
        // Arrange (Preparar datos)
        String email = "usuario@test.com";
        String password = "password123";
        String nombre = "Juan Pérez";

        // Act (Ejecutar acción)
        boolean resultado = gestor.registrarUsuario(email, password, nombre);

        // Assert (Verificar resultados)
        assertTrue(resultado, "El registro debe ser exitoso");
        assertEquals(1, gestor.obtenerCantidadUsuarios(), "Debe haber 1 usuario registrado");

        // Verificar que el usuario se guardó correctamente
        Usuario usuario = gestor.buscarUsuario(email);
        assertNotNull(usuario, "El usuario debe existir");
        assertEquals(email, usuario.getEmail(), "El email debe coincidir");
        assertEquals(nombre, usuario.getNombre(), "El nombre debe coincidir");
        assertNotNull(usuario.getFechaCreacion(), "Debe tener fecha de creación");
    }

    // ========== CASO DE PRUEBA 2: VALIDACIÓN DE EMAIL ==========

    @Test
    @DisplayName("CP002 - Validar formato de email")
    void testValidarFormatoEmail() {
        String password = "password123";
        String nombre = "Usuario Test";

        // Casos de emails inválidos - DEBEN lanzar excepción
        IllegalArgumentException excepcion1 = assertThrows(
                IllegalArgumentException.class,
                () -> gestor.registrarUsuario("emailinvalido", password, nombre),
                "Email sin @ debe fallar"
        );
        assertTrue(excepcion1.getMessage().contains("Email inválido"));

        assertThrows(IllegalArgumentException.class,
                () -> gestor.registrarUsuario("email@", password, nombre),
                "Email sin dominio debe fallar");

        assertThrows(IllegalArgumentException.class,
                () -> gestor.registrarUsuario("@dominio.com", password, nombre),
                "Email sin usuario debe fallar");

        assertThrows(IllegalArgumentException.class,
                () -> gestor.registrarUsuario(null, password, nombre),
                "Email nulo debe fallar");

        // Casos de emails válidos - NO deben lanzar excepción
        assertDoesNotThrow(
                () -> gestor.registrarUsuario("valido@test.com", password, nombre),
                "Email válido básico debe funcionar");

        assertDoesNotThrow(
                () -> gestor.registrarUsuario("usuario.test+tag@dominio.co.uk", "pass123", "Usuario"),
                "Email complejo válido debe funcionar");
    }

    // ========== CASO DE PRUEBA 3: AUTENTICACIÓN ==========

    @Test
    @DisplayName("CP003 - Autenticar usuario existente")
    void testAutenticarUsuario() {
        // Arrange - Registrar usuario primero
        String email = "test@example.com";
        String password = "mypassword";
        String nombre = "Test User";
        gestor.registrarUsuario(email, password, nombre);

        // Act & Assert - Autenticación CORRECTA
        Usuario usuarioAutenticado = gestor.autenticar(email, password);
        assertNotNull(usuarioAutenticado, "La autenticación debe ser exitosa");
        assertEquals(email, usuarioAutenticado.getEmail(), "Debe retornar el usuario correcto");

        // Act & Assert - Autenticación con password INCORRECTA
        Usuario fallaPassword = gestor.autenticar(email, "passwordincorrecta");
        assertNull(fallaPassword, "La autenticación con password incorrecta debe fallar");

        // Act & Assert - Autenticación con usuario INEXISTENTE
        Usuario usuarioInexistente = gestor.autenticar("noexiste@test.com", password);
        assertNull(usuarioInexistente, "La autenticación de usuario inexistente debe fallar");

        // Act & Assert - Autenticación con parámetros nulos
        Usuario parametrosNulos = gestor.autenticar(null, null);
        assertNull(parametrosNulos, "La autenticación con parámetros nulos debe fallar");
    }

    // ========== CASO DE PRUEBA 4: ELIMINACIÓN ==========

    @Test
    @DisplayName("CP004 - Eliminar usuario existente")
    void testEliminarUsuario() {
        // Arrange - Registrar varios usuarios
        gestor.registrarUsuario("user1@test.com", "pass123", "Usuario 1");
        gestor.registrarUsuario("user2@test.com", "pass123", "Usuario 2");
        gestor.registrarUsuario("user3@test.com", "pass123", "Usuario 3");
        assertEquals(3, gestor.obtenerCantidadUsuarios(), "Deben haber 3 usuarios inicialmente");

        // Act - Eliminar un usuario EXISTENTE
        boolean eliminacionExitosa = gestor.eliminarUsuario("user2@test.com");

        // Assert - Verificar eliminación exitosa
        assertTrue(eliminacionExitosa, "La eliminación debe ser exitosa");
        assertEquals(2, gestor.obtenerCantidadUsuarios(), "Deben quedar 2 usuarios");

        // Verificar que el usuario eliminado no existe
        assertNull(gestor.buscarUsuario("user2@test.com"), "El usuario eliminado no debe existir");

        // Verificar que los otros usuarios siguen existiendo
        assertNotNull(gestor.buscarUsuario("user1@test.com"), "Usuario 1 debe seguir existiendo");
        assertNotNull(gestor.buscarUsuario("user3@test.com"), "Usuario 3 debe seguir existiendo");

        // Act & Assert - Intentar eliminar usuario INEXISTENTE
        boolean eliminacionFallida = gestor.eliminarUsuario("noexiste@test.com");
        assertFalse(eliminacionFallida, "Eliminar usuario inexistente debe retornar false");

        // Verificar que no se afectó el número de usuarios
        assertEquals(2, gestor.obtenerCantidadUsuarios(), "El número de usuarios no debe cambiar");
    }

    // ========== CASO DE PRUEBA 5: ACTUALIZACIÓN ==========

    @Test
    @DisplayName("CP005 - Actualizar datos de usuario")
    void testActualizarUsuario() {
        // Arrange
        String email = "update@test.com";
        String passwordOriginal = "password123";
        String nombreOriginal = "Nombre Original";
        String nuevoNombre = "Nombre Actualizado";

        gestor.registrarUsuario(email, passwordOriginal, nombreOriginal);

        // Act - Actualización EXITOSA
        boolean actualizacionExitosa = gestor.actualizarUsuario(email, nuevoNombre);

        // Assert - Verificar actualización
        assertTrue(actualizacionExitosa, "La actualización debe ser exitosa");
        Usuario usuarioActualizado = gestor.buscarUsuario(email);
        assertEquals(nuevoNombre, usuarioActualizado.getNombre(), "El nombre debe estar actualizado");

        // Verificar que otros datos no cambiaron
        assertEquals(email, usuarioActualizado.getEmail(), "El email no debe cambiar");
        assertEquals(passwordOriginal, usuarioActualizado.getPassword(), "La password no debe cambiar");

        // Act & Assert - Actualizar usuario INEXISTENTE
        boolean actualizacionFallida = gestor.actualizarUsuario("noexiste@test.com", "Nuevo Nombre");
        assertFalse(actualizacionFallida, "Actualizar usuario inexistente debe fallar");

        // Act & Assert - Actualizar con nombre VACÍO
        assertThrows(IllegalArgumentException.class,
                () -> gestor.actualizarUsuario(email, ""),
                "Actualizar con nombre vacío debe lanzar excepción");

        assertThrows(IllegalArgumentException.class,
                () -> gestor.actualizarUsuario(email, "   "),
                "Actualizar con nombre solo espacios debe lanzar excepción");

        assertThrows(IllegalArgumentException.class,
                () -> gestor.actualizarUsuario(email, null),
                "Actualizar con nombre nulo debe lanzar excepción");
    }

    // ========== CASO DE PRUEBA 6: PREVENIR DUPLICADOS ==========

    @Test
    @DisplayName("CP006 - Prevenir registro de usuario duplicado")
    void testPrevenirUsuarioDuplicado() {
        // Arrange
        String email = "duplicado@test.com";

        // Act - Primer registro (debe ser exitoso)
        boolean primerRegistro = gestor.registrarUsuario(email, "pass1", "Usuario 1");

        // Assert - Primer registro exitoso
        assertTrue(primerRegistro, "El primer registro debe ser exitoso");
        assertEquals(1, gestor.obtenerCantidadUsuarios(), "Debe haber 1 usuario");

        // Act - Segundo registro con el mismo email (debe fallar)
        boolean segundoRegistro = gestor.registrarUsuario(email, "pass2", "Usuario 2");

        // Assert - Segundo registro debe fallar
        assertFalse(segundoRegistro, "No debe permitir registrar el mismo email dos veces");
        assertEquals(1, gestor.obtenerCantidadUsuarios(), "Solo debe haber 1 usuario");

        // Verificar que se mantuvo el primer usuario
        Usuario usuario = gestor.buscarUsuario(email);
        assertEquals("Usuario 1", usuario.getNombre(), "Debe mantener los datos del primer registro");
        assertEquals("pass1", usuario.getPassword(), "Debe mantener la password del primer registro");
    }

    // ========== CASO DE PRUEBA 7: VALIDACIÓN DE PASSWORD ==========

    @Test
    @DisplayName("CP007 - Validar longitud mínima de password")
    void testValidarLongitudPassword() {
        String email = "test@test.com";
        String nombre = "Usuario Test";

        // Passwords inválidas (menos de 6 caracteres)
        assertThrows(IllegalArgumentException.class,
                () -> gestor.registrarUsuario(email, "1", nombre),
                "Password de 1 caracter debe fallar");

        assertThrows(IllegalArgumentException.class,
                () -> gestor.registrarUsuario(email, "", nombre),
                "Password vacío debe fallar");

        assertThrows(IllegalArgumentException.class,
                () -> gestor.registrarUsuario(email, null, nombre),
                "Password nulo debe fallar");

        // Passwords válidas (6 o más caracteres)
        assertDoesNotThrow(
                () -> gestor.registrarUsuario("test1@test.com", "12345", nombre),
                "Password de 6 caracteres debe ser válido");

        assertDoesNotThrow(
                () -> gestor.registrarUsuario("test2@test.com", "passwordlargo123", nombre),
                "Password largo debe ser válido");
    }

    // ========== CASO DE PRUEBA 8: LISTAR USUARIOS ==========

    @Test
    @DisplayName("CP008 - Listar todos los usuarios")
    void testListarUsuarios() {
        // Arrange - Lista vacía inicialmente
        List<Usuario> listaVacia = gestor.listarUsuarios();
        assertEquals(0, listaVacia.size(), "La lista inicial debe estar vacía");

        // Arrange - Registrar varios usuarios
        gestor.registrarUsuario("user1@test.com", "pass123", "Usuario 1");
        gestor.registrarUsuario("user2@test.com", "pass123", "Usuario 2");
        gestor.registrarUsuario("user3@test.com", "pass123", "Usuario 3");

        // Act - Obtener lista de usuarios
        List<Usuario> listaUsuarios = gestor.listarUsuarios();

        // Assert - Verificar contenido de la lista
        assertEquals(3, listaUsuarios.size(), "Debe retornar 3 usuarios");

        // Verificar que todos los usuarios están en la lista
        assertTrue(listaUsuarios.stream().anyMatch(u -> u.getEmail().equals("user1@test.com")),
                "Debe contener user1@test.com");
        assertTrue(listaUsuarios.stream().anyMatch(u -> u.getEmail().equals("user2@test.com")),
                "Debe contener user2@test.com");
        assertTrue(listaUsuarios.stream().anyMatch(u -> u.getEmail().equals("user3@test.com")),
                "Debe contener user3@test.com");

        // Verificar que la lista retornada es una copia (no afecta el original)
        listaUsuarios.clear();
        assertEquals(3, gestor.obtenerCantidadUsuarios(),
                "Limpiar la lista retornada no debe afectar los usuarios originales");
    }

    // ========== PRUEBAS ADICIONALES (BONUS) ==========

    @Test
    @DisplayName("CP009 - Cambiar password de usuario")
    void testCambiarPassword() {
        // Arrange
        String email = "cambio@test.com";
        String passwordOriginal = "password123";
        String nuevaPassword = "nuevapassword456";

        gestor.registrarUsuario(email, passwordOriginal, "Usuario Test");

        // Act - Cambio exitoso
        boolean cambioExitoso = gestor.cambiarPassword(email, passwordOriginal, nuevaPassword);

        // Assert
        assertTrue(cambioExitoso, "El cambio de password debe ser exitoso");

        // Verificar que la nueva password funciona
        Usuario usuario = gestor.autenticar(email, nuevaPassword);
        assertNotNull(usuario, "Debe poder autenticarse con la nueva password");

        // Verificar que la password vieja ya no funciona
        Usuario usuarioViejo = gestor.autenticar(email, passwordOriginal);
        assertNull(usuarioViejo, "No debe poder autenticarse con la password vieja");
    }

    @Test
    @DisplayName("CP010 - Validar nombre de usuario")
    void testValidarNombreUsuario() {
        String email = "nombre@test.com";
        String password = "password123";

        // Nombres inválidos
        assertThrows(IllegalArgumentException.class,
                () -> gestor.registrarUsuario(email, password, null),
                "Nombre nulo debe fallar");

        assertThrows(IllegalArgumentException.class,
                () -> gestor.registrarUsuario(email, password, ""),
                "Nombre vacío debe fallar");

        assertThrows(IllegalArgumentException.class,
                () -> gestor.registrarUsuario(email, password, "   "),
                "Nombre solo espacios debe fallar");

        // Nombres válidos
        assertDoesNotThrow(
                () -> gestor.registrarUsuario("test1@test.com", password, "Juan"),
                "Nombre simple debe funcionar");

        assertDoesNotThrow(
                () -> gestor.registrarUsuario("test2@test.com", password, "Juan Carlos Pérez"),
                "Nombre completo debe funcionar");
    }
}