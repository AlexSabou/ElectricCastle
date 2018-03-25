package utils.impl;

import model.Ticket;
import utils.ModelToTableDataConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TicketToTableDataConverter implements ModelToTableDataConverter<Ticket> {
    @Override
    public List<List<Object>> modelToTableData(List<Ticket> list) {
        Integer size = Ticket.class.getDeclaredFields().length;
        List<List<Object>> objectData = new ArrayList<List<Object>>(size);
        for(int row = 0; row < size; row++) {
            List<Object> objectList = new ArrayList<>();
            objectList.add(list.get(row).getId());
            objectList.add(list.get(row).getName());
            objectList.add(list.get(row).getPrice());
            objectData.add(objectList);
        }
        return objectData;
    }

    @Override
    public String[] modelToTableColumnNames() {
        List<String> columns = Arrays.stream(Ticket.class.getDeclaredFields())
                .map(f -> f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1))
                .collect(Collectors.toList());
        return Arrays.copyOf(columns.toArray(), columns.size(), String[].class);
    }
}
