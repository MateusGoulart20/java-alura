package br.com.alurases.salurase.dto;

import br.com.alurases.salurase.model.Categoria;

public record SerieDTO(
    Long id
    , String titulo
    , Integer totalTemporadas
    , Double avaliacao
    , String votos
    , Categoria genero
    , String atores
    , String poster
    , String sinopse
) {} 
