package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.model.DB;
import sample.model.Navigation;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewJobsCompanyController implements Initializable {
    DB db = new DB();
    @FXML
    public ComboBox comb_categ;
    @FXML
    public TextField tf_search;
    @FXML
    public Button bt_profile;
    @FXML
    public ListView<ArrayList<String>> listview, listview2, listview3, listview4, listview5, listview6, listview7;
    @FXML
    public Label name;
    Navigation navigation = new Navigation();
    @FXML
    public AnchorPane root;
    ObservableList list;
    ObservableList list2;
    ObservableList list3;
    ObservableList list4;
    ObservableList list5;
    ObservableList list6;
    ObservableList list7;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();

    }

    private void init() {
        String username = "\"10005\"";
        name.setText(db.getNameFromUsernameCompany(username));
        comb_categ.setItems(FXCollections.observableArrayList(db.getTypes()));
        ArrayList<Object> jl_name = db.getJobListingNameCompany(username);
        ArrayList<Object> jl_type = db.getJobListingJobTypeCompany(username);
        ArrayList<Object> jl_desc = db.getJobListingDescriptionCompany(username);
        ArrayList<Object> jl_countChance = db.getJobListingCountOfChancesCompany(username);
        ArrayList<Object> jl_view = db.getJobListingViewsCompany(username);
        ArrayList<Object> jl_uniqueView = db.getJobListingUniqueViewsCompany(username);
        ArrayList<Object> jl_applies = db.getJobListingAppliesCompany(username);

        list = FXCollections.observableArrayList(jl_type);
        list2 = FXCollections.observableArrayList(jl_name);
        list3 = FXCollections.observableArrayList(jl_desc);
        list4 = FXCollections.observableArrayList(jl_countChance);
        list5 = FXCollections.observableArrayList(jl_view);
        list6 = FXCollections.observableArrayList(jl_uniqueView);
        list7 = FXCollections.observableArrayList(jl_applies);

        listview.setItems(list);
        listview2.setItems(list2);
        listview3.setItems(list3);
        listview4.setItems(list4);
        listview5.setItems(list5);
        listview6.setItems(list6);
        listview7.setItems(list7);

    }


    public void addJob(ActionEvent actionEvent) {
        navigation.navTo(root, navigation.move_to_add_job);
    }

    public void search(ActionEvent actionEvent) {
    }

    public void showProfile(ActionEvent actionEvent) {
        navigation.navTo(root, navigation.move_to_profile_company);
    }

    public void showCateg(ActionEvent actionEvent) {
    }

    public void clicked(MouseEvent mouseEvent) {
    }
}
