package com.literalura.literalura.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosGenerales() {
    private static DatosLibros resultado;

    public DatosLibros resultado() {
        return resultado;
    }
}
