package repository.dao;
import model.Autor;
import jakarta.persistence.EntityManager;
import repository.IAutor;
import java.util.List;

public class AutorDao implements IAutor{
    private final EntityManager em;
    public AutorDao(EntityManager em) {this.em = em;}

    @Override
    public Autor guardar(Autor autor) {
        if (autor.getId() == null) {
            em.getTransaction().begin();
            em.persist(autor);
            em.getTransaction().commit();
            return autor;
        }
        return em.merge(autor);
    }

    @Override
    public List<Autor> listar() {
        List<Autor> lista = em.createQuery("from Autor", Autor.class).getResultList();
        return lista;
    }

    @Override
    public Autor buscarPorId(Long idCarrera) {
        Autor autor = em.find(Autor.class, idCarrera);
        return autor;
    }



}