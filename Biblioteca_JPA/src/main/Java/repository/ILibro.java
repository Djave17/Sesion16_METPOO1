
package repository;
import entities.Libro;
import java.util.List;

public interface ILibro {
    Libro guardar(Libro libro);
    List<Libro> listar();
    Libro buscarPorId(Long id);

}








