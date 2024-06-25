package com.literalura.literalura.servicios;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.literalura.literalura.modelos.DatosGenerales;

public class ConvierteDatos {
    public DatosGenerales obtenerDatos(String json, Class<DatosGenerales> clazz) {
        try {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.get("results"), clazz);
        } catch (Exception e) {
            System.out.println("Error al convertir los datos: " + e.getMessage());
            return null;
        }
    }
}