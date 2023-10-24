package gui;

import application.controller.Controller;
import application.model.Betalingsmetode;
import application.model.Kunde;
import application.model.Person;
import application.model.Salg;
import gui.Kunde.TabsWebshop.BetalingsWindow;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class MedarbejderBarBetaling extends Stage {

    public MedarbejderBarBetaling(String title) {
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
    private TextField txfTotal = new TextField();
    private TextField txfTotalKlip = new TextField();
    private Button btnOpdater = new Button("Opdater Total");
    private Button btnKøb = new Button("Betal");
    private Button btnReturn = new Button("Tilbage til menu");
    private Salg salg = controller.getAktueltSalg();
    private ComboBox<Betalingsmetode> betalingsmetodeComboBox = new ComboBox<>();


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        btnKøb.setPrefWidth(130);
        btnReturn.setPrefWidth(130);
        txfTotalKlip.setPrefWidth(130);
        txfTotal.setPrefWidth(130);
        btnKøb.setOnAction(event -> this.gennemførSalg());
        btnReturn.setOnAction(event -> this.cancelAction());
        btnOpdater.setOnAction(event -> this.opdater());
        betalingsmetodeComboBox.getItems().setAll(Betalingsmetode.values());
        betalingsmetodeComboBox.setPromptText("Betalingsmetode");
        txfTotal.setEditable(false);
        txfTotalKlip.setEditable(false);

        VBox box = new VBox();
        VBox hBox = new VBox();
        hBox.setSpacing(10);
        box.setSpacing(10);
        hBox.getChildren().setAll(new Label("Klip: "), txfTotalKlip, new Label("Total: "), txfTotal, betalingsmetodeComboBox, btnKøb, btnReturn);
        pane.add(hBox, 0, 1, 4, 5);
        pane.add(box, 5, 1, 7, 5);


    }

    private void printKvittering() {
        controller.writeReceiptToFile(this.salg);
    }


    private void cancelAction() {
        this.hide();
    }

    public void opdater() {
        this.salg = controller.getAktueltSalg();
        if (salg != null) {
            txfTotal.setText("" + salg.samletPris() + " KR");
            txfTotalKlip.setText("" + salg.samletPrisKlip() + " klip");
        }
    }

    private void gennemførSalg() {
        this.salg = controller.getAktueltSalg();
        this.salg.setDato(LocalDate.now());
        this.salg.setBetalingsmetode(betalingsmetodeComboBox.getValue());
        controller.addSalg(this.salg);
        alert();
        this.printKvittering();
    }


    public void alert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Tak for dit køb! Din kvittering forefindes i src.");
        alert.setContentText("Kom snart igen");
        alert.show();
        this.hide();
    }


}
