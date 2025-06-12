package br.com.alurases.salurase.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alurases.salurase.model.Categoria;
import br.com.alurases.salurase.model.Episodio;
import br.com.alurases.salurase.model.Serie;

public interface SerieRepository 
extends JpaRepository<Serie, Long>{

        Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

        List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);
        List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);
        List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer temporadas, Double avaliacao);    
        List<Serie> findTop5ByOrderByAvaliacao();
        List<Serie> findTop5ByOrderByAvaliacaoDesc();
        List<Serie> findByGenero(Categoria genero);
        List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int totalTemporadas, double avaliacao);
        List<Serie> findTop5ByOrderByEpisodiosDataLancamentoDesc();
        @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
        List<Episodio> episodiosPorTrecho(String trechoEpisodio);
        @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
        List<Episodio> topEpisodiosPorSerie(Serie serie);
        @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoLancamento")
        List<Episodio> episodiosPorSerieEAno(Optional<Serie> serie, String anoLancamento);
}
