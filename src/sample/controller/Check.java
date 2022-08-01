package sample.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.model.DB;
import sample.model.Navigation;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Check implements Initializable {
    DB db = new DB();
    Navigation nav = new Navigation();

    @FXML
    AnchorPane root;
    @FXML
    TextField name;
    @FXML
    TextField major;
    @FXML
    ComboBox day;
    @FXML
    ComboBox month;
    @FXML
    ComboBox year;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        day();
        month();
        year();
    }

    public void give() {
        if (name.getText() != null && major.getText() != null && day.getValue() != null && month.getValue() != null && year.getValue() != null) {
            if (name.getText().length() == 7) {
                String e_day = "";
                String e_month = "";
                if ((int) (day.getValue()) < 10) e_day = "0" + day.getValue().toString();
                else e_day = day.getValue().toString();
                if ((int) (month.getValue()) < 10) e_month = "0" + month.getValue().toString();
                else e_month = month.getValue().toString();
                String date = e_day + "/" + e_month + "/" + year.getValue().toString();
                String str = db.get_password(name.getText(), major.getText(), date);
                showAlert(Alert.AlertType.INFORMATION, "Password", "Your password is", str);
                nav.navTo(root, nav.move_to_login);
            } else showAlert(Alert.AlertType.ERROR, "Error", "Error", "Invalid input");
        } else showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please fill all fields");

    }

    public void back() {
        nav.navTo(root, nav.move_to_login);
    }

    public void day() {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 1; i <= 31; i++) arr.add(i);
        day.setItems(FXCollections.observableArrayList(arr));
    }

    public void month() {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 1; i <= 12; i++) arr.add(i);
        month.setItems(FXCollections.observableArrayList(arr));
    }

    public void year() {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 1970; i <= 2000; i++) arr.add(i);
        year.setItems(FXCollections.observableArrayList(arr));
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


