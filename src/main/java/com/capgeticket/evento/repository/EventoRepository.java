package com.capgeticket.evento.repository;

import com.capgeticket.evento.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
     * Encuentra los eventos según la localidad (ciudad).
     *
     * @param localidad La ciudad por la que se desea filtrar los eventos.
     * @return Lista de eventos en la ciudad dada.
     */
    @Query("SELECT e FROM Evento e WHERE e.localidad = :localidad")
    List<Evento> findByCity(String localidad);

    /**
     * Busca eventos cuyo género contenga el texto especificado
     *
     * @param genero El género a buscar en los eventos.
     * @return Lista de eventos que coinciden con el criterio de búsqueda.
     */
    List<Evento> findByGenero(String genero);

    @Query("SELECT e FROM Evento e WHERE e.mostrar")
    List<Evento> findAllMostrar();

    @Query("UPDATE Evento e SET e.mostrar = false WHERE e.id = ?1")
    @Modifying
    void deleteById(Long id);
}
