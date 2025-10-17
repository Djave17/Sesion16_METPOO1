package repository.dao;
import model.Categoria;
import jakarta.persistence.EntityManager;
import repository.IAutor;
import repository.ICategoria;

import java.util.List;

public class CategoriaDao implements ICategoria {
    private final EntityManager em;
    public CategoriaDao(EntityManager em) {this.em = em;}

    @Override
    public Categoria guardar(Categoria categoria) {
        if (categoria.getId() == null) {
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
            return categoria;
        }
        return em.merge(categoria);
    }

    @Override
    public List<Categoria> listar() {
        List<Categoria> lista = em.createQuery("from Categoria", Categoria.class).getResultList();
        return lista;
    }

    @Override
    public Categoria buscarPorId(Long idCategoria) {
        Categoria categoria = em.find(Categoria.class, idCategoria);
        return categoria;
    }

}