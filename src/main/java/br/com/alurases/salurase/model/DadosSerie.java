package br.com.alurases.salurase.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(  
    @JsonAlias({"Title", "Titulo"}) // consegue ler vários campos
    String titulo,
    @JsonAlias("totalSeasons")
    Integer totalTemporadas,
    @JsonAlias("imdbRating")
    String avaliacao,
    @JsonProperty("imdbVotes") // lê e escreve por esse campo.
    String votos
) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
}
