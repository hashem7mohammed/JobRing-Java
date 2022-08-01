package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import sample.model.Navigation;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentApplicationController implements Initializable {

    Navigation navigation = new Navigation();
    @FXML
    public TableView applications_table;
    @FXML
    public AnchorPane root;
    @FXML
    public TableColumn comp_name, job_id, desc, status;
    @FXML
    public Button bt_home_page;
    @FXML
    public Label name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Back(ActionEvent actionEvent) {
        navigation.navTo(root, navigation.move_to_profile_student);
    }

    public void BackToHomePage(ActionEvent actionEvent) {
        navigation.navTo(root, navigation.move_to_view_jobs);

    }
}
