package com.capgeticket.evento.service;

import com.capgeticket.evento.dto.EventoDto;
import com.capgeticket.evento.exception.EventoNotFoundException;
import com.capgeticket.evento.model.Evento;
import com.capgeticket.evento.repository.EventoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoServiceImpl implements EventoService{
    private static final Logger logger = LoggerFactory.getLogger(EventoServiceImpl.class);

    @Autowired
    private EventoRepository repository;

    /**
     * Obtener todos los eventos.
     *
     * @return una lista de todos los eventos en formato EventoDto.
     */
    @Override
    public List<EventoDto> findAll() {
        logger.info("Iniciando la búsqueda de todos los eventos en EventoServiceImpl");

        List<Evento> eventos = repository.findAll();
        List<EventoDto> eventoDtos = EventoDto.of(eventos);

        logger.info("Eventos encontrados: {} en EventoServiceImpl", eventoDtos.size());

        return eventoDtos;
    }
    /**
     * Agrega un nuevo evento a la base de datos.
     *
     * @param eventoDto El DTO del evento a agregar.
     * @return El DTO del evento guardado con el ID generado.
     * @throws IllegalArgumentException Si el eventoDto es nulo o inválido.
     */
    @Override
    public EventoDto add(EventoDto eventoDto) {
        if (eventoDto == null) {
            throw new IllegalArgumentException("El evento no puede ser nulo");
        }
        // Convertir de EventoDto a Evento
        Evento evento = new Evento();
        evento.setNombre(eventoDto.getNombre());
        evento.setDescripcion(eventoDto.getDescripcion());
        evento.setFechaEvento(eventoDto.getFechaEvento());
        evento.setPrecioMinimo(eventoDto.getPrecioMinimo());
        evento.setPrecioMaximo(eventoDto.getPrecioMaximo());
        evento.setLocalidad(eventoDto.getLocalidad());
        evento.setNombreDelRecinto(eventoDto.getNombreDelRecinto());
        evento.setGenero(eventoDto.getGenero());
        evento.setMostrar(eventoDto.getMostrar());

        Evento savedEvento = repository.save(evento);

        return EventoDto.of(savedEvento);
    }
    /**
     * Verifica si un evento con el ID dado existe.
     *
     * @param id El ID del evento a verificar.
     * @return true si el evento existe, false si no.
     */
    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    /**
     * Verifica si un evento con el ID dado existe y lo elimina si es encontrado.
     *
     * @param id El ID del evento a eliminar.
     * @return true si el evento fue eliminado correctamente, false si no fue encontrado.
     */
    @Override
    public boolean deleteById(Long id) {
        logger.info("Intentando eliminar el evento con ID: {} en EventoServiceImpl", id);

        if (existsById(id)) {
            repository.deleteById(id);
            logger.info("Evento con ID {} eliminado exitosamente en EventoServiceImpl", id);
            return true;
        } else {
            logger.warn("El evento con ID {} no existe en EventoServiceImpl", id);
            return false;
        }

    }

    /**
     * Busca eventos por nombre, ignorando mayúsculas y minúsculas.
     *
     * @param name el nombre del evento a buscar; no puede ser nulo o vacío
     * @return una lista de {@link EventoDto} que coinciden con el nombre proporcionado
     * @throws EventoNotFoundException si no se encuentran eventos que coincidan con el nombre proporcionado
     */
    @Override
    public List<EventoDto> findByName(String name) {
        logger.info(name);

        List<Evento> eventos = repository.findByNombreContainingIgnoreCase(name);

        if (eventos.isEmpty()) {
            throw new EventoNotFoundException();
        }

        return EventoDto.of(eventos);
    }

}
