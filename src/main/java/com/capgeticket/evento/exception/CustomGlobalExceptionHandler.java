package com.capgeticket.evento.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

    // Manejo de la excepción EventoNotFoundException
    @ResponseStatus(HttpStatus.NOT_FOUND)
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        logger.error("Método HTTP no soportado: {}", ex.getMessage());

        // Crear un mensaje personalizado
        StringBuilder message = new StringBuilder();
        message.append("El método HTTP '").append(ex.getMethod()).append("' no está permitido para esta ruta. ");
        message.append("Métodos soportados: ");
        if (ex.getSupportedHttpMethods() != null) {
            ex.getSupportedHttpMethods().forEach(m -> message.append(m + " "));
        }

        // Estructura personalizada del cuerpo de la respuesta
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        body.put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
        body.put("error", "Método HTTP no permitido");
        body.put("message", message.toString());
        body.put("path", request.getDescription(false));

        return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED);
    }



}

