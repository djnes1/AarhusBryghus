package gui;

import application.controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import storage.Storage;

public class MedarbejderLogin extends Stage {


    private MedarbejderBarUdlejning medarbejderBarUdlejning;
    public MedarbejderLogin(String title, Stage owner) {
        this.initOwner(owner);
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setMinHeight(100);
        this.setMinWidth(200);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);


//      barSimple = new BarSimple("Bar",this);
        medarbejderSalgStartPane = new MedarbejderSalgStartPane();
    }

    private MedarbejderSalgStartPane medarbejderSalgStartPane;
//    private MedarbejsSalgPane medarbejderSalgPane;
    private Controller controller = new Controller(Storage.getUniqueInstance());
    private TextField txfMedarbejderNavn = new TextField();
    private final PasswordField medarbejderNr = new PasswordField();
    private Button btnLogin = new Button("Login");
    private Button btnReturn = new Button("Tilbage til menu");
    private void initContent(GridPane pane) {

        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);


        Label lblMedarbejderNavn = new Label("Navn:");
        Label lblMedarbejderNr = new Label("Medarbejdernr:");
        pane.add(lblMedarbejderNavn, 0, 0);
        pane.add(lblMedarbejderNr, 0, 1);
        pane.add(txfMedarbejderNavn, 1, 0, 3, 1);
        pane.add(medarbejderNr, 1, 1, 3, 1);

        pane.add(btnLogin, 2, 2, 1, 1);
        pane.add(btnReturn, 3, 2, 1, 1);
        btnLogin.setOnAction(e -> this.okAction());
        btnReturn.setOnAction(e -> this.returnAction());
    }

    private void returnAction() {
        medarbejderNr.clear();
        txfMedarbejderNavn.clear();
        this.hide();
    }

    private void okAction()  {
        String navn = txfMedarbejderNavn.getText().trim();
        String password = medarbejderNr.getText().trim();
        if (controller.findMedarbejder(navn, password,false)) {
          this.medarbejderSalgStartPane.start(this);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Medarbejder findes ikke");
            alert.showAndWait();
        }
        txfMedarbejderNavn.clear();
        medarbejderNr.clear();
    }





}
