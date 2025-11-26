package util;

import java.util.Scanner;

public class Validador {
    private static final Scanner scanner = new Scanner(System.in);

    public static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("✗ Error: Debe ingresar un número entero válido.");
            }
        }
    }

    public static Long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("✗ Error: Debe ingresar un número válido.");
            }
        }
    }

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public static String leerTextoNoVacio(String mensaje) {
        String texto;
        do {
            texto = leerTexto(mensaje);
            if (texto.isEmpty()) {
                System.out.println("✗ Error: Este campo no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    public static int leerEnteroEnRango(String mensaje, int min, int max) {
        int numero;
        do {
            numero = leerEntero(mensaje);
            if (numero < min || numero > max) {
                System.out.println("✗ Error: El número debe estar entre " + min + " y " + max);
            }
        } while (numero < min || numero > max);
        return numero;
    }

    public static boolean confirmar(String mensaje) {
        String respuesta;
        do {
            System.out.print(mensaje + " (s/n): ");
            respuesta = scanner.nextLine().trim().toLowerCase();
        } while (!respuesta.equals("s") && !respuesta.equals("n"));
        return respuesta.equals("s");
    }

    public static void cerrarScanner() {
        scanner.close();
    }
}