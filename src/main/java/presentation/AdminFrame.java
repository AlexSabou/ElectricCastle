package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import presentation.controller.AdminFrameController;
import presentation.controller.CashierFrameController;
import service.RulesService;
import service.SaleService;
import service.UserService;

import java.io.IOException;

public class AdminFrame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin.fxml"));
        //Scene
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        //Settings
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void load(UserService userService, RulesService rulesService) {
        Stage primaryStage = new Stage(StageStyle.DECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(AdminFrame.class.getResource("/admin.fxml"));
        //Scene
        try {
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            //Controller
            ((AdminFrameController)fxmlLoader.getController()).initData(userService, rulesService);
            //Settings
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
