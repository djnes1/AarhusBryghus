package gui;

import application.controller.Controller;
import application.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MedarbejderSalgStartPane extends Application {


    Produkt produkt;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Aarhus Bryghus");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setHeight(700);
        stage.setWidth(900);
        stage.show();
        stage.centerOnScreen();
    }

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        this.initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabFredagsbar = new Tab("Fredagsbar");

        Tab tabInkoebskurv = new Tab("Indkøbskurv");
        Tab tabUdlejning = new Tab("Udlejning");
        Tab tabRundvisning = new Tab("Rundvisning");


        MedarbejderSalgPane medarbejderSalgPane = new MedarbejderSalgPane(tabPane, tabInkoebskurv);
        tabFredagsbar.setContent(medarbejderSalgPane);
        MedarbejderIndkøbskurv medarbejderIndkøbskurv = new MedarbejderIndkøbskurv(tabPane, tabFredagsbar);
        tabInkoebskurv.setContent(medarbejderIndkøbskurv);
        MedarbejderBarUdlejning medarbejderBarUdlejning = new MedarbejderBarUdlejning(tabPane, tabFredagsbar);
        tabUdlejning.setContent(medarbejderBarUdlejning);
        MedarbejderBarRundvisning medarbejderBarRundvisning = new MedarbejderBarRundvisning(tabPane, tabFredagsbar);
        tabRundvisning.setContent(medarbejderBarRundvisning);


        tabPane.getTabs().addAll(tabFredagsbar, tabInkoebskurv,
                tabUdlejning,
                tabRundvisning);

        tabInkoebskurv.setOnSelectionChanged(e -> {
            medarbejderIndkøbskurv.opdater();
            medarbejderIndkøbskurv.getMedarbejderBarBetaling().opdater();
        });
    }
}
