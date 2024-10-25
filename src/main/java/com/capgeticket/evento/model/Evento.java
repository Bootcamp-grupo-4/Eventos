package com.capgeticket.evento.model;

import com.capgeticket.evento.dto.EventoDto;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "fechaevento", nullable = false)
    private LocalDate fechaEvento;

    @Column(name = "preciominimo", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioMinimo;

    @Column(name = "preciomaximo", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioMaximo;

    @Column(name = "localidad", nullable = false, length = 255)
    private String localidad;

    @Column(name = "nombredelrecinto", nullable = false, length = 255)
    private String nombreDelRecinto;

    @Column(name = "genero", nullable = false, length = 255)
    private String genero;

    @Column(name = "mostrar", nullable = false)
    private Boolean mostrar;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;



    public static Evento of(EventoDto eventoDto, boolean editing) {
        Evento evento = new Evento();
        if (editing) {
            //Como icaro, quise volar muy cerca del sol y me queme
            evento.setId(eventoDto.getId());
        }
        evento.setNombre(eventoDto.getNombre());
        evento.setDescripcion(eventoDto.getDescripcion());
        evento.setFechaEvento(eventoDto.getFechaEvento());
        evento.setPrecioMinimo(eventoDto.getPrecioMinimo());
        evento.setPrecioMaximo(eventoDto.getPrecioMaximo());
        evento.setLocalidad(eventoDto.getLocalidad());
        evento.setNombreDelRecinto(eventoDto.getNombreDelRecinto());
        evento.setGenero(eventoDto.getGenero());
        evento.setMostrar(eventoDto.getMostrar());
        evento.setPrecio(eventoDto.getPrecio());
        return evento;
    }
}