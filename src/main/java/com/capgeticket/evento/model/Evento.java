package com.capgeticket.evento.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "fechaEvento", nullable = false)
    private LocalDate fechaEvento;

    @Column(name = "precioMinimo", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioMinimo;

    @Column(name = "precioMaximo", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioMaximo;

    @Column(name = "localidad", nullable = false, length = 255)
    private String localidad;

    @Column(name = "nombreDelRecinto", nullable = false, length = 255)
    private String nombreDelRecinto;

    @Column(name = "genero", nullable = false, length = 255)
    private String genero;

    @Column(name = "mostrar", nullable = false)
    private Boolean mostrar;
}