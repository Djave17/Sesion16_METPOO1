package repository.dao;

import jakarta.persistence.EntityManager;
import model.Libro;
import repository.ILibro;

import java.util.List;

public class LibroDao implements ILibro {
    private final EntityManager em;
    public LibroDao(EntityManager em) {this.em = em;}

    @Override
    public Libro guardar(Libro libro) {
        if (libro.getId() == null) {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
            return libro;
        }
        return em.merge(libro);
    }

    @Override
    public List<Libro> listar() {
        List<Libro> lista = em.createQuery("from Libro", Libro.class).getResultList();
        return lista;
    }

    @Override
    public Libro buscarPorId(Long idCarrera) {
        Libro libro = em.find(Libro.class, idCarrera);
        return libro;
    }

}