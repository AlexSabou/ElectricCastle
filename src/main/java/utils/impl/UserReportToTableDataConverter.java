package utils.impl;

import model.Ticket;
import model.UserReport;
import utils.ModelToTableDataConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserReportToTableDataConverter implements ModelToTableDataConverter<UserReport> {
    @Override
    public List<List<Object>> modelToTableData(List<UserReport> list) {
        List<List<Object>> objects = new ArrayList<>();
        for (UserReport userReport : list) {
            List<Object> objectList = new ArrayList<>();
            objectList.add(userReport.getUser().getUsername());
            objectList.add(userReport.getTickets());
            //
            objects.add(objectList);
        }
        return objects;
    }

    @Override
    public String[] modelToTableColumnNames() {
        List<String> columns = Arrays.stream(UserReport.class.getDeclaredFields())
                .map(f -> f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1))
                .collect(Collectors.toList());
        return Arrays.copyOf(columns.toArray(), columns.size(), String[].class);
    }
}
