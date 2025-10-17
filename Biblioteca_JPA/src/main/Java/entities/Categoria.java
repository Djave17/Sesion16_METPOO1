package entities;
import jakarta.persistence.*;
import lombok.Setter;


@Entity
@Table(name = "Categorias")
@Getter
@Setter

public class Categoria {
    @Id//ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    private String Nombre;


    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", Nombre='" + Nombre + '\'' +
                '}';
    }

    public Categoria(Long id, String nombre) {
        this.id = id;
        this.Nombre = nombre;

    }

}




