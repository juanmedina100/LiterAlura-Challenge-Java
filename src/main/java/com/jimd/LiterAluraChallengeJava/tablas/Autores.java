package com.jimd.LiterAluraChallengeJava.tablas;

import com.jimd.LiterAluraChallengeJava.model.AuthorModel;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAutor;
    @Column(unique = true)
    private String name;
    @Column(name = "birth_year")
    private int birthyear;
    @Column(name = "death_year")
    private int deathyear;
    @OneToMany(mappedBy = "authors",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Libros> libros;

    public Autores(AuthorModel authorModel){
        this.name = authorModel.name();
        this.birthyear = authorModel.birth_year();
        this.deathyear = authorModel.death_year();

    }

    public Autores(){

    }


    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        libros.forEach(l->l.setAuthors(this));
        this.libros = libros;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirth_year() {
        return birthyear;
    }

    public void setBirth_year(int birth_year) {
        this.birthyear = birth_year;
    }

    public int getDeath_year() {
        return deathyear;
    }

    public void setDeatyear(int death_year) {
        this.deathyear = death_year;
    }
}
