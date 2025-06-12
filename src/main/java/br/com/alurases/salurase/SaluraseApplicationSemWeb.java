package br.com.alurases.salurase;


import org.springframework.beans.factory.annotation.Autowired;

//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import br.com.alurases.salurase.model.DadosEpisodio;
//import br.com.alurases.salurase.model.DadosSerie;
//import br.com.alurases.salurase.model.DadosTemporada;
//import br.com.alurases.salurase.service.ConsumoApi;
//import br.com.alurases.salurase.service.ConverteDados;
import br.com.alurases.salurase.principal.Principal;
import br.com.alurases.salurase.repository.SerieRepository;
/*
 * 
 *
@SpringBootApplication
public class SaluraseApplicationSemWeb implements CommandLineRunner {
	@Autowired
	private SerieRepository repositorio;
	public static void main(String[] args) {
		SpringApplication.run(SaluraseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {	
		Principal principal = new Principal(repositorio);
		principal.exibeMenu();
	}
	
}
*/