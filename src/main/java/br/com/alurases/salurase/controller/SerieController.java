package br.com.alurases.salurase.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.alurases.salurase.dto.SerieDTO;
import br.com.alurases.salurase.service.SerieService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    
}
