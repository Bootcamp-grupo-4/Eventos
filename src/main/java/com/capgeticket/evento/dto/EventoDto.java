package com.capgeticket.evento.dto;

import com.capgeticket.evento.model.Evento;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class EventoDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaEvento;
    private BigDecimal precioMinimo;
    private BigDecimal precioMaximo;
    private String localidad;
    private String nombreDelRecinto;
    private String genero;
    private Boolean mostrar;
    private BigDecimal precio;

    public static EventoDto of(Evento evento) {
        EventoDto eventoDto = new EventoDto();
        eventoDto.setId(evento.getId());
        eventoDto.setNombre(evento.getNombre());
        eventoDto.setDescripcion(evento.getDescripcion());
        eventoDto.setFechaEvento(evento.getFechaEvento());
        eventoDto.setPrecioMinimo(evento.getPrecioMinimo());
        eventoDto.setPrecioMaximo(evento.getPrecioMaximo());
        eventoDto.setLocalidad(evento.getLocalidad());
        eventoDto.setNombreDelRecinto(evento.getNombreDelRecinto());
        eventoDto.setGenero(evento.getGenero());
        eventoDto.setMostrar(evento.getMostrar());
        eventoDto.setPrecio(evento.getPrecio());
        return eventoDto;
    }

    public static List<EventoDto> of(List<Evento> eventos) {
        return eventos.stream().map(EventoDto::of).collect(Collectors.toList());
    }
}
