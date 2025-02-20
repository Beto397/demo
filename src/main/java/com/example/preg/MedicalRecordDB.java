package com.example.preg;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDB {
    private static final String URL = "jdbc:sqlite:/Users/betofachasanchez/IdeaProjects/demo/TeenPregnant.sqlite";

    public MedicalRecordDB() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS MedicalRecords (" +
                    "userId TEXT NOT NULL, " +
                    "appointmentKey TEXT PRIMARY KEY, " +
                    "appointmentDate TEXT NOT NULL, " +
                    "reason TEXT NOT NULL, " +
                    "symptoms TEXT, " +
                    "prescription TEXT" +
                    ")";
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(MedicalRecord record) {
        String sql = "INSERT INTO MedicalRecords (userId, appointmentKey, appointmentDate, reason, symptoms, prescription) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, record.getUserId());
            pstmt.setString(2, record.getAppointmentKey());
            pstmt.setString(3, record.getAppointmentDate());
            pstmt.setString(4, record.getReason());
            pstmt.setString(5, record.getSymptoms());
            pstmt.setString(6, record.getPrescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MedicalRecord> getRecords(String userId) {
        List<MedicalRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM MedicalRecords WHERE userId = ?";
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                records.add(new MedicalRecord(
                        rs.getString("userId"), rs.getString("appointmentKey"),
                        rs.getString("appointmentDate"), rs.getString("reason"),
                        rs.getString("symptoms"), rs.getString("prescription")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}
