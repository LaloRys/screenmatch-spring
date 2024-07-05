package com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;


@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private Integer totalDeTemporadas;
    private Double evaluacion;
    private String poster;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String actores;
    private String sinopsis;
    private String fechaLanzamiento;
    private String idioma;

    // @Transient indica que no queremos guardar este dato
//    @Transient

    //TODO: Buscar como con cascade actuliza lo que se necesita para guardar cambios
    //  Traer los datos carga perezosa (lazy loading) y la carga ansiosa (eager loading)
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios;

    //Constructor predeterminado || Necesario para JPA
    public Serie() {
    }

    public Serie(DatosSerie datosSerie) {
        this.titulo = datosSerie.titulo();
        this.totalDeTemporadas = datosSerie.totalDeTemporadas();
        //Optinal puede devolver un valor o no *orElse si no logra pondra 0.0
        this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(0.0);
        this.poster = datosSerie.poster();
        //trim() elimina los espacios original (por ejemplo, " Drama "), trim() los elimina, dejando solo "Drama".
        this.genero = Categoria.fromString(datosSerie.genero().split(",")[0].trim());
        this.actores = datosSerie.actores();
        this.sinopsis = datosSerie.sinopsis();
        this.fechaLanzamiento = datosSerie.fechaLanzamiento();
        this.idioma = datosSerie.idioma();
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(episodio -> {
            episodio.setSerie(this);
        });
        this.episodios = episodios;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalDeTemporadas() {
        return totalDeTemporadas;
    }

    public void setTotalDeTemporadas(Integer totalDeTemporadas) {
        this.totalDeTemporadas = totalDeTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        return
                "genero=" + genero +
                        ", titulo='" + titulo + '\'' +
                        ", totalDeTemporadas=" + totalDeTemporadas +
                        ", evaluacion=" + evaluacion +
                        ", poster='" + poster + '\'' +
                        ", actores='" + actores + '\'' +
                        ", sinopsis='" + sinopsis + '\'' +
                        ", fechaLanzamiento='" + fechaLanzamiento + '\'' +
                        ", idioma='" + idioma + '\'' +
                        ", --> Episodios='" + episodios + '\'';
    }
}