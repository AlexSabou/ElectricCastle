package presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InfoPopupController {
    @FXML
    private TextArea infoArea;
    @FXML
    private Label labelTitle;
    @FXML
    private Button btnOk;

    @FXML
    void onOkClicked(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY)
            this.closeWindow();
    }

    public void initData(String title, String info) {
        this.labelTitle.setText(title);
        this.infoArea.setText(info);
    }

    private void closeWindow() {
        ((Stage)labelTitle.getScene().getWindow()).close();
    }

    public void setInfo(String info) {
        this.infoArea.setText(info);
    }

    public void setTitle(String title) {
        this.labelTitle.setText(title);
    }
}
