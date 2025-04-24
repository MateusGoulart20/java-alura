package br.com.alurases.salurase;


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

@SpringBootApplication
public class SaluraseApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SaluraseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {	
		Principal principal = new Principal();
		principal.exibeMenu();
	}
	
}
