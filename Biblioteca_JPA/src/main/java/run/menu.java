package run;

import config.JPAUtil;
import entities.Autor;
import entities.Categoria;
import entities.Libro;
import jakarta.persistence.EntityManager;
import repository.dao.AutorDao;
import repository.dao.CategoriaDao;
import repository.dao.LibroDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuApp {

    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        AutorDao autorDao = new AutorDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);
        LibroDao libroDao = new LibroDao(em);

        Scanner entrada = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Autores");
            System.out.println("2. Categorías");
            System.out.println("3. Libros");
            System.out.println("4. Salir");
            System.out.print("Opción: ");
            int opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {
                case 1 -> menuAutores(entrada, autorDao);
                case 2 -> menuCategorias(entrada, categoriaDao);
                case 3 -> menuLibros(entrada, libroDao, autorDao, categoriaDao);
                case 4 -> salir = true;
                default -> System.out.println("Opción inválida");
            }
        }

        entrada.close();
        em.close();
        JPAUtil.close();
    }

    private static void menuAutores(Scanner entrada, AutorDao dao) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- AUTORES ---");
            System.out.println("1. Agregar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar");
            System.out.println("4. Eliminar");
            System.out.println("5. Volver");
            System.out.print("Opción: ");
            int op = entrada.nextInt(); entrada.nextLine();

            switch (op) {
                case 1 -> {
                    Autor autor = new Autor();
                    System.out.print("Nombre: "); autor.setNombre(entrada.nextLine());
                    System.out.print("Nacionalidad: "); autor.setNacionalidad(entrada.nextLine());
                    System.out.print("Fecha Nac. (yyyy-mm-dd): "); autor.setFechaNacimiento(entrada.nextLine());
                    dao.guardarAutor(autor);
                }
                case 2 -> dao.listarAutor().forEach(System.out::println);
                case 3 -> {
                    System.out.print("ID: ");
                    Autor a = dao.buscarAutorPorId(entrada.nextLong()); entrada.nextLine();
                    System.out.println(a != null ? a : "No encontrado");
                }
                case 4 -> {
                    System.out.print("ID: ");
                    dao.eliminarAutor(entrada.nextLong()); entrada.nextLine();
                }
                case 5 -> back = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void menuCategorias(Scanner entrada, CategoriaDao dao) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- CATEGORIAS ---");
            System.out.println("1. Agregar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar");
            System.out.println("4. Eliminar");
            System.out.println("5. Volver");
            System.out.print("Opción: ");
            int op = entrada.nextInt(); entrada.nextLine();

            switch (op) {
                case 1 -> {
                    Categoria cat = new Categoria();
                    System.out.print("Nombre: "); cat.setNombre(entrada.nextLine());
                    dao.guardarCategoria(cat);
                }
                case 2 -> dao.listarCategoria().forEach(System.out::println);
                case 3 -> {
                    System.out.print("ID: ");
                    Categoria c = dao.buscarCategoriaPorId(entrada.nextLong()); entrada.nextLine();
                    System.out.println(c != null ? c : "No encontrada");
                }
                case 4 -> {
                    System.out.print("ID: ");
                    dao.eliminarCategoria(entrada.nextLong()); entrada.nextLine();
                }
                case 5 -> back = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void menuLibros(Scanner entrada, LibroDao dao, AutorDao autorDao, CategoriaDao catDao) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- LIBROS ---");
            System.out.println("1. Agregar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar");
            System.out.println("4. Eliminar");
            System.out.println("5. Volver");
            System.out.print("Opción: ");
            int op = entrada.nextInt(); entrada.nextLine();

            switch (op) {
                case 1 -> {
                    Libro libro = new Libro();

                    // Autor
                    List<Autor> autores = autorDao.listarAutor();
                    if (autores.isEmpty()) { System.out.println("No hay autores"); break; }
                    autores.forEach(a -> System.out.println(a.getId() + ": " + a.getNombre()));
                    System.out.print("ID autor: "); Long idAutor = entrada.nextLong(); entrada.nextLine();
                    Autor autor = autorDao.buscarAutorPorId(idAutor);
                    if (autor == null) { System.out.println("Autor no encontrado"); break; }

                    // Categirias
                    List<Categoria> categorias = catDao.listarCategoria();
                    if (categorias.isEmpty()) { System.out.println("No hay categorías"); break; }
                    List<Categoria> seleccionadas = new ArrayList<>();
                    while (true) {
                        categorias.forEach(c -> System.out.println(c.getId() + ": " + c.getNombre()));
                        System.out.print("ID categoría (0 terminar): "); Long idCat = entrada.nextLong(); entrada.nextLine();
                        if (idCat == 0) break;
                        Categoria cat = catDao.buscarCategoriaPorId(idCat);
                        if (cat != null) seleccionadas.add(cat);
                    }

                    System.out.print("Título: "); libro.setTitulo(entrada.nextLine());
                    System.out.print("Año: "); libro.setAnioPublicacion(entrada.nextInt()); entrada.nextLine();
                    libro.setAutor(autor.getNombre());
                    libro.setCategorias(seleccionadas); // si Libro yiene lista de categorías
                    dao.guardarLibro(libro);
                }
                case 2 -> dao.listarLibro().forEach(System.out::println);
                case 3 -> {
                    System.out.print("ID: "); Libro l = dao.buscarLibroPorId(entrada.nextLong()); entrada.nextLine();
                    System.out.println(l != null ? l : "No encontrado");
                }
                case 4 -> {
                    System.out.print("ID: "); dao.eliminarLibro(entrada.nextLong()); entrada.nextLine();
                }
                case 5 -> back = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }
}