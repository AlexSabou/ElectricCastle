package presentation.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DayReport;
import model.User;
import model.UserReport;
import service.ReportService;
import service.SaleService;
import service.impl.SaleServiceImpl;
import utils.ModelToTableDataConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ReportFrameController {
    @FXML
    private TableView<List<Object>> tableView;
    @FXML
    private Button btnOk;

    @FXML
    void OnOkClicked(MouseEvent event) {
        this.closeWindow();
    }

    public void initData(ReportService reportService, ModelToTableDataConverter model) {
        tableView.setItems(FXCollections.observableArrayList(model.modelToTableData(reportService.getReport())));
        setUpColumns(model.modelToTableColumnNames());
    }

    private void setUpColumns(String[] columns) {
        for(int i = 0; i < columns.length; i++) {
            final Integer id = i;
            TableColumn<List<Object>, Object> tableColumn = new TableColumn<>(columns[i]);
            tableColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().get(id)));
            tableColumn.setSortable(true);
            tableColumn.setSortType(TableColumn.SortType.DESCENDING);
            tableView.getColumns().add(tableColumn);
        }
    }

    private void closeWindow() {
        ((Stage) btnOk.getScene().getWindow()).close();
    }
}
