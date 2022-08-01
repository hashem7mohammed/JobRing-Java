package sample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.model.Navigation;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewAccountController implements Initializable {
    Navigation navigation = new Navigation();
    @FXML
    public AnchorPane root;
    @FXML
    public ComboBox comb_type_account, comb_year, comb_month, comb_day, comb_type;
    @FXML
    public TextField tf_name, tf_email, tf_phone, tf_major, tf_username, tf_password, tf_desc, tf_image, tf_skill;
    @FXML
    public Label label_birthday;
    @FXML
    public Button bt_add_image;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveData(ActionEvent actionEvent) {
    }

    public void Back(ActionEvent actionEvent) {
        navigation.navTo(root, navigation.move_to_login);
    }

    public void EnterType(ActionEvent actionEvent) {

    }
}