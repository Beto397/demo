package com.example.preg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class MedicalRecordApp extends Application {

    private TextField userIdField;
    private TextField appointmentKeyField;
    private DatePicker appointmentDatePicker;
    private ComboBox<String> reasonComboBox;
    private TextField symptomsField;
    private TextField prescriptionField;
    private TableView<MedicalRecord> tableView;
    private MedicalRecordDB database;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        database = new MedicalRecordDB();

        VBox layout = new VBox();
        layout.setStyle("-fx-padding: 25;");

        // Form Inputs
        userIdField = new TextField();
        appointmentKeyField = new TextField();
        appointmentDatePicker = new DatePicker();
        reasonComboBox = new ComboBox<>();
        reasonComboBox.getItems().addAll("Sickness", "Pregnancy", "General Consultation");
        symptomsField = new TextField();
        prescriptionField = new TextField();

        // Search Button
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchRecords());

        // Save Button
        Button saveButton = new Button("Save Record");
        saveButton.setOnAction(e -> saveRecord());

        // Layout
        layout.getChildren().addAll(
                new Label("User ID:"), userIdField,
                new Label("Appointment Key:"), appointmentKeyField,
                new Label("Appointment Date:"), appointmentDatePicker,
                new Label("Reason:"), reasonComboBox,
                new Label("Main Symptoms:"), symptomsField,
                new Label("Prescription:"), prescriptionField,
                saveButton, searchButton
        );

        // Table View
        tableView = new TableView<>();
        setupTable();
        layout.getChildren().add(tableView);

        Scene scene = new Scene(layout, 700, 500);
        stage.setTitle("Medical Record System");
        stage.setScene(scene);
        stage.show();
    }

    private void setupTable() {
        TableColumn<MedicalRecord, String> userIdCol = new TableColumn<>("User ID");
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<MedicalRecord, String> appointmentKeyCol = new TableColumn<>("Appointment Key");
        appointmentKeyCol.setCellValueFactory(new PropertyValueFactory<>("appointmentKey"));

        TableColumn<MedicalRecord, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));

        TableColumn<MedicalRecord, String> reasonCol = new TableColumn<>("Reason");
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));

        TableColumn<MedicalRecord, String> symptomsCol = new TableColumn<>("Symptoms");
        symptomsCol.setCellValueFactory(new PropertyValueFactory<>("symptoms"));

        TableColumn<MedicalRecord, String> prescriptionCol = new TableColumn<>("Prescription");
        prescriptionCol.setCellValueFactory(new PropertyValueFactory<>("prescription"));

        tableView.getColumns().addAll(userIdCol, appointmentKeyCol, dateCol, reasonCol, symptomsCol, prescriptionCol);
    }

    private void saveRecord() {
        String userId = userIdField.getText();
        String appointmentKey = appointmentKeyField.getText();
        LocalDate date = appointmentDatePicker.getValue();
        String reason = reasonComboBox.getValue();
        String symptoms = symptomsField.getText();
        String prescription = prescriptionField.getText();

        if (userId.isEmpty() || appointmentKey.isEmpty() || date == null || reason == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all required fields.");
            return;
        }

        MedicalRecord record = new MedicalRecord(userId, appointmentKey, date.toString(), reason, symptoms, prescription);
        database.save(record);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Medical record saved successfully.");
    }

    private void searchRecords() {
        String userId = userIdField.getText().trim();
        if (userId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a User ID.");
            return;
        }

        List<MedicalRecord> records = database.getRecords(userId);
        tableView.getItems().setAll(records);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
