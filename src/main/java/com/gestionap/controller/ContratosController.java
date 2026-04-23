package com.gestionap.controller;

import com.gestionap.dao.ContratoDAO;
import com.gestionap.model.Contrato;
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

public class ContratosController implements Initializable {

    @FXML private TableView<Contrato>             tablaContratos;
    @FXML private TableColumn<Contrato, Integer>  colId;
    @FXML private TableColumn<Contrato, String>   colInquilino;
    @FXML private TableColumn<Contrato, Integer>  colHabitacion;
    @FXML private TableColumn<Contrato, Object>   colFechaInicio;
    @FXML private TableColumn<Contrato, Object>   colFechaFin;
    @FXML private TableColumn<Contrato, Object>   colPrecio;

    private ContratoDAO contratoDAO;
    private ObservableList<Contrato> listaContratos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        initGUI();
        actions();
    }

    private void instances() {
        contratoDAO    = new ContratoDAO();
        listaContratos = FXCollections.observableArrayList();
    }

    private void initGUI() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idContrato"));
        colInquilino.setCellValueFactory(new PropertyValueFactory<>("nombreInquilino"));
        colHabitacion.setCellValueFactory(new PropertyValueFactory<>("numeroHabitacion"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioMensual"));

        cargarDatos();
    }

    private void actions() {
        // sin acciones adicionales en esta vista
    }

    private void cargarDatos() {
        try {
            listaContratos.setAll(contratoDAO.listarTodos());
            tablaContratos.setItems(listaContratos);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudieron cargar los contratos");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}
