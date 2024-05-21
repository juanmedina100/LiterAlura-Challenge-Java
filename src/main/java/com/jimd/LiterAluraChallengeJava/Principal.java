package com.jimd.LiterAluraChallengeJava;

import com.jimd.LiterAluraChallengeJava.model.AuthorModel;
import com.jimd.LiterAluraChallengeJava.model.ResultModel;
import com.jimd.LiterAluraChallengeJava.service.ConsumirAPI;
import com.jimd.LiterAluraChallengeJava.service.ConvierteDatos;

import java.util.ResourceBundle;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";

    private ConsumirAPI consumirAPI = new ConsumirAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    public void mostrarInformacion(){
        var json = consumirAPI.obtenerDatos(URL_BASE);
        var datos = convierteDatos.obtenerDatos(json, ResultModel.class);

        datos.results().stream()
                .limit(15)
                .map(t->t.title()+" - ")
                //.map(l-> "Titulo : "+l.title() +" - Descargas : "+l.download_count()+" - Nombre :")
                .forEach(System.out::println);
    }



}
