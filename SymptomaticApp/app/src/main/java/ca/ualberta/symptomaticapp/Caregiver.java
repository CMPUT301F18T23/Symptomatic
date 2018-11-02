package ca.ualberta.symptomaticapp;

import java.util.ArrayList;

import static java.lang.System.in;

/**
 *
 */
public class Caregiver extends User {
    private ArrayList<User> patients = new ArrayList<User>(); //A list of the caregiver's patients

    /**
     * Creates a User object with the specified parameters, and sets the userType to Caregiver
     * @param username: The username entered by the user
     * @param phone: The phone number enetered by the user
     * @param email: The email entered by the user
     */
    public Caregiver(String username, String phone, String email) {
        super(username, phone, email);
        this.userType = "Caregiver";
    }

    /**
     *Adds a patient to the caregiver's patient list
     * @param patient: The patient to be added.
     * @return True if the patient did not already exist in the patient list and could be added, false if not.
     */
    public boolean addPatient(User patient){
        if (this.patients.contains(patient)){
            return false;
        } else {
            this.patients.add(patient);
            return true;
        }
    }

    /**
     * Gets the patient list of the caregiver
     * @return The patient list of the caregiver
     */
    public ArrayList<User> getPatients(){
        return this.patients;
    }

    /**
     * Deletes a patient from the caregiver's patient list
     * @param patient: The patient to be deleted.
     * @return True if the patient existed in the patient list and could be deleted, false if not.
     */
    public boolean deletePatient(User patient){
        if (this.patients.contains(patient)){
            this.patients.remove(patient);
            return true;
        } else {
            return false;
        }
    }

}
