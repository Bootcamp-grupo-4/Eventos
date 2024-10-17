package com.capgeticket.evento.service;

import com.capgeticket.evento.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoServiceImpl {
    @Autowired
    private EventoRepository repository;
}
