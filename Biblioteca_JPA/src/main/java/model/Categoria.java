package model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(mappedBy = "categoria")
    private Set<Libro> libros = new HashSet<>();

    public Categoria() {}

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public Collection<Libro> getLibros() {
        return libros;
    }

    public Object getNombre() {
        return nombre;
    }
}
