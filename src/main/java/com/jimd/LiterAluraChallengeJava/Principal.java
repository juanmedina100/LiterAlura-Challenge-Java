package com.jimd.LiterAluraChallengeJava;

import com.jimd.LiterAluraChallengeJava.model.*;
import com.jimd.LiterAluraChallengeJava.repositorio.AutoresRepositorio;
import com.jimd.LiterAluraChallengeJava.repositorio.LibrosRerpositorio;
import com.jimd.LiterAluraChallengeJava.service.ConsumirAPI;
import com.jimd.LiterAluraChallengeJava.service.ConvierteDatos;
import com.jimd.LiterAluraChallengeJava.tablas.Autores;
import com.jimd.LiterAluraChallengeJava.tablas.Libros;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private static final String URL_LENGUAGE = "https://juanmedina100.github.io/idiomas-iso-639-1-json/idiomas-639-1.json";

    private ConsumirAPI consumirAPI = new ConsumirAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    Scanner teclado = new Scanner(System.in);

    private LibrosRerpositorio repositorio;
    private AutoresRepositorio autoresRepositorio;
    private List<Libros> libros;
    private Libros libro;
    private Optional<Autores> autor;

    public Principal(LibrosRerpositorio librosRerpositorio,AutoresRepositorio autoresRepositorio) {
        this.repositorio = librosRerpositorio;
        this.autoresRepositorio = autoresRepositorio;
    }

    public void mostrarInformacion(){
        while(true){
            //Opciones
            System.out.println("*********************************");
            System.out.println("1 - Buscar libro por titulo");
            System.out.println("2 - Listar libros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos en determinado año");
            System.out.println("5 - Listar libros por idioma");
            System.out.println("6 - Top 10 libros con mas descargas");
            System.out.println("7 - Top 10 libros con menos descargas");
            System.out.println("8 - Top 10 Autores con mas años");
            System.out.println("9 - Top 10 Autores con menos años");
            System.out.println("0 - Salir");
            System.out.println("*********************************");

            int seleccionar = -1;
            System.out.println("Seleccione una opción : ");
            try{
                seleccionar = Integer.parseInt(teclado.nextLine());
            }catch (Exception e){
                System.out.println("Error: No se ha ingresado un número valido");
            }
            if (seleccionar == 0){
                System.out.println("Cerrando aplicación");
                break;
            }
            switch (seleccionar){
                case 1:
                    buscarWeb();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    autoresVivosEnDeterminadoAnio();
                    break;
                case 5:
                    librosPorIdioma();
                    break;
                case 6:
                    topDiezMasDescargados();
                    break;
                case 7:
                    topDiezMenosDescargados();
                    break;
                case 8:
                    topDiezAutoresConMasAnios();
                    break;
                case 9:
                    topDiezAutoresConMenosAnios();
                    break;
            }
        }
    }

    private void buscarWeb(){
        var json = consumirAPI.obtenerDatos(URL_BASE);
        var datos = convierteDatos.obtenerDatos(json, ResultModel.class);
        System.out.println("Ingresa el nombre de un libro :");
        var tituloLibro = teclado.nextLine();
        json = consumirAPI.obtenerDatos(URL_BASE+"?search="+tituloLibro.replace(" ","%20"));
        datos = convierteDatos.obtenerDatos(json, ResultModel.class);

        Optional<BookModel> libroBuscado =datos.results()
                .stream().filter(l->l.title().toLowerCase()
                        .contains(tituloLibro.toLowerCase())).findFirst();
        try{
            if (libroBuscado.isPresent()){
                AuthorModel autores = libroBuscado.get().authors().get(0);
                autor = autoresRepositorio.findByName(autores.name());
                if (autor.isPresent()){
                    libro = new Libros(libroBuscado.get());
                    libro.setAuthors(autor.get());
                    repositorio.save(libro);
                }else{
                    libros = libroBuscado.stream().map(l->new Libros(l)).collect(Collectors.toList());
                    Autores newAutor = new Autores(autores);
                    newAutor.setLibros(libros);
                    autoresRepositorio.save(newAutor);
                }
                libroBuscado.stream().flatMap(l->l.authors().stream().map(a->
                                """
                                <---------- LIBRO ENCONTRADO ---------> 
                                Titulo : """ +l.title()+ """ 
                                \nAutor : """ + a.name()+ """
                                        \nDescargas : """ + l.download_count())).forEach(System.out::println);
            }else{
                System.out.println("El libro no fue encontrado.");
            }
        }catch (Exception e){
            System.out.println("Error: No es posible guardar la información.");
        }
    }
    private String llamarIdioma(String codigo){
        var json = consumirAPI.obtenerDatos(URL_LENGUAGE);
        var datos = convierteDatos.obtenerDatos(json, Idiomas.class);
        datos = convierteDatos.obtenerDatos(json, Idiomas.class);
        Optional<Idioma> idiomas = datos.idiomas().stream().filter(i->i.codigo().toLowerCase().contains(codigo.toLowerCase())).findFirst();
        var idioma = idiomas.map(Idioma::idioma).get();
        return idioma;
    }

    private void listarLibros(){
        List<Libros> libros = repositorio.findAll();
        System.out.println("------------ LIBROS EN BASE DE DATOS ----------");
        for (Libros libro : libros) {
            System.out.println("Título : " + libro.getTitle());
            System.out.println("Autor : " + libro.getAuthors().getName());
            System.out.println("Lenguaje : " + llamarIdioma(libro.getLanguages()));
            System.out.println("Descargas : " + libro.getDownload_count());
            System.out.println("--------------------");
        }
    }
    private void listarAutores(){
        List<Autores> autores = autoresRepositorio.findAll();
        System.out.println("------------ AUTORES EN BASE DE DATOS ----------");
        for (Autores autor : autores){
            var edad = autor.getDeath_year() - autor.getBirth_year();
            System.out.println("Nombre : " + autor.getName());
            System.out.println("Nacimiento : " +autor.getBirth_year());
            System.out.println("Fallecimiento : " +autor.getDeath_year());
            System.out.println("Vivio : " +edad+ " años");
            System.out.println("--------------------");
        }
    }
    private void autoresVivosEnDeterminadoAnio(){
        int anio = 0;
        System.out.println("Ingrese el año : ");
        try{
            anio = Integer.parseInt(teclado.nextLine());
            List<Autores> autoresVivos = autoresRepositorio.findByBirthyearLessThanEqualAndDeathyearGreaterThanEqual(anio,anio);
            if (autoresVivos.isEmpty()){
                System.out.println("No hay registros");
            }else{
                System.out.println("------------ AUTORES VIVOS EN EL AÑO "+anio +" ----------");
                for (Autores autor : autoresVivos){
                    var edad = autor.getDeath_year() - autor.getBirth_year();
                    System.out.println("Nombre : " + autor.getName());
                    System.out.println("Nacimiento : " +autor.getBirth_year());
                    System.out.println("Fallecimiento : " +autor.getDeath_year());
                    System.out.println("Años vividos : " +edad);
                    System.out.println("--------------------");
                }
            }
        }catch (Exception e){
            System.out.println("Error: No se ha ingresado un numero entero");
        }
    }
    private void librosPorIdioma(){
        System.out.println("Ingrese un lenguaje ejemplo: en, es, fr : ");
        String langua = teclado.nextLine();

        var idioma = llamarIdioma(langua);

        List<Libros> librosPorLenguaje = repositorio.findByLanguages(langua.toLowerCase());
        if (librosPorLenguaje.isEmpty()){
            System.out.println("No se encontraron libros en el idioma ");
        }else{
            System.out.println("------------ LIBROS EN BASE DE DATOS EN "+idioma.toUpperCase()+" ----------");
            for (Libros libro : librosPorLenguaje){
                System.out.println("Título : " + libro.getTitle());
                System.out.println("Autor : " + libro.getAuthors().getName());
                System.out.println("Lenguaje : " + idioma);
                System.out.println("Descargas : " + libro.getDownload_count());
                System.out.println("--------------------");
            }
        }
    }
    private void topDiezMasDescargados(){
        List<Libros> topLibros = repositorio.findAllByOrderByDownloadcountDesc();
        System.out.println("------------ TOP 10 LIBROS CON MAS DESCARGAS ----------");
        var topCinco = topLibros.stream().limit(10).collect(Collectors.toList());
        for (Libros libro : topCinco) {
            System.out.println("Título : " + libro.getTitle());
            System.out.println("Autor : " + libro.getAuthors().getName());
            System.out.println("Lenguaje : " + llamarIdioma(libro.getLanguages()));
            System.out.println("Descargas : " + libro.getDownload_count());
            System.out.println("--------------------");
        }
    }
    private void topDiezMenosDescargados(){
        List<Libros> topLibros = repositorio.findAllByOrderByDownloadcountAsc();
        System.out.println("------------ TOP 10 LIBROS CON MENOS DESCARGAS ----------");
        var topDiez = topLibros.stream().limit(10).collect(Collectors.toList());
        for (Libros libro : topDiez) {
            System.out.println("Título : " + libro.getTitle());
            System.out.println("Autor : " + libro.getAuthors().getName());
            System.out.println("Lenguaje : " + llamarIdioma(libro.getLanguages()));
            System.out.println("Descargas : " + libro.getDownload_count());
            System.out.println("--------------------");
        }
    }
    private void topDiezAutoresConMasAnios(){
        List<Autores> autores = autoresRepositorio.findAllByOrderByAgeDesc();
        var topAutores = autores.stream().limit(10).collect(Collectors.toList());
        System.out.println("------------ AUTORES CON MAS AÑOS ----------");
        for (Autores autor : topAutores){
            System.out.println("Nombre : " + autor.getName());
            System.out.println("Nacimiento : " +autor.getBirth_year());
            System.out.println("Fallecimiento : " +autor.getDeath_year());
            System.out.println("Vivio : " +autor.getAge()+ " años");
            System.out.println("--------------------");
        }
    }
    private void topDiezAutoresConMenosAnios(){
        List<Autores> autores = autoresRepositorio.findAllByOrderByAgeAsc();
        var topAutores = autores.stream().limit(10).collect(Collectors.toList());
        System.out.println("------------ AUTORES CON MENOS AÑOS ----------");
        for (Autores autor : topAutores){
            System.out.println("Nombre : " + autor.getName());
            System.out.println("Nacimiento : " +autor.getBirth_year());
            System.out.println("Fallecimiento : " +autor.getDeath_year());
            System.out.println("Vivio : " +autor.getAge()+ " años");
            System.out.println("--------------------");
        }
    }

}
