package presentation.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Sale;
import model.User;
import service.SaleService;
import service.UserService;
import service.impl.SaleServiceImpl;
import service.impl.UserServiceImpl;
import utils.ModelToTableDataConverter;
import utils.impl.UserSaleToTableDataConverter;

import java.util.List;
import java.util.stream.Collectors;

public class HistoryFrameController {

    @FXML
    private TableView<List<Object>> tableView;

    @FXML
    private Button btnOk;


    @FXML
    void OnOkClicked(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY)
            this.closeWindow();
    }

    public void initData(UserService userService, SaleService saleService, ModelToTableDataConverter<Sale> modelToTableDataConverter) {
        User currentUser = userService.getCurrentUser();
        String[] columns = modelToTableDataConverter.modelToTableColumnNames();
        List<Sale> sales = saleService.findByUserOrderByDate(currentUser);
        //
        for(int i = 0; i < columns.length; i++) {
            final Integer id = i;
            TableColumn<List<Object>, Object> tableColumn = new TableColumn<>(columns[i]);
            tableColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().get(id).toString()));
            tableColumn.setSortable(true);
            tableColumn.setSortType(TableColumn.SortType.DESCENDING);
            tableView.getColumns().add(tableColumn);
        }
        tableView.setItems(FXCollections.observableArrayList(modelToTableDataConverter.modelToTableData(sales)));
    }

    private void closeWindow() {
        ((Stage) btnOk.getScene().getWindow()).close();
    }

}
