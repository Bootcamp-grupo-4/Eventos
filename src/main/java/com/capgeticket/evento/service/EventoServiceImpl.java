package com.capgeticket.evento.service;

import com.capgeticket.evento.dto.EventoDto;
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

        logger.info("Eventos encontrados: {}", eventoDtos.size());

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
}
