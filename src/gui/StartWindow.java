package gui;


import gui.Kunde.KundeWebshop;
import application.controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StartWindow extends Application  {


    @Override
    public void init() {
         Controller.getUniqueInstance().init();
    }



    //    @Override
    public void start(Stage stage) {
        stage.setTitle("AARHUS BRYGHUS");
        GridPane pane = new GridPane();
        this.initContent(pane);
        
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();


        medarbejderLogin = new MedarbejderLogin("Medarbeder",stage);


        adminLogin = new AdminLogin("Admin",stage);
        kundeWebshop = new KundeWebshop();
        
    }

    private MedarbejderLogin medarbejderLogin;
    private AdminLogin adminLogin;
    private KundeWebshop kundeWebshop;

    private final Button btnMedarbejder = new Button("Medarbejder");
    private final Button btnKunde = new Button("Kunde");
    private final Button btnAdmin = new Button("Administrator");


    private void initContent(GridPane pane) {

        pane.setPadding(new Insets(10));
        pane.setMinWidth(200);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPrefHeight(100);
        pane.setGridLinesVisible(false);

        Label label = new Label("Tilgå salgssytem som");
        label.setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
        pane.add(label,2,0,3,1);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(btnKunde,btnMedarbejder,btnAdmin);
        btnKunde.setPrefWidth(100);
        btnMedarbejder.setPrefWidth(100);
        pane.add(hBox,2,2,1,1);
        btnMedarbejder.setOnAction(e -> medarbejderMenu());
        btnKunde.setOnAction(e -> salgKunde());
        btnAdmin.setOnAction(e -> adminMenu());

    }



    private void adminMenu() {
        this.adminLogin.showAndWait();
    }

    private void salgKunde() {
        this.kundeWebshop.start(new Stage());
    }



    private void medarbejderMenu() {
        medarbejderLogin.showAndWait();
        this.medarbejderLogin.hide();


    }


}
