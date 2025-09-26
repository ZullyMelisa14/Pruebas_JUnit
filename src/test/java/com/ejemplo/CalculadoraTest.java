package com.ejemplo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Casos de prueba para la clase Calculadora
 * Ubicación: src/test/java/com/ejemplo/CalculadoraTest.java
 */
class CalculadoraTest {

    private Calculadora calculadora;

    @BeforeEach
    void setUp() {
        // Se ejecuta antes de cada prueba
        calculadora = new Calculadora();
    }

    @AfterEach
    void tearDown() {
        // Se ejecuta después de cada prueba
        calculadora = null;
    }

    // ========== PRUEBAS PARA SUMA ==========

    @Test
    @DisplayName("Suma de números positivos")
    void testSumarNumerosPositivos() {
        // Arrange (Preparar)
        double a = 5.0;
        double b = 3.0;
        double esperado = 8.0;

        // Act (Actuar)
        double resultado = calculadora.sumar(a, b);

        // Assert (Verificar)
        assertEquals(esperado, resultado, 0.001, "La suma de 5 + 3 debe ser 8");
    }

    @Test
    @DisplayName("Suma con números negativos")
    void testSumarConNumerosNegativos() {
        double resultado = calculadora.sumar(-5.0, -3.0);
        assertEquals(-8.0, resultado, 0.001);
    }

    @Test
    @DisplayName("Suma con cero")
    void testSumarConCero() {
        double resultado = calculadora.sumar(5.0, 0.0);
        assertEquals(5.0, resultado, 0.001);
    }

    // ========== PRUEBAS PARA RESTA ==========

    @Test
    @DisplayName("Resta de números positivos")
    void testRestarNumerosPositivos() {
        double resultado = calculadora.restar(10.0, 4.0);
        assertEquals(6.0, resultado, 0.001);
    }

    @Test
    @DisplayName("Resta que resulta en número negativo")
    void testRestarResultadoNegativo() {
        double resultado = calculadora.restar(3.0, 8.0);
        assertEquals(-5.0, resultado, 0.001);
    }

    // ========== PRUEBAS PARA MULTIPLICACIÓN ==========

    @Test
    @DisplayName("Multiplicación de números positivos")
    void testMultiplicarNumerosPositivos() {
        double resultado = calculadora.multiplicar(4.0, 5.0);
        assertEquals(20.0, resultado, 0.001);
    }

    @Test
    @DisplayName("Multiplicación por cero")
    void testMultiplicarPorCero() {
        double resultado = calculadora.multiplicar(7.0, 0.0);
        assertEquals(0.0, resultado, 0.001);
    }

    @Test
    @DisplayName("Multiplicación con números negativos")
    void testMultiplicarConNegativos() {
        double resultado = calculadora.multiplicar(-3.0, 4.0);
        assertEquals(-12.0, resultado, 0.001);
    }

    // ========== PRUEBAS PARA DIVISIÓN ==========

    @Test
    @DisplayName("División de números positivos")
    void testDividirNumerosPositivos() {
        double resultado = calculadora.dividir(15.0, 3.0);
        assertEquals(5.0, resultado, 0.001);
    }

    @Test
    @DisplayName("División por cero debe lanzar excepción")
    void testDividirPorCero() {
        // Verificamos que se lance la excepción esperada
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.dividir(10.0, 0),
                "Dividir por cero debe lanzar IllegalArgumentException");

        // Verificamos el mensaje de la excepción
        assertEquals("No se puede dividir por cero", excepcion.getMessage());
    }

    @Test
    @DisplayName("División con resultado decimal")
    void testDividirResultadoDecimal() {
        double resultado = calculadora.dividir(7.0, 2.0);
        assertEquals(3.5, resultado, 0.001);
    }

    // ========== PRUEBAS PARA POTENCIA ==========

    @Test
    @DisplayName("Potencia con exponente positivo")
    void testPotenciaExponentePositivo() {
        double resultado = calculadora.potencia(2.0, 3);
        assertEquals(8.0, resultado, 0.001);
    }

    @Test
    @DisplayName("Potencia con exponente cero")
    void testPotenciaExponenteCero() {
        double resultado = calculadora.potencia(5.0, 0);
        assertEquals(1.0, resultado, 0.001);
    }

    @Test
    @DisplayName("Potencia con exponente negativo")
    void testPotenciaExponenteNegativo() {
        double resultado = calculadora.potencia(2.0, -2);
        assertEquals(0.25, resultado, 0.001);
    }

    // ========== PRUEBAS PARA RAÍZ CUADRADA ==========

    @Test
    @DisplayName("Raíz cuadrada de número positivo")
    void testRaizCuadradaPositiva() {
        double resultado = calculadora.raizCuadrada(9.0);
        assertEquals(3.0, resultado, 0.001);
    }

    @Test
    @DisplayName("Raíz cuadrada de cero")
    void testRaizCuadradaDeCero() {
        double resultado = calculadora.raizCuadrada(0.0);
        assertEquals(0.0, resultado, 0.001);
    }

    @Test
    @DisplayName("Raíz cuadrada de número negativo debe lanzar excepción")
    void testRaizCuadradaNegativa() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.raizCuadrada(-4.0),
                "La raíz cuadrada de un número negativo debe lanzar excepción");

        assertTrue(excepcion.getMessage().contains("negativo"));
    }

    // ========== PRUEBAS PARA NÚMERO PAR ==========

    @Test
    @DisplayName("Verificar número par")
    void testNumeroPar() {
        assertTrue(calculadora.esPar(4), "4 debe ser par");
        assertTrue(calculadora.esPar(0), "0 debe ser par");
        assertTrue(calculadora.esPar(-2), "-2 debe ser par");
    }

    @Test
    @DisplayName("Verificar número impar")
    void testNumeroImpar() {
        assertFalse(calculadora.esPar(5), "5 no debe ser par");
        assertFalse(calculadora.esPar(-3), "-3 no debe ser par");
        assertFalse(calculadora.esPar(1), "1 no debe ser par");
    }

    // ========== PRUEBAS AGRUPADAS ==========

    @Nested
    @DisplayName("Pruebas de casos límite")
    class CasosLimite {

        @Test
        @DisplayName("Operaciones con números muy grandes")
        void testNumerosGrandes() {
            double resultado = calculadora.sumar(Double.MAX_VALUE, 1.0);
            assertEquals(Double.MAX_VALUE, resultado, 0.001);
        }

        @Test
        @DisplayName("Operaciones con números muy pequeños")
        void testNumerosPequenos() {
            double resultado = calculadora.multiplicar(0.0001, 0.0001);
            assertEquals(0.00000001, resultado, 0.000000001);
        }
    }

    // ========== PRUEBAS PARAMETRIZADAS ==========

    @ParameterizedTest
    @DisplayName("Pruebas múltiples de suma")
    @ValueSource(doubles = { 1.0, 2.0, 3.0, 4.0, 5.0 })
    void testSumaConVariosValores(double valor) {
        double resultado = calculadora.sumar(valor, valor);
        assertEquals(valor * 2, resultado, 0.001);
    }
}