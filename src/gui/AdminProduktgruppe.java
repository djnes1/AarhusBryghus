package gui;


import application.controller.Controller;
import application.model.Pant;
import application.model.ProduktGruppe;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import storage.Storage;

import java.util.Comparator;
import java.util.stream.Collectors;

public class AdminProduktgruppe extends GridPane {

    private Controller controller = Controller.getUniqueInstance();
    private TextField txfNavn = new TextField();
    private ListView<ProduktGruppe> lvwGruppe = new ListView<>();
    private AdminStartPane am = new AdminStartPane();
    private Button btnOpret = new Button("Godkend");
    private RadioButton rbPantA = new RadioButton("Pant A");
    private RadioButton rbPantB = new RadioButton("Pant B");
    private RadioButton rbPantC = new RadioButton("Pant C");
    private RadioButton rbFustage = new RadioButton("Fustage");
    private RadioButton rbIngen = new RadioButton("Ingen pant");


    private VBox vBoxPant = new VBox();
    private VBox vBoxType = new VBox();
    private ToggleGroup toggleGroupPant = new ToggleGroup();



    public AdminProduktgruppe() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        btnOpret.setOnAction(e -> opretAction());
        vBoxPant.getChildren().addAll(rbPantA, rbPantB, rbPantC, rbFustage, rbIngen);
        vBoxPant.setSpacing(10);
        rbFustage.setToggleGroup(toggleGroupPant);
        rbIngen.setToggleGroup(toggleGroupPant);
        rbPantA.setToggleGroup(toggleGroupPant);
        rbPantB.setToggleGroup(toggleGroupPant);
        rbPantC.setToggleGroup(toggleGroupPant);




        this.add(new Label("Navn:"), 0, 1,3,1);
        this.add(txfNavn, 2, 1);
        this.add(vBoxPant, 1, 2);
        this.add(vBoxType,2,2);
        this.add(btnOpret, 2, 7,4,1);



    }

    public void opretAction() {
        String navn = txfNavn.getText().trim();
        Pant pant = (Pant) toggleGroupPant.getSelectedToggle().getUserData();

        if (navn.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Der skal angives et navn på produktgruppen");
            alert.showAndWait();
        } else {
            controller.createProduktGruppe(navn, pant);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Produktgruppen " + navn + " er oprettet");
            alert.showAndWait();
        }

        this.updateControls();

    }

    public void updateControls() {
        this.lvwGruppe.getItems().setAll(controller.getProduktGrupper()
                        .stream()
                            .sorted(Comparator.comparing(ProduktGruppe::getNavn))
                                .collect(Collectors.toList()));
        txfNavn.clear();
        toggleGroupPant.selectToggle(rbPantA);


    }


}
