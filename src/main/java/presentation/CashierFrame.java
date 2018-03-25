package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import presentation.controller.CashierFrameController;
import presentation.controller.InfoPopupController;
import service.SaleService;
import service.TicketService;
import service.UserService;

import java.io.IOException;

public class CashierFrame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cashier.fxml"));
        //Scene
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        //Settings
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void load(UserService userService, TicketService ticketService, SaleService saleService) {
        Stage primaryStage = new Stage(StageStyle.DECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(CashierFrame.class.getResource("/cashier.fxml"));
        //Scene
        try {
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            //Controller
            ((CashierFrameController)fxmlLoader.getController()).initData(userService, ticketService, saleService);
            //Settings
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
