package gui.Kunde;

import gui.Kunde.TabsWebshop.Tab_Salg;
import gui.Kunde.TabsWebshop.Tab_Indkøbskurv;
import application.controller.Controller;
import gui.StartWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class KundeWebshop extends Application {


    @Override
    public void init() {
        Controller.getUniqueInstance().init();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Webshop");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(800);
        stage.show();
    }

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        this.initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabFlaske = new Tab("Salg");
        Tab tabindkoebskurv = new Tab("Indkøbskurv");
        Tab_Salg visTabFlaske  = new Tab_Salg(tabPane, tabindkoebskurv);
        Tab_Indkøbskurv visTabIndkøbskurv = new Tab_Indkøbskurv(tabPane, tabFlaske);

        tabFlaske.setContent(visTabFlaske);
        tabindkoebskurv.setContent(visTabIndkøbskurv);
        tabindkoebskurv.setOnSelectionChanged(event -> {
            visTabIndkøbskurv.opdater();
            visTabIndkøbskurv.getBetalingsWindow().opdater();
        });


        tabPane.getTabs().add(tabFlaske);
        tabPane.getTabs().add(tabindkoebskurv);

    }

}
