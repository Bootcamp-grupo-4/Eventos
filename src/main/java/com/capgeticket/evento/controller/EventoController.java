package com.capgeticket.evento.controller;

import com.capgeticket.evento.dto.EventoDto;
import com.capgeticket.evento.exception.EventoNotFoundException;
import com.capgeticket.evento.service.EventoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/evento")
@Tag(name="Evento", description = "Api evento")
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

    @GetMapping("/{id}")
    public ResponseEntity<EventoDto> findById(@PathVariable("id") Long id) {
        logger.info("Recibida petición, iniciando findById");

        EventoDto e = service.findById(id);

        return ResponseEntity.ok(e);
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
     * Elimina un evento por su ID.
     *
     * @param id El ID del evento a eliminar.
     * @return ResponseEntity con un valor booleano indicando si la eliminación fue exitosa o no.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        logger.info("Petición para eliminar el evento con ID: {}", id);

        // Verificar si el evento existe
        if (!service.existsById(id)) {
            logger.warn("El evento con ID {} no existe. Lanzando EventoNotFoundException", id);
            throw new EventoNotFoundException(id);
        }

        // Si existe, proceder a eliminar
        boolean isDeleted = service.deleteById(id);

        if (isDeleted) {
            logger.info("El evento con ID {} fue eliminado con éxito", id);
        } else {
            logger.warn("El evento con ID {} no pudo ser eliminado", id);
        }

        // Devolver respuesta con estado OK y el resultado de la eliminación
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
}





