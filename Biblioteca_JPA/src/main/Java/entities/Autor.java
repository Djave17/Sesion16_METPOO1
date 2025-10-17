package entities;
import lombok.Setter;
import jakarta.persistence.*;


@Entity
@Table(name = "Autores")
@Getter
@Setter

public class Autor {

    @Id//ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    private String Nombre;

    @Column(length = 30, nullable = false)
    private String Nacionalidad;

    @Column(nullable = false)
    private String FechaNacimiento;

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", Nombre='" + Nombre + '\'' +
                ", Nacionalidad ='" + Nacionalidad + '\'' +
                ", Fecha de Nacimiento =" + FechaNacimiento +
                '}';
    }

    public Autor(String nombre, String nacionalidad, String fechaNacimiento) {
        this.Nombre = nombre;
        this.Nacionalidad = nacionalidad;
        this.FechaNacimiento = fechaNacimiento;
    }
}








