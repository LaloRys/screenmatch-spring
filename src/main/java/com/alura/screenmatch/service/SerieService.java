package com.alura.screenmatch.service;

import com.alura.screenmatch.dto.EpisodioDTO;
import com.alura.screenmatch.dto.SerieDTO;
import com.alura.screenmatch.model.Categoria;
import com.alura.screenmatch.model.Serie;
import com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> convierteDatos(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalDeTemporadas(), s.getEvaluacion(),
                        s.getPoster(),
                        s.getGenero(), s.getActores(), s.getSinopsis(), s.getFechaLanzamiento(), s.getIdioma()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerTodasLasSeries() {
        System.out.println("serieRepository.findAll() = " + serieRepository.findAll());
        return convierteDatos(serieRepository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return convierteDatos(serieRepository.findTop5ByOrderByEvaluacionDesc());
    }


    public List<SerieDTO> obtenerSeriesMasRecientes() {
        System.out.println("serieRepository.lanzamientoMasRecientes() = " + serieRepository.lanzamientoMasRecientes());
        return convierteDatos(serieRepository.lanzamientoMasRecientes());
    }

    public SerieDTO obtenerSeriePorId(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            System.out.println("serie s por id: " + id + " = " + s);
            var serieDTO = new SerieDTO(s.getId(), s.getTitulo(), s.getTotalDeTemporadas(), s.getEvaluacion(),
                    s.getPoster(),
                    s.getGenero(), s.getActores(), s.getSinopsis(), s.getFechaLanzamiento(), s.getIdioma());
            System.out.println("serieDTO = " + serieDTO);
            return serieDTO;
        }
        return null;
    }

    public List<EpisodioDTO> obtenerTodasLasTemporadas(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio(), e.getEvaluacion()))
                    .collect(Collectors.toList());
        }
        return null;

    }

    public List<EpisodioDTO> obtenerTemporadaPorNumero(Long id, Integer temporada) {
//        Optional<Serie> serie = serieRepository.findById(id);
//        System.out.println("serie = " + serie);
//        if(serie.isPresent()){
//            Serie s = serie.get();
//            return s.getEpisodios().stream()
//                    .filter(e -> e.getTemporada().equals(temporada))
//                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
//                    .collect(Collectors.toList());
//        }
//        return null;
//    }
        return serieRepository.obtenerTemporadasPorNumero(id, temporada).stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio(), e.getEvaluacion()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerSeriesPorGenero(String nombreGenero) {
        Categoria categoria = Categoria.fromEspanol(nombreGenero);
        return convierteDatos(serieRepository.findByGenero(categoria));
    }

    public List<EpisodioDTO> obtenerTopEpisodios(Long id) {
        var topSerie = serieRepository.obtenerTopEpisodios(id).stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio(), e.getEvaluacion()))
                .collect(Collectors.toList());
        System.out.println("topSerie = " + topSerie);
        return topSerie;
    }
}
