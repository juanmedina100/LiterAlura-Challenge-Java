package com.jimd.LiterAluraChallengeJava.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Idiomas(
        @JsonAlias("idiomas") List<Idioma> idiomas
) {

}
