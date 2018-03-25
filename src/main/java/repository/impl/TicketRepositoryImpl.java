package repository.impl;

import model.Ticket;
import repository.TicketRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketRepositoryImpl implements TicketRepository {
    @Override
    public List<Ticket> findAll() {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket");
            return createObjects(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Ticket> createObjects(ResultSet resultSet) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            while(resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getLong(1));
                ticket.setName(resultSet.getString(2));
                ticket.setPrice(resultSet.getLong(3));
                //
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
