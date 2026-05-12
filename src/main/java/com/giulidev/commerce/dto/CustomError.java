package com.giulidev.commerce.dto;

import org.hibernate.Internal;

import java.time.Instant;

// Classe criada para dar uma resposta personalizada assim que estourar uma exceção que será tratada
//  por uma das classes customizadas. É o "DTO" da exception
public class CustomError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;

    public CustomError(Instant timestamp, Integer status, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }
}
