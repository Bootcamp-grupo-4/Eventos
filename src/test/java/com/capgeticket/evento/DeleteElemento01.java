package com.capgeticket.evento;

import com.capgeticket.evento.controller.EventoController;
import com.capgeticket.evento.exception.EventoNotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteElemento01 {

    // Mocks para el repositorio y servicio
    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private EventoServiceImpl eventoService;

    // Mocks para el servicio y controlador
    @Mock
    private EventoService mockEventoService;

    @InjectMocks
    private EventoController eventoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Verifica que el método deleteById() elimine un evento cuando el ID existe.
     */
    @Test
    void testDeleteByIdWhenExists_Service() {
        // Simular que el evento existe
        when(eventoRepository.existsById(1L)).thenReturn(true);

        // Llamar al método deleteById
        boolean result = eventoService.deleteById(1L);

        // Verificar que el evento fue eliminado
        assertTrue(result);
        verify(eventoRepository, times(1)).deleteById(1L);
    }

    /**
     * Verifica que el método deleteById() no elimine un evento cuando el ID no existe.
     */
    @Test
    void testDeleteByIdWhenNotExists_Service() {
        // Simular que el evento no existe
        when(eventoRepository.existsById(1L)).thenReturn(false);

        // Llamar al método deleteById
        boolean result = eventoService.deleteById(1L);

        // Verificar que no se eliminó el evento
        assertFalse(result);
        verify(eventoRepository, never()).deleteById(1L);
    }

    /**
     * Verifica que el controlador elimine un evento y devuelva la respuesta correcta.
     */
    @Test
    void testDeleteByIdSuccess_Controller() {
        // Simular que el evento existe
        when(mockEventoService.existsById(1L)).thenReturn(true);
        when(mockEventoService.deleteById(1L)).thenReturn(true);

        // Llamar al controlador
        ResponseEntity<Boolean> response = eventoController.deleteById(1L);

        // Verificar respuesta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(mockEventoService, times(1)).deleteById(1L);
    }

    /**
     * Verifica que se lance EventoNotFoundException cuando el evento no existe.
     */
    @Test
    void testDeleteByIdNotFound_Controller() {
        // Simular que el evento no existe
        when(mockEventoService.existsById(1L)).thenReturn(false);

        // Verificar que se lanza la excepción
        try {
            eventoController.deleteById(1L);
        } catch (EventoNotFoundException ex) {
            assertEquals("Epic Fail: No existe el evento con ID 1", ex.getMessage());
        }

        // Verificar que no se intenta eliminar el evento
        verify(mockEventoService, never()).deleteById(1L);
    }
}
