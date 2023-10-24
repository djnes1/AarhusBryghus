package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import storage.Storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MedarbejderBarRundvisning extends GridPane {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

    public void setAktuelRundvisning(Rundvisning aktueltRundvisning) {
        this.aktuelRundvisning = aktueltRundvisning;
        txfNavn.setText(this.aktuelRundvisning.getPerson().getNavn());
        txfAdresse.setText(this.aktuelRundvisning.getPerson().getAdresse());
        txfEmail.setText(this.aktuelRundvisning.getPerson().getEmail());
        txfMobil.setText(this.aktuelRundvisning.getPerson().getTlfnr());
        txfCpr.setText(this.aktuelRundvisning.getPerson().getCpr());
        dato.setValue(this.aktuelRundvisning.getDato());
        txfTidspunkt.setText(this.aktuelRundvisning.getTidspunkt().format(dateTimeFormatter));
        spinner.getValueFactory().setValue(this.aktuelRundvisning.getAntalPersoner());

    }

    private Rundvisning aktuelRundvisning;
    private TabPane tabPane;
    private Tab tabTilBageSalg;
    private Controller controller = new Controller(Storage.getUniqueInstance());
    private Spinner<Integer> spinner = new Spinner<>();
    private ComboBox<Betalingsmetode> betalingsmetodeComboBox = new ComboBox<>();
    private Button btnTilbage = new Button("Tilbage");
    private DatePicker dato = new DatePicker();
    private TextField txfTidspunkt = new TextField();
    private TextField txfEmail = new TextField();
    private TextField txfMobil = new TextField();
    private TextField txfNavn = new TextField();
    private TextField txfAdresse = new TextField();
    private TextField txfCpr = new TextField();
    private TextField txfRundvisningsNavn = new TextField();
    private Button btnOK = new Button("OK");
    private Label lblPriserPrem = new Label();
    private ListView<Rundvisning> rundvisninger = new ListView<>();
    private Button btnBetal = new Button("Betal");
    private Button btnClear = new Button("Clear");
    private DatePicker datoAfregning = new DatePicker();
    private TextField lblSamletPris = new TextField();
    private Button btnReceipt = new Button("Print kvittering");

    public MedarbejderBarRundvisning(TabPane tabPane, Tab tab) {
        this.tabPane = tabPane;
        this.tabTilBageSalg = tab;
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);
        betalingsmetodeComboBox.getItems().setAll(Betalingsmetode.values());
        betalingsmetodeComboBox.getItems().remove(Betalingsmetode.KLIPPEKORT);

        betalingsmetodeComboBox.setPrefWidth(150);
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        vBox2.setSpacing(10);
        this.add(vBox1, 3, 0, 2, 8);
        this.add(vBox2, 5, 0, 2, 8);


        vBox.getChildren().setAll(
                new Label("Dato for rundvisning"), dato, new Label("Starttidspunkt"),
                txfTidspunkt, new Label("Antal deltagere:"), spinner);
        this.add(vBox, 0, 0, 2, 8);


        vBox2.getChildren().setAll(new Label("Ikke betalte rundvisninger: "),
                rundvisninger, lblSamletPris, new Label("Afregningsdato:"),
                datoAfregning, new Label("Betalingsmetode"), betalingsmetodeComboBox, btnBetal, btnReceipt);
        vBox1.setSpacing(10);
        vBox1.getChildren().setAll(new Label("Navn:"), txfNavn, new Label("Adresse:"), txfAdresse
                , new Label("CPR:"), txfCpr,
                new Label("Mobil:"), txfMobil, new Label("Email:"), txfEmail, btnOK, btnClear
        );


        lblPriserPrem.setOpacity(0.7);
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
        spinner.setValueFactory(valueFactory);
        btnOK.setOnAction(e -> okAction());
        btnTilbage.setOnAction(e -> tilbageSalg());
        btnBetal.setOnAction(e -> betalRundvisning());
        btnClear.setOnAction(e -> clear());
        ChangeListener<Rundvisning> listener = (ov, oldSalg, newValue) -> this.selectionChanged();
        rundvisninger.getSelectionModel().selectedItemProperty().addListener(listener);

    }


    private void selectionChanged() {
        this.aktuelRundvisning = rundvisninger.getSelectionModel().getSelectedItem();
        if (this.aktuelRundvisning != null) {
            this.setAktuelRundvisning(this.aktuelRundvisning);
            datoAfregning.setValue(this.aktuelRundvisning.getDato().plusDays(1));
            datoAfregning.setDisable(true);
        }
    }

    private void betalRundvisning() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        this.aktuelRundvisning = rundvisninger.getSelectionModel().getSelectedItem();
        Salg salg = this.aktuelRundvisning.createSalg();
        salg.betal(betalingsmetodeComboBox.getValue());
        this.setAktuelRundvisning(this.aktuelRundvisning);
        controller.writeReceiptToFile(this.aktuelRundvisning.getSalg());
        alert.setContentText("Betaling er nu gennemført. " + salg.samletPris() + "\n");
        alert.showAndWait();
        rundvisninger.getItems().remove(this.aktuelRundvisning);
        rundvisninger.getSelectionModel().clearSelection();
    }


    private void okAction() {
        String kundeNavn = txfNavn.getText().trim();
        String kundeAdresse = txfNavn.getText().trim();
        String kundeTlf = txfMobil.getText().trim();
        String kundeMail = txfEmail.getText().trim();
        String kundeCpr = txfCpr.getText().trim();
        LocalDate rundvisningsDato = dato.getValue();
        Person kunde = controller.createKunde(kundeNavn, kundeAdresse, kundeTlf, kundeCpr, kundeMail);
        Rundvisning rundvisning = controller.createRundvisning(kunde, rundvisningsDato, LocalTime.parse(txfTidspunkt.getText().trim()), spinner.getValue());
        rundvisninger.getItems().add(rundvisning);
        clear();
    }

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
        spinner.getValueFactory().setValue(1);
        betalingsmetodeComboBox.getSelectionModel().clearSelection();
        dato.valueProperty().setValue(LocalDate.now());
        datoAfregning.setDisable(false);
    }



}
