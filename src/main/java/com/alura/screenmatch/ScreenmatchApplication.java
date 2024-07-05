package com.alura.screenmatch;

import com.alura.screenmatch.model.DatosEpisodio;
import com.alura.screenmatch.model.DatosSerie;
import com.alura.screenmatch.model.DatosTemporadas;
import com.alura.screenmatch.principal.EjemplosStreams;
import com.alura.screenmatch.principal.Principal;
import com.alura.screenmatch.repository.SerieRepository;
import com.alura.screenmatch.service.ConsultaChatGPT;
import com.alura.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.service.ConvierteDatos;
import com.alura.screenmatch.test.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    //Injecion de dependencias
    @Autowired
    private SerieRepository repository;
    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repository);
//        principal.muestraElMenu();
        principal.iniciarPrograma();

//        try {
//            var res = ConsultaChatGPT.obtenerTraduccion("A paraplegic Marine dispatched to the moon Pandora on a unique " +
//                    "mission " +
//                    "becomes torn between following his orders and protecting the world he feels is his home.");
//            System.out.println("res = " + res);
//
//        } catch (Exception e) {
//            throw new RuntimeException("Error:" + e);
//        }

//        var consumoApi = new ConsumoAPI();
////        var json = consumoApi.obtenerDatos("https://jsonplaceholder.typicode.com/users");
//        var json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&apikey=3decb139");
//
//        ConvierteDatos conversor = new ConvierteDatos();
//        var datos = conversor.obtenerDatos(json, DatosSerie.class);
//        System.out.println(datos);
//
//        json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season=1&episode=1&apikey=3decb139");
//        DatosEpisodio espisodioDatos = conversor.obtenerDatos(json, DatosEpisodio.class);
//        System.out.println(espisodioDatos);
//
//        List<DatosTemporadas> temporadas = new ArrayList<>();
//        for (int i = 1; i <= datos.totalDeTemporadas() ; i++) {
//            json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season="+ i +"&apikey=3decb139");
////            System.out.println(json);
//            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
//            temporadas.add(datosTemporadas);
//        }
//        temporadas.forEach(System.out::println);


//        EjemplosStreams ejemplosStreams = new EjemplosStreams();
//        ejemplosStreams.muestraEjemplo();

//        Alumno alumno = new Alumno("Lalo", "1994-05-15");
//        System.out.println(alumno.getEdad());
//        System.out.println(alumno);
    }
}
