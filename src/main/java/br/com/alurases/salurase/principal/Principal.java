package br.com.alurases.salurase.principal;

import java.lang.StackWalker.Option;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.com.alurases.salurase.model.Categoria;
import br.com.alurases.salurase.model.DadosEpisodio;
import br.com.alurases.salurase.model.DadosSerie;
import br.com.alurases.salurase.model.DadosTemporada;
import br.com.alurases.salurase.model.Episodio;
import br.com.alurases.salurase.model.Serie;
import br.com.alurases.salurase.repository.SerieRepository;
import br.com.alurases.salurase.service.ConsumoApi;
import br.com.alurases.salurase.service.ConverteDados;

public class Principal {
    private Scanner leitura = new Scanner(System.in, "utf-8");
    private ConsumoApi consumo = new ConsumoApi(); 	
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=659f265d";
    //("gilmore+girls&season="+i+);
    private List<DadosSerie> dadosSeries = new ArrayList<>();
	private SerieRepository repositorio;
    private List<Serie> series = new ArrayList<>();
    public Principal(SerieRepository repository){
        this.repositorio = repository;
    }


    public void exibeMenu(){
        var opcao = -1;
        while(opcao != 0){
            var menu = """
                    1 - Buscar séries.
                    2 - Buscar episódios.
                    3 - Listar séries buscadas.
                    4 - Buscar série por titulo.
                    5 - Buscar série por ator.
                    6 - Top 5 Séries
                    7 - Buscar séries por categoria.
                    8 - Buscar séries por temporadas e avaliacao.
                    9 - Buscar Episodio por trecho de nome.
                    10 - Top 5 episodios por série.
                    11 - Buscar episodios a partir de uma data.

                    0 - Sair                                 
                    """;
    
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();
    
            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3: 
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                break;
                case 5:
                    buscarSeriePorAtor();
                break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                break;
                case 8:
                    buscarSeriesPorTemporadaAvaliacao();
                break;
                case 9:
                    buscarEpisodioPorTrecho();
                break;
                case 10:
                    topEpisodiosPorSerie();
                break;
                case 11:
                    buscarEpisodioDepoisDeUmaData();
                break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    


    private void listarSeriesBuscadas(){
        /*/
        List<Serie> series = new ArrayList<>();
        series = dadosSeries.stream()
        .map(d -> new Serie(d))
        .collect(Collectors.toList());
        List<Serie> 
        /*/
        series = repositorio.findAll();
        series.stream()
            .sorted(Comparator.comparing(Serie::getGenero))
            .forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        repositorio.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);
        /*/
        Optional<Serie> serie = series.stream()
        .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
        .findFirst();   
        /*/
        
        if(serie.isPresent()){
            //DadosSerie dadosSerie = getDadosSerie();
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();
    
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        } else{
            System.out.println("Série não encontrada");
        }
    }

    public void ocult(){
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
		var json = consumo
            .obterDados(
                ENDERECO 
                + nomeSerie
                    .replace(" ", "+") 
                + API_KEY
            );
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

		List<DadosTemporada> temporadas = new ArrayList();
        
		for (int i = 1; i<=dados.totalTemporadas(); i++){
			json = consumo.obterDados(
                ENDERECO 
                + nomeSerie
                    .replace(" ", "+") 
                + "&season="
                + i
                + API_KEY
            );
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		} 

		//temporadas.forEach(System.out::println);
        //for( int i = 0 ; i < dados.totalTemporadas(); i++){
        //    List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
        //    for (int j = 0; j < episodiosTemporada.size(); j++){
        //        System.out.println(episodiosTemporada.get(j).titulo());
        //    }
        //}

        temporadas.forEach(t -> 
            t.episodios().forEach(e -> 
                System.out.println(e.titulo())
            )
        );

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
            .flatMap(t-> t.episodios().stream())
            .collect(Collectors.toList()); // Coletas as informações desejadas.
            //.toList(); Lista imutável.
        /*/
        System.out.println("\nTop 10 episódios.");
        dadosEpisodios.stream()
            .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
            .peek(e -> System.out.println("Primeiro Filtro (N/A): " + e))
            .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
            .peek(e -> System.out.println("Ordenação: " + e))
            .limit(10)
            .peek(e -> System.out.println("Limite: " + e))
            .map(e -> e.titulo().toUpperCase())
            .peek(e -> System.out.println("Mapeamento: " + e))
            .forEach(System.out::println);
        /*/
        List<Episodio> episodios = temporadas.stream()
            .flatMap(t-> t.episodios().stream()
                .map(d -> new Episodio(t.numero(), d))
            ).collect(Collectors.toList());
        
        episodios.forEach(System.out::println);
/*/
        System.out.println("Digite um trecho do titulo do episoóio");
        var trechoTitulo = leitura.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
            .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
            .findFirst();

        if(episodioBuscado.isPresent()) {
            System.out.println("Episódio encontrado! ");
            System.out.println("Temporada: "+episodioBuscado.get().getTemporada());
        }else{
            System.out.println("Episódio não encontrado!");
        }
        
        System.out.println("A partir de que ano você deseja ver os episódios? ");
        var ano = leitura.nextInt();

        LocalDate dataBusca = LocalDate.of(ano ,1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
            .filter(e -> e .getDataLancamento()!= null && e.getDataLancamento().isAfter(dataBusca))
            .forEach(e -> System.out.println(
                "Temporada: " + e.getTemporada()
                + "Episodio: " + e.getTitulo()
                + "Data lançamento: " + e.getDataLancamento().format(formatador)
            ));
        /*/

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
            .collect(Collectors.groupingBy(Episodio::getTemporada,
                    Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
            .filter(e -> e.getAvaliacao() > 0.0)
            .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Média: "+est.getAverage());
        System.out.println("Melhor Episódio: "+est.getMin());
        System.out.println("Pior Episódio: "+est.getMax());
        System.out.println("Quantidade: "+est.getCount());
    }
    
    private Optional<Serie> buscarSeriePorTitulo() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serieBuscada = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if(serieBuscada.isPresent()){
            System.out.println("Dados da série: "+serieBuscada.get());
        }else{
            System.out.println("Série não encontrada.");
        }
        return serieBuscada;
    }

    private void buscarSeriePorAtor(){
        System.out.println("Digite o nome do ator buscar");
        var nomeAtor = leitura.nextLine();
        System.out.println("A partir de qual avaliacao?");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Series em que '"+nomeAtor+"' trabalhou: ");
        seriesEncontradas.forEach(s->
            System.out.println(s.getTitulo() +", avalicao: "+s.getAvaliacao())
        );
    }

    private void buscarTop5Series(){
        List<Serie> serieTop = repositorio.findTop5ByOrderByAvaliacaoDesc();
        serieTop.forEach(s->
            System.out.println(s.getTitulo() +", avalicao: "+s.getAvaliacao())
        );
    }

    private void buscarSeriesPorCategoria(){
        System.out.println("Deseja buscar séries de que categoria/gênero?");
        String nomeGenero = leitura.nextLine();
        System.out.println(nomeGenero);
        var categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Series da categoria "+categoria);
        seriesPorCategoria.forEach(System.out::println);
    }
    
    private void buscarSeriesPorTemporadaAvaliacao(){
        System.out.println("Deseja buscar séries com até quanto temporadas?");
        var temporadas = leitura.nextInt();
        System.out.println("Deseja buscar séries com mais de quanto de avaliação?");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesPorCategoria = repositorio.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(temporadas, avaliacao);
        System.out.println("Series: ");
        seriesPorCategoria.forEach(System.out::println);
    }
    private void buscarEpisodioPorTrecho(){
        System.out.println("Digite o nome do episodio para busca");
        var nomeEpisodio = leitura.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorTrecho(nomeEpisodio);
        episodiosEncontrados.forEach(e->
        System.out.printf("Série: %s Temporadas %s - Episódio %s - %s\n",
                e.getSerie().getTitulo(),
                e.getTemporada(),
                e.getNumeroEpisodio(),
                e.getTitulo()));
    }
    private void topEpisodiosPorSerie(){
        var seriebuscada = buscarSeriePorTitulo();
        if(seriebuscada.isPresent()){
            var serie = seriebuscada.get();
            List<Episodio> topEpisodios = repositorio.topEpisodiosPorSerie(serie);
            System.out.println();
            topEpisodios.forEach(e->
                System.out.printf("Série: %s Temporadas %s - Episódio %s - %s - Avaliacao %s\n",
                        e.getSerie().getTitulo(),
                        e.getTemporada(),
                        e.getNumeroEpisodio(),
                        e.getTitulo(),
                        e.getAvaliacao()
                )
            );
        }
    }
    private void buscarEpisodioDepoisDeUmaData(){
        var seriebuscada = buscarSeriePorTitulo();
        if(seriebuscada.isPresent()){
            System.out.println("Digite o ano limite para do lançamento");
            var anoLancamento = leitura.nextLine();

            List<Episodio> episodiosAno = repositorio.episodiosPorSerieEAno(seriebuscada, anoLancamento);
            episodiosAno.forEach(System.out::println);
        }
    }
    
}
