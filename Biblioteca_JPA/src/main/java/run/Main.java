package run;

import config.JPAUtil;
import jakarta.persistence.EntityManager;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import repository.dao.AutorDao;
import repository.dao.CategoriaDao;
import repository.dao.LibroDao;

public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        Scanner sc = new Scanner(System.in);
        AutorDao aDao = new AutorDao(em);
        LibroDao lDao = new LibroDao(em);
        CategoriaDao cDao = new CategoriaDao(em);

        int op;
        do {
            System.out.println("1. Registrar autores");
            System.out.println("2. Registrar categorías");
            System.out.println("3. Registrar libros");
            System.out.println("4. Listar libros con su autor y categorías");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    System.out.println("\n--- Registro de Autores  ---");
                    System.out.print("Cantidad de autores a ingresar: ");
                    int cantAutores = sc.nextInt();
                    sc.nextLine();

                    for(int i = 0; i < cantAutores; i++) {
                        System.out.println("\nAutor #" + (i + 1));
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Nacionalidad: ");
                        String nacionalidad = sc.nextLine();
                        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
                        LocalDate fecha = LocalDate.parse(sc.nextLine());
                        Autor nuevoAutor = new Autor(nombre, nacionalidad, fecha);
                        aDao.guardar(nuevoAutor);
                        System.out.println("Autor agregado correctamente.");
                    }
                    break;
                case 2:
                    System.out.println("\n--- Registro de Categorias ---");
                    System.out.print("Cantidad de categorias a ingresar: ");
                    int cantCategorias = sc.nextInt();
                    sc.nextLine();

                    for(int i = 0; i < cantCategorias; i++) {
                        System.out.println("\nCategoría #" + (i + 1));
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        Categoria nueva = new Categoria(nombre);
                        cDao.guardar(nueva);
                        System.out.println("Categoría agregada correctamente.");
                    }
                    break;
                case 3:
                    System.out.println("\n--- Registro de Libros ---");
                    System.out.print("Cantidad de libros a ingresar: ");
                    int cantLibros = sc.nextInt();
                    sc.nextLine();
                    int i = 0;

                    for(; i < cantLibros; i++) {
                        System.out.println("\nLibro #" + (i + 1));
                        System.out.print("Título: ");
                        String titulo = sc.nextLine();
                        System.out.print("Año de publicación: ");
                        int anio = sc.nextInt();
                        sc.nextLine();
                        System.out.println("\nAutores disponibles:");

                        for(Autor a : aDao.listar()) {
                            PrintStream var41 = System.out;
                            Long var43 = a.getId();
                            var41.println(var43 + " - " + a.getNombre());
                        }

                        System.out.print("Seleccione ID del autor: ");
                        Long idAutor = sc.nextLong();
                        sc.nextLine();
                        Autor autor = aDao.buscarPorId(idAutor);
                        if (autor == null) {
                            System.out.println("Autor no encontrado, se omite este libro.");
                        } else {
                            System.out.println("\nCategorías disponibles:");

                            for(Categoria c : cDao.listar()) {
                                PrintStream var42 = System.out;
                                Long var44 = c.getId();
                                var42.println(var44 + " - " + c.getNombre());
                            }

                            System.out.print("Ingrese los IDs de las categorías separadas por coma: ");
                            String entrada = sc.nextLine();
                            String[] partes = entrada.split(",");
                            List<Categoria> categoriasSeleccionadas = new ArrayList();

                            for(String idStr : partes) {
                                try {
                                    Long idCat = Long.parseLong(idStr.trim());
                                    Categoria cat = cDao.buscarPorId(idCat);
                                    if (cat != null) {
                                        categoriasSeleccionadas.add(cat);
                                    }
                                } catch (NumberFormatException var24) {
                                    System.out.println("ID inválido ignorado: " + idStr);
                                }
                            }

                            Libro nuevoLibro = new Libro(titulo, anio, autor);
                            nuevoLibro.getCategoria().addAll(categoriasSeleccionadas);
                            lDao.guardar(nuevoLibro);
                            System.out.println("Libro agregado.");
                        }
                    }
                    break;
                case 4:
                    System.out.println("\n--- Listado de libros ---");
                    List<Libro> libros = lDao.listar();
                    if (libros.isEmpty()) {
                        System.out.println("No hay libros registrados.");
                        break;
                    }

                    for(Libro l : libros) {
                        PrintStream var10000 = System.out;
                        String var10001 = l.getTitulo();
                        var10000.println(var10001 + " (" + l.getAnioPublicacion() + ")");
                        System.out.println("Autor: " + l.getAutor().getNombre());
                        System.out.print("Categorías: ");
                        l.getCategoria().forEach((cx) -> System.out.print(cx.getNombre() + " "));
                        System.out.println("\n--------------------------");
                    }
                    break;
                case 5:
                    System.out.println("Saliendo..");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while(op != 5);

        em.close();
    }
}
