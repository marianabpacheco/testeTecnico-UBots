package br.com.movies.api.service;

import br.com.movies.api.dto.RatingDTO;
import br.com.movies.api.entities.Customer;
import br.com.movies.api.entities.Movie;
import br.com.movies.api.entities.Rating;
import br.com.movies.api.repository.CustomerRepository;
import br.com.movies.api.repository.MovieRepository;
import br.com.movies.api.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final CustomerRepository customerRepository;
    private final RatingRepository ratingRepository;

    public MovieService(MovieRepository movieRepository, CustomerRepository customerRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.customerRepository = customerRepository;
        this.ratingRepository = ratingRepository;
    }


    public List<Movie> listMovies(){
        return movieRepository.findAll();
    }

    public Movie searchMovieById(UUID id){
       return movieRepository.findById(id).orElse(null);
    }

    public Movie createMovie(Movie movie){
        return movieRepository.save(movie);
    }

    public Movie updateMovie(UUID id, Movie updatedMovie){
       return movieRepository.save(updatedMovie);
    }

    public void deleteMovie(UUID id){
        movieRepository.deleteById(id);
    }


    public Movie rateMovie(RatingDTO ratingDTO) {
       Movie movie = movieRepository.findById(ratingDTO.getMovieId()).orElse(null);
       Customer customer = customerRepository.findById(ratingDTO.getCustomerId()).orElse(null);
       if(movie != null && customer != null){
         if(ratingRepository.existsByCustomerAndMovie(customer,movie)){
             throw new RuntimeException("Customer already rated this movie.");
         }
         Rating rating = new Rating();
         rating.setMovie(movie);
         rating.setCustomer(customer);
         rating.setRating(ratingDTO.getRating());

         ratingRepository.save(rating);

         return movieRepository.save(movie);
       }

        return null;
    }

    public Movie searchForMovieUnratedByCustomerId(UUID customerID){
        List<Movie> movies = movieRepository.findAll();
        List<Rating> ratingsCustomer = ratingRepository.findByCustomerId(customerID);

        for(Rating rating : ratingsCustomer){
            movies.removeIf(movie -> movie.getId().equals(rating.getMovie().getId()));
        }

        if(movies.isEmpty()){
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(movies.size());

        return movies.get(randomIndex);
    }


}


