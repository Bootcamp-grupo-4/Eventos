package com.capgeticket.evento;

import com.capgeticket.evento.controller.EventoController;
import com.capgeticket.evento.dto.EventoDto;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindAllEventos01 {

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
    void testFindAllServiceWithData() {
        // Simular datos en la base de datos
        Evento evento1 = new Evento();
        evento1.setNombre("Evento 1");
        evento1.setMostrar(true);

        Evento evento2 = new Evento();
        evento2.setNombre("Evento 2");
        evento2.setMostrar(true);

        when(eventoRepository.findAllMostrar()).thenReturn(Arrays.asList(evento1, evento2));

        // Llamada al servicio
        List<EventoDto> resultado = eventoService.findAll();

        // Verificar que devuelve una lista con 2 elementos
        assertEquals(2, resultado.size());
        assertEquals("Evento 1", resultado.getFirst().getNombre());
    }

    /**
     * Test del método findAll() en EventoServiceImpl cuando la BD está vacía.
     */
    @Test
    void testFindAllServiceEmpty() {
        // Simular base de datos vacía
        when(eventoRepository.findAll()).thenReturn(Collections.emptyList());

        // Llamada al servicio
        List<EventoDto> resultado = eventoService.findAll();

        // Verificar que devuelve una lista vacía
        assertEquals(0, resultado.size());
    }

    /**
     * Test del método findAll() en EventoController cuando hay datos.
     */
    @Test
    void testFindAllControllerWithData() {
        // Simular datos en el servicio
        EventoDto evento1 = new EventoDto();
        evento1.setNombre("Evento 1");

        EventoDto evento2 = new EventoDto();
        evento2.setNombre("Evento 2");

        when(mockEventoService.findAll()).thenReturn(Arrays.asList(evento1, evento2));

        // Llamada al controlador
        ResponseEntity<Collection<EventoDto>> respuesta = eventoController.findAll();

        // Verificar que devuelve status 200 y una lista con 2 elementos
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(2, respuesta.getBody().size());
    }

    /**
     * Test del método findAll() en EventoController cuando la BD está vacía.
     */
    @Test
    void testFindAllControllerEmpty() {
        // Simular servicio vacío
        when(mockEventoService.findAll()).thenReturn(Collections.emptyList());

        // Llamada al controlador
        ResponseEntity<Collection<EventoDto>> respuesta = eventoController.findAll();

        // Verificar que devuelve status 200 y una lista vacía
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(0, respuesta.getBody().size());
    }
}
