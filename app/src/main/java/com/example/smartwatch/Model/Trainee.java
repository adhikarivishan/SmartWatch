package com.example.smartwatch.Model;

public class Trainee {

    private int traineeId;
    private String name, address, phone, email;

    public Trainee(int traineeId, String name, String address, String phone, String email){
        this.traineeId = traineeId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public int getTraineeId() {
        return traineeId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
