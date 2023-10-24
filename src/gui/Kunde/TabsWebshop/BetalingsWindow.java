package gui.Kunde.TabsWebshop;

import application.controller.Controller;
import application.model.Betalingsmetode;
import application.model.Kunde;
import application.model.Person;
import application.model.Salg;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class BetalingsWindow extends Stage {


    public BetalingsWindow(String title) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setMinHeight(100);
        this.setMinWidth(500);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private Controller controller = Controller.getUniqueInstance();
    private TextField txfNavn = new TextField();
    private TextField txfEmail = new TextField();
    private TextField txfAdresse = new TextField();
    private TextField txfTlf = new TextField();
    private TextField txfCpr = new TextField();
    private TextField txfTotal = new TextField();
    private Button btnOpdater = new Button("Opdater Total");
    private Button btnKøb = new Button("Betal");
    private Button btnReturn = new Button("Tilbage til menu");
    private Salg salg = controller.getAktueltSalg();
    private ComboBox<Betalingsmetode> betalingsmetodeComboBox = new ComboBox<>();

    private void initContent(GridPane pane) {
        makeEditable();
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        btnKøb.setPrefWidth(155);
        btnReturn.setPrefWidth(155);
        betalingsmetodeComboBox.setPrefWidth(155);
        btnKøb.setOnAction(event -> this.gennemførSalg());
        btnReturn.setOnAction(event -> this.cancelAction());
        btnOpdater.setOnAction(event -> this.opdater());
        betalingsmetodeComboBox.getItems().setAll(Betalingsmetode.values());
        betalingsmetodeComboBox.getItems().remove(Betalingsmetode.KLIPPEKORT);
        betalingsmetodeComboBox.getItems().remove(Betalingsmetode.KONTANT);

        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().setAll(new Label("Navn:"), txfNavn,
                new Label("Adresse:"), txfAdresse,
                new Label("Telefon:"), txfTlf,
                new Label("Email:"), txfEmail,
                new Label("Cpr:"), txfCpr,new Label("Samlet pris: "), txfTotal, betalingsmetodeComboBox, btnKøb, btnReturn);
        pane.add(box, 6, 0, 1, 10);


    }

    private void printKvittering() {
        controller.writeReceiptToFile(this.salg);
    }


    public void makeEditable() {
        txfTotal.setEditable(false);
    }

    private void cancelAction() {
        BetalingsWindow.this.hide();
    }

    public void opdater() {
        this.salg = controller.getAktueltSalg();
        if (salg != null) {
            txfTotal.setText("" + salg.samletPris() + " KR");
        }
        else{
            txfTotal.clear();
        }
    }

    private void gennemførSalg() {
        if (betalingsmetodeComboBox.getSelectionModel().getSelectedItem() == null) {
            alertManglerBetalingsmetode();
        } else {
            this.salg = controller.getAktueltSalg();
            String navn = txfNavn.getText().trim();
            String email = txfEmail.getText().trim();
            String adresse = txfAdresse.getText().trim();
            String tlf = txfTlf.getText().trim();
            String cpr = txfCpr.getText().trim();

            Person kunde = controller.createKunde(navn, adresse, tlf, cpr, email);
            this.salg.setKunde((Kunde) kunde);
            this.salg.setDato(LocalDate.now());
            this.salg.setBetalingsmetode(betalingsmetodeComboBox.getValue());
            controller.addSalg(this.salg);
            controller.setAktueltSalg(null);
            alert();
        }
    }

    public void alert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Tak for dit køb! Din kvittering forefindes i src.");
        alert.setContentText("Kom snart igen");
        alert.show();
        this.hide();
    }

    public void alertManglerBetalingsmetode() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mangler info");
        alert.setHeaderText("Du mangler at vælge en betalingsmetode");
        alert.setContentText("Luk vinduet og prøv igen");
        alert.show();
    }

}
