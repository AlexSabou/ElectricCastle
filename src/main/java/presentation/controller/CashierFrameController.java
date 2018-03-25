package presentation.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Sale;
import model.Ticket;
import model.User;
import presentation.HistoryFrame;
import presentation.InfoPopup;
import presentation.LoginFrame;
import repository.TicketRepository;
import repository.impl.TicketRepositoryImpl;
import service.SaleService;
import service.TicketService;
import service.UserService;
import service.impl.SaleServiceImpl;
import service.impl.TicketServiceImpl;
import service.impl.UserServiceImpl;
import utils.ModelToTableDataConverter;
import utils.impl.TicketToTableDataConverter;
import utils.impl.UserSaleToTableDataConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CashierFrameController {
    @FXML
    private TableView<Ticket> tableView;
    @FXML
    private Button btnHistory;

    private UserService userService;
    private SaleService saleService;

    public void initData(UserService userService, TicketService ticketService, SaleService saleService) {
        this.userService = userService;
        this.saleService = saleService;
        //Populate list
        ModelToTableDataConverter<Ticket> modelToTableDataConverter = new TicketToTableDataConverter();
        String[] columns = modelToTableDataConverter.modelToTableColumnNames();
        for(String column: columns) {
            TableColumn<Ticket, String> tableColumn = new TableColumn<>(column);
            tableColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>(column));
            tableView.getColumns().add(tableColumn);
        }
        tableView.setItems(FXCollections.observableArrayList(ticketService.findAll()));
    }

    @FXML
    void onHistoryClicked(MouseEvent event) {
        HistoryFrame.load(userService, saleService, new UserSaleToTableDataConverter());
    }

    @FXML
    void onSellClicked(MouseEvent event) {
        Ticket selectedTicket = tableView.getSelectionModel().getSelectedItem();
        if(selectedTicket != null) {
            //Get current user
            User currentUser = userService.getCurrentUser();
            //Save ticket
            if(currentUser != null) {
                Sale sale = new Sale(currentUser, selectedTicket, LocalDateTime.now());
                try {
                    saleService.validateSale(sale);
                    sale = this.saleService.add(sale);
                    if(sale != null) {
                        InfoPopup.load("Success", "A ticket \"" + selectedTicket.getName()
                                + "\" has been sold for " + selectedTicket.getPrice() + ".");
                    }
                } catch (Exception e) {
                    InfoPopup.load("Error", e.getMessage());
                }
            }
        }
        else {
            InfoPopup.load("Error", "Please select exactly one type of ticket.");
        }
    }

    @FXML
    void onLogoutClicked(MouseEvent event) {
        this.userService.logout();
        LoginFrame.load();
        this.closeWindow();
    }

    private void closeWindow() {
        ((Stage)btnHistory.getScene().getWindow()).close();
    }
}
