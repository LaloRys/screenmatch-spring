package com.alura.screenmatch.controller;

import com.alura.screenmatch.dto.EpisodioDTO;
import com.alura.screenmatch.dto.SerieDTO;
import com.alura.screenmatch.model.Serie;
import com.alura.screenmatch.repository.SerieRepository;
import com.alura.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
//URL base
@RequestMapping("/series")
public class SerieController {
    @Autowired
    private SerieService service;
    // Rutas
    @GetMapping()
    public List<SerieDTO> obtenerTodasLasSeries() {
        return service.obtenerTodasLasSeries();
    }
    @GetMapping("/top5")
    public List<SerieDTO> obtenerTop5() {
        return service.obtenerTop5();
    }
    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerSeriesMasRecientes() {
        return service.obtenerSeriesMasRecientes();
    }

    //Parametro dinamico
    @GetMapping("/{id}")
    public SerieDTO obtenerSeriePorId(@PathVariable Long id) {
        return service.obtenerSeriePorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obtenerTodasLasTemporadas(@PathVariable Long id){
        return service.obtenerTodasLasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{temporada}")
    public List<EpisodioDTO> obtenerTemporadaPorNumero(@PathVariable Long id,
                                                       @PathVariable Integer temporada){
        return service.obtenerTemporadaPorNumero(id, temporada);
    }
    @GetMapping("/categoria/{nombreGenero}")
    public List<SerieDTO> obtenerSeriesPorGenero(@PathVariable String nombreGenero){
        return service.obtenerSeriesPorGenero(nombreGenero);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obtenerTopEpisodios(@PathVariable Long id){
        return service.obtenerTopEpisodios(id);
    }
}
