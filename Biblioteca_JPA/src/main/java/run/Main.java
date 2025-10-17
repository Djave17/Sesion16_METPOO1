package run;

import config.JPAUtil;
import jakarta.persistence.*;
import model.Autor;
import model.Categoria;
import model.Libro;
import repository.dao.AutorDao;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    static EntityManager em = JPAUtil.getEntityManager();

    static AutorDao autorDao = new AutorDao(em);


    public static void main(String[] args) {

        


    }
}
