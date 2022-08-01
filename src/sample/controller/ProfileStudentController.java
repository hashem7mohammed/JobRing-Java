package sample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.model.DB;
import sample.model.Navigation;
import sample.model.Project;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileStudentController implements Initializable {

    Navigation navigation = new Navigation();
    DB db = new DB();
    @FXML
    public AnchorPane root;
    @FXML
    public Text tf_name, tf_username, tf_birthday, tf_email, tf_phone, tf_major, tf_category, tf_skill;
    @FXML
    public Button show_application, save;
    @FXML
    public TextField tf_desc, tf_name_project;
    @FXML
    public TableView table_project;
    @FXML
    public TableColumn<Project, Object> name, desc;
    private String username = "\"3000000\"";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("project"));
        desc.setCellValueFactory(new PropertyValueFactory<>("por_desc"));

        tf_name.setText(db.getStudentName(username));
        tf_username.setText(db.getUsernameStd(username));
        tf_birthday.setText(db.getDateOfBirth(username));
        tf_email.setText(db.getEmailStd(username));
        tf_major.setText(db.getMajor(username));
        //    tf_category.setText(db.getMajor(username));
        tf_phone.setText(String.valueOf(db.getPhoneStd(username)));

        table_project.setItems(FXCollections.observableArrayList(
                db.showProjectsStd(username)));
    }

    public void insertProject(ActionEvent actionEvent) {
        tf_desc.setVisible(true);
        tf_name_project.setVisible(true);
        save.setVisible(true);
    }

    public void saveData(ActionEvent actionEvent) {
        if (tf_desc.getText() != null && tf_name_project.getText() != null) {
            db.insertProjectStd(db.getIDFromUsername(username), tf_name_project.getText(), tf_desc.getText());
            showAlert("Success", "Added Successfully", "The project added successfully");
        }

        tf_desc.setVisible(false);
        tf_name_project.setVisible(false);
        save.setVisible(false);
    }

    public void showApplications() {
        navigation.navTo(root, navigation.move_to_student_application);
    }

    public void Back() {
        navigation.navTo(root, navigation.move_to_view_jobs);
    }

    public void showAlert(String message, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(message);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait().ifPresent(rs -> {
        });
    }

}