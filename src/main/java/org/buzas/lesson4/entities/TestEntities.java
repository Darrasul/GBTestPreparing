package org.buzas.lesson4.entities;

import org.buzas.lesson4.entities.films.Film;
import org.buzas.lesson4.entities.films.FilmMapper;
import org.buzas.lesson4.entities.schedules.Session;
import org.buzas.lesson4.entities.schedules.SessionMapper;
import org.buzas.lesson4.entities.schedules.TicketType;
import org.buzas.lesson4.entities.tickets.Ticket;
import org.buzas.lesson4.entities.tickets.TicketMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Time;

public class TestEntities {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/GB_testPrep", "root", "root");
//            Films - успех
            FilmMapper filmMapper = new FilmMapper(connection);
//            filmMapper.addFilm(new Film("test", new Time(01, 00, 00)));
//            System.out.println(filmMapper.findAll());
//            filmMapper.updateFilm(6L, new Film("testing", new Time(02, 00, 00)));
//            filmMapper.deleteFilm(6L);

//            Schedule - успех
            SessionMapper sessionMapper = new SessionMapper(connection, filmMapper);
//            sessionMapper.addSession(new Session(filmMapper.findFilm(2L), TicketType.MOURNING, new Time(00, 00,00)));
//            System.out.println(sessionMapper.findAll());
//            sessionMapper.updateSession(6L, new Session(filmMapper.findFilm(1L), TicketType.DAY, new Time(14, 00, 00)));
//            sessionMapper.deleteSession(6L);

//            Ticket - успех
            TicketMapper ticketMapper = new TicketMapper(connection, sessionMapper);
//            ticketMapper.addTicket(new Ticket(ticketMapper.getSessionMapper().findSession(3L), 5));
//            ticketMapper.addTicket(new Ticket(ticketMapper.getSessionMapper().findSession(3L), 4));
//            ticketMapper.addTicket(new Ticket(ticketMapper.getSessionMapper().findSession(3L), 5));
//            System.out.println(ticketMapper.findAll());
//            ticketMapper.updateTicket(4L, new Ticket(ticketMapper.getSessionMapper().findSession(3L), 6));
//            ticketMapper.deleteTicket(4L);
//            ticketMapper.deleteTicket(5L);
            sessionMapper.checkForMistakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
