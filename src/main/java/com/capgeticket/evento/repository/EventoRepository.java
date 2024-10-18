package com.capgeticket.evento.repository;

import com.capgeticket.evento.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento,Long> {
    /**
     * Busca eventos cuyo nombre contenga el texto especificado, ignorando mayúsculas y minúsculas.
     *
     * @param nombre El nombre a buscar en los eventos.
     * @return Lista de eventos que coinciden con el criterio de búsqueda.
     */
    List<Evento> findByNombreContainingIgnoreCase(String nombre);
    /**
     * Busca eventos cuyo género contenga el texto especificado
     *
     * @param genero El género a buscar en los eventos.
     * @return Lista de eventos que coinciden con el criterio de búsqueda.
     */
    List<Evento> findByGenero(String genero);
}
