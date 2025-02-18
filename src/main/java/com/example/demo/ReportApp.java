package com.example.demo;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;

public class ReportApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Database Storage Object
        StorageDB storageDB = new StorageDB();  // Initialize database connection

        // Layout
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 15; -fx-alignment: center-left;");

        // Name & Surname Input
        HBox namePane = new HBox(5);
        TextField nameField = new TextField();
        namePane.getChildren().addAll(new Label("Name:"), nameField);

        HBox surnamePane = new HBox(5);
        TextField surnameField = new TextField();
        surnamePane.getChildren().addAll(new Label("Surname:"), surnameField);

        layout.getChildren().addAll(new Label("Enter your credentials:"), namePane, surnamePane);

        // Pregnancy Status (Radio Buttons)
        Label pregnantLabel = new Label("Pregnant:");
        RadioButton noButton = new RadioButton("No");
        RadioButton yesButton = new RadioButton("Yes");
        ToggleGroup pregnantGroup = new ToggleGroup();
        noButton.setToggleGroup(pregnantGroup);
        yesButton.setToggleGroup(pregnantGroup);
        HBox pregnantPane = new HBox(10, pregnantLabel, noButton, yesButton);
        layout.getChildren().add(pregnantPane);

        // Checkboxes for Appointment Days
        Label appointmentLabel = new Label("Appointment Days:");
        CheckBox[] checkBoxes = {
                new CheckBox("Monday"), new CheckBox("Tuesday"),
                new CheckBox("Wednesday"), new CheckBox("Thursday"),
                new CheckBox("Friday"), new CheckBox("Saturday"),
                new CheckBox("Sunday")
        };
        HBox appointmentPane = new HBox(10);
        appointmentPane.getChildren().addAll(checkBoxes);
        layout.getChildren().addAll(appointmentLabel, appointmentPane);

        // Consultancy Type (ComboBox)
        Label needsLabel = new Label("Select your needs:");
        ComboBox<String> needsComboBox = new ComboBox<>();
        needsComboBox.getItems().addAll("Counseling", "Medical", "Shelter");
        needsComboBox.setPromptText("Select a need");
        layout.getChildren().addAll(needsLabel, needsComboBox);

        // Register Button
        Button registerButton = new Button("Register");
        layout.getChildren().add(registerButton);

        // Button Event Handler
        registerButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String surname = surnameField.getText().trim();

            // Validate Inputs
            if (name.isEmpty() || surname.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Name and Surname cannot be empty!");
                return;
            }

            // Get Pregnancy Status
            RadioButton selectedPregnant = (RadioButton) pregnantGroup.getSelectedToggle();
            boolean isPregnant = (selectedPregnant != null && selectedPregnant.getText().equals("Yes"));

            // Get Selected Days (boolean array)
            boolean[] selectedDays = new boolean[7];
            for (int i = 0; i < checkBoxes.length; i++) {
                selectedDays[i] = checkBoxes[i].isSelected();
            }

            // Get Consultancy Type
            String selectedNeed = needsComboBox.getValue() != null ? needsComboBox.getValue() : "No need selected";

            // Create User Object
            User user = new User(name, surname, isPregnant, selectedDays, selectedNeed);

            // Save User to Database
            storageDB.save(user);
            showAlert(Alert.AlertType.INFORMATION, "Success", "User registered successfully!");
        });

        // Create Scene and Set Stage
        Scene scene = new Scene(layout, 700, 600);
        stage.setTitle("User Form");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
