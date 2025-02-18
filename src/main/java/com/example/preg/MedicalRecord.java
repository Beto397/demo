package com.example.preg;

public class MedicalRecord {
    private String userId;
    private String appointmentKey;
    private String appointmentDate;
    private String reason;
    private String symptoms;
    private String prescription;

    public MedicalRecord(String userId, String appointmentKey, String appointmentDate, String reason, String symptoms, String prescription) {
        this.userId = userId;
        this.appointmentKey = appointmentKey;
        this.appointmentDate = appointmentDate;
        this.reason = reason;
        this.symptoms = symptoms;
        this.prescription = prescription;
    }

    public String getUserId() { return userId; }
    public String getAppointmentKey() { return appointmentKey; }
    public String getAppointmentDate() { return appointmentDate; }
    public String getReason() { return reason; }
    public String getSymptoms() { return symptoms; }
    public String getPrescription() { return prescription; }
}
