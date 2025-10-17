package repository.dao;
import entities.Autor;
import jakarta.persistence.EntityManager;
import repository.IAutor;
import java.util.List;

public class AutorDao implements IAutor{
    private final EntityManager em;
    public AutorDao(EntityManager em) {this.em = em;}

    @Override
    public Autor guardarAutor(Autor autor) {
        if (autor.getId() == null) {
            em.getTransaction().begin();
            em.persist(autor);
            em.getTransaction().commit();
            return autor;
        }
        return em.merge(autor);
    }

    @Override
    public List<Autor> listarAutor() {
        List<Autor> lista = em.createQuery("from Autor", Autor.class).getResultList();
        return lista;
    }

    @Override
    public void actualizarAutor(Long idAutor) {

    }

    @Override
    public void eliminarAutor(Long id) {
        Autor autor = em.find(Autor.class, id);
        try {
            if (autor != null) {
                em.getTransaction().begin();
                em.remove(autor);
                em.getTransaction().commit();
            }
        }catch (jakarta.persistence.NoResultException exception){
            System.out.println("No se encontro el autor, consulta invalida.");
            System.out.print("ERROR: ");
            throw new RuntimeException(exception);
        }
    }
    @Override
    public Autor buscarAutorPorId(Long idCarrera) {
        Autor autor = em.find(Autor.class, idCarrera);
        return autor;
    }



}
