package com.ejemplo;

/**
 * Clase Calculadora con operaciones básicas
 */
public class Calculadora {

    /**
     * Suma dos números
     * @param a primer número
     * @param b segundo número
     * @return resultado de la suma
     */
    public double sumar(double a, double b) {
        return a + b;
    }

    /**
     * Resta dos números
     * @param a primer número (minuendo)
     * @param b segundo número (sustraendo)
     * @return resultado de la resta
     */
    public double restar(double a, double b) {
        return a - b;
    }

    /**
     * Multiplica dos números
     * @param a primer número
     * @param b segundo número
     * @return resultado de la multiplicación
     */
    public double multiplicar(double a, double b) {
        return a * b;
    }

    /**
     * Divide dos números
     * @param a dividendo
     * @param b divisor
     * @return resultado de la división
     * @throws IllegalArgumentException si el divisor es cero
     */
    public double dividir(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("No se puede dividir por cero");
        }
        return a / b;
    }

    /**
     * Calcula la potencia de un número
     * @param base base
     * @param exponente exponente
     * @return resultado de base^exponente
     */
    public double potencia(double base, int exponente) {
        return Math.pow(base, exponente);
    }

    /**
     * Calcula la raíz cuadrada de un número
     * @param numero número del cual calcular la raíz
     * @return raíz cuadrada del número
     * @throws IllegalArgumentException si el número es negativo
     */
    public double raizCuadrada(double numero) {
        if (numero < 0) {
            throw new IllegalArgumentException("No se puede calcular la raíz cuadrada de un número negativo");
        }
        return Math.sqrt(numero);
    }

    /**
     * Verifica si un número es par
     * @param numero número a verificar
     * @return true si es par, false si es impar
     */
    public boolean esPar(int numero) {
        return numero % 2 == 0;
    }
}