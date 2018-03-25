package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import presentation.controller.InfoPopupController;
import presentation.controller.LoginController;

import java.io.IOException;

public class LoginFrame extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        //Scene
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        //Controller
        ((LoginController)fxmlLoader.getController()).initData();
        //Settings
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void load() {
        Stage primaryStage = new Stage(StageStyle.DECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(LoginFrame.class.getResource("/login.fxml"));
        //Scene
        try {
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            //Controller
            ((LoginController)fxmlLoader.getController()).initData();
            //Settings
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
