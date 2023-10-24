package gui;

import application.controller.Controller;
import application.model.*;
import gui.AdminProduktgruppe;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class MedarbejderSalgPane extends GridPane {
    private TabPane tabPane;
    private Tab tab_indkøbskurv;
    private AdminProduktgruppe adminProduktgruppe = new AdminProduktgruppe();
    private ListView<PerProduktPris> lvwPerProduktPriser = new ListView<>();
    private ListView<ProduktGruppe> lvwProduktGruppe = new ListView<>();
    private TextField txfNavn = new TextField();
    private Button btnIndkoebskurv = new Button("Se kurv");
    private TextField txfPrisKr = new TextField();
    private TextField txfKlip = new TextField();
    private Button btnTilføjTilKurv = new Button("Tilføj til kurv");
    private Controller controller = Controller.getUniqueInstance();
    //private ProduktGruppe flaskeProduktGruppe = controller.findProduktGruppe("Flaske");
    private Salg salg = controller.getAktueltSalg();


    // Spinner
    private Spinner<Integer> spinner = new Spinner<Integer>();
    private int initialValue = 0;
    private SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, initialValue);


    public void init() {
        Controller.getUniqueInstance().init();
    }


    public MedarbejderSalgPane(TabPane tabPane, Tab tab) {
        this.tabPane = tabPane;
        tab_indkøbskurv = tab;
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.makeEditable();
        VBox vBox = new VBox();
        vBox.getChildren().addAll(txfKlip, txfPrisKr, spinner, btnTilføjTilKurv, btnIndkoebskurv);
        this.add(vBox, 9, 3, 1, 1);
        vBox.setSpacing(10);

        txfPrisKr.setMaxWidth(70);
        txfPrisKr.setPromptText("0 kr");
        //this.add(new Label("KR"), 10, 3);


        txfKlip.setMaxWidth(70);
        txfKlip.setPromptText("Klip");
        //this.add(new Label("Klip"), 10, 4);


        this.add(new Label("Produkter"), 5, 1);
        this.add(lvwPerProduktPriser, 5, 2, 3, 6);
        ChangeListener<PerProduktPris> listener1 = (ov, oldValue, newValue) -> this.selectionchangedPriser();
        lvwPerProduktPriser.getSelectionModel().selectedItemProperty().addListener(listener1);

        this.add(new Label("Produktgrupper"), 0, 1);
        this.add(lvwProduktGruppe, 0, 2, 3, 6);
        lvwProduktGruppe.getItems().setAll(controller.getProduktGrupperFraWebshop());
        ChangeListener<ProduktGruppe> listener = (ov, oldFrivillig, newFrivillig) -> this.selectionchangedProduktgruppe();
        lvwProduktGruppe.getSelectionModel().selectedItemProperty().addListener(listener);

        btnTilføjTilKurv.setOnAction(e -> tilføjTilKurv());

        btnIndkoebskurv.setOnAction(e -> gåTilIndkøbskurv());

        // Spinner
        spinner.setValueFactory(valueFactory);

        // Callback
        lvwPerProduktPriser.setCellFactory(new PersonCellFactory());
    }

    private void selectionchangedProduktgruppe() {
        ProduktGruppe newProduktGruppe = lvwProduktGruppe.getSelectionModel().getSelectedItem();

        if (newProduktGruppe != null) {
            lvwPerProduktPriser.getItems().setAll(controller.getProdukterFraProduktGruppe(newProduktGruppe));
        }


        txfPrisKr.clear();
        txfKlip.clear();
        spinner.getValueFactory().setValue(1);
    }

    private void selectionchangedPriser() {
        PerProduktPris newPPP = lvwPerProduktPriser.getSelectionModel().getSelectedItem();

        if (newPPP != null) {
            txfPrisKr.setText("" + newPPP.getPris() + " KR");
        }
        if (newPPP != null) {
            txfKlip.setText("" + newPPP.getKlip() + " Klip");
        }

    }


    private void tilføjTilKurv() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        PerProduktPris newPPP = lvwPerProduktPriser.getSelectionModel().getSelectedItem();
        int antal = spinner.getValue();
        if (salg == null) {
            salg = controller.createSalg(null, null, null);
            controller.setAktueltSalg(salg);
        }

        if (newPPP != null) {
            Salgslinje salgslinje = salg.createSalgslinje(newPPP, antal);
            salg.addSalgslinje(salgslinje);
            alert.setContentText("Du har tilføjet " + salgslinje.getPerProduktPris().getProdukt() + " til kurven");
            alert.showAndWait();
        }
        lvwPerProduktPriser.getSelectionModel().clearSelection();
        slutTilføjet();
    }

    private void slutTilføjet() {
        spinner.getValueFactory().setValue(1);
    }

    private void selectionChanged() {
        spinner.getValueFactory().setValue(1);
    }


    private void gåTilIndkøbskurv() {
        tabPane.getSelectionModel().select(tab_indkøbskurv);
    }

    public void makeEditable() {
        txfKlip.setEditable(false);
        txfPrisKr.setEditable(false);
    }

    class PersonCellFactory implements Callback<ListView<PerProduktPris>, ListCell<PerProduktPris>> {
        @Override
        public ListCell<PerProduktPris> call(ListView<PerProduktPris> param) {
            return new ListCell<>() {
                @Override
                public void updateItem(PerProduktPris ppp, boolean empty) {
                    super.updateItem(ppp, empty);
                    if (empty || ppp == null) {
                        setText(null);
                    } else {
                        setText(ppp.getProdukt().getNavn());
                    }
                }
            };
        }
    }

    public Salg getSalg() {
        return salg;
    }
}
