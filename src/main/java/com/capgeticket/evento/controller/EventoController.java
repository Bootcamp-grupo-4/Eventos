package com.capgeticket.evento.controller;

import com.capgeticket.evento.dto.EventoDto;
import com.capgeticket.evento.exception.EventoNotFoundException;
import com.capgeticket.evento.service.EventoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/evento")
public class EventoController {
    private static final Logger logger = LoggerFactory.getLogger(EventoController.class);

    @Autowired
    private EventoService service;

    /**
     * Obtener todos los eventos.
     *
     * @return ResponseEntity con la colección de todos los eventos en formato EventoDto.
     */
    @GetMapping
    public ResponseEntity<Collection<EventoDto>> findAll() {
        logger.info("Iniciando búsqueda de todos los eventos");

        Collection<EventoDto> eventos = service.findAll();

        logger.info("Eventos encontrados: {}", eventos.size());

        return ResponseEntity.ok(eventos);
    }

    /**
     * Maneja solicitudes POST para agregar un nuevo evento.
     *
     * @param eventoDto El DTO del evento a agregar.
     * @return El ResponseEntity con el evento guardado y el estado HTTP 201.
     * @throws IllegalArgumentException Si el eventoDto es nulo o inválido.
     */
    @PostMapping("/")
    public ResponseEntity<EventoDto> add(@RequestBody EventoDto eventoDto) {
        if (eventoDto == null || eventoDto.getNombre() == null || eventoDto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El evento no puede ser nulo o tener un nombre vacío");
        }
        EventoDto savedEvento = service.add(eventoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvento);
    }

    /**
     * Maneja las solicitudes GET para buscar eventos por nombre.
     *
     * @param name el nombre del evento a buscar; no puede ser nulo o vacío
     * @return una respuesta HTTP que contiene una colección de {@link EventoDto} encontrados
     * @throws IllegalArgumentException si el nombre del evento es nulo o vacío
     * @throws EventoNotFoundException en service si no se encuentran eventos que coincidan con el nombre proporcionado
     */
    @GetMapping("/nombre")
    public ResponseEntity<Collection<EventoDto>> findByName(@RequestParam String name) {
        // Validar nombre
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del evento no puede ser nulo o vacío");
        }

        List<EventoDto> eventos = service.findByName(name);
        return ResponseEntity.ok(eventos);
    }
}

