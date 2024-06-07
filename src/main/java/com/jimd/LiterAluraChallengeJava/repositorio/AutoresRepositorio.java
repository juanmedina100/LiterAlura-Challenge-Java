package com.jimd.LiterAluraChallengeJava.repositorio;

import com.jimd.LiterAluraChallengeJava.tablas.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutoresRepositorio extends JpaRepository<Autores,Long> {

    Optional<Autores> findByName(String name);

    List<Autores> findByBirthyearLessThanEqualAndDeathyearGreaterThanEqual(int year, int death);

}
