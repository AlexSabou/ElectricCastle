package repository.impl;

import repository.RulesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RulesRepositoryImpl implements RulesRepository {
    @Override
    public Integer getCapacity() {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT max_capacity FROM rules LIMIT 1");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean updateCapacity(Integer capacity) {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE rules SET max_capacity = ?");
            statement.setInt(1, capacity);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
