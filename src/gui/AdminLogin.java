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

public class AdminLogin extends Stage {


        public AdminLogin(String title, Stage owner) {
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

            adminStartPane = new AdminStartPane();
        }

        private Controller controller = Controller.getUniqueInstance();
        private AdminStartPane adminStartPane;
        private final PasswordField adminPassword = new PasswordField();
        private TextField txfAdminNavn = new TextField();
        private Button btnLogin = new Button("Login");
        private Button btnReturn = new Button("Tilbage til menu");

        private void initContent(GridPane pane) {

            pane.setPadding(new Insets(20));
            pane.setHgap(10);
            pane.setVgap(10);

            pane.add(new Label("Brugernavn:"),0,0);
            pane.add(new Label("Password:"),0,1);
            pane.add(txfAdminNavn,1,0,3,1);
            pane.add(adminPassword, 1, 1, 3, 1);
            pane.add(btnLogin, 2, 2, 1, 1);
            btnLogin.setOnAction(event -> this.okAction());
            pane.add(btnReturn,3,2);
            btnReturn.setOnAction(e -> this.returnAction());
        }

    private void returnAction() {
            txfAdminNavn.clear();
            adminPassword.clear();
            this.hide();

    }

    private void okAction() {
            String navn = txfAdminNavn.getText().trim();
            String password = adminPassword.getText().trim();
            if (controller.findMedarbejder(navn, password,true)) {
                this.adminStartPane.start(new Stage());

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Administrator findes ikke");
                alert.showAndWait();
            }
            txfAdminNavn.clear();
            adminPassword.clear();



    }

}
