package gui;

import application.controller.Controller;
import application.model.Salgssituation;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import storage.Storage;

import java.util.Comparator;
import java.util.stream.Collectors;

public class AdminSalgssituation extends GridPane {

    private static Controller controller = Controller.getUniqueInstance();
    private ListView<Salgssituation> lvwPrislister = new ListView<>();
    private TextField txfNavn = new TextField();
    private Button btnOk = new Button("Godkend");


    public AdminSalgssituation() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);


        btnOk.setOnAction(e -> okAction());
        lvwPrislister.getItems().addAll(controller.getSalgssituationer()
                .stream()
                .sorted(Comparator.comparing(Salgssituation::getNavn))
                .toList());

        this.add(new Label("Navn:"), 0, 0, 2, 1);
        this.add(txfNavn, 1, 0, 3, 1);
        this.add(new Label("Salgsituation"), 0, 1);
        this.add(lvwPrislister, 0, 2, 4, 4);
        this.add(btnOk, 4, 10,4,1);
    }

    private void okAction() {
        String navn = txfNavn.getText().trim();
        Alert alert;
        if (navn.isEmpty()) {
             alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Salgssituationen skal have et navn");
            alert.showAndWait();
        } else {
            controller.createSalgssituation(navn);
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Salgssituationen " + navn + " er oprettet");
            alert.showAndWait();
        }
        this.updateControls();

    }

    public void updateControls() {
        lvwPrislister.getItems().setAll(controller.getSalgssituationer()
                    .stream()
                            .sorted(Comparator.comparing(Salgssituation::getNavn))
                                    .collect(Collectors.toList()));
        txfNavn.clear();


    }
}
