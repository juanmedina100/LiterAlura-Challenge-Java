package com.jimd.LiterAluraChallengeJava.tablas;

import com.jimd.LiterAluraChallengeJava.model.BookModel;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libros {

    @Id
    private Long idLibro;

    @Column(unique = true)
    private String title;

    @ManyToOne
    @JoinColumn(name = "idAutor",referencedColumnName = "idAutor",nullable = false)
    private Autores authors;

    private String languages;
    private int download_count;


    public Libros(BookModel bookModel){
        this.idLibro = bookModel.idLibro();
        this.title = bookModel.title();
        //this.authors = bookModel.authors();
        this.languages = bookModel.languages().get(0);
        this.download_count = bookModel.download_count();
    }

    public Libros(){

    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Autores getAuthors() {
        return authors;
    }

    public void setAuthors(Autores authors) {
        this.authors = authors;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }
}
