package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.model.DB;
import sample.model.Navigation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfileCompanyController implements Initializable {
    DB db = new DB();
    Navigation navigation = new Navigation();
    @FXML
    public AnchorPane root;
    @FXML
    public Text tf_name, tf_username, tf_phone, tf_email, tf_category;

    @FXML
    public ImageView img_view;
    @FXML
    public Label tf_desc;
    private String username = "\"10005\"";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tf_name.setText(db.getCompanyName(username));
        tf_username.setText(db.getCompanyUsername(username));
        tf_email.setText(db.getEmailCompany(username));
        tf_phone.setText(db.getPhoneCompany(username));
        tf_desc.setText(db.getDescriptionCompany(username));
        try {
            Image img = db.getPhoto(username);
            if (img != null) {
                img_view.setImage(img);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Back() {
        navigation.navTo(root, navigation.move_to_view_jobs_company);

    }

}
