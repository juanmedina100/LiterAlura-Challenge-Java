package com.jimd.LiterAluraChallengeJava.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookModel(
        @JsonAlias("id") int id,
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<AuthorModel> authors,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") int download_count
) {
}
