package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class Film extends Model {
    private int id;

    @NotNull
    @NotBlank
    private String filmName;

    @NotBlank
    @Size(max = 200)
    private String filmDescription;

    private LocalDate releaseDate;

    @PositiveOrZero
    private Duration duration;
}
