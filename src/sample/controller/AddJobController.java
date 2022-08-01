package sample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.model.DB;
import sample.model.Navigation;

import java.net.URL;
import java.util.ResourceBundle;

public class AddJobController implements Initializable {
    DB db = new DB();

    Navigation navigation = new Navigation();
    @FXML
    public AnchorPane root;
    @FXML
    public TextField tf_desc, tf_count, tf_name;
    @FXML
    public ComboBox comb_category;
    @FXML
    public Button bt_show_profile;
    @FXML
    public Label name_company;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comb_category.setItems(FXCollections.observableArrayList(db.getTypes()));
    }

    public void Add() {
        //    int company_id = Integer.parseInt(LoginController.username_text )/5;
        if (tf_desc.getText() == null || tf_count.getText().equals("")
                || tf_name.getText().equals("")
                || comb_category.getValue().equals("")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error", "Please Enter all fields");
        } else {
            if (db.insertJob("\"2001\"",
                    String.valueOf(db.getIDsJobs() + 1),
                    tf_count.getText(),
                    tf_desc.getText(),
                    comb_category.getValue().toString(),
                    tf_name.getText()
            )) {

                showAlert(Alert.AlertType.INFORMATION, "Success", "Success", "The student Added Successfully");
                tf_desc.clear();
                tf_count.clear();
                tf_name.clear();
                comb_category.setValue("");
            }
        }
    }

    public void showAlert(Alert.AlertType type, String message, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(message);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait().ifPresent(rs -> {

        });
    }

    public void Back() {
        navigation.navTo(root, navigation.move_to_view_jobs_company);
    }

    public void showProfile() {
        navigation.navTo(root, navigation.move_to_profile_company);
    }
}
