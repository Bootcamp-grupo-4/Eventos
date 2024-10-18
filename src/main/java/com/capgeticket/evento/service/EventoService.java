package com.capgeticket.evento.service;

import com.capgeticket.evento.dto.EventoDto;

import java.util.List;

public interface EventoService {
    List<EventoDto> findAll();
    EventoDto add(EventoDto evento);
}
