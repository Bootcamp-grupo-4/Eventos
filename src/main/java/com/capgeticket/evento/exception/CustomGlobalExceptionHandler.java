package com.capgeticket.evento.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

    // Manejo de la excepción EventoNotFoundException
    @ExceptionHandler(EventoNotFoundException.class)
    public ResponseEntity<Object> handleEventoNotFound(EventoNotFoundException ex, WebRequest request) {
        logger.info("Manejando EventoNotFoundException: {}", ex.getMessage());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Evento no encontrado");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false));

        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    // Manejo de la excepción IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        logger.info("Manejando IllegalArgumentException: {}", ex.getMessage());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Solicitud incorrecta");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false));

        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


}

