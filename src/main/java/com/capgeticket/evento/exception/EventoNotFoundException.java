package com.capgeticket.evento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EventoNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EventoNotFoundException() {
        super("Epic Fail: No existe el evento");
        action1();
    }

    public EventoNotFoundException(Long id) {
        super("Epic Fail: No existe el evento con ID " + id);
    }

    public void action1() {

    }
}
