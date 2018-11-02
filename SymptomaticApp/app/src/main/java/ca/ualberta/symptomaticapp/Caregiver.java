package ca.ualberta.symptomaticapp;

import java.util.ArrayList;

public class Caregiver extends User {
    public ArrayList<User> patients = new ArrayList<User>();

    public Caregiver(String username, String phone, String email) {
        super(username, phone, email);
        this.userType = "Caregiver";
    }

    public ArrayList<User> getPatients(){
        return this.patients;
    }

    public void deletePatient(User patient){
        this.patients.remove(patient);
    }

    public void addPatient(User patient){
        this.patients.add(patient);
    }

}
