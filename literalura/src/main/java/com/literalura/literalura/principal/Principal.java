package com.literalura.literalura.principal;

import com.literalura.literalura.modelos.*;
import com.literalura.literalura.repository.AutorRepository;
import com.literalura.literalura.repository.LibrosRepository;
import com.literalura.literalura.servicios.ConsumoApi;
import com.literalura.literalura.servicios.ConvierteDatos;
import java.util.stream.Stream;

import java.util.*;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private List<DatosLibros> datosLibros = new ArrayList<>();
    private LibrosRepository repository;
    private AutorRepository repositoryAutor;
    private List<Libros> libros;
    private List<Autor> autor;

    public Principal (){}
    public Principal(LibrosRepository repository, AutorRepository repositoryAutor) {
        this.repository = repository;
        this.repositoryAutor = repositoryAutor;
    }

    private void infoMenu(){
        var menu = """
                    ----------------------------------------
                        MENU:
                    
                    1 - Buscar libros por titulo
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Mostrar autores vivos en determinado año
                    5 - Mostrar libros por idiomas
               
                        
                    0 - Salir
                    -----------------------------------------
                    """;
        System.out.println(menu);
    }

    //filtro de opcion para solo permitir numeros
    private void scannerSoloNumeros() {
        while (!teclado.hasNextInt()) {
            System.out.println("ingrese solo numeros");
            teclado.next();
        }

    }

    public void opcionesMenu (){
        var opcion = -1;
        while (opcion != 0) {

            infoMenu();
            scannerSoloNumeros();

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutorVivoEnAno();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    // Forzar salida de la aplicación
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

//    public void buscarLibro() {
//        System.out.println("Ingrese nombre del libro");
//        var buscaLibro = teclado.nextLine();
//        var url = URL_BASE + "?search=" + URLEncoder.encode(buscaLibro, StandardCharsets.UTF_8);
//        var json = consumoApi.obtenerDatos(url);
//        DatosGenerales buscador = convierteDatos.obtenerDatos(json);
//        Optional<DatosLibros> libroBuscado = Optional.ofNullable(buscador.resultado());
//
//        //Analisa respuesta de consulta. si ecuentra coincidencia lo procesa, sino informa que no encontro el libro
//        if (libroBuscado.isPresent()) {
//            DatosLibros datosLibros = libroBuscado.get();
//            DatosAutor datosAutor = datosLibros.autor().get(0);
//            Autor autor = repositoryAutor.findByNombre(datosAutor.nombre());
//
//            //Analiza que autor este regsitrado en base de datos
//            //Si ya esta registrado no lo almacena, pero si alacena informacion de libro
//            if (autor == null) {
//
//                autor = new Autor(datosAutor);
//                repositoryAutor.save(autor);
//            }
//
//
//            //Analiza que libro este regsitrado en base de datos
//            //Si ya esta registrado no lo almacena
//            Libros libro = repository.findByTituloContainsIgnoreCase(datosLibros.titulo());
//
//
//            if (libro == null) {
//                System.out.println("¡Libro encontrado!");
//                libro = new Libros(datosLibros, autor);
//                repository.save(libro);
//                System.out.println(libro);
//            }else {
//                System.out.println("Libro ya esta Registrado");
//            }
//        } else {
//            System.out.println("Libro no encontrado");
//        }
//    }

    public void buscarLibro() {
        System.out.println("Ingrese nombre del libro");
        var buscaLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + buscaLibro.replace(" ", "+"));
        DatosGenerales buscador = convierteDatos.obtenerDatos(json);

        if (buscador != null && buscador.resultado() != null) {
            List<DatosLibros> resultados = Collections.singletonList(buscador.resultado());
            Optional<DatosLibros> libroBuscado = resultados.stream()
                    .filter(l -> l.titulo().toUpperCase().contains(buscaLibro.toUpperCase()))
                    .findFirst();

            if (libroBuscado.isPresent()) {
                DatosLibros datosLibros = libroBuscado.get();
                DatosAutor datosAutor = datosLibros.autor().get(0);
                Autor autor = repositoryAutor.findByNombre(datosAutor.nombre());

                if (autor == null) {
                    autor = new Autor(datosAutor);
                    repositoryAutor.save(autor);
                }

                Libros libro = repository.findByTituloContainsIgnoreCase(datosLibros.titulo());

                if (libro == null) {
                    System.out.println("¡Libro encontrado!");
                    libro = new Libros(datosLibros, autor);
                    repository.save(libro);
                    System.out.println(libro);
                } else {
                    System.out.println("Libro ya está registrado");
                }
            } else {
                System.out.println("Libro no encontrado");
            }
        } else {
            System.out.println("No se pudo obtener información del libro");
        }
    }
    
    private void mostrarLibrosRegistrados(){
        libros = repository.findAll();
        libros.forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados(){
        autor = repositoryAutor.findAll();
        autor.forEach(System.out::println);
    }

    private void buscarLibrosPorIdioma(){
        System.out.println("""
        --------------------------------
        Ingrese numero de idioma deseado
        
        1- Ingles
        2- Español
        3- Portuges
        4- Italiano
        
        -------------------------------
        """);
        scannerSoloNumeros();
        var  numero = teclado.nextInt();
        switch (numero) {
            case 1:
                buscarIdioma("en");
                break;
            case 2:
                buscarIdioma("es");
                break;
            case 3:
                buscarIdioma("pt");
                break;
            case 4:
                buscarIdioma("it");
                break;
            default:
                System.out.println("Opción inválida");
        }
    }
    private void buscarIdioma(String idioma) {
        try {
            libros = repository.findByIdiomas(idioma);

            if (libros == null) {
                System.out.println("No hay Libros registrados");
            } else {
                libros.forEach(System.out::println);
            }
        }catch (Exception e){
            System.out.println("Error en la busqueda");
        }

    }

    private void buscarAutorVivoEnAno () {
        System.out.println("Ingrese año");
        scannerSoloNumeros();
        var ano = teclado.nextInt();
        autor = repositoryAutor.autoresVivosEnDeterminadoAno(ano);
        if (autor == null) {
            System.out.println("No hay registro de autores en ese año");
        } else {
            autor.forEach(System.out::println);
        }

    }

}
