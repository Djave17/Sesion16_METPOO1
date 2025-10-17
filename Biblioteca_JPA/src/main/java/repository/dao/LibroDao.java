package repository.dao;

import jakarta.persistence.EntityManager;

import java.util.List;

public class LibroDao {
    private final EntityManager em;
    public LibroDao(EntityManager em) {this.em = em;}

    @Override
    public Libro guardarLibro(Libro libro) {
        if (libro.getId() == null) {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
            return libro;
        }
        return em.merge(libro);
    }

    @Override
    public List<Libro> listarLibro() {
        List<Libro> lista = em.createQuery("from Libro", Libro.class).getResultList();
        return lista;
    }

    @Override
    public void actualizarLibro(Long idLibro) {

    }

    @Override
    public void eliminarLibro(Long id) {
        Libro libro = em.find(Libro.class, id);
        try {
            if (libro != null) {
                em.getTransaction().begin();
                em.remove(libro);
                em.getTransaction().commit();
            }
        }catch (jakarta.persistence.NoResultException exception){
            System.out.println("No se encontro el libro, consulta invalida.");
            System.out.print("ERROR: ");
            throw new RuntimeException(exception);
        }
    }
    @Override
    public Libro buscarLibroPorId(Long idCarrera) {
        Libro libro = em.find(Libro.class, idCarrera);
        return libro;
    }

}
