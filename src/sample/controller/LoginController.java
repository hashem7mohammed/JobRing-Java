package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.model.DB;
import sample.model.Navigation;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    Navigation navigation = new Navigation();
    DB db;

    @FXML
    public AnchorPane root;
    @FXML
    public TextField username;
    @FXML
    public TextField password;
    @FXML
    public Button bt_login;
    @FXML
    public Button bt_new_account;
    @FXML
    public RadioButton rb_std, rb_comp;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void create_new_account(ActionEvent actionEvent) {
        navigation.navTo(root, navigation.move_to_create_account);
    }

    public void login() {
        if (username.getText() != null && password.getText() != null) {
            try {
                if (rb_std.isSelected()) {
                    if (username.getText().length() == 7 && password.getText().length() == 6
                            && Integer.parseInt(username.getText()) % 3 == 0) {
                        if (db.isExistStd(username.getText(), password.getText())) {
                            navigation.navTo(root, navigation.move_to_view_jobs);
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Error", "Error", "Username or password Invalid");
                        }
                    } else
                        showAlert(Alert.AlertType.ERROR, "Error", "Error", "Username or password Empty");


                } else if (rb_comp.isSelected()) {

                    if (username.getText().length() == 5 && password.getText().length() == 6
                            && Integer.parseInt(username.getText()) % 5 == 0) {
                        if (db.isExistComp(username.getText(), password.getText()))
                            navigation.navTo(root, navigation.move_to_view_jobs_company);
                        else showAlert(Alert.AlertType.ERROR, "Error", "Error", "Username or password Invalid");
                    } else
                        showAlert(Alert.AlertType.ERROR, "Error", "Error", "Username or password Empity");


                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Error", "Username or password Invalid");
            }

        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please fill all fields");
        }
    }

    public void get_password() {
        navigation.navTo(root, navigation.move_to_check);
    }

    public void showAlert(Alert.AlertType type, String message, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(message);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait().ifPresent(rs -> {

        });
    }
}