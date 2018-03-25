package repository.impl;

import model.Sale;
import model.Ticket;
import model.User;
import repository.SaleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleRepositoryImpl implements SaleRepository {

    @Override
    public List<Sale> findAll() {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT s.id, s.sale_date, u.*, t.*" +
                    "FROM sale AS s INNER JOIN user AS u ON(s.user_id = u.id)" +
                    "INNER JOIN ticket AS t ON(s.ticket_id = t.id)");
            return createObjects(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Sale save(Sale sale) {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO sale (user_id, ticket_id, sale_date) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, sale.getUser().getId());
            statement.setLong(2, sale.getTicket().getId());
            statement.setTimestamp(3, Timestamp.valueOf(sale.getDate()));
            //
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                sale.setId(resultSet.getLong(1));
                return sale;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Sale update(Sale sale) {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE sale SET user_id = ?, ticket_id = ?, sale_date = ? WHERE id = ?", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, sale.getUser().getId());
            statement.setLong(2, sale.getTicket().getId());
            statement.setTimestamp(3, Timestamp.valueOf(sale.getDate()));
            statement.setLong(4, sale.getId());

            int rowsChanged = statement.executeUpdate();
            if(rowsChanged > 0)
                return sale;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Sale sale) {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM sale WHERE id = ?");
            statement.setLong(1, sale.getId());
            return (statement.executeUpdate() > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Sale> findByUser(User user) {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT s.id, s.sale_date, u.*, t.*" +
                    "FROM sale AS s INNER JOIN user AS u ON(s.user_id = u.id)" +
                    "INNER JOIN ticket AS t ON(s.ticket_id = t.id)" +
                    "WHERE u.id = ?");
            statement.setLong(1, user.getId());
            return createObjects(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Sale> findByUserOrderByDate(User user) {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT s.id, s.sale_date, u.*, t.*" +
                    "FROM sale AS s INNER JOIN user AS u ON(s.user_id = u.id)" +
                    "INNER JOIN ticket AS t ON(s.ticket_id = t.id)" +
                    "WHERE u.id = ? ORDER BY sale_date DESC");
            statement.setLong(1, user.getId());
            return createObjects(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<Ticket, Integer> findTicketsSales() {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT t.*, COUNT(s.id) " +
                    "FROM sale AS s INNER JOIN " +
                    "ticket AS t ON(s.ticket_id = t.id) " +
                    "GROUP BY s.ticket_id");
            ResultSet resultSet = statement.executeQuery();
            Map<Ticket, Integer> map = new HashMap<>();
            while(resultSet.next()) {
                Ticket ticket = new Ticket();
                //
                ticket.setId(resultSet.getLong(1));
                ticket.setName(resultSet.getString(2));
                ticket.setPrice(resultSet.getLong(3));
                //
                map.put(ticket, resultSet.getInt(4));
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Sale> createObjects(ResultSet resultSet) {
        List<Sale> sales = new ArrayList<>();
        try {
            while(resultSet.next()) {
                Sale sale = new Sale();
                User user = new User();
                Ticket ticket = new Ticket();
                //User
                user.setId(resultSet.getLong(3));
                user.setUsername(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setEmail(resultSet.getString(6));
                user.setUserRole(Enum.valueOf(model.UserRole.class, resultSet.getString(7)));
                //Ticket
                ticket.setId(resultSet.getLong(8));
                ticket.setName(resultSet.getString(9));
                ticket.setPrice(resultSet.getLong(10));
                //Sale
                sale.setId(resultSet.getLong(1));
                sale.setDate(resultSet.getTimestamp(2).toLocalDateTime());
                sale.setUser(user);
                sale.setTicket(ticket);
                //
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }
}
