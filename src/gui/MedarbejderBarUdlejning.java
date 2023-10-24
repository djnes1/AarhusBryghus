package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import storage.Storage;

import java.util.ArrayList;

public class MedarbejderBarUdlejning extends GridPane {


    public void setAktuelUdlejning(Udlejning aktueltUdlejning) {
        this.aktuelUdlejning = aktueltUdlejning;
        if (this.aktuelUdlejning != null) {
            txfNavn.setText(this.aktuelUdlejning.getPerson().getNavn());
            txfAdresse.setText(this.aktuelUdlejning.getPerson().getAdresse());
            txfEmail.setText(this.aktuelUdlejning.getPerson().getEmail());
            txfMobil.setText(this.aktuelUdlejning.getPerson().getTlfnr());
            txfCpr.setText(this.aktuelUdlejning.getPerson().getCpr());

        }
    }

    private Salg salg;
    private Udlejning aktuelUdlejning;
    private TabPane tabPane;
    private Tab tabTilBageSalg;
    private Controller controller = Controller.getUniqueInstance();
    private Spinner<Integer> spinner = new Spinner<>();
    private Button btnaddTilKurv = new Button("Tilføj til kurv");
    private Button btnBetal = new Button();
    private ComboBox<ProduktGruppe> produktGruppeComboBox = new ComboBox<>();
    private ListView<PerProduktPris> produktListView = new ListView<>();
    private ComboBox<Salgssituation> salgssituationComboBox = new ComboBox<>();
    private ComboBox<Udlejning> udlejningComboBox = new ComboBox<>();
    private ListView<PerProduktPris> kurvList = new ListView<>();
    private ListView<Udlejning> udlejningListView = new ListView<>();
    private Button btnFjern = new Button();
    private Button btnTilbage = new Button("Tilbage");
    private Button btnAfhent = new Button("Afhent");
    private Button btnAflever = new Button("Aflever");
    private Button opretUdlejning = new Button("Opret udlejning");
    private TextField txfKunde = new TextField();
    private Udlejning udlejning;
    private ListView<Udlejning> udlejningControls = new ListView<>();
    private Button btnIkkeAfleveret = new Button("Ikke afleveret");
    private DatePicker datoAfhent = new DatePicker();
    private ListView<Udlejning> ikkeAfleveredeList = new ListView<>();
    private ComboBox<Betalingsmetode> betalingsmetodeComboBox = new ComboBox<>();
    private DatePicker datoAflever = new DatePicker();
    private ComboBox<Betalingsmetode> betalingsmetodeComboBoxAflever = new ComboBox<>();
    private TextField txfTidspunkt = new TextField();
    private TextField txfEmail = new TextField();
    private TextField txfMobil = new TextField();
    private TextField txfNavn = new TextField();
    private TextField txfAdresse = new TextField();
    private TextField txfCpr = new TextField();
    private TextField txfRundvisningsNavn = new TextField();


    public MedarbejderBarUdlejning(TabPane tabPane, Tab tab) {
        this.tabPane = tabPane;
        this.tabTilBageSalg = tab;
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);


        VBox vBoxProdukter = new VBox();
        VBox vBoxKurv = new VBox();
        VBox vBoxUdlejninger = new VBox();
        vBoxUdlejninger.setSpacing(10);
        vBoxKurv.setSpacing(10);
        vBoxProdukter.setSpacing(10);
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        VBox vBoxKunde = new VBox();
        vBoxKunde.setSpacing(10);


        btnBetal.setText("Betal");
        txfKunde.setPromptText("Navn");
        btnFjern.setText("Fjern fra kurv");
        udlejningComboBox.setPrefWidth(50);
        btnaddTilKurv.setPrefWidth(100);
        btnFjern.setPrefWidth(120);
        btnBetal.setPrefWidth(120);
        spinner.setPrefWidth(120);
        produktListView.setMinHeight(200);
        produktListView.setPrefWidth(200);
        kurvList.setPrefWidth(200);
        udlejningListView.setPrefWidth(200);
        udlejningListView.setMinHeight(150);
        kurvList.setPrefHeight(400);
        betalingsmetodeComboBox.setMinWidth(150);
        btnBetal.setPrefWidth(150);
        btnAfhent.setPrefWidth(150);
        btnAflever.setPrefWidth(150);
        betalingsmetodeComboBoxAflever.setMinWidth(150);
        betalingsmetodeComboBoxAflever.getSelectionModel().selectFirst();
        betalingsmetodeComboBox.getSelectionModel().selectFirst();


        // ON ACTIOn
        btnFjern.setOnAction(e -> fjernProdukt());
        btnAfhent.setOnAction(e -> afhent());
        btnTilbage.setOnAction(e -> tilbageSalg());
        opretUdlejning.setOnAction(e -> udlejningOnAction());
        btnAflever.setOnAction(e -> afleverBetal());
        btnaddTilKurv.setOnAction(event -> this.addToKurv());


        betalingsmetodeComboBox.getItems().setAll(Betalingsmetode.values());
        betalingsmetodeComboBox.getItems().remove(Betalingsmetode.KLIPPEKORT);
        betalingsmetodeComboBoxAflever.getItems().setAll(Betalingsmetode.values());
        betalingsmetodeComboBoxAflever.getItems().remove(Betalingsmetode.KLIPPEKORT);
        betalingsmetodeComboBox.getSelectionModel().selectFirst();
        betalingsmetodeComboBoxAflever.getSelectionModel().selectFirst();

        this.add(vBox, 9, 0, 2, 10);
        this.add(vBoxProdukter, 0, 0, 2, 10);
        this.add(vBoxKurv, 3, 0, 2, 10);
        this.add(vBoxUdlejninger, 7, 0, 2, 10);
        this.add(vBoxKunde, 5, 0, 2, 10);
        this.add(btnTilbage, 10, 10);

        for (Salgssituation s : controller.getSalgssituationer()) {
            if (s.getNavn().equals("Butik")) {
                salgssituationComboBox.getItems().setAll(s);
                salgssituationComboBox.getSelectionModel().select(s);
            }
        }
        for (ProduktGruppe p : controller.getProduktGrupper()) {
            if (p.getPant().equals(Pant.FUSTAGE)) {
                produktGruppeComboBox.getItems().setAll(p);
                produktGruppeComboBox.getSelectionModel().select(p);
                produktListView.getItems().setAll(controller.getProdukterFraProduktGruppe(p));

            }
        }

        // ---------- VBOX -----------
        vBoxProdukter.getChildren().setAll(new Label("Salgssituation"),
                salgssituationComboBox, new Label("Produkgruppe"), produktGruppeComboBox,
                produktListView, spinner, btnaddTilKurv);
        vBoxKurv.getChildren().setAll(new Label("Bestillingsprodukter"),
                kurvList, btnFjern);
        vBoxUdlejninger.getChildren().setAll(new Label("Ikke afhentet udlejninger"),
                udlejningListView, new Label("Ikke afleverede udlejninger"), ikkeAfleveredeList);
        ikkeAfleveredeList.setMaxHeight(130);
        vBox.getChildren().setAll(new Label("Dato for afhentning:"), datoAfhent, betalingsmetodeComboBox, btnAfhent, new Label("\nAflevering"),
                datoAflever, betalingsmetodeComboBoxAflever, btnAflever);
        vBoxKunde.getChildren().setAll(new Label("Navn:"), txfNavn, new Label("Adresse:"), txfAdresse
                , new Label("CPR:"), txfCpr,
                new Label("Mobil:"), txfMobil, new Label("Email:"), txfEmail, opretUdlejning);

        // ------ SPINNER
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
        spinner.setValueFactory(valueFactory);
        btnIkkeAfleveret.setOnAction(e -> ikkeAfleverede());


        ChangeListener<Udlejning> listener = (ov, oldSalg, newValue) -> this.selectionChanged();
        udlejningListView.getSelectionModel().selectedItemProperty().addListener(listener);

    }

    private void selectionChanged() {
        this.aktuelUdlejning = udlejningListView.getSelectionModel().getSelectedItem();
        this.setAktuelUdlejning(this.aktuelUdlejning);
    }

    // ------- IKKE AFLEVEREDE UDLEJNINGER
    private void ikkeAfleverede() {
        udlejningControls.getItems().setAll(controller.ikkeAfleveredeUdlejninger());
    }

    // --------- AFHENTNINGSSALG
    private void afhent() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (udlejningListView.getSelectionModel().getSelectedItem() != null) {
            this.salg = udlejningListView.getSelectionModel().getSelectedItem().createAfhentningsSalg();
            this.udlejningListView.getSelectionModel().getSelectedItem().afhent(betalingsmetodeComboBox.getValue(), datoAfhent.getValue());
            this.salg.betal(betalingsmetodeComboBox.getValue());

            if (this.udlejningListView.getSelectionModel().getSelectedItem() == null) {
                alert.setContentText("Vælg venligst udlejningen du ønsker at afhente");
                alert.showAndWait();
            }
            alert.setContentText("Du har afhentet din udlejning og betalt " + this.udlejningListView.getSelectionModel().getSelectedItem().getAfhentningsSalg().samletPris() + " i pant med " + betalingsmetodeComboBox.getValue());
            alert.showAndWait();
            this.ikkeAfleveredeList.getItems().setAll(controller.ikkeAfleveredeUdlejninger());
            this.udlejningListView.getItems().remove(this.udlejningListView.getSelectionModel().getSelectedItem());

        }
    }


    // ------- AFLEVER BETAL
    private void afleverBetal() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        this.aktuelUdlejning = this.ikkeAfleveredeList.getSelectionModel().getSelectedItem();
        if (!this.aktuelUdlejning.isAfhentet()) {
            alert.setContentText("Udregning kan først foregå efter du har afhentet din bestilling");

        } else {
            this.salg = this.aktuelUdlejning.createAfleveringsSalg();
            this.aktuelUdlejning.aflever(betalingsmetodeComboBox.getValue());
            alert.setContentText("De har afleveret fustagerne fra Deres udlejning.\nBetalingsmetode: "
                    + betalingsmetodeComboBoxAflever.getValue() + "\nSamlet pris: " + "" + this.aktuelUdlejning.getBetalingVedAflevering());
            controller.writeReceiptToFile(this.aktuelUdlejning.getAfleveringsSalg());

        }
        this.clear();
        udlejningListView.getSelectionModel().clearSelection();

        alert.showAndWait();
        this.ikkeAfleverede();

    }


    // ----- FJERN PRODUKT
    private void fjernProdukt() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        PerProduktPris salgslinje = kurvList.getSelectionModel().getSelectedItem();
        if (salgslinje == null) {
            alert.setContentText("Vælg venligst produkt");
            alert.showAndWait();
        } else {
            alert.setContentText(kurvList.getSelectionModel().getSelectedItem() + " er fjernet fra kurv");
            kurvList.getItems().remove(kurvList.getSelectionModel().getSelectedItem());
            alert.showAndWait();
        }

    }


    // --------- UDLEJ --------
    private void udlejningOnAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String kundeNavn = txfNavn.getText().trim();
        String kundeAdresse = txfNavn.getText().trim();
        String kundeTlf = txfMobil.getText().trim();
        String kundeMail = txfEmail.getText().trim();
        String kundeCpr = txfCpr.getText().trim();
        Person kunde = controller.createKunde(kundeNavn, kundeAdresse, kundeTlf, kundeCpr, kundeMail);
        this.udlejning = controller.createUdlejning(kunde);
        udlejningListView.getItems().add(udlejning);
        alert.setContentText("Udlejning er oprettet");
        alert.showAndWait();


        clear();
    }


    // -------- ADD TIL KURV
    private void addToKurv() {
        PerProduktPris ppp = produktListView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (ppp == null) {
            alert.setContentText("Vælg venligst produkt");
            alert.showAndWait();
        } else {
            kurvList.getItems().add(ppp);

        }


    }


    // ------ TILBAGEKNAP
    private void tilbageSalg() {
        tabPane.getSelectionModel().select(tabTilBageSalg);

    }

    public void clear() {
        txfRundvisningsNavn.clear();
        txfNavn.clear();
        txfTidspunkt.clear();
        txfAdresse.clear();
        txfCpr.clear();
        txfMobil.clear();
        txfEmail.clear();
        kurvList.getItems().clear();
    }


    public void update() {

    }
}
