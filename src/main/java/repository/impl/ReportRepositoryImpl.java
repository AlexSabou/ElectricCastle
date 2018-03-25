package repository.impl;

import model.DayReport;
import model.User;
import model.UserReport;
import model.UserRole;
import repository.ReportRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportRepositoryImpl implements ReportRepository {
    @Override
    public List<UserReport> getUsersReport() {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT u.*, COUNT(s.id)" +
                    "FROM sale AS s INNER JOIN user AS u ON(s.user_id = u.id) " +
                    "GROUP BY u.id");
            ResultSet resultSet = statement.executeQuery();
            List<UserReport> userReports = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                UserReport userReport = new UserReport();
                //User
                user.setId(resultSet.getLong(1));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                user.setUserRole(UserRole.valueOf(resultSet.getString(5)));
                //Report
                userReport.setUser(user);
                userReport.setTickets(resultSet.getLong(6));
                //
                userReports.add(userReport);
            }
            return userReports;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DayReport> getDaysReport(Integer beforeDays) {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT STR_TO_DATE(DATE_FORMAT(sale_date, '%d/%m/%Y'), '%d/%m/%Y'), SUM(t.price) " +
                    "FROM sale AS s INNER JOIN ticket AS t ON(s.ticket_id = t.id) " +
                    "WHERE DAY(NOW()) - DAY(sale_date) < ? GROUP BY DAY(sale_date) " +
                    "ORDER BY sale_date DESC");
            statement.setInt(1, beforeDays);
            ResultSet resultSet = statement.executeQuery();
            List<DayReport> dayReports = new ArrayList<>();
            while (resultSet.next()) {
                DayReport dayReport = new DayReport();
                dayReport.setDate(resultSet.getTimestamp(1).toLocalDateTime());
                dayReport.setTotal(resultSet.getLong(2));
                //
                dayReports.add(dayReport);
            }
            return dayReports;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
