package com.capgeticket.evento.service;

import com.capgeticket.evento.dto.EventoDto;

import java.util.Collection;
import java.util.List;

public interface EventoService {
    List<EventoDto> findAll();
    EventoDto add(EventoDto evento);
    boolean existsById(Long id);
    boolean deleteById(Long id);
    List<EventoDto> findByName(String name);
    Collection<EventoDto> findByCity(String city);
}
