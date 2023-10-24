package gui;

import application.controller.Controller;
import application.model.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import storage.Storage;

import java.util.Comparator;
import java.util.stream.Collectors;

import static application.model.Pant.FUSTAGE;

public class AdminProdukt extends GridPane {




    private  Controller controller = Controller.getUniqueInstance();
    private ChoiceBox<Salgssituation> salgssituationChoiceBox = new ChoiceBox<>();
    private ListView<ProduktGruppe> lvwProduktGrupper = new ListView<>();
    private TextField txfNavn = new TextField();
    private TextField txfPrisKr = new TextField();
    private TextField txfKlip = new TextField();
    private Button btnOk = new Button("Godkend");
    private TextField txfLiter = new TextField();
    private Produkt produkt;
    private TabPane tabPane;
    private Tab tabOpretProduktGruppe;
    private Button btnOpretProduktGruppe = new Button("Opret produktgruppe");
    private Label lblPrisliste = new Label("Salgssituation");
    private ProduktGruppe produktGruppe;
    private Salgssituation salgssituation;

    public AdminProdukt(TabPane tabPane, Tab tab) {
        this.tabPane = tabPane;
        this.tabOpretProduktGruppe = tab;
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);


        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(lblPrisliste, salgssituationChoiceBox, txfPrisKr, txfKlip);
        salgssituationChoiceBox.getItems().setAll(controller.getSalgssituationer());
        salgssituationChoiceBox.getSelectionModel().selectFirst();
        txfPrisKr.setPromptText("0 kr");
        txfKlip.setPromptText("Klip");
        btnOk.setOnAction(e -> okAction());
        lvwProduktGrupper.getItems().addAll(controller.getProduktGrupper()
                .stream()
                .sorted(Comparator.comparing(ProduktGruppe::getNavn))
                .toList());
        lvwProduktGrupper.setPrefHeight(200);
        txfLiter.setPromptText("Liter");
        VBox vBox1 = new VBox();
        vBox1.getChildren().setAll(txfLiter, btnOk, btnOpretProduktGruppe);
        btnOpretProduktGruppe.setPrefWidth(150);
        btnOk.setPrefWidth(150);
        vBox1.setPrefWidth(150);
        vBox1.setSpacing(2);
        this.add(vBox1, 3, 10, 3, 4);
        this.add(new Label("Navn:"), 0, 0, 2, 1);
        this.add(txfNavn, 1, 0, 3, 1);
        this.add(new Label("Produktgruppe"), 0, 1);
        this.add(lvwProduktGrupper, 0, 2, 4, 4);
        this.add(vBox, 0, 8, 3, 3);
        btnOpretProduktGruppe.setOnAction(e -> opretProduktGruppe());



    }




    private void okAction() {
        this.produktGruppe = lvwProduktGrupper.getSelectionModel().getSelectedItem();
        if (this.produktGruppe.getNavn().equals("Fustage")) {
            this.produktFustage();
        }
        if (!txfPrisKr.getText().isEmpty()) {
            this.produktMedPris();

        }
        if (!this.produktGruppe.getNavn().equals("Fustage")) {
            this.produktUdenPris();
        }
        this.

                updateControls();


    }

    public void produktUdenPris() {
        if (!txfNavn.getText().isEmpty() && lvwProduktGrupper.getSelectionModel().getSelectedItem() != null
                && txfPrisKr.getText().isEmpty()) {
            this.produktGruppe = lvwProduktGrupper.getSelectionModel().getSelectedItem();
            controller.createProdukt(txfNavn.getText().trim(), this.produktGruppe);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setContentText(txfNavn.getText().trim() + " er tilføjet til produktgruppen " + this.produktGruppe);
            alert1.showAndWait();
        }

    }


    public void produktMedPris() {
        this.produktGruppe = lvwProduktGrupper.getSelectionModel().getSelectedItem();
        this.salgssituation = salgssituationChoiceBox.getSelectionModel().getSelectedItem();
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        String navn = txfNavn.getText().trim();
        int klip;
        String s = navn + " er oprettet til gruppen " + produktGruppe;
        if (!txfPrisKr.getText().isEmpty()) {
            try {
                this.produkt = controller.createProdukt(navn, this.produktGruppe);
                PerProduktPris perProduktPris = this.salgssituation.createPerProduktPris(Double.parseDouble(txfPrisKr.getText().trim()), this.produkt);
                info.setContentText(s);
                info.showAndWait();

                if (!txfKlip.getText().trim().isEmpty()) {
                    klip = Integer.parseInt(txfKlip.getText().trim());
                    perProduktPris.setKlip(klip);

                }

            } catch (NumberFormatException e) {
                info.setContentText("Pris er af numerisk format");
                info.showAndWait();

            }


        }
        this.updateControls();


    }

    public void produktFustage() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        this.produktGruppe = lvwProduktGrupper.getSelectionModel().getSelectedItem();
        String navn = txfNavn.getText().trim();
        String s = navn + " er oprettet i " + produktGruppe;
        if (this.produktGruppe.getPant().equals(FUSTAGE)) {
            if (!txfLiter.getText().isEmpty()) {
                try {
                    this.produkt = controller.createProdukt(txfNavn.getText().trim(),
                            this.produktGruppe, Integer.parseInt(txfLiter.getText().trim()));
                    info.setContentText(s);
                    info.showAndWait();
                    if (!txfPrisKr.getText().isEmpty() && this.salgssituation != null) {
                        this.salgssituation.createPerProduktPris(Double.parseDouble(txfPrisKr.getText().trim()), produkt);

                        if (!txfKlip.getText().isEmpty()) {
                            int klip = Integer.parseInt(txfKlip.getText().trim());
                            this.salgssituation.createPerProduktPris(Double.parseDouble(txfPrisKr.getText().trim()), klip, this.produkt);
                        }


                    }

                } catch (NumberFormatException e) {
                    info.setContentText("Pris er af numerisk format");
                    info.showAndWait();


                }


            } else {
                info.setContentText("Angiv venligst antal liter");
                info.showAndWait();

            }


        }

        this.updateControls();
    }


    public void updateControls() {
        lvwProduktGrupper.getItems().setAll(controller.getProduktGrupper()
                .stream()
                .sorted(Comparator.comparing(ProduktGruppe::getNavn))
                .collect(Collectors.toList()));
        salgssituationChoiceBox.getItems().setAll(controller.getSalgssituationer());
        salgssituationChoiceBox.getSelectionModel().selectFirst();
        txfNavn.clear();
        txfKlip.clear();
        txfPrisKr.clear();
        txfLiter.clear();
    }

    public void opretProduktGruppe() {
        tabPane.getSelectionModel().select(tabOpretProduktGruppe);
    }

}
