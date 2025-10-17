package model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categoria", uniqueConstraints = @UniqueConstraint(columnNames = "nombre"))
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nombre;


    @ManyToMany(mappedBy = "categorias")
    private Set<Libro> libros = new HashSet<>();

    public Categoria() {}

    public Categoria(String nombre) { this.nombre = nombre; }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Set<Libro> getLibros() { return libros; }
}
