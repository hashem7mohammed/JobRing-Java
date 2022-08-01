package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.model.Navigation;

import java.net.URL;
import java.util.ResourceBundle;

public class JobDetailsController implements Initializable {
    Navigation navigation = new Navigation();
    @FXML
    public AnchorPane root;
    @FXML
    public ListView listview;
    @FXML
    public Button bt_profile_company;
    @FXML
    public Label name;
    @FXML
    public Text text_applies, text_views, text_unique_views;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Back(ActionEvent actionEvent) {
        navigation.navTo(root, navigation.move_to_view_jobs_company);
    }

    public void showProfile(ActionEvent actionEvent) {
        navigation.navTo(root, navigation.move_to_profile_company);

    }
}
