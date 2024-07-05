package com.alura.screenmatch.principal;

import com.alura.screenmatch.model.*;
import com.alura.screenmatch.repository.SerieRepository;
import com.alura.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();

    //Final para que no se pueda modificar y deben ser en mayusculas
    private final String API_KEY = "&apikey=" + System.getenv("API_KEY_OMDB");
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private List<Serie> series;
    private Optional<Serie> serieBuscada;
    private SerieRepository repositorio;

    public Principal(SerieRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        System.out.println("Escribe el nombre de la seria que deseas buscar: ");
        //Busca los datos generales de la serie
        var nombreSeries = sc.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSeries.replace(" ", "+") + API_KEY);
        System.out.println("json = " + json);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println("datos = " + datos);

        //Busca los datos de todas las temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoApi.obtenerDatos(URL_BASE + nombreSeries.replace(
                    " ", "+") + "&Season=" + i + API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
        System.out.println("temporadas = " + temporadas);
        temporadas.forEach(System.out::println);

        //Mostrar solo el titulo de los episodios para las temporadas
//        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
//            List<DatosEpisodio> episodiosTemporardas = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporardas.size(); j++) {
//                System.out.println(episodiosTemporardas.get(j).titulo());
//            }
//        }

        //Mejoria usando funciones lambda || Funciones anonimas
//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //Convertir todas las informaciones a una lista del tipo DatosEpisodio

        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());
//                .toList();

        System.out.println("datosEpisodios = " + 3.);
        //Top 5 Episodios
//        System.out.println("Top 5 Episodios");
//        datosEpisodios.stream()
//                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
//                //peek sirve para ver el estado de los datos
//                .peek(e -> System.out.println("Primer filtro (N/A) " + e))
//                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//                .peek(e -> System.out.println("Segundo filtro (Ordenas M>m) " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Tercer filtro (Mayusculas) " + e))
//                .limit(5)
//                .forEach(System.out::println);

        //Convirtiendo los datos a una lista de tipo episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(e -> new Episodio(t.temporada(), e)))
                .collect(Collectors.toList());

        System.out.println("episodios = " + episodios);
//        episodios.forEach(System.out::println);

        System.out.println("Top 10 con temporada: ");
        episodios.stream()
                .sorted(Comparator.comparing(Episodio::getEvaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);

        // Busqueda de espisodios a partir de x año
//        System.out.println("Escribe el año a partir de cual deseas ver los episodios: ");
//        var fecha = sc.nextInt();
//        sc.nextLine();
//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1 , 1);
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getFechaLanzamiento() != null && e.getFechaLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Episodio: " + e.getTitulo() +
//                                " Fecha de lanzamiento: " + e.getFechaLanzamiento().format(dtf)
//                ));

        //Busqueda de episodio por un pedazo del titulo
//        System.out.println("Escribe el titulo del episodio que deseas buscar: ");
//        var pedazoTitulo = sc.nextLine();
//        System.out.println("pedazoTitulo = " + pedazoTitulo.toUpperCase());
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
//                .findFirst();
//        System.out.println("episodioBuscado = " + episodioBuscado);
//
//        if (episodioBuscado.isPresent()) {
//            System.out.println("Episodio encontrado: " + episodioBuscado.get());
//        } else {
//            System.out.println("No se encontro el episodio");
//        }

        //Rating para temporadas
        System.out.println("Rating para temporadas");
        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println("evaluacionesPorTemporada = " + evaluacionesPorTemporada);
        /* Esta clase se utiliza para calcular estadísticas resumidas de valores numéricos de tipo double.
        Es útil cuando necesitas calcular varios estadísticos, como el número total de valores,
        la suma, el promedio, el mínimo y el máximo, todo en un solo paso. */
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("est = " + est);
        System.out.println("Promedio: " + est.getAverage());
        System.out.println("Maximo: " + est.getMax());
        System.out.println("Minimo: " + est.getMin());
        System.out.println("Total: " + est.getSum());
        System.out.println("Cantidad: " + est.getCount());

    }

    public void iniciarPrograma() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    Elige una opcion: 
                    1. Buscar series
                    2. Buscar episodios 
                    3. Mostrar Series Buscados
                    4. Buscar series por Titulo
                    5. Top 5 episodios con mayor rating
                    6. Buscar Series por categoria
                    7. Buscar serie por numero de temporada y rating
                    8. Buscar episodios por titulo
                    9. Top 5 episodios por serie
                                        
                    0. Salir
                    """);
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1 -> buscarSerieWeb();
                case 2 -> buscarEpisodioPorSerie();
                case 3 -> mostrarSeriesBuscadas();
                case 4 -> buscarSeriesPorTitulo();
                case 5 -> top5EpisodiosConMayorRating();
                case 6 -> buscarSeriesPorCategoria();
                case 7 -> buscarSeriePorNumeroDeTemporadaYRating();
                case 8 -> buscarEpisodioPorTitulo();
                case 9 -> buscarTop5EpisodiosPorSerie();

                case 0 -> System.out.println("Cerrando la aplicacion... Adios...");
                default -> System.out.println("Opcion no valida");
            }
        }
    }


    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar: ");
        var nombreSeries = sc.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSeries.replace(" ", "+") + API_KEY);
        System.out.println("json = " + json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie de la cual quieres ver los episodios: ");
        var nombreSerie = sc.nextLine();

        Optional<Serie> serieBuscada = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        System.out.println("serieBuscada = " + serieBuscada);

        List<DatosTemporadas> temporadas = null;
        if (serieBuscada.isPresent()) {
            var serieEncontrada = serieBuscada.get();
            temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                var json =
                        consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&Season=" + i + API_KEY);
                var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporadas);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(e -> new Episodio(t.temporada(), e)))
                    .collect(Collectors.toList());
            System.out.println("episodios = " + episodios);

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        } else {
            System.out.println("No se encontro la serie");
        }
    }

    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        System.out.println("datos = " + datos);
        try {
            if (datos.titulo() != null) {
                Serie serie = new Serie(datos);
                repositorio.save(serie);
//                repositorio.
                datosSeries.add(datos);
                System.out.println("datosSeries = " + datosSeries);
            } else {
                System.out.println("No se encontro la serie");
            }

        } catch (Exception e) {
            System.out.println("Error al agregar la serie: " + e.getMessage());
        }

    }

    private void mostrarSeriesBuscadas() {
//        List<Serie> series = new ArrayList<>();
//        series = datosSeries.stream()
//                .map(d -> new Serie(d))
//                .collect(Collectors.toList());

//        List<Serie> series = repositorio.findAll();
        series = repositorio.findAll();
        System.out.println("repositorio.findAll() = " + series);
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriesPorTitulo() {
        System.out.println("Escribe el titulo de la serie que deseas buscar: ");
        var titulo = sc.nextLine();
         serieBuscada = repositorio.findByTituloContainsIgnoreCase(titulo);
//        System.out.println("serieBuscada = " + serieBuscada);
        if (serieBuscada.isPresent()) {
            System.out.println("La series buscada es: " + serieBuscada.get());
        } else {
            System.out.println("No se encontro la serie");
        }

    }

    private void top5EpisodiosConMayorRating() {
        System.out.println("Top 5 Series por Rating:");
        List<Serie> top5Series = repositorio.findTop5ByOrderByEvaluacionDesc();
        top5Series.forEach(s ->
                System.out.println("Serie: " + s.getTitulo() + " --" + " Rating: " + s.getEvaluacion()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Escribe la categoria/genero de la serie que deseas buscar: ");
        var genero = sc.nextLine();
        var categoria = Categoria.fromEspanol(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Las Series de la categoria " + genero);
        seriesPorCategoria.forEach(System.out::println);

    }

    private void buscarSeriePorNumeroDeTemporadaYRating() {
        System.out.println("Escribe el numero de temporada de la serie que deseas buscar: ");
        var numeroTemporada = sc.nextInt();
        sc.nextLine();
        System.out.println("Escribe el rating de la serie que deseas buscar: ");
        var rating = sc.nextDouble();
        sc.nextLine();
//        List<Serie> seriesPorTemporadaYRating = repositorio
//        .findByTotalDeTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(numeroTemporada, rating);
        List<Serie> seriesPorTemporadaYRating = repositorio.seriesPorTemporadaYEvaluacion(numeroTemporada, rating);
        System.out.println("La Series con las temporadas " + numeroTemporada + " y rating " + rating + " son:");
        seriesPorTemporadaYRating.forEach(s -> System.out.println("Serie: " + "'" + s.getTitulo() + "'" + " -->" + " " +
                "Rating" +
                ": " + s.getEvaluacion()));
    }

    private void buscarEpisodioPorTitulo() {
        System.out.println("Escribe el nombre del episodio que deseas buscar: ");
        var nombreEpisodio = sc.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.espisodiosPorNombre(nombreEpisodio);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Serie: %s Temporada: %s Episodio: %s Evaluacion: %s\n",
                        e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));
    }

    private void buscarTop5EpisodiosPorSerie() {
        buscarSeriesPorTitulo();
        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();
            List<Episodio> top5Episodios = repositorio.top5Episodios(serie);
            top5Episodios.forEach(e ->
                    System.out.printf("Serie: %s - Titulo: %s - Temporada: %s - Episodio: %s - Evaluacion: %s\n",
                            e.getSerie().getTitulo(), e.getTitulo(), e.getTemporada(), e.getNumeroEpisodio(),
                            e.getEvaluacion()));
        }
    }


}
