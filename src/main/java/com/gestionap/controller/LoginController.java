package com.gestionap.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField txtUsuario;
    @FXML private TextField txtPassword;
    @FXML private Button    btnLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actions();
    }

    private void actions() {
        btnLogin.setOnAction(e -> {
            if (txtUsuario.getText().isBlank() || txtPassword.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Introduce usuario y contraseña.");
                alert.showAndWait();
                return;
            }
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/gestionap/main-view.fxml"));
                Scene scene = new Scene(loader.load(), 900, 600);
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setTitle("GestionAp - Panel Principal");
                stage.setScene(scene);
                stage.setMaximized(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
