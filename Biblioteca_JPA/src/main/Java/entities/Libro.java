package entities;
import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "Libros")
@Getter
@Setter
public class Libro {
    @Id//ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    private String Titulo;

    @Column(length = 30, nullable = false)
    private String Autor;

    @Column(nullable = false)
    private int anioPublicacion;

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", Titulo='" + Titulo + '\'' +
                ", Autor='" + Autor + '\'' +
                ", Años de publicación =" + anioPublicacion +
                '}';
    }

    public Libro( String titulo, String autor, Double aniopublicacion) {
        this.Titulo = titulo;
        this.Autor = autor;
        this.anioPublicacion = anioPublicacion;
    }

}



