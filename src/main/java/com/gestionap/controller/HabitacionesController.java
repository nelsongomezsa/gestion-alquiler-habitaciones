package com.gestionap.controller;

import com.gestionap.dao.HabitacionDAO;
import com.gestionap.model.Habitacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HabitacionesController implements Initializable {

    @FXML private TableView<Habitacion>          tablaHabitaciones;
    @FXML private TableColumn<Habitacion, Integer>     colId;
    @FXML private TableColumn<Habitacion, Integer>     colNumero;
    @FXML private TableColumn<Habitacion, Object>      colPrecio;
    @FXML private TableColumn<Habitacion, Object>      colEstado;
    @FXML private TableColumn<Habitacion, String>      colPiso;

    private HabitacionDAO habitacionDAO;
    private ObservableList<Habitacion> listaHabitaciones;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        initGUI();
        actions();
    }

    private void instances() {
        habitacionDAO      = new HabitacionDAO();
        listaHabitaciones  = FXCollections.observableArrayList();
    }

    private void initGUI() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idHabitacion"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colPiso.setCellValueFactory(new PropertyValueFactory<>("direccionPiso"));

        cargarDatos();
    }

    private void actions() {
        // sin acciones adicionales en esta vista
    }

    private void cargarDatos() {
        try {
            listaHabitaciones.setAll(habitacionDAO.listarTodas());
            tablaHabitaciones.setItems(listaHabitaciones);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudieron cargar las habitaciones");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}
