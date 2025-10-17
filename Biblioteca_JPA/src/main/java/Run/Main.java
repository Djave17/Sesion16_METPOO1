package Run;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n====Bienvenido a la biblioteca virtual===");
        System.out.print("¿Cuántos libros desea registrar? ");

        int cantidad = leerEntero(sc);

        List<Libro> libros = new ArrayList<>();

        for (int i = 1; i <= cantidad; i++) {
            System.out.println("\nLibro " + i + " de " + cantidad);

            System.out.print("Nombre del libro: ");
            String nombre = sc.nextLine().trim(); // <-- quita el texto extraño "que me hace falta"

            System.out.print("Autor: ");
            String autor = sc.nextLine().trim();

            System.out.print("Categoría: ");
            String categoria = sc.nextLine().trim();

            libros.add(new Libro(nombre, autor, categoria));
        }

        // Resumen
        System.out.println("\n=== Libros registrados ===");
        if (libros.isEmpty()) {
            System.out.println("(ninguno)");
        } else {
            int i = 1;
            for (Libro l : libros) {
                System.out.printf("%d) %s — %s — %s%n",
                        i++, l.nombre, l.autor, l.categoria);
            }
        }
    }
}

// Lee un entero con validación básica
private static int leerEntero(Scanner sc) {
    while (true) {
        String s = sc.nextLine();
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            System.out.print("Ingrese un número válido: ");
        }
    }
}




