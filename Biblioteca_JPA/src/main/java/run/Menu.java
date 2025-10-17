package run;

import java.util.Scanner;

public class Menu {

    Scanner sc = new Scanner(System.in);
    public void menu(){
        int opcion = 0;
        do {
            System.out.println("----- MENÚ -----");
            System.out.println("1. Crear ");
            System.out.println("2. Eliminar ");
            System.out.println("3. Listar ");
            System.out.println("4. Actualizar ");
            System.out.println("5. Salir");
            opcion = sc.nextInt();
            sc.nextLine(); // consumir la nueva línea pendiente

            switch (opcion) {
                case 1:





                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;

            }
        }while (opcion != 5);
    }
}

