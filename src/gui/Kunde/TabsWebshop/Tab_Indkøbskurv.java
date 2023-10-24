package gui.Kunde.TabsWebshop;

import application.controller.Controller;
import application.model.*;
import gui.AdminProduktgruppe;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.ArrayList;

public class Tab_Indkøbskurv extends GridPane {

    private BetalingsWindow betalingsWindow = new BetalingsWindow("Betaling");
    private TabPane tabPane;

    private AdminProduktgruppe adminProduktgruppe = new AdminProduktgruppe();
    private ListView<Salgslinje> lvwSalgslinjer = new ListView<>();
    private TextField txfNavn = new TextField();
    private Button btnCheckout = new Button("Til betaling");
    private TextField txfTotal = new TextField();
    private Button btnFjern = new Button("Fjern fra kurv");
    private Controller controller = Controller.getUniqueInstance();
    private Salg salg;

    public Tab_Indkøbskurv(TabPane tabPane, Tab tab) {
        this.tabPane = tabPane;

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.makeEditable();

        this.add(new Label("Total"), 3, 20);
        this.add(txfTotal, 3, 21, 2, 1);
        txfTotal.setMaxWidth(70);


        this.add(new Label("Produkter"), 0, 1);
        this.add(lvwSalgslinjer, 0, 2, 3, 25);
        if(salg!=null){
            lvwSalgslinjer.getItems().setAll(salg.getSalgslinjer());
        }
        this.add(btnFjern, 3, 5, 2, 1);
        this.add(btnCheckout, 3, 25, 1, 1);


        btnCheckout.setOnAction(e -> checkout());
        btnFjern.setOnAction(e -> fjernFraKurv());

        // Callback
        lvwSalgslinjer.setCellFactory(new PersonCellFactory());

    }

    private void checkout() {
        betalingsWindow.showAndWait();
        betalingsWindow.opdater();
        opdater();
    }

    public void fjernFraKurv() {
        Salgslinje newSalgslinje = lvwSalgslinjer.getSelectionModel().getSelectedItem();
        if (newSalgslinje != null) {
            salg.removeSalgslinje(newSalgslinje);
        }
        opdater();
    }

    public void opdater() {

        if(controller.getAktueltSalg()!=null){
            salg = controller.getAktueltSalg();
            lvwSalgslinjer.getItems().setAll(salg.getSalgslinjer());
            txfTotal.setText("" + salg.samletPris() + " KR");
        }
        else{
            lvwSalgslinjer.getItems().setAll();
            txfTotal.clear();
        }

    }

    public BetalingsWindow getBetalingsWindow(){
        return betalingsWindow;
    }


    public void makeEditable() {
        txfTotal.setEditable(false);

    }

    class PersonCellFactory implements Callback<ListView<Salgslinje>, ListCell<Salgslinje>> {
        @Override
        public ListCell<Salgslinje> call(ListView<Salgslinje> param) {
            return new ListCell<>() {
                @Override
                public void updateItem(Salgslinje salgslinje, boolean empty) {
                    super.updateItem(salgslinje, empty);
                    if (empty || salgslinje == null) {
                        setText(null);
                    } else {
                        setText(salgslinje.getPerProduktPris().getProdukt().getNavn() + " / " + salgslinje.getAntal() + " stk.");
                    }
                }
            };
        }
    }
}
