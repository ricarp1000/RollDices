/*
 * Taller 1 - Funciones en Java
 * Programa: rollDices
 * Descripción: Juego de dados con sistema de vidas, condiciones de victoria/derrota
 *              y reporte final de estadísticas.
 */

// Importaciones completas de Java
import java.util.Random;
import java.util.Scanner;
import java.lang.Math;
import java.io.PrintStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal del juego RollDices.
 * Permite lanzar dos dados y gestiona vidas, victorias y derrotas.
 */
public class RollDices {

    // ─── Constantes del juego ───────────────────────────────────────────────
    static final int VIDAS_INICIALES    = 5;
    static final int CARA_MIN           = 1;
    static final int CARA_MAX           = 6;
    static final int RACHA_GANADORA     = 3;   // lanzamientos iguales consecutivos para YOU WIN

    // ─── Variables globales de estadísticas ────────────────────────────────
    static int totalLanzamientos  = 0;
    static int totalSumaPar       = 0;
    static int totalSumaImpar     = 0;
    static int totalDadosIguales  = 0;

    // ─── Generador de números aleatorios ───────────────────────────────────
    static Random random = new Random();

    /**
     * Lanza un dado y retorna un valor aleatorio entre CARA_MIN y CARA_MAX.
     *
     * @return int valor del dado (1-6)
     */
    static int lanzarDado() {
        return random.nextInt(CARA_MAX - CARA_MIN + 1) + CARA_MIN;
    }

    /**
     * Lanza los dos dados (Dice1 y Dice2) y retorna sus valores en un arreglo.
     *
     * @return int[] arreglo con [Dice1, Dice2]
     */
    static int[] rollDices() {
        int dice1 = lanzarDado();
        int dice2 = lanzarDado();
        return new int[]{dice1, dice2};
    }

    /**
     * Verifica si una suma es par.
     *
     * @param suma valor a evaluar
     * @return true si la suma es par, false si es impar
     */
    static boolean esPar(int suma) {
        return suma % 2 == 0;
    }

    /**
     * Muestra en pantalla el informe final con las estadísticas del juego.
     */
    static void mostrarInforme() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         INFORME FINAL DEL JUEGO      ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf( "║  Número total de lanzamientos : %4d  ║%n", totalLanzamientos);
        System.out.printf( "║  Total con suma PAR           : %4d  ║%n", totalSumaPar);
        System.out.printf( "║  Total con suma IMPAR         : %4d  ║%n", totalSumaImpar);
        System.out.printf( "║  Total con dados IGUALES      : %4d  ║%n", totalDadosIguales);
        System.out.println("╚══════════════════════════════════════╝");
    }

    /**
     * Método principal que controla el flujo del juego.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int vidas              = VIDAS_INICIALES;
        int rachaIgual         = 0;      // contador de lanzamientos consecutivos con dados iguales
        int[] ultimoDado       = null;   // resultado del lanzamiento anterior

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        BIENVENIDO A ROLL DICES       ║");
        System.out.printf( "║       Vidas iniciales: %d              ║%n", VIDAS_INICIALES);
        System.out.println("╚══════════════════════════════════════╝");

        boolean juegoActivo = true;

        while (juegoActivo) {

            // Mostrar estado actual
            System.out.printf("%n[Vidas: %d] Presiona ENTER para lanzar los dados (o escribe 'salir' para terminar): ", vidas);
            String entrada = scanner.nextLine().trim().toLowerCase();

            if (entrada.equals("salir")) {
                System.out.println("\nHas abandonado el juego.");
                break;
            }

            // ── Lanzar dados ────────────────────────────────────────────────
            int[] dados = rollDices();
            int dice1   = dados[0];
            int dice2   = dados[1];
            int suma    = dice1 + dice2;

            totalLanzamientos++;

            // Etiqueta del lanzamiento: L1, L2, L3...
            String etiqueta = "L" + totalLanzamientos;

            System.out.printf("%n--- %s ----%n", etiqueta);
            System.out.printf("  Dice1 = %d  |  Dice2 = %d  |  Suma = %d%n", dice1, dice2, suma);

            // ── Estadísticas ────────────────────────────────────────────────
            if (esPar(suma)) {
                totalSumaPar++;
            } else {
                totalSumaImpar++;
            }

            if (dice1 == dice2) {
                totalDadosIguales++;
            }

            // ── Regla 1: Par de seises → vida extra ─────────────────────────
            if (dice1 == 6 && dice2 == 6) {
                vidas++;
                System.out.println("  🎲 ¡Par de SEISES! Ganas una vida extra. Vidas: " + vidas);
            }

            // ── Regla 2: Dados iguales consecutivos → YOU WIN ───────────────
            if (dice1 == dice2) {
                rachaIgual++;
                System.out.printf("  🎯 Dados iguales. Racha actual: %d/%d%n", rachaIgual, RACHA_GANADORA);
                if (rachaIgual >= RACHA_GANADORA) {
                    System.out.println("\n╔══════════════════════════════════════╗");
                    System.out.println("║             🏆  YOU WIN  🏆           ║");
                    System.out.println("║  ¡Lograste 3 dados iguales seguidos! ║");
                    System.out.println("╚══════════════════════════════════════╝");
                    juegoActivo = false;
                }
            } else {
                // Reinicia la racha si los dados NO son iguales
                rachaIgual = 0;
            }

            // ── Regla 3: Suma impar → perder vida ───────────────────────────
            if (juegoActivo && !esPar(suma)) {
                vidas--;
                System.out.printf("  💀 Suma IMPAR (%d). Pierdes una vida. Vidas restantes: %d%n", suma, vidas);

                if (vidas <= 0) {
                    System.out.println("\n╔══════════════════════════════════════╗");
                    System.out.println("║           💀  GAME OVER  💀           ║");
                    System.out.println("║       ¡Te quedaste sin vidas!        ║");
                    System.out.println("╚══════════════════════════════════════╝");
                    juegoActivo = false;
                }
            } else if (juegoActivo) {
                System.out.printf("  ✅ Suma PAR (%d). El juego continúa.%n", suma);
            }

        } // fin while

        // ── Informe final ────────────────────────────────────────────────────
        mostrarInforme();

        scanner.close();
    }
}
