package com.capgeticket.evento;

import com.capgeticket.evento.controller.EventoController;
import com.capgeticket.evento.dto.EventoDto;
import com.capgeticket.evento.exception.EventoNotFoundException;
import com.capgeticket.evento.model.Evento;
import com.capgeticket.evento.service.EventoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FindByName01 {

    @Mock
    private EventoService eventoService;

    @InjectMocks
    private EventoController eventoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventoController).build();
    }

    @Test
    void testFindByName() {
        // Arrange
        Evento evento1 = new Evento();
        evento1.setId(1L);
        evento1.setNombre("Concierto");
        evento1.setDescripcion("Descripción del concierto");
        evento1.setFechaEvento(LocalDate.now());
        evento1.setPrecioMinimo(new BigDecimal("10.00"));
        evento1.setPrecioMaximo(new BigDecimal("50.00"));
        evento1.setLocalidad("Madrid");
        evento1.setNombreDelRecinto("Palacio de Deportes");
        evento1.setGenero("Música");
        evento1.setMostrar(true);

        EventoDto eventoDto1 = EventoDto.of(evento1);

        when(eventoService.findByName("Concierto")).thenReturn(Arrays.asList(eventoDto1));

        // Act
        ResponseEntity<Collection<EventoDto>> response = eventoController.findByName("Concierto");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Concierto", response.getBody().iterator().next().getNombre());
        verify(eventoService, times(1)).findByName("Concierto");
    }

    @Test
    void testFindByNameNoEvents() {
        // Arrange
        when(eventoService.findByName("Inexistente")).thenThrow(new EventoNotFoundException());

        // Act & Assert
        assertThrows(EventoNotFoundException.class, () -> eventoController.findByName("Inexistente"));
        verify(eventoService, times(1)).findByName("Inexistente");
    }

    @Test
    void testFindByNameNull() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/evento/nombre")
                        .param("name", (String) null))  // Enviar null como parámetro
                .andExpect(status().isBadRequest());

        // Verificar que se lanza IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> eventoController.findByName(null));
    }

    @Test
    void testFindByNameEmptyList() {
        // Arrange
        when(eventoService.findByName("Vacio")).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<Collection<EventoDto>> response = eventoController.findByName("Vacio");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(eventoService, times(1)).findByName("Vacio");
    }
}
