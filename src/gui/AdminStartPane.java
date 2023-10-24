package gui;

import application.controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminStartPane extends Application {

    @Override
    public void init() {
        Controller.getUniqueInstance().init();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Aarhus Bryghus");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(400);
        stage.show();
    }

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        this.initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);


        Tab tabOpretProdukt = new Tab("Produkt");
        Tab tabOpretProduktGruppe = new Tab("Produktgruppe");
        Tab tabOpretSalgssituation = new Tab("Salgssituation");
        Tab tabStatistik = new Tab("Statistik");


        AdminProdukt visAdminProdukt = new AdminProdukt(tabPane,tabOpretProduktGruppe);

        tabOpretProdukt.setContent(visAdminProdukt);
        AdminProduktgruppe visAdminProduktgruppe = new AdminProduktgruppe();
        tabOpretProduktGruppe.setContent(visAdminProduktgruppe);
        AdminSalgssituation visAdminSalgssituation = new AdminSalgssituation();
        tabOpretSalgssituation.setContent(visAdminSalgssituation);
        AdminStatistik visAdminStatistik = new AdminStatistik();
        tabStatistik.setContent(visAdminStatistik);

        tabPane.getTabs().addAll(
                tabOpretProdukt,
                tabOpretProduktGruppe,
                tabOpretSalgssituation,
                tabStatistik);

        tabOpretProduktGruppe.setOnSelectionChanged(e -> visAdminProduktgruppe.updateControls());
        tabOpretProdukt.setOnSelectionChanged(event -> visAdminProdukt.updateControls());


    }

    
}
