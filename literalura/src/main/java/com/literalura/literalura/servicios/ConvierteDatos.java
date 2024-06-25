package com.literalura.literalura.servicios;

import com.google.gson.Gson;
import com.literalura.literalura.modelos.DatosGenerales;

import java.util.Arrays;

public class ConvierteDatos {
    public DatosGenerales obtenerDatos(String json) {
        try {
            Gson gson = new Gson();
            DatosGenerales datosGenerales = gson.fromJson(json, DatosGenerales.class);
            return datosGenerales;
        } catch (Exception e) {
            System.out.println("Error al convertir los datos: " + e.getMessage());
            return null;
        }
    }
}