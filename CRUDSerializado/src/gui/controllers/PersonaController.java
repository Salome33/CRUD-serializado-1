package gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Persona;
import servicio.ServicioPersona;

import java.util.Optional;

public class PersonaController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtEdad;
    @FXML private TextField txtEmail;
    @FXML private TextField txtBuscar;

    @FXML private TableView<Persona> tablePersonas;
    @FXML private TableColumn<Persona, Long> colId;
    @FXML private TableColumn<Persona, String> colNombre;
    @FXML private TableColumn<Persona, String> colApellido;
    @FXML private TableColumn<Persona, Integer> colEdad;
    @FXML private TableColumn<Persona, String> colEmail;

    @FXML private Label lblEstado;
    @FXML private Label lblTotal;

    private ServicioPersona servicioPersona;
    private ObservableList<Persona> listaPersonas;

    @FXML
    public void initialize() {
        servicioPersona = new ServicioPersona();
        listaPersonas = FXCollections.observableArrayList();

        configurarTabla();
        cargarDatos();
        configurarSeleccionTabla();
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tablePersonas.setItems(listaPersonas);
    }

    private void configurarSeleccionTabla() {
        tablePersonas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        mostrarPersonaEnFormulario(newValue);
                    }
                }
        );
    }

    private void mostrarPersonaEnFormulario(Persona persona) {
        txtId.setText(persona.getId().toString());
        txtNombre.setText(persona.getNombre());
        txtApellido.setText(persona.getApellido());
        txtEdad.setText(String.valueOf(persona.getEdad()));
        txtEmail.setText(persona.getEmail());
    }

    @FXML
    private void handleCrear() {
        if (validarCampos()) {
            try {
                Persona persona = new Persona(
                        null,
                        txtNombre.getText().trim(),
                        txtApellido.getText().trim(),
                        Integer.parseInt(txtEdad.getText().trim()),
                        txtEmail.getText().trim()
                );

                servicioPersona.crearPersona(persona);
                mostrarMensaje("Persona creada exitosamente con ID: " + persona.getId(), "success");
                limpiarFormulario();
                cargarDatos();

            } catch (NumberFormatException e) {
                mostrarMensaje("La edad debe ser un número válido", "error");
            } catch (Exception e) {
                mostrarMensaje("Error al crear persona: " + e.getMessage(), "error");
            }
        }
    }

    @FXML
    private void handleActualizar() {
        if (txtId.getText().trim().isEmpty()) {
            mostrarMensaje("Seleccione una persona de la tabla para actualizar", "error");
            return;
        }

        if (validarCampos()) {
            try {
                Long id = Long.parseLong(txtId.getText().trim());

                Persona persona = new Persona(
                        id,
                        txtNombre.getText().trim(),
                        txtApellido.getText().trim(),
                        Integer.parseInt(txtEdad.getText().trim()),
                        txtEmail.getText().trim()
                );

                servicioPersona.actualizarPersona(persona);
                mostrarMensaje("Persona actualizada exitosamente", "success");
                limpiarFormulario();
                cargarDatos();

            } catch (NumberFormatException e) {
                mostrarMensaje("ID y edad deben ser números válidos", "error");
            } catch (Exception e) {
                mostrarMensaje("Error al actualizar persona: " + e.getMessage(), "error");
            }
        }
    }

    @FXML
    private void handleEliminar() {
        Persona personaSeleccionada = tablePersonas.getSelectionModel().getSelectedItem();

        if (personaSeleccionada == null) {
            mostrarMensaje("Seleccione una persona de la tabla para eliminar", "error");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Está seguro de eliminar esta persona?");
        alert.setContentText(personaSeleccionada.getNombre() + " " + personaSeleccionada.getApellido());

        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            servicioPersona.eliminarPersona(personaSeleccionada.getId());
            mostrarMensaje("Persona eliminada exitosamente", "success");
            limpiarFormulario();
            cargarDatos();
        }
    }

    @FXML
    private void handleBuscar() {
        String terminoBusqueda = txtBuscar.getText().trim();

        if (terminoBusqueda.isEmpty()) {
            cargarDatos();
            return;
        }

        listaPersonas.clear();
        listaPersonas.addAll(servicioPersona.buscarPorNombreLista(terminoBusqueda));
        actualizarContador();

        if (listaPersonas.isEmpty()) {
            mostrarMensaje("No se encontraron personas con ese nombre", "info");
        } else {
            mostrarMensaje("Se encontraron " + listaPersonas.size() + " resultado(s)", "info");
        }
    }

    @FXML
    private void handleLimpiar() {
        limpiarFormulario();
        txtBuscar.clear();
        cargarDatos();
    }

    @FXML
    private void handleRefrescar() {
        cargarDatos();
        mostrarMensaje("Datos actualizados", "info");
    }

    private void cargarDatos() {
        listaPersonas.clear();
        listaPersonas.addAll(servicioPersona.obtenerTodasLasPersonas());
        actualizarContador();
    }

    private void actualizarContador() {
        lblTotal.setText("Total: " + listaPersonas.size() + " persona(s)");
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarMensaje("El nombre es obligatorio", "error");
            txtNombre.requestFocus();
            return false;
        }

        if (txtApellido.getText().trim().isEmpty()) {
            mostrarMensaje("El apellido es obligatorio", "error");
            txtApellido.requestFocus();
            return false;
        }

        if (txtEdad.getText().trim().isEmpty()) {
            mostrarMensaje("La edad es obligatoria", "error");
            txtEdad.requestFocus();
            return false;
        }

        try {
            int edad = Integer.parseInt(txtEdad.getText().trim());
            if (edad < 0 || edad > 150) {
                mostrarMensaje("La edad debe estar entre 0 y 150", "error");
                txtEdad.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("La edad debe ser un número válido", "error");
            txtEdad.requestFocus();
            return false;
        }

        if (txtEmail.getText().trim().isEmpty()) {
            mostrarMensaje("El email es obligatorio", "error");
            txtEmail.requestFocus();
            return false;
        }

        return true;
    }

    private void limpiarFormulario() {
        txtId.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtEdad.clear();
        txtEmail.clear();
        tablePersonas.getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(String mensaje, String tipo) {
        lblEstado.setText(mensaje);

        switch (tipo) {
            case "success":
                lblEstado.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                break;
            case "error":
                lblEstado.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                break;
            case "info":
                lblEstado.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
                break;
        }
    }
}