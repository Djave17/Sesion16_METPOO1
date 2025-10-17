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

public class Menu {

    private EntityManager em;
    private AutorDao autorDao;
    private CategoriaDao categoriaDao;
    private LibroDao libroDao;
    private Scanner entrada;

    public Menu() {
        em = JPAUtil.getEntityManager();
        autorDao = new AutorDao(em);
        categoriaDao = new CategoriaDao(em);
        libroDao = new LibroDao(em);
        entrada = new Scanner(System.in);
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Autores");
            System.out.println("2. Categorías");
            System.out.println("3. Libros");
            System.out.println("4. Salir");
            System.out.print("Opción: ");
            int opcion = entrada.nextInt(); entrada.nextLine();

            switch (opcion) {
                case 1 -> menuAutores();
                case 2 -> menuCategorias();
                case 3 -> menuLibros();
                case 4 -> salir = true;
                default -> System.out.println("Opción inválida");
            }
        }

        entrada.close();
        em.close();
        JPAUtil.close();
    }

    private void menuAutores() {
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
                    System.out.print("Fecha Nac: "); autor.setFechaNacimiento(entrada.nextLine());
                    autorDao.guardarAutor(autor);
                    System.out.println("Autor agregado.");
                }
                case 2 -> autorDao.listarAutor().forEach(System.out::println);
                case 3 -> {
                    System.out.print("ID: ");
                    Autor a = autorDao.buscarAutorPorId(entrada.nextLong()); entrada.nextLine();
                    System.out.println(a != null ? a : "No encontrado");
                }
                case 4 -> {
                    System.out.print("ID: ");
                    autorDao.eliminarAutor(entrada.nextLong()); entrada.nextLine();
                    System.out.println("Autor eliminado (si existía).");
                }
                case 5 -> back = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void menuCategorias() {
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
                    categoriaDao.guardarCategoria(cat);
                    System.out.println("Categoría agregada.");
                }
                case 2 -> categoriaDao.listarCategoria().forEach(System.out::println);
                case 3 -> {
                    System.out.print("ID: ");
                    Categoria c = categoriaDao.buscarCategoriaPorId(entrada.nextLong()); entrada.nextLine();
                    System.out.println(c != null ? c : "No encontrada");
                }
                case 4 -> {
                    System.out.print("ID: ");
                    categoriaDao.eliminarCategoria(entrada.nextLong()); entrada.nextLine();
                    System.out.println("Categoría eliminada (si existía).");
                }
                case 5 -> back = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void menuLibros() {
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
                    if (autorDao.listarAutor().isEmpty() || categoriaDao.listarCategoria().isEmpty()) {
                        System.out.println("Deben existir al menos un autor y una categoría.");
                        break;
                    }

                    Libro libro = new Libro();

                    //autor
                    autorDao.listarAutor().forEach(a -> System.out.println(a.getId() + ": " + a.getNombre()));
                    System.out.print("ID autor: "); Long idAutor = entrada.nextLong(); entrada.nextLine();
                    Autor autor = autorDao.buscarAutorPorId(idAutor);
                    if (autor == null) { System.out.println("Autor no encontrado."); break; }

                    //categoruas
                    List<Categoria> seleccionadas = new ArrayList<>();
                    while (true) {
                        categoriaDao.listarCategoria().forEach(c -> System.out.println(c.getId() + ": " + c.getNombre()));
                        System.out.print("ID categoría (0 terminar): ");
                        Long idCat = entrada.nextLong(); entrada.nextLine();
                        if (idCat == 0) break;
                        Categoria cat = categoriaDao.buscarCategoriaPorId(idCat);
                        if (cat != null) seleccionadas.add(cat);
                    }

                    // libro
                    System.out.print("Título: "); libro.setTitulo(entrada.nextLine());
                    System.out.print("Año: "); libro.setAnioPublicacion(entrada.nextInt()); entrada.nextLine();
                    libro.setAutor(autor.getNombre());
                    libro.setCategorias(seleccionadas);

                    libroDao.guardarLibro(libro);
                    System.out.println("Libro agregado.");
                }
                case 2 -> libroDao.listarLibro().forEach(System.out::println);
                case 3 -> {
                    System.out.print("ID: ");
                    Libro l = libroDao.buscarLibroPorId(entrada.nextLong()); entrada.nextLine();
                    System.out.println(l != null ? l : "No encontrado");
                }
                case 4 -> {
                    System.out.print("ID: ");
                    libroDao.eliminarLibro(entrada.nextLong()); entrada.nextLine();
                    System.out.println("Libro eliminado (si existía).");
                }
                case 5 -> back = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }
}
