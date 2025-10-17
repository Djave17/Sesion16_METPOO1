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
    public Categoria guardarCategoria(Categoria categoria) {
        if (categoria.getId() == null) {
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
            return categoria;
        }
        return em.merge(categoria);
    }

    @Override
    public List<Categoria> listarCategoria() {
        List<Categoria> lista = em.createQuery("from Categoria", Categoria.class).getResultList();
        return lista;
    }

    @Override
    public void actualizarCategoria(Long idCategoria) {

    }

    @Override
    public void eliminarCategoria(Long id) {
        Categoria categoria = em.find(Categoria.class, id);
        try {
            if (categoria != null) {
                em.getTransaction().begin();
                em.remove(categoria);
                em.getTransaction().commit();
            }
        }catch (jakarta.persistence.NoResultException exception){
            System.out.println("No se encontro la categoria, consulta invalida.");
            System.out.print("ERROR: ");
            throw new RuntimeException(exception);
        }
    }
    @Override
    public Categoria buscarCategoriaPorId(Long idCategoria) {
        Categoria categoria = em.find(Categoria.class, idCarrera);
        return categoria;
    }

}