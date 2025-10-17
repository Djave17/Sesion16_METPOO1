package run;

import config.JPAUtil;
import jakarta.persistence.*;
import model.Autor;
import model.Categoria;
import model.Libro;
import repository.dao.AutorDao;
import repository.dao.CategoriaDao;
import repository.dao.LibroDao;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static run.Menu.iniciar;

public class Main {

    static EntityManager em = JPAUtil.getEntityManager();

    static AutorDao autorDao = new AutorDao(em);
    static LibroDao libroDao = new LibroDao(em);
    static CategoriaDao categoriaDao = new CategoriaDao(em);
    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        iniciar();
    }


}
