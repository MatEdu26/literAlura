package com.literalura.literalura.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(

        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autor,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Double totalDescargas){

}
