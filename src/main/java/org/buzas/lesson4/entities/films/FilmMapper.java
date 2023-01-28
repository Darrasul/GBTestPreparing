package org.buzas.lesson4.entities.films;

import java.lang.module.FindException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class FilmMapper {
    private final Connection connection;
    private final FilmMap filmMap;

    public FilmMapper(Connection connection) {
        this.connection = connection;
        this.filmMap = new FilmMap();
    }

    public Set<Film> findAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM films");
        try (ResultSet resultSet = statement.executeQuery()){
                Set<Film> films = new HashSet<>();
                while (resultSet.next()) {
                    Film film = new Film();
                    film.setId(resultSet.getLong(1));
                    film.setName(resultSet.getString(2));
                    film.setDuration(resultSet.getTime(3));
                    films.add(film);
                    if (filmMap.getFilm(film.getId()) == null){
                        filmMap.addFilm(film);
                    }
                }
                return films;
        }
    }

//    По хорошему, тут везде расписывать Optional<Film или еще аналоги в соседних мапперах>, но тогда придется тянуть лишний код, ради функционала,
//    который по задаче не нужен
    public Film findFilm(Long id) throws SQLException {
        if (filmMap.getFilm(id) != null){
            return filmMap.getFilm(id);
        } else {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM films WHERE id = ?");
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    Film film = new Film();
                    film.setId(resultSet.getLong(1));
                    film.setName(resultSet.getString(2));
                    film.setDuration(resultSet.getTime(3));
                    if (filmMap.getFilm(film.getId()) == null){
                        filmMap.addFilm(film);
                    }
                    return film;
                }
            }
            throw new FindException("No such film with id: " + id);
        }
    }

    public Long findFilmId(Film film) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("" +
                "SELECT id FROM films WHERE name = ? AND duration = ?");
        statement.setString(1, film.getName());
        statement.setTime(2, film.getDuration());
        try (ResultSet resultSet = statement.executeQuery()){
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            throw new FindException("No such film with that name and duration");
        }
    }

    public void addFilm(Film film) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO films (name, duration) VALUES (?, ?)");
            statement.setString(1, film.getName());
            statement.setTime(2, film.getDuration());
            statement.executeUpdate();
            film.setId(findFilmId(film));
            filmMap.addFilm(film);
            System.out.println("Adding " + film.getName() + " was success");
        } catch (SQLException e) {
//            Не стал прикручивать нормальное логирование, т.к. это пример, который дальше IDE не уйдет
            e.printStackTrace();
            System.out.println("Adding " + film.getName() + " was failure");
        }
    }

    public void updateFilm(Long id, Film film) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE films SET name = ?, duration = ? WHERE id = ?");
            statement.setString(1, film.getName());
            statement.setTime(2, film.getDuration());
            statement.setLong(3, id);
            statement.executeUpdate();
            filmMap.updateFilm(id, film);
            System.out.println("Updating " + film.getName() + " at " + id + " was success");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Updating " + film.getName() + " at " + id + " was failure");
        }
    }

    public void deleteFilm(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM films WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            filmMap.deleteFilm(id);
            System.out.println("Deleting film with id " + id + " was success");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Deleting film with id " + id + " was failure");
        }
    }
}
