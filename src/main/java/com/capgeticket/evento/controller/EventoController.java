package com.capgeticket.evento.controller;

import com.capgeticket.evento.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class EventoController {
    @Autowired
    private EventoService service;
}
