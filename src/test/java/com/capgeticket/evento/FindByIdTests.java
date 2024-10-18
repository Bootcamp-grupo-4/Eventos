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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindByIdTests {

    // Mocks para el Servicio
    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private EventoServiceImpl eventoService;

    // Mocks para el Controlador
    @Mock
    private EventoService mockEventoService;

    @InjectMocks
    private EventoController eventoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test del método findAll() en EventoServiceImpl cuando hay datos.
     */
    @Test
    void testFindByIdData() {
        // Simular datos en la base de datos
        Evento evento1 = new Evento();
        evento1.setNombre("Evento 1");

        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento1));

        // Llamada al servicio
        EventoDto resultado = eventoService.findById(1L);

        // Verificar que se encontro el elemento que se buscaba
        assertEquals("Evento 1", resultado.getNombre());
    }

    /**
     * Test del método findAll() en EventoServiceImpl cuando la BD está vacía.
     */
    @Test
    void testFindByIdNoData() {
        // Simular base de datos vacía
        when(eventoRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamada al servicio
        Exception exception = assertThrows(EventoNotFoundException.class, () -> eventoService.findById(1L));

        assertEquals("Epic Fail: No existe el evento con ID " + 1L, exception.getMessage());
    }

    /**
     * Test del método findAll() en EventoController cuando hay datos.
     */
    @Test
    void testFindAllControllerWithData() {
        // Simular datos en el servicio
        EventoDto evento1 = new EventoDto();
        evento1.setNombre("Evento 1");

        when(mockEventoService.findById(1L)).thenReturn(evento1);

        // Llamada al controlador
        ResponseEntity<EventoDto> respuesta = eventoController.findById(1L);

        // Verificar que devuelve status 200 y una lista con 2 elementos
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Evento 1", respuesta.getBody().getNombre());
    }
}