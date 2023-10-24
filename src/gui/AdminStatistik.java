package gui;

import application.controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import storage.Storage;

import java.time.LocalDate;


public class AdminStatistik extends GridPane {

    private Controller controller = Controller.getUniqueInstance();

    private ListView<String> listView = new ListView<>();
    private DatePicker datePickerStart = new DatePicker();

    public AdminStatistik() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.add(listView, 0, 2);

        this.add(new Label("Dagsopgørelse"), 0, 0);
        this.add(datePickerStart, 0, 1, 4, 1);
        datePickerStart.setPromptText("Startdato");
        datePickerStart.valueProperty().addListener((v, o, n) -> dagsSalg());
    }


    public void dagsSalg() {
        LocalDate date = datePickerStart.getValue();
        listView.getItems().setAll(controller.dagensSalg(date));
    }





}
