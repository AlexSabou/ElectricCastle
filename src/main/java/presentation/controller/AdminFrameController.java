package presentation.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import model.UserRole;
import presentation.InfoPopup;
import presentation.LoginFrame;
import presentation.ReportFrame;
import service.RulesService;
import service.UserService;
import service.impl.DayReportServiceImpl;
import service.impl.UserReportServiceImpl;
import service.impl.UserServiceImpl;
import utils.impl.DayReportToTableDataConverter;
import utils.impl.UserReportToTableDataConverter;

import java.io.InvalidObjectException;

public class AdminFrameController {
    @FXML
    private TableView<User> tableView;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfCapacity;
    @FXML
    private Label labelCapacity;

    private UserService userService;
    private RulesService rulesService;

    @FXML
    void onCashierRepClicked(MouseEvent event) {
        ReportFrame.load(new UserReportServiceImpl(), new UserReportToTableDataConverter());
    }

    @FXML
    void onDeleteClicked(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)) {
            int selectedPos = tableView.getSelectionModel().getSelectedIndex();
            User selectedUser = tableView.getItems().get(selectedPos);
            if(selectedUser.getId() != null) {
                if(userService.delete(selectedUser)) {
                    tableView.getItems().remove(selectedPos);
                    InfoPopup.load("Success", "The user has been deleted from the database.");
                }
                else
                    InfoPopup.load("Error", "There has been a problem while deleting the selected user.");
            }
            else {
                tableView.getItems().remove(selectedPos);
                InfoPopup.load("Success", "The user has been deleted from the local storage.");
            }
        }
    }

    @FXML
    void onInsertClicked(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)) {
            int selectedPos = tableView.getSelectionModel().getSelectedIndex();
            User selectedUser = tableView.getItems().get(selectedPos);
            try {
                userService.validateUser(selectedUser);
                selectedUser = userService.add(selectedUser);
                if(selectedUser != null) {
                    tableView.getItems().set(selectedPos, selectedUser);
                    InfoPopup.load("Success", "The user has been inserted successfully.");
                }
            } catch (IllegalArgumentException e) {
                InfoPopup.load("Error", e.getMessage());
            } catch (Exception e1) {
                InfoPopup.load("Error", e1.getMessage());
                tableView.getItems().remove(selectedPos);
            }
        }
    }

    @FXML
    void onLogoutClicked(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)) {
            UserService userService = new UserServiceImpl();
            userService.logout();
            LoginFrame.load();
            this.closeWindow();
        }
    }

    @FXML
    void onNewClicked(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY))
            tableView.getItems().add(new User("ChangeName", "ChangePass", "ChangeEmail", UserRole.ROLE_CASHIER));
    }

    @FXML
    void onReportDaysClicked(MouseEvent event) {
        ReportFrame.load(new DayReportServiceImpl(), new DayReportToTableDataConverter());
    }

    @FXML
    void onUpdateClicked(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)) {
            int selectedPos = tableView.getSelectionModel().getSelectedIndex();
            User selectedUser = tableView.getItems().get(selectedPos);
            try {
                userService.validateUser(selectedUser);
                selectedUser = userService.update(selectedUser);
                if(selectedUser != null) {
                    tableView.getItems().set(selectedPos, selectedUser);
                    InfoPopup.load("Succes", "The user has been updated successfully.");
                }
                else
                    InfoPopup.load("Error", "Please select a valid user.");
            } catch (Exception e) {
                InfoPopup.load("Error", e.getMessage());
            }
        }
    }

    @FXML
    void onCapacityClicked(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY) {
            Integer newCapacity;
            try {
                newCapacity = Integer.parseInt(tfCapacity.getText().trim());
                if(rulesService.updateCapacity(newCapacity)) {
                    InfoPopup.load("Succes", "The maximum capacity/day has been set to " + newCapacity);
                    labelCapacity.setText("Capacity: " + newCapacity);
                    tfCapacity.setText("");
                }
                else
                    InfoPopup.load("Error", "Something went wrong while updating the capacity.Please try again.");
            } catch (NumberFormatException e) {
                InfoPopup.load("Error", "Capacity must be a positive number.");
                tfCapacity.setText("");
            } catch (IllegalArgumentException e) {
                InfoPopup.load("Error", e.getMessage());
                tfCapacity.setText("");
            }
        }
    }

    public void initData(UserService userService, RulesService rulesService) {
        this.userService = userService;
        this.rulesService = rulesService;
        //Initialize capacity value
        labelCapacity.setText("Capacity: " + rulesService.getCapacity());
        //Id column
        TableColumn<User, String> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(data -> {
            if(data.getValue().getId() != null)
                return new ReadOnlyStringWrapper(data.getValue().getId().toString());
            else
                return new ReadOnlyStringWrapper("-");
        });
        tableView.getColumns().add(idCol);
        //Username column
        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getUsername()));
        usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        usernameCol.setOnEditCommit(event -> event.getRowValue().setUsername(event.getNewValue()));
        tableView.getColumns().add(usernameCol);
        //Password column
        TableColumn<User, String> passwordCol = new TableColumn<>("Password");
        passwordCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPassword()));
        passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordCol.setOnEditCommit(event -> event.getRowValue().setPassword(event.getNewValue()));
        tableView.getColumns().add(passwordCol);
        //Email column
        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEmail()));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit(event -> event.getRowValue().setEmail(event.getNewValue()));
        tableView.getColumns().add(emailCol);
        //UserRole column
        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getUserRole().toString()));
        tableView.getColumns().add(roleCol);
        //
        tableView.setEditable(true);
        tableView.setItems(FXCollections.observableArrayList(userService.findAllByRole(UserRole.ROLE_CASHIER)));
    }

    private void closeWindow() {
        ((Stage)btnAdd.getScene().getWindow()).close();
    }
}
