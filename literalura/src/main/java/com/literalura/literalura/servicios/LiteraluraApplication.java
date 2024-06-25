package com.literalura.literalura.servicios;

import com.literalura.literalura.principal.Principal;
import com.literalura.literalura.repository.AutorRepository;
import com.literalura.literalura.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;


@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
    @Autowired
    private LibrosRepository repository;
    @Autowired
    private AutorRepository repositoryAutor;
    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repository,repositoryAutor);
        principal.opcionesMenu();

        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread t : threadSet) {
            System.out.println(t);
        }
    }
}
