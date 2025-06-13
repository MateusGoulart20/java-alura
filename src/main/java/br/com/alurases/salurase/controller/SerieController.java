package br.com.alurases.salurase.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.alurases.salurase.dto.EpisodioDTO;
import br.com.alurases.salurase.dto.SerieDTO;
import br.com.alurases.salurase.service.SerieService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/series")
public class SerieController {
    
    @Autowired
    private SerieService service;

    @GetMapping
    public List<SerieDTO> obterSeries() {
        return service.obterTodasAsSeries();
    }   
    
    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Seroes() {
        return service.obterTop5Series();
    }   
    
    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return service.obterLancamentos();
    }
    
    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id) {
        return service.obterPorId(id);
    }
    
    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id) {
        return service.obterTodasAsTemporadas(id);
    }
    
    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterTemporadasPorNumero(
        @PathVariable Long id
        , @PathVariable Long numero 
    ) {
        return service.obterTemporadasPorNumero(id, numero);
    }
    
    @GetMapping("/categoria/{categoria}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String categoria) {
        return service.obterSeriesPorCategoria(categoria);
    }
    
    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obterSeriesPorCategoria(@PathVariable Long id) {
        return service.obterTopEpisodiosPorSerie(id);
    }
    
}
