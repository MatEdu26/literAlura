package com.literalura.literalura.repository;

import com.literalura.literalura.modelos.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long>  {
    Autor findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :ano AND a.fechaDefuncion >= :ano")
    List<Autor> autoresVivosEnDeterminadoAno (int ano);

}