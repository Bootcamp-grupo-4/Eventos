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
}
