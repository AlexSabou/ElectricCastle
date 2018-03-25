package utils.impl;

import model.Sale;
import utils.ModelToTableDataConverter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserSaleToTableDataConverter implements ModelToTableDataConverter<Sale> {
    @Override
    public List<List<Object>> modelToTableData(List<Sale> list) {
        Integer size = list.size();
        List<List<Object>> objectData = new ArrayList<List<Object>>(size);
        for(int row = 0; row < size; row++) {
            List<Object> objectList = new ArrayList<>();
            objectData.add(objectList);
            objectList.add(list.get(row).getId());
            objectList.add(list.get(row).getTicket().getName());
            objectList.add(list.get(row).getTicket().getPrice());
            objectList.add(list.get(row).getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        }
        return objectData;
    }

    @Override
    public String[] modelToTableColumnNames() {
        return new String[]{"Id", "Name", "Price", "Date"};
    }
}
