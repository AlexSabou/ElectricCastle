package presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Sale;
import model.User;
import model.UserRole;
import presentation.AdminFrame;
import presentation.CashierFrame;
import presentation.InfoPopup;
import service.RulesService;
import service.SaleService;
import service.TicketService;
import service.UserService;
import service.impl.RulesServiceImpl;
import service.impl.SaleServiceImpl;
import service.impl.TicketServiceImpl;
import service.impl.UserServiceImpl;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;

    private UserService userService;
    private TicketService ticketService;
    private SaleService saleService;
    private RulesService rulesService;

    public void initData() {
        userService = new UserServiceImpl();
        ticketService = new TicketServiceImpl();
        saleService = new SaleServiceImpl();
        rulesService = new RulesServiceImpl();
    }

    @FXML
    void onLoginClicked(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY) {
            if(!userService.isLogged()) {
                User user = userService.loginWithCredentials(tfUsername.getText(), pfPassword.getText());
                if(user != null) {
                    switch (user.getUserRole()) {
                        case ROLE_CASHIER:
                            CashierFrame.load(userService, ticketService, saleService);
                            break;
                        case ROLE_ADMIN:
                            AdminFrame.load(userService, rulesService);
                            break;
                    }
                    this.closeWindow();
                }
                else {
                    InfoPopup.load("Error", "Invalid username or password.");
                }
            }
        }
    }

    private void closeWindow() {
        ((Stage)tfUsername.getScene().getWindow()).close();
    }
}