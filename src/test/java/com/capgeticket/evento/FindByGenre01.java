package com.capgeticket.evento;

import com.capgeticket.evento.controller.EventoController;
import com.capgeticket.evento.dto.EventoDto;
import com.capgeticket.evento.exception.EventoNotFoundException;
import com.capgeticket.evento.model.Evento;
import com.capgeticket.evento.service.EventoService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@ActiveProfiles("test")
public class FindByGenre01 {

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
    void testFindByGenreExists() throws Exception {
        // Arrange
        Evento evento1 = new Evento();
        evento1.setId(1L);
        evento1.setNombre("Concierto");
        evento1.setGenero("Música");
        evento1.setDescripcion("Descripción del concierto");
        evento1.setFechaEvento(LocalDate.now());
        evento1.setPrecioMinimo(new BigDecimal("10.00"));
        evento1.setPrecioMaximo(new BigDecimal("50.00"));
        evento1.setLocalidad("Madrid");
        evento1.setNombreDelRecinto("Palacio de Deportes");
        evento1.setMostrar(true);

        EventoDto eventoDto1 = EventoDto.of(evento1);

        when(eventoService.findByGenre("Música")).thenReturn(Arrays.asList(eventoDto1));

        // Act
        ResponseEntity<Collection<EventoDto>> response = eventoController.findByGenre("Música");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Concierto", response.getBody().iterator().next().getNombre());
    }

    @Test
    void testFindByGenreNotFound() {
        // Arrange
        when(eventoService.findByGenre("Inexistente")).thenThrow(new EventoNotFoundException("No existen eventos con el género: Inexistente"));

        // Act & Assert
        assertThrows(EventoNotFoundException.class, () -> eventoController.findByGenre("Inexistente"));
    }

    @Test
    void testFindByGenreNull() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/evento/genero")
                        .param("genre", (String) null))  // Enviar null como parámetro
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindByGenreEmptyList() {
        // Arrange
        when(eventoService.findByGenre("Vacio")).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<Collection<EventoDto>> response = eventoController.findByGenre("Vacio");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testFindByGenreWithRestAssured() {

        // Inserta un evento en la base de datos para la prueba
        Evento evento1 = new Evento();
        evento1.setId(1L);
        evento1.setNombre("Concierto");
        evento1.setGenero("Música");
        evento1.setDescripcion("Descripción del concierto");
        evento1.setFechaEvento(LocalDate.now());
        evento1.setPrecioMinimo(new BigDecimal("10.00"));
        evento1.setPrecioMaximo(new BigDecimal("50.00"));
        evento1.setLocalidad("Madrid");
        evento1.setNombreDelRecinto("Palacio de Deportes");
        evento1.setMostrar(true);

        eventoService.add(EventoDto.of(evento1));  // Guarda el evento

        // Realiza una solicitud GET a /evento/genero con el parámetro genre
        given()
                .contentType(ContentType.JSON)
                .queryParam("genre", "Música")
                .when()
                .get("/evento/genero")
                .then()
                .statusCode(HttpStatus.OK.value())  // Verificar que el estado es 200 OK
                .body("size()", is(1))  // Verificar que la respuesta tiene 1 evento
                .body("[0].nombre", equalTo("Concierto"))  // Verificar que el nombre es correcto
                .body("[0].localidad", equalTo("Madrid"))  // Verificar que la localidad es "Madrid"
                .body("[0].precioMinimo", equalTo(10.00f))  // Verificar que el precio mínimo es 10.00
                .body("[0].precioMaximo", equalTo(50.00f));  // Verificar que el precio máximo es 50.00
    }
}
