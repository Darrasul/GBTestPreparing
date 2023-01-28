package org.buzas.lesson4.entities.tickets;

import org.buzas.lesson4.entities.schedules.TicketType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketPriceCenter {
    private final TicketPriceMap priceMap;
    private final Connection connection;

    public TicketPriceCenter(Connection connection) {
        this.connection = connection;
        this.priceMap = new TicketPriceMap();
    }

    public void findAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM tickets_type");
        try (ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                priceMap.correctPrice(resultSet.getInt(1), resultSet.getDouble(3));
            }
        }
    }

    public double getPrice(int tt_id) throws SQLException {
        if (priceMap.getPrice(tt_id) != null){
            return priceMap.getPrice(tt_id);
        }
        PreparedStatement statement = connection.prepareStatement(
                "SELECT cost FROM tickets_type WHERE id = ?");
        statement.setInt(1, tt_id);
        try (ResultSet resultSet = statement.executeQuery()){
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            throw new SQLException();
        }
    }

    public void correctPrice(Integer tt_id, Double price) {
        TicketType type;
        switch (tt_id) {
            case 1 -> type = TicketType.NIGHT;
            case 2 -> type = TicketType.MOURNING;
            case 3 -> type = TicketType.DAY;
            case 4 -> type = TicketType.EVENING;
            default -> {
                throw new NullPointerException();
            }
        }
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE tickets_type SET cost = ? WHERE id = ?");
            statement.setDouble(1, price);
            statement.setInt(2, tt_id);
            statement.executeUpdate();
            priceMap.correctPrice(tt_id, price);
            System.out.println("Updating cost of " + type + " sessions was a success");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Updating cost of " + type + " sessions was a failure");
        }
    }
}
