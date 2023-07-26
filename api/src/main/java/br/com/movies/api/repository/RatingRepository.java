package br.com.movies.api.repository;

import br.com.movies.api.entities.Customer;
import br.com.movies.api.entities.Movie;
import br.com.movies.api.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {
    boolean existsByCustomerAndMovie(Customer customer, Movie movie);

    List<Rating> findByCustomerId(UUID costumerId);
}
