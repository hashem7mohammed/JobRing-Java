package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.model.DB;
import sample.model.Navigation;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewJobsController implements Initializable {
    DB db = new DB();
    Navigation navigation = new Navigation();
    @FXML
    public AnchorPane root;
    @FXML
    public ListView<ArrayList<String>> listview, listview2, listview3, listview4, listview5, listview6, listview7;
    @FXML
    public ComboBox comb_categ;
    @FXML
    public Button bt_profile, bt_notif;
    @FXML
    public TextField ft_search;
    @FXML
    public Label name;
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
        comb_categ.setItems(FXCollections.observableArrayList(db.getTypes()));
        ArrayList<Object> jl_name = db.getJobListingName();
        ArrayList<Object> jl_type = db.getJobListingJobType();
        ArrayList<Object> jl_desc = db.getJobListingDescription();
        ArrayList<Object> jl_countChance = db.getJobListingCountOfChances();
        ArrayList<Object> jl_view = db.getJobListingViews();
        ArrayList<Object> jl_uniqueView = db.getJobListingUniqueViews();
        ArrayList<Object> jl_applies = db.getJobListingApplies();

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

    public void show_categories(ActionEvent actionEvent) {
        System.out.println(comb_categ.getValue().toString());
//
//        switch (comb_categ.getValue().toString()) {
//            case "Application development":
//
//                if (listview.getSelectionModel().getSelectedItem().toString().equals("Application development")) {
//                    listview.setItems(list);
//                    listview2.setItems(list2);
//                    listview3.setItems(list3);
//                    listview4.setItems(list4);
//                    listview5.setItems(list5);
//                    listview6.setItems(list6);
//                    listview7.setItems(list7);
//                }
//                break;
//            case "Design":
//                if (listview.getSelectionModel().getSelectedItem().toString().equals("Design")) {
//                    listview.setItems(list);
//                    listview2.setItems(list2);
//                    listview3.setItems(list3);
//                    listview4.setItems(list4);
//                    listview5.setItems(list5);
//                    listview6.setItems(list6);
//                    listview7.setItems(list7);
//                }
//                break;
//            case "E-Marketing and Sales":
//                break;
//            case "Writing":
//                break;
//            case "Training, education and assistance":
//                break;
//            case "Accounting":
//                break;
//            case "Website development":
//                break;
//            case "Translation, languages":
//                break;
//            case "Other":
//                break;
//        }
    }

    public void showProfile(ActionEvent actionEvent) {
        navigation.navTo(root, navigation.move_to_profile_student);
    }


    public void showNotification(ActionEvent actionEvent) {
        navigation.navTo(root, navigation.move_to_std_notification);
    }

    public void search(ActionEvent actionEvent) {
    }

    public void clicked(MouseEvent mouseEvent) {
        String s = listview.getSelectionModel().getSelectedItems().toString();
        System.out.println(s);
//
    }

}