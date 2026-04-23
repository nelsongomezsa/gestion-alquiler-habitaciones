package com.gestionap.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private Button   btnHabitaciones;
    @FXML private Button   btnInquilinos;
    @FXML private Button   btnContratos;
    @FXML private VBox     contenidoCentro;
    @FXML private BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        initGUI();
        actions();
    }

    private void instances() {
        // instancias adicionales si se necesitan
    }

    private void initGUI() {
        // estado inicial: panel de bienvenida ya definido en el FXML
    }

    private void actions() {
        btnHabitaciones.setOnAction(e -> cargarVista("/com/gestionap/habitaciones-view.fxml"));
        btnInquilinos.setOnAction(e  -> cargarVista("/com/gestionap/inquilinos-view.fxml"));
        btnContratos.setOnAction(e   -> cargarVista("/com/gestionap/contratos-view.fxml"));
    }

    private void cargarVista(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node vista = loader.load();
            borderPane.setCenter(vista);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
