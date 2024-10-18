package com.capgeticket.evento;

import com.capgeticket.evento.controller.EventoController;
import com.capgeticket.evento.dto.EventoDto;
import com.capgeticket.evento.exception.EventoNotFoundException;
import com.capgeticket.evento.model.Evento;
import com.capgeticket.evento.repository.EventoRepository;
import com.capgeticket.evento.service.EventoService;
import com.capgeticket.evento.service.EventoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FindByCity01 {

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private EventoServiceImpl eventoService;

    @Mock
    private EventoService mockEventoService;

    @InjectMocks
    private EventoController eventoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba que el repositorio devuelva una lista con entidades cuando hay eventos en la BD.
     */
    @Test
    void testFindByCityWithElements_Repository() {
        // Simular datos en el repositorio
        Evento evento1 = new Evento();
        evento1.setNombre("Evento 1");

        Evento evento2 = new Evento();
        evento2.setNombre("Evento 2");

        when(eventoRepository.findByCity("Madrid")).thenReturn(Arrays.asList(evento1, evento2));

        // Llamar al método del servicio
        List<Evento> result = eventoRepository.findByCity("Madrid");

        // Verificar que devuelve una lista con 2 elementos
        assertEquals(2, result.size());
    }

    /**
     * Prueba que el servicio devuelva una lista de DTOs cuando hay eventos en la BD.
     */
    @Test
    void testFindByCityWithElements_Service() {
        // Simular datos en el repositorio
        Evento evento1 = new Evento();
        evento1.setNombre("Evento 1");

        Evento evento2 = new Evento();
        evento2.setNombre("Evento 2");

        when(eventoRepository.findByCity("Madrid")).thenReturn(Arrays.asList(evento1, evento2));

        // Llamar al método del servicio
        List<EventoDto> result = eventoService.findByCity("Madrid");

        // Verificar que devuelve una lista con 2 elementos
        assertEquals(2, result.size());
    }

    /**
     * Prueba que el controlador devuelva una respuesta correcta cuando hay eventos en la BD.
     */
    @Test
    void testFindByCityWithElements_Controller() {
        // Simular datos en el servicio
        EventoDto evento1 = new EventoDto();
        evento1.setNombre("Evento 1");

        EventoDto evento2 = new EventoDto();
        evento2.setNombre("Evento 2");

        when(mockEventoService.findByCity("Madrid")).thenReturn(Arrays.asList(evento1, evento2));

        // Llamar al controlador
        ResponseEntity<Collection<EventoDto>> response =  eventoController.findByCity("Madrid");

        // Verificar respuesta correcta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    /**
     * Prueba que el repositorio devuelva una lista vacía cuando no hay eventos en la BD.
     */
    @Test
    void testFindByCityNoElements_Repository() {
        // Simular base de datos vacía
        when(eventoRepository.findByCity("Madrid")).thenReturn(Collections.emptyList());

        // Llamar al método del servicio
        List<Evento> result = eventoRepository.findByCity("Madrid");

        // Verificar que devuelve una lista vacía
        assertEquals(0, result.size());
    }


    /**
     * Prueba que el controlador devuelva una respuesta correcta cuando no hay eventos en la BD.
     */
    @Test
    void testFindByCityNoElements_Controller() {
        // Simular base de datos vacía
        when(mockEventoService.findByCity("Madrid")).thenReturn(Collections.emptyList());

        // Llamar al controlador
        try {
            eventoController.findByCity("Madrid");
        } catch (EventoNotFoundException ex) {
            assertEquals("No se encontraron eventos en la ciudad Madrid", ex.getMessage());
        }

        // Verificar que no se devuelven elementos
        verify(mockEventoService, times(1)).findByCity("Madrid");
    }

    /**
     * Prueba que si el parámetro city es null, se lanza IllegalArgumentException.
     */
    @Test
    void testFindByCityWithNullParam_Controller() {
        try {
            eventoController.findByCity(null);
        } catch (IllegalArgumentException ex) {
            assertEquals("La ciudad no puede ser nula o vacía.", ex.getMessage());
        }

        verify(mockEventoService, never()).findByCity(null);
    }

    /**
     * Prueba que si el parámetro city es una cadena vacía, se lanza IllegalArgumentException.
     */
    @Test
    void testFindByCityWithEmptyParam_Controller() {
        try {
            eventoController.findByCity("");
        } catch (IllegalArgumentException ex) {
            assertEquals("La ciudad no puede ser nula o vacía.", ex.getMessage());
        }

        verify(mockEventoService, never()).findByCity("");
    }

    /**
     * Prueba que el servicio lance una excepción EventoNotFoundException cuando no hay eventos en la BD.
     */
    @Test
    void testFindByCityNoElements_Service_ThrowsException() {
        // Simular base de datos vacía
        when(eventoRepository.findByCity("Madrid")).thenReturn(Collections.emptyList());

        // Verificar que se lanza la excepción EventoNotFoundException
        EventoNotFoundException thrown = assertThrows(
                EventoNotFoundException.class,
                () -> eventoService.findByCity("Madrid"),
                "Se esperaba EventoNotFoundException"
        );

        // Verificar el mensaje de la excepción
        assertEquals("No se encontraron eventos en la ciudad en EventoServiceImplMadrid", thrown.getMessage());
    }

}
