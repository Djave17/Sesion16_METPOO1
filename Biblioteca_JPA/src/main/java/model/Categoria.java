package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/*Autor → Libro: @OneToMany (un autor tiene muchos libros)
Libro → Autor: @ManyToOne (cada libro tiene un autor)
Libro ↔ Categoria: @ManyToMany (libros pueden tener varias categorías y viceversa)
*/
@Entity

public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String nombre;

}
