package br.com.alurases.salurase.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alurases.salurase.dto.EpisodioDTO;
import br.com.alurases.salurase.dto.SerieDTO;
import br.com.alurases.salurase.model.Categoria;
import br.com.alurases.salurase.model.Episodio;
import br.com.alurases.salurase.model.Serie;
import br.com.alurases.salurase.repository.SerieRepository;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obterTodasAsSeries(){
        return converteSerie(repository.findAll());  
    }

    public List<SerieDTO> obterTop5Series() {
        return converteSerie(repository.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<SerieDTO> converteSerie(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(
                    s.getId()
                    , s.getTitulo()
                    , s.getTotalTemporadas()
                    , s.getAvaliacao()
                    , s.getVotos()
                    , s.getGenero()
                    , s.getAtores()
                    , s.getPoster()
                    , s.getSinopse()
                ))
                .collect(Collectors.toList());  
    }

    public List<SerieDTO> obterLancamentos() {
        return converteSerie(repository.lancamentosMaisRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repository.findById(id); 
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(
                    s.getId()
                    , s.getTitulo()
                    , s.getTotalTemporadas()
                    , s.getAvaliacao()
                    , s.getVotos()
                    , s.getGenero()
                    , s.getAtores()
                    , s.getPoster()
                    , s.getSinopse()
            );
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasAsTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id); 
        if (serie.isPresent()){
            Serie s = serie.get();
            return converteEpisodios(s.getEpisodios());
        }
        return null;    
    }

    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
        return converteEpisodios(
            repository.obterEpisodiosPorTemporadas(
                id, numero
            )
        );
    }

    private List<EpisodioDTO> converteEpisodios(List<Episodio> episodios){
        return episodios.stream()
                .map(e -> new EpisodioDTO(
                    e.getTemporada()
                    , e.getNumeroEpisodio()
                    , e.getTitulo()
                ))
                .collect(Collectors.toList());  
    }

    public List<SerieDTO> obterSeriesPorCategoria(String nomeCategoria) {
        Categoria categoria = Categoria.fromString(nomeCategoria);
        return converteSerie(repository.findByGenero(categoria));
    }

    public List<EpisodioDTO> obterTopEpisodiosPorSerie(Long id){
        return converteEpisodios(repository.topEpisodiosPorSerie(id));
    }
}
