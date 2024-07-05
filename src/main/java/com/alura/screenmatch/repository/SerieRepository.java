package com.alura.screenmatch.repository;

import com.alura.screenmatch.model.Categoria;
import com.alura.screenmatch.model.Episodio;
import com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    //Consultas Derivadas (derived queries)
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria categoria);

    //    List<Serie> findByTotalDeTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(Integer totalDeTemporadas,
    //    Double evaluacion);
    //SQL nativo
//    @Query(value = "SELECT * FROM series WHERE total_de_temporadas <= 6 AND evaluacion >= 8", nativeQuery = true)
//    JPQL
    @Query("SELECT s FROM Serie s WHERE s.totalDeTemporadas <= :totalDeTemporadas AND s.evaluacion >= :evaluacion")
    List<Serie> seriesPorTemporadaYEvaluacion(Integer totalDeTemporadas, Double evaluacion);

    //LIKE %abc%: Coincide con cualquier cadena que contenga "abc".
    //LIKE abc%: Coincide con cualquier cadena que comience con "abc".
    //LIKE %abc: Coincide con cualquier cadena que termine con "abc".
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> espisodiosPorNombre(String nombreEpisodio);
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);
}

