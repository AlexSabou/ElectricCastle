package utils.impl;

import model.DayReport;
import utils.ModelToTableDataConverter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayReportToTableDataConverter implements ModelToTableDataConverter<DayReport> {
    private static final Integer DEFAULT_BEFORE_DAYS = 3;

    @Override
    public List<List<Object>> modelToTableData(List<DayReport> list) {
        List<LocalDateTime> days = new ArrayList<>();
        List<List<Object>> objects = new ArrayList<>();
        int dayId = 0;
        long epochDay = LocalDateTime.now().getLong(ChronoField.EPOCH_DAY);
        //
        for(int i = 0; i < DEFAULT_BEFORE_DAYS; i++)
            days.add(LocalDateTime.now().minusDays(i));
        //
        for (LocalDateTime day: days) {
            List<Object> objectList = new ArrayList<>();
            int daysCount = Long.valueOf(epochDay - day.getLong(ChronoField.EPOCH_DAY)).intValue();
            //
            switch (daysCount) {
                case 0:
                    objectList.add("Today");
                    break;
                case 1:
                    objectList.add("Yesterday");
                    break;
                default:
                    objectList.add("Today-" + daysCount);
                    break;
            }
            //
            if(dayId < list.size()) {
                if(day.getLong(ChronoField.EPOCH_DAY) == list.get(dayId).getDate().getLong(ChronoField.EPOCH_DAY))
                    objectList.add(list.get(dayId++).getTotal());
                else
                    objectList.add(0L);
            }
            else
                objectList.add(0L);
            //
            objects.add(objectList);
        }
        objects.add(Arrays.asList("Total", list.stream().mapToLong(DayReport::getTotal).sum()));
        return objects;
    }

    @Override
    public String[] modelToTableColumnNames() {
        return new String[]{"Day", "Earnings"};
    }
}
