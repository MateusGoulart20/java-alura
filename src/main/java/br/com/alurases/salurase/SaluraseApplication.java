package br.com.alurases.salurase;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alurases.salurase.model.DadosSerie;
import br.com.alurases.salurase.service.ConsumoApi;
import br.com.alurases.salurase.service.ConverteDados;

@SpringBootApplication
public class SaluraseApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SaluraseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi(); 	
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=659f265d");
		//System.out.println(json);
		//json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
	}
	
}
