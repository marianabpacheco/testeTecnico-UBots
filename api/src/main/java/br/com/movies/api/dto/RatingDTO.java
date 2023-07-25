package br.com.movies.api.dto;


import br.com.movies.api.entities.MovieRatingEnum;
import br.com.movies.api.utils.UUIDDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RatingDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;
    @JsonDeserialize(using = UUIDDeserializer.class)
    private UUID customerId;

    private UUID movieId;

    private MovieRatingEnum rating;
    private LocalDateTime ratingDate;
}
