package org.buzas.lesson4.entities.schedules;


import lombok.Getter;
import org.buzas.lesson4.entities.films.Film;
import org.buzas.lesson4.entities.films.FilmMapper;

import java.lang.module.FindException;
import java.sql.*;
import java.util.*;

public class SessionMapper {
    private final Connection connection;
    private final SessionMap sessionMap;
    @Getter
    private final FilmMapper filmMapper;

    public SessionMapper(Connection connection) {
        this.connection = connection;
        this.sessionMap = new SessionMap();
        this.filmMapper = new FilmMapper(connection);
        try {
            filmMapper.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SessionMapper(Connection connection, FilmMapper filmMapper) {
        this.connection = connection;
        this.sessionMap = new SessionMap();
        this.filmMapper = filmMapper;
        try {
            filmMapper.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<Session> findAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM schedule");
        try (ResultSet resultSet = statement.executeQuery()){
            Set<Session> sessions = new HashSet<>();
            while (resultSet.next()) {
                Session session = new Session();
                session.setId(resultSet.getLong(1));
                session.setFilm(filmMapper.findFilm(resultSet.getLong(2)));
                session.setStartTime(resultSet.getTime(3));
                session.setTt_id(resultSet.getInt(4));
                sessions.add(session);
                if (sessionMap.getSession(session.getId()) == null) {
                    sessionMap.addSession(session);
                }
            }
            return sessions;
        }
    }

    public Session findSession(Long id) throws SQLException {
        if (sessionMap.getSession(id) != null){
            return sessionMap.getSession(id);
        } else {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM schedule WHERE id_session = ?");
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    Session session = new Session();
                    session.setId(resultSet.getLong(1));
                    session.setFilm(filmMapper.findFilm(resultSet.getLong(2)));
                    session.setStartTime(resultSet.getTime(3));
                    session.setTt_id(resultSet.getInt(4));
                    if (sessionMap.getSession(session.getId()) == null) {
                        sessionMap.addSession(session);
                    }
                    return session;
                }
            }
            throw new FindException("No such session with id: " + id);
        }
    }

    public Long findSessionId(Session session) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id_session FROM schedule WHERE id_film = ? AND tickets_type = ? AND start_time = ?");
        statement.setLong(1, session.getFilm().getId());
        statement.setInt(2, session.getTt_id());
        statement.setTime(3, session.getStartTime());
        try (ResultSet resultSet = statement.executeQuery()){
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            throw new FindException("No such session with that film_id, ticket_type and start_time");
        }
    }

    public void addSession(Session session) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO schedule(id_film, start_time, tickets_type) VALUES (?, ?, ?)");
            statement.setLong(1, session.getFilm().getId());
            statement.setTime(2, session.getStartTime());
            statement.setInt(3, session.getTt_id());
            statement.executeUpdate();
            session.setId(findSessionId(session));
            sessionMap.addSession(session);
            System.out.println("Adding session " + session.getId() + " was success");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Adding session " + session.getId() + " was failure");
        }
    }

    public void updateSession(Long id, Session session) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE schedule SET id_film = ?, start_time = ?, tickets_type = ? WHERE id_session = ?");
            statement.setLong(1, session.getFilm().getId());
            statement.setTime(2, session.getStartTime());
            statement.setInt(3, session.getTt_id());
            statement.setLong(4, id);
            statement.executeUpdate();
            sessionMap.updateSession(id, session);
            System.out.println("Updating session " + session.getFilm().getName() + session.getStartTime() + " at " + id + " was success");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Updating session " + session.getFilm().getName() + session.getStartTime() + " at " + id + " was failure");
        }
    }

    public void deleteSession(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM schedule WHERE id_session = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            sessionMap.deleteSession(id);
            System.out.println("Deleting session " + id + " was success");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Deleting session " + id + " was failure");
        }
    }

//    !!    !!      !!
//    Реальная часть ДЗ
//    !!    !!      !!
    public void checkForMistakes() throws SQLException {
        HashMap<Long, Long> startTime = new HashMap<>();
        HashMap<Long, Long> finishTime = new HashMap<>();
        fillingStartAndFinishTime(startTime, finishTime);
        for (Map.Entry<Long, Long> finish : finishTime.entrySet()) {
            for (Map.Entry<Long, Long> start : startTime.entrySet()) {
                if (!start.getKey().equals(finish.getKey())){
                    if (finish.getValue().compareTo(start.getValue()) > 0 && finish.getKey() == start.getKey() - 1){
                        announceFullResult(finish, start);
                    } else if (finish.getValue().compareTo(start.getValue()) < 0 && finish.getKey() == start.getKey() + 1){
                        announceFullResult(finish, start);
                    }
                }
            }
        }
    }

    private void announceFullResult(Map.Entry<Long, Long> finish, Map.Entry<Long, Long> start) {
        String result = switchLongToString(finish.getValue() - start.getValue());
        Session session1 = sessionMap.getSession(finish.getKey());
        Session session2 = sessionMap.getSession(start.getKey());
        System.out.println("First name, start time and duration, then second ones");
        soutFirstAnswer(session1, session2);
        System.out.println("The time difference was " + result);
    }

    private static void soutFirstAnswer(Session session1, Session session2) {
        Film film1 = session1.getFilm();
        Time time1 = session1.getStartTime();
        Time dur1 = film1.getDuration();
        Film film2 = session2.getFilm();
        Time time2 = session2.getStartTime();
        Time dur2 = film2.getDuration();
        System.out.printf("%s, %tH:%tM:%tS, %tH:%tM:%tS, %s, %tH:%tM:%tS, %tH:%tM:%tS\n",
               film1.getName(), time1, time1, time1, dur1, dur1, dur1,
                film2.getName(), time2, time2, time2, dur2, dur2, dur2);
    }

    private void fillingStartAndFinishTime(HashMap<Long, Long> startTime, HashMap<Long, Long> finishTime) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT f.id, s.start_time + f.duration, start_time - 0 FROM films f\n" +
                            "JOIN schedule s on f.id = s.id_film");
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    startTime.put(resultSet.getLong(1), resultSet.getLong(3));
                    finishTime.put(resultSet.getLong(1), resultSet.getLong(2));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String switchLongToString(Long toString) {
        String init = toString.toString();
        switch (init.length()) {
            case 6 -> {
                String hours = init.substring(0, 2);
                String minutes = init.substring(2, 4);
                String seconds = init.substring(4, 6);
                List<String> strings = Arrays.asList(hours, minutes, seconds);
                return String.join(":", strings);
            }
            case 5 -> {
                String hours = init.substring(0, 1);
                String minutes = init.substring(1, 3);
                String seconds = init.substring(3, 5);
                List<String> strings = Arrays.asList(hours, minutes, seconds);
                return String.join(":", strings);
            }
            case 4 -> {
                String minutes = init.substring(0, 2);
                String seconds = init.substring(2, 4);
                List<String> strings = Arrays.asList("00", minutes, seconds);
                return String.join(":", strings);
            }
            case 3 -> {
                String minutes = init.substring(0, 1);
                String seconds = init.substring(1, 3);
                List<String> strings = Arrays.asList("00", minutes, seconds);
                return String.join(":", strings);
            }
            case 2 -> {
                String seconds = init.substring(0, 2);
                List<String> strings = Arrays.asList("00", "00", seconds);
                return String.join(":", strings);
            }
            case 1 -> {
                List<String> strings = Arrays.asList("00", "00", init);
                return String.join(":", strings);
            }
            default -> {
                throw new IllegalStateException();
            }
        }
    }
}
