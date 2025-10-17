package run;

import config.JPAUtil;
import model.Autor;
import model.Categoria;
import model.Libro;
import jakarta.persistence.EntityManager;
import repository.dao.AutorDao;
import repository.dao.CategoriaDao;
import repository.dao.LibroDao;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {

    // === Igual que tu plantilla ===
    static EntityManager em = JPAUtil.getEntityManager();

    static AutorDao autorDao = new AutorDao(em);
    static CategoriaDao categoriaDao = new CategoriaDao(em);
    static LibroDao libroDao = new LibroDao(em);

    public static Scanner sc = new Scanner(System.in);

    public static void iniciar() {
        int opcion = 0;
        do {
            System.out.println("----- MENÚ BIBLIOTECA -----");
            System.out.println("1. Agregar autor");
            System.out.println("2. Agregar categoría");
            System.out.println("3. Agregar libro");
            System.out.println("4. Listar autores");
            System.out.println("5. Listar categorías");
            System.out.println("6. Listar libros");
            System.out.println("7. Salir");
            opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> crearAutor();
                case 2 -> crearCategoria();
                case 3 -> crearLibro();
                case 4 -> listarAutores();
                case 5 -> listarCategorias();
                case 6 -> listarLibros();
                case 7 -> {
                    System.out.println("Bye");
                    if (em != null && em.isOpen()) em.close();
                }
                default -> System.out.println("Opción no válida");
            }
            System.out.println();
        } while (opcion != 7);
    }

    // =============== AGREGAR =================

    private static void crearAutor() {
        System.out.println("Crear autor:");

        String nombre = leerTexto("Nombre: ");
        String nacionalidad = leerTexto("Nacionalidad (opcional): ");
        if (nacionalidad.isBlank()) nacionalidad = null;

        String f = leerTexto("Fecha nacimiento YYYY-MM-DD (opcional): ");
        LocalDate fecha = f.isBlank() ? null : LocalDate.parse(f);

        Autor a = new Autor();
        a.setNombre(nombre);
        a.setNacionalidad(nacionalidad);
        a.setFechaNacimiento(fecha);

        autorDao.guardar(a);
        System.out.println("Autor creado (id=" + a.getId() + ").");
    }

    private static void crearCategoria() {
        System.out.println("Crear categoría:");

        String nombre = leerTexto("Nombre: ");
        Categoria c = new Categoria();
        c.setNombre(nombre);

        categoriaDao.guardar(c);
        System.out.println("Categoría creada (id=" + c.getId() + ").");
    }

    private static void crearLibro() {
        System.out.println("Crear libro:");
        // Mostrar disponibles para elegir
        System.out.println("Autores:");
        autorDao.listar().forEach(a -> System.out.printf("(%d) %s%n", a.getId(), a.getNombre()));
        System.out.println("Categorías:");
        categoriaDao.listar().forEach(c -> System.out.printf("(%d) %s%n", c.getId(), c.getNombre()));

        String titulo = leerTexto("Título: ");
        Integer anio = leerEntero("Año de publicación (int): ");
        Long autorId = leerLong("ID del autor: ");

        Autor autor = em.find(Autor.class, autorId);
        if (autor == null) {
            System.out.println("Autor no encontrado. Cancela.");
            return;
        }

        String categeorias = leerTexto("IDs de categorías (coma, opcional): ");

        Libro l = new Libro();
        l.setTitulo(titulo);
        l.setAnioPublicacion(anio);
        l.setAutor(autor);

        if (!categeorias.isBlank()) {
            List<Long> ids = Arrays.stream(categeorias.split(","))
                    .map(String::trim).filter(s -> !s.isEmpty())
                    .map(Long::parseLong).toList();
            for (Long cid : ids) {
                Categoria c = em.find(Categoria.class, cid);
                if (c != null) {
                    l.getCategorias().add(c);
                    c.getLibros().add(l); // sincronizar lado inverso
                }
            }
        }

        libroDao.guardar(l);
        System.out.println("Libro creado (id=" + l.getId() + ").");
    }

    // =============== LISTAR =================

    private static void listarAutores() {
        System.out.println("Lista de autores:");
        var autores = autorDao.listar();
        if (autores.isEmpty()) { System.out.println("(sin registros)"); return; }
        autores.forEach(a ->
                System.out.printf("(%d) %s | %s | %s%n",
                        a.getId(),
                        a.getNombre(),
                        a.getNacionalidad() == null ? "-" : a.getNacionalidad(),
                        a.getFechaNacimiento() == null ? "-" : a.getFechaNacimiento().toString()
                ));
    }

    private static void listarCategorias() {
        System.out.println("Lista de categorías:");
        var categorias = categoriaDao.listar();
        if (categorias.isEmpty()) { System.out.println("(sin registros)"); return; }
        categorias.forEach(c ->
                System.out.printf("(%d) %s%n",
                        c.getId(), c.getNombre()));
    }

    private static void listarLibros() {
        System.out.println("Lista de libros:");
        var libros = libroDao.listar(); // mantengo tu nombre de método
        if (libros.isEmpty()) { System.out.println("(sin registros)"); return; }

        // Para evitar problemas de lazy, mostramos básico (id, título, año).
        // Si tus DAO ya hacen JOIN FETCH, puedes imprimir autor/categorías sin miedo.
        libros.forEach(l ->
                System.out.printf("(%d) %s (%d)%n",
                        l.getId(), l.getTitulo(), l.getAnioPublicacion()));
    }

    // =============== HELPERS INPUT =================

    private static String leerTexto(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
    private static int leerEntero(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) { System.out.print("Ingrese número válido: "); sc.next(); }
        int v = sc.nextInt(); sc.nextLine(); return v;
    }
    private static long leerLong(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextLong()) { System.out.print("Ingrese número válido: "); sc.next(); }
        long v = sc.nextLong(); sc.nextLine(); return v;
    }
}
