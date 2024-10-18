package com.capgeticket.evento.service;

import com.capgeticket.evento.dto.EventoDto;

import java.util.List;


public interface EventoService {

    List<EventoDto> findAll();

    EventoDto findById(Long id);

    EventoDto add(EventoDto evento);
    boolean existsById(Long id);
    boolean deleteById(Long id);
    List<EventoDto> findByName(String name);
    List<EventoDto> findByGenre(String genre);
}
