package repository;
import model.Categoria;

import java.util.List;

public interface ICategoria {

    Categoria guardar(Categoria categoria);
    List<Categoria> listar();
    Categoria buscarPorId(Long id);

}
