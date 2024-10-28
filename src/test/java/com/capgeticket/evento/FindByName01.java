package com.capgeticket.evento;

import com.capgeticket.evento.controller.EventoController;
import com.capgeticket.evento.dto.EventoDto;
import com.capgeticket.evento.exception.EventoNotFoundException;
import com.capgeticket.evento.model.Evento;
import com.capgeticket.evento.service.EventoService;
import io.restassured.RestAssured;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@ActiveProfiles("test")
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
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
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

    @Test
    public void testFindByNameWithRestAssured() {
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

        // Act & Assert: Realiza una solicitud GET y verifica el código de estado y el contenido de la respuesta
        given()
                .contentType(ContentType.JSON)
                .queryParam("name", "Concierto")
                .when()
                .get("/evento/nombre")
                .then()
                .statusCode(HttpStatus.OK.value())  // Verificar que el código de respuesta sea 200
                .body("size()", is(1))  // Verificar que la respuesta contiene 1 evento
                .body("[0].nombre", equalTo("Concierto de Rock"))  // Verificar que el nombre del evento sea "Concierto"
                .body("[0].localidad", equalTo("Madrid"))  // Verificar que la localidad sea "Madrid"
                .body("[0].precioMinimo", equalTo(30.00f))  // Verificar el precio mínimo
                .body("[0].precioMaximo", equalTo(150.00f));  // Verificar el precio máximo
    }
}

