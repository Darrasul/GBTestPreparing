package org.buzas.lesson4.entities.tickets;

import lombok.Getter;
import org.buzas.lesson4.entities.schedules.Session;
import org.buzas.lesson4.entities.schedules.SessionMapper;

import java.lang.module.FindException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TicketMapper {
        private final Connection connection;
        private final TicketMap ticketMap;
        @Getter
        private final SessionMapper sessionMapper;
        @Getter
        private final TicketPriceCenter priceCenter;

    public TicketMapper(Connection connection) {
        this.connection = connection;
        this.ticketMap = new TicketMap();
        this.sessionMapper = new SessionMapper(connection);
        this.priceCenter = new TicketPriceCenter(connection);
        try {
            this.sessionMapper.getFilmMapper().findAll();
            this.sessionMapper.findAll();
            this.priceCenter.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TicketMapper(Connection connection, SessionMapper sessionMapper) {
        this.connection = connection;
        this.ticketMap = new TicketMap();
        this.sessionMapper = sessionMapper;
        this.priceCenter = new TicketPriceCenter(connection);
        try {
            this.sessionMapper.getFilmMapper().findAll();
            this.sessionMapper.findAll();
            this.priceCenter.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<Ticket> findAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM tickets");
        try (ResultSet resultSet = statement.executeQuery()){
            Set<Ticket> tickets = new HashSet<>();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getLong(1));
                Session session = sessionMapper.findSession(resultSet.getLong(2));
                ticket.setSession(session);
                ticket.setSeat(resultSet.getInt(3));
                ticket.setSold(resultSet.getBoolean(4));
                ticket.setCost(priceCenter.getPrice(session.getTt_id()));
                tickets.add(ticket);
                if (ticketMap.getTicket(ticket.getId()) == null) {
                    ticketMap.addTicket(ticket);
                }
            }
            return tickets;
        }
    }

    public Ticket findTicket(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM tickets WHERE id = ?");
        statement.setLong(1, id);
        try (ResultSet resultSet = statement.executeQuery()){
            if (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getLong(1));
                Session session = sessionMapper.findSession(resultSet.getLong(2));
                ticket.setSession(session);
                ticket.setCost(priceCenter.getPrice(session.getTt_id()));
                ticket.setSeat(resultSet.getInt(3));
                ticket.setSold(resultSet.getBoolean(4));
                if (ticketMap.getTicket(ticket.getId()) == null) {
                    ticketMap.addTicket(ticket);
                }
                return ticket;
            }
            throw new FindException("No such ticket with id: " + id);
        }
    }

    public Long findTicketId(Ticket ticket) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM tickets WHERE session = ? AND seat = ? AND sold = ?");
        statement.setLong(1, ticket.getSession().getId());
        statement.setInt(2, ticket.getSeat());
        statement.setBoolean(3, ticket.isSold());
        try (ResultSet resultSet = statement.executeQuery()){
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            throw new FindException("No such session with that session_id, seat and sold flag");
        }
    }

    public void addTicket(Ticket ticket) {
        if (ticketMap.isSeatAvailable(ticket)) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO tickets(session, seat, sold) VALUES (?, ?, default)");
                statement.setLong(1, ticket.getSession().getId());
                statement.setInt(2, ticket.getSeat());
                statement.executeUpdate();
                ticket.setId(findTicketId(ticket));
                ticketMap.addTicket(ticket);
                System.out.println("Adding ticket " + ticket.getId() + " was success");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Adding ticket " + ticket.getId() + " was failure");
            }
        } else {
            System.out.println("Seat " + ticket.getSeat() + " at " + ticket.getSession().getFilm() +
                    " " + ticket.getSession().getStartTime() + " is already purchased");
        }
    }

    public void updateTicket(Long id, Ticket ticket) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE tickets SET session = ?, seat = ?, sold = ? WHERE id = ?");
            statement.setLong(1, ticket.getSession().getId());
            statement.setInt(2, ticket.getSeat());
            statement.setBoolean(3, ticket.isSold());
            statement.setLong(4, id);
            statement.executeUpdate();
            ticketMap.updateTicket(id, ticket);
            System.out.println("Updating ticket " + id + " was success");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Updating ticket " + id + " was failure");
        }
    }

    public void deleteTicket(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM tickets WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            ticketMap.deleteTicket(id);
            System.out.println("Deleting ticket " + id + " was success");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Deleting ticket " + id + " was failure");
        }
    }

}
