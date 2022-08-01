package sample.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class Navigation {
    public final String move_to_create_account = "/sample/view/new_account.fxml";
    public final String move_to_login = "/sample/view/login.fxml";
    public final String move_to_view_jobs = "/sample/view/view_jobs.fxml";
    public final String move_to_view_jobs_company = "/sample/view/view_jobs_company.fxml";
    public final String move_to_std_notification = "/sample/view/student_notification.fxml";
    public final String move_to_profile_student = "/sample/view/profile_student.fxml";
    public final String move_to_student_application = "/sample/view/student_application.fxml";
    public final String move_to_profile_company = "/sample/view/profile_company.fxml";
    public final String move_to_add_job = "/sample/view/add_job.fxml";
    public final String move_to_check = "/sample/view/check.fxml";


    public void navTo(Parent rootPane, String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
