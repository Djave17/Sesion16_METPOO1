package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/*Autor → Libro: @OneToMany (un autor tiene muchos libros)
Libro → Autor: @ManyToOne (cada libro tiene un autor)
Libro ↔ Categoria: @ManyToMany (libros pueden tener varias categorías y viceversa)
*/
@Entity
@Getter
@Setter
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Titulo
    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private Integer anioPublicacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_libro_autor"))
    private Autor autor;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "libro_categoria",
            joinColumns = @JoinColumn(name = "libro_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id"))
    private List<Categoria> categoria = new ArrayList<>();


    public Libro() {}

    public Libro(String titulo, Integer anioPublicacion, Autor autor) {
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.autor = autor;
    }


    public Collection<Categoria> getCategorias() {
        return categoria;
    }
}
