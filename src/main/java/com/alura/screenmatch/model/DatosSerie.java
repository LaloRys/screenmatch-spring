package com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Ignora las propiedades del archivo JSON que no se encuentren en la clase
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
        @JsonAlias({"Title", "titulo"}) String titulo,
        @JsonAlias("totalSeasons") Integer totalDeTemporadas,
        @JsonAlias("imdbRating") String evaluacion,
        @JsonAlias("Poster") String poster,
        @JsonAlias("Genre") String genero,
        @JsonAlias("Actors") String actores,
        @JsonAlias("Plot") String sinopsis,
        @JsonAlias("Released") String fechaLanzamiento,
        @JsonAlias("Language") String idioma


//        @JsonProperty("")
) {
}

