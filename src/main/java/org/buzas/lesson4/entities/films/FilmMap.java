package org.buzas.lesson4.entities.films;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class FilmMap {
    private final HashMap<Long, Film> films = new HashMap<>();

    public void addFilm(Film film) {
        films.put(film.getId(), film);
    }

    public Film getFilm(Long id) {
        return films.get(id);
    }

    public void updateFilm(Long id, Film film) {
        films.put(id, film);
    }

    public void deleteFilm(Long id) {
        films.remove(id);
    }
}
