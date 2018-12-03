/*
 * Caregiver.java
 *
 * Version 2
 *
 * December, 3, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Creates a caregiver model.
 *
 * Issues:
 *
 */
package ca.ualberta.symptomaticapp;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static java.lang.System.in;

/**
 *
 */
public class Caregiver extends User {
    private ArrayList<String> patients = new ArrayList<String>(); //A list of the caregiver's patients

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

    public Caregiver(){}

    public static Caregiver createNewCaregiver(String username, String phone, String email){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference newUser = db.collection("caregivers")
                .document();

        Caregiver caregiver = new Caregiver (username,phone,email);

        newUser.set(caregiver).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                int x=1; //dummy activity
            }
        });

        return caregiver;
    }

    /**
     *Adds a patient to the caregiver's patient list
     * @param patient: The patient to be added.
     * @return True if the patient did not already exist in the patient list and could be added, false if not.
     */
    public boolean addPatient(String patient){
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
    public ArrayList<String> getPatients(){
        return this.patients;
    }

    /**
     * Deletes a patient from the caregiver's patient list
     * @param patient: The patient to be deleted.
     * @return True if the patient existed in the patient list and could be deleted, false if not.
     */
    public boolean deletePatient(String patient){
        if (this.patients.contains(patient)){
            this.patients.remove(patient);
            return true;
        } else {
            return false;
        }
    }

}