package com.jimd.LiterAluraChallengeJava.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Idioma(
        @JsonAlias("idioma") String idioma,
        @JsonAlias("codigo") String codigo
) {


}
