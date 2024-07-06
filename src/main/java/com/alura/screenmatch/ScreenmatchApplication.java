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
public class ScreenmatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

}
