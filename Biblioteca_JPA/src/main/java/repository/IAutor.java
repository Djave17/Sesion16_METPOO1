
package repository;
import model.Autor;
import java.util.List;

public interface IAutor {
    Autor guardar(Autor autor);
    List<Autor> listar();
    Autor buscarPorId(Long id);


}
