package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Sale;
import presentation.controller.CashierFrameController;
import presentation.controller.HistoryFrameController;
import service.SaleService;
import service.UserService;
import utils.ModelToTableDataConverter;

import java.io.IOException;

public class HistoryFrame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/history.fxml"));
        //Scene
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        //Settings
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void load(UserService userService, SaleService saleService, ModelToTableDataConverter<Sale> modelToTableDataConverter) {
        Stage primaryStage = new Stage(StageStyle.DECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(HistoryFrame.class.getResource("/history.fxml"));
        //Scene
        try {
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            //Controller
            ((HistoryFrameController)fxmlLoader.getController()).initData(userService, saleService, modelToTableDataConverter);
            //Settings
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
