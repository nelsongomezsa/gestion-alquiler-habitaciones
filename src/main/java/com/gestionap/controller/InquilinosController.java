package com.gestionap.controller;

import com.gestionap.dao.InquilinoDAO;
import com.gestionap.model.Inquilino;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InquilinosController implements Initializable {

    @FXML private TableView<Inquilino>              tablaInquilinos;
    @FXML private TableColumn<Inquilino, Integer>   colId;
    @FXML private TableColumn<Inquilino, String>    colNombre;
    @FXML private TableColumn<Inquilino, String>    colApellidos;
    @FXML private TableColumn<Inquilino, String>    colDni;
    @FXML private TableColumn<Inquilino, String>    colTelefono;
    @FXML private Button                            btnAnadir;

    private InquilinoDAO inquilinoDAO;
    private ObservableList<Inquilino> listaInquilinos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        initGUI();
        actions();
    }

    private void instances() {
        inquilinoDAO    = new InquilinoDAO();
        listaInquilinos = FXCollections.observableArrayList();
    }

    private void initGUI() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idInquilino"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        cargarDatos();
    }

    private void actions() {
        btnAnadir.setOnAction(e -> abrirDialogoAnadir());
    }

    private void cargarDatos() {
        try {
            listaInquilinos.setAll(inquilinoDAO.listarTodos());
            tablaInquilinos.setItems(listaInquilinos);
        } catch (Exception ex) {
            mostrarError("No se pudieron cargar los inquilinos", ex.getMessage());
        }
    }

    private void abrirDialogoAnadir() {
        Dialog<Inquilino> dialog = new Dialog<>();
        dialog.setTitle("Añadir Inquilino");
        dialog.setHeaderText("Introduce los datos del nuevo inquilino");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField tfNombre    = new TextField();
        TextField tfApellidos = new TextField();
        TextField tfDni       = new TextField();
        TextField tfTelefono  = new TextField();
        TextField tfEmail     = new TextField();

        tfNombre.setPromptText("Nombre");
        tfApellidos.setPromptText("Apellidos");
        tfDni.setPromptText("DNI");
        tfTelefono.setPromptText("Teléfono");
        tfEmail.setPromptText("Email");

        grid.add(new Label("Nombre:"),    0, 0); grid.add(tfNombre,    1, 0);
        grid.add(new Label("Apellidos:"), 0, 1); grid.add(tfApellidos, 1, 1);
        grid.add(new Label("DNI:"),       0, 2); grid.add(tfDni,       1, 2);
        grid.add(new Label("Teléfono:"),  0, 3); grid.add(tfTelefono,  1, 3);
        grid.add(new Label("Email:"),     0, 4); grid.add(tfEmail,     1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == btnGuardar) {
                Inquilino i = new Inquilino();
                i.setNombre(tfNombre.getText().trim());
                i.setApellidos(tfApellidos.getText().trim());
                i.setDni(tfDni.getText().trim());
                i.setTelefono(tfTelefono.getText().trim());
                i.setEmail(tfEmail.getText().trim());
                return i;
            }
            return null;
        });

        Optional<Inquilino> resultado = dialog.showAndWait();
        resultado.ifPresent(inquilino -> {
            try {
                inquilinoDAO.insertar(inquilino);
                cargarDatos();
            } catch (Exception ex) {
                mostrarError("No se pudo guardar el inquilino", ex.getMessage());
            }
        });
    }

    private void mostrarError(String header, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
