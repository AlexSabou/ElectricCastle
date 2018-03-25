package utils;

import java.util.List;

public interface ModelToTableDataConverter<E> {
    public List<List<Object>> modelToTableData(List<E> list);

    String[] modelToTableColumnNames();
}
