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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventoController.class)
public class AddEvento01 {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventoService eventoService;

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private EventoServiceImpl eventoServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Pruebas del service
    @Test
    void testAddValidEventoService() {
        //Get
        EventoDto eventoDto = new EventoDto();
        eventoDto.setNombre("Concierto");
        eventoDto.setDescripcion("Concierto de música clásica");
        eventoDto.setFechaEvento(LocalDate.of(2024, 12, 1));
        eventoDto.setPrecioMinimo(new BigDecimal("10.00"));
        eventoDto.setPrecioMaximo(new BigDecimal("50.00"));
        eventoDto.setLocalidad("Madrid");
        eventoDto.setNombreDelRecinto("Palacio de Deportes");
        eventoDto.setGenero("Música");
        eventoDto.setMostrar(true);

        Evento evento = new Evento();
        evento.setId(1L);
        evento.setNombre("Concierto");

        //When
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        EventoDto result = eventoServiceImpl.add(eventoDto);

        //Then
        assertNotNull(result);
        assertEquals("Concierto", result.getNombre());
        verify(eventoRepository, times(1)).save(any(Evento.class));
    }

    @Test
    void testAddNullEventoService() {
        assertThrows(IllegalArgumentException.class, () -> eventoServiceImpl.add(null), "El evento no puede ser nulo");
    }

    // Pruebas del controller
    @Test
    void testAddValidEventoController() throws Exception {
        // Get
        EventoDto eventoDto = new EventoDto();
        eventoDto.setId(1L);
        eventoDto.setNombre("Concierto");
        eventoDto.setDescripcion("Concierto de música clásica");
        eventoDto.setFechaEvento(LocalDate.of(2024, 12, 1));
        eventoDto.setPrecioMinimo(new BigDecimal("10.00"));
        eventoDto.setPrecioMaximo(new BigDecimal("50.00"));
        eventoDto.setLocalidad("Madrid");
        eventoDto.setNombreDelRecinto("Palacio de Deportes");
        eventoDto.setGenero("Música");
        eventoDto.setMostrar(true);

        when(eventoService.add(any(EventoDto.class))).thenReturn(eventoDto);

        // When
        mockMvc.perform(post("/evento/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"nombre\": \"Concierto\",\n" +
                                "    \"descripcion\": \"Concierto de música clásica\",\n" +
                                "    \"fechaEvento\": \"2024-12-01\",\n" +
                                "    \"precioMinimo\": 10.00,\n" +
                                "    \"precioMaximo\": 50.00,\n" +
                                "    \"localidad\": \"Madrid\",\n" +
                                "    \"nombreDelRecinto\": \"Palacio de Deportes\",\n" +
                                "    \"genero\": \"Música\",\n" +
                                "    \"mostrar\": true\n" +
                                "}"))
                //Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Concierto"));

        verify(eventoService, times(1)).add(any(EventoDto.class));
    }

    @Test
    void testAddNullEventoController() throws Exception {
        mockMvc.perform(post("/evento/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")) // Cuerpo vacío (evento nulo)
                .andExpect(status().isBadRequest());

        verify(eventoService, never()).add(any(EventoDto.class));
    }


    @Test
    void testAddEventoWithInvalidDataController() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/evento/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"nombre\": \"\",\n" +  // Nombre vacío
                                "    \"descripcion\": \"Concierto de música clásica\",\n" +
                                "    \"fechaEvento\": \"2024-12-01\",\n" +
                                "    \"precioMinimo\": -10.00,\n" +  // Precio inválido
                                "    \"precioMaximo\": 50.00,\n" +
                                "    \"localidad\": \"Madrid\",\n" +
                                "    \"nombreDelRecinto\": \"Palacio de Deportes\",\n" +
                                "    \"genero\": \"Música\",\n" +
                                "    \"mostrar\": true\n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400)) // Verificamos el status 400
                .andExpect(jsonPath("$.error").value("Solicitud incorrecta"))
                .andExpect(jsonPath("$.message").value("El evento no puede ser nulo o tener un nombre vacío"))
                .andExpect(jsonPath("$.path").value("uri=/evento/"));

        verify(eventoService, never()).add(any(EventoDto.class)); // Asegura que el método del servicio no fue llamado
    }

}
