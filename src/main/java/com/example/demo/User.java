package com.example.demo;

public class User {
    private String name;
    private String surname;
    private boolean pregnant;
    private boolean[] days; // 7-day boolean array (Mon-Sun)
    private String appointmentType;

    public User(String name, String surname, boolean pregnant, boolean[] days, String appointmentType) {
        this.name = name;
        this.surname = surname;
        this.pregnant = pregnant;
        this.days = days;
        this.appointmentType = appointmentType;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isPregnant() {
        return pregnant;
    }

    public boolean[] getDays() {
        return days;
    }

    public String getAppointmentType() {
        return appointmentType;
    }
}
