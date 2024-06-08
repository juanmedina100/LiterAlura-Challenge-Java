package com.jimd.LiterAluraChallengeJava.repositorio;

import com.jimd.LiterAluraChallengeJava.tablas.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibrosRerpositorio extends JpaRepository<Libros,Long> {

    List<Libros> findByLanguages(String languages);

    List<Libros> findAllByOrderByDownloadcountDesc();

    List<Libros> findAllByOrderByDownloadcountAsc();
}
