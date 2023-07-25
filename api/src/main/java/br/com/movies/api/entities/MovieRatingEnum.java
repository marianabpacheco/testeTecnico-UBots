package br.com.movies.api.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum MovieRatingEnum {
    EXCELENT,
    GOOD,
    FINE,
    BAD,
    TERRIBLE
}
