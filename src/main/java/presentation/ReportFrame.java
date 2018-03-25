package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import presentation.controller.ReportFrameController;
import service.ReportService;
import utils.ModelToTableDataConverter;

import java.io.IOException;

public class ReportFrame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/report.fxml"));
        //Scene
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        //Settings
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void load(ReportService reportService, ModelToTableDataConverter model) {
        Stage primaryStage = new Stage(StageStyle.DECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(ReportFrame.class.getResource("/report.fxml"));
        //Scene
        try {
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            //Controller
            ((ReportFrameController)fxmlLoader.getController()).initData(reportService, model);
            //Settings
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
