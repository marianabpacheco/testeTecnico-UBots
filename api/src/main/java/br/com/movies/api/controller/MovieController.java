package br.com.movies.api.controller;

import br.com.movies.api.dto.MovieDTO;
import br.com.movies.api.dto.RatingDTO;
import br.com.movies.api.entities.Movie;
import br.com.movies.api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping
    public List<MovieDTO> listMovies(){
        return movieService.listMovies().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> searchMovieById(@PathVariable UUID id){
        Movie movie = movieService.searchMovieById(id);

        if(movie != null){
            MovieDTO movieDTO = mapToDTO(movie);
            return new ResponseEntity<>(movieDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO){
        Movie movie = mapToEntity(movieDTO);
        Movie createdMovie = movieService.createMovie(movie);
        MovieDTO createdMovieDTO = mapToDTO(createdMovie);
        return new ResponseEntity<>(createdMovieDTO, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable UUID id, @RequestBody MovieDTO movieDTO){
        Movie movie = mapToEntity(movieDTO);
        movie.setId(id);
        Movie updatedMovie = movieService.updateMovie(id,movie);
        if(updatedMovie != null){
            MovieDTO updatedMovieDTO = mapToDTO(updatedMovie);
            return new ResponseEntity<>(updatedMovieDTO,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable UUID id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/rate")
    public ResponseEntity<MovieDTO> rateMovie( @RequestBody RatingDTO ratingDTO) {
        Movie ratedMovie = movieService.rateMovie(ratingDTO);
        if (ratedMovie != null) {
            MovieDTO ratedMovieDTO = mapToDTO(ratedMovie);
            return new ResponseEntity<>(ratedMovieDTO,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/suggestion")
    public ResponseEntity<MovieDTO> searchForUnratedMovieByCustomerId(@RequestBody RatingDTO ratingDTO) {
        Movie unratedMovie = movieService.searchForMovieUnratedByCustomerId(ratingDTO.getCustomerId());
        if(unratedMovie != null){
            MovieDTO unratedMovieDTO = mapToDTO(unratedMovie);
            return ResponseEntity.ok(unratedMovieDTO);
        }
        return ResponseEntity.notFound().build();
    }

    private MovieDTO mapToDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setDirector(movie.getDirector());
        movieDTO.setReleaseYear(movie.getReleaseYear());
        movieDTO.setGenre(movie.getGenre());
        return movieDTO;
    }

    private Movie mapToEntity(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setId(movieDTO.getId());
        movie.setTitle(movieDTO.getTitle());
        movie.setDirector(movieDTO.getDirector());
        movie.setReleaseYear(movieDTO.getReleaseYear());
        movie.setGenre(movieDTO.getGenre());
        return movie;
    }
}

