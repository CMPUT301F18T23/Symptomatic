package ca.ualberta.symptomaticapp;

import junit.framework.TestCase;

import java.util.ArrayList;

public class userTest extends TestCase {

    /**
     * Tests the creation of a user object
     */
    public void testUser() {
        //Test initial creation of a User Object
        User user = new User("ryan1234", "(780)481-3905", "ryan@hotmail.com");
        assertTrue("Username Incorrect", user.returnUsername().equals("ryan1234"));
        assertTrue("Phone Incorrect", user.returnPhone().equals("(780)481-3905"));
        assertTrue("Email Incorrect", user.returnEmail().equals("ryan@hotmail.com"));
        assertTrue("userType Incorrect", user.returnUserType().equals("Patient"));
    }

    /**
     * Tests the changing of a user's:
     *      1. Email
     *      2. Phone
     */
    public void testChange(){
        User user = new User("ryan1234", "(780)481-3905", "ryan@hotmail.com");

        user.changeEmail("freddy@hotmail.com");
        assertTrue("Email Incorrect", user.returnEmail().equals("freddy@hotmail.com"));

        user.changePhone("(780)585-3905");
        assertTrue("Phone Incorrect", user.returnPhone().equals("(780)585-3905"));
    }

    /**
     *Tests the changing of a user's contact information with:
     *      1. Invalid Email formats
     *      2. Invalid Phone formats
     *Then ensures the values are unchanged at the end of all attempts to change with invalid values
     */
    public void testExceptions(){
        User user = new User("ryan1234", "(780)585-3905", "freddy@hotmail.com");

        //Text not updating invalid field
        //Invalid emails
        assertTrue("Incorrect Email updated 1",!user.changeEmail("freddy@hotmail"));
        assertTrue("Incorrect Email updated 2",!user.changeEmail("freddyhotmail.com"));
        assertTrue("Incorrect Email updated 3",!user.changeEmail("freddyhotmail"));

        //Invalid phone numbers
        assertTrue("Incorrect Phone updated 1",!user.changePhone("780)585-3905"));
        assertTrue("Incorrect Phone updated 2",!user.changePhone("(780585-3905"));
        assertTrue("Incorrect Phone updated 3",!user.changePhone("(780)5853905"));

        //Ensure values are the same as it was before changes
        assertTrue("Incorrect Phone Updated Final",user.returnPhone().equals("(780)585-3905"));
        assertTrue("Incorrect Email Updated Final",user.returnEmail().equals("freddy@hotmail.com"));

    }

    /**
     *Tests the creation of a Caregiver type object
     */
    public void testCaregiver() {
        User user = new User("ryan1234", "(780)481-3905", "ryan@hotmail.com");

        //Add Caregiver
        Caregiver caregiver = new Caregiver("doctorphil", "(780)911-9111", "drphil@doctors.com");
        assertTrue("Caregiver Username Incorrect", caregiver.returnUsername().equals("doctorphil"));
        assertTrue("Caregiver Phone Incorrect", caregiver.returnPhone().equals("(780)911-9111"));
        assertTrue("Caregiver Email Incorrect", caregiver.returnEmail().equals("drphil@doctors.com"));
        assertTrue("Caregiver Usertype Incorrect", caregiver.returnUserType().equals("Caregiver"));

    }

    /**
     * Tests the addition of patients to a caregiver that:
     *      1. Do not already exist in the caregivers patient list
     *      2. Already exist in the caregivers patient list
     */
    public void testPatientAdd(){
        User user = new User("ryan1234", "(780)481-3905", "ryan@hotmail.com");
        Caregiver caregiver = new Caregiver("doctorphil", "(780)911-9111", "drphil@doctors.com");

        //Add Patient to a Caregiver
        assertTrue("Patient not added that doesn't already exist in caregivers patient list",caregiver.addPatient(user.username));
        ArrayList<String> caregiverPatients = caregiver.getPatients();
        assertTrue("Invalid patient list length",caregiverPatients.size()==1); //Should be 1 patient in the caregivers patient list after the addition

        //Add patient that already exists in the caregiver's patient list
        assertFalse("Patient added that is already in the caregiver's patient list",caregiver.addPatient(user.username));
        caregiverPatients = caregiver.getPatients();
        assertTrue("Invalid patient list length",caregiverPatients.size()==1); //Should be 1 patient in the caregivers patient list after the addition

    }

    /**
     * Tests the deletion of patients that:
     *      1. Exist in the caregivers patient list
     *      2. Do not exist in the caregivers patient list
     */
   public void testPatientDelete(){
        User user = new User("ryan1234", "(780)481-3905", "ryan@hotmail.com");
        Caregiver caregiver = new Caregiver("doctorphil", "(780)911-9111", "drphil@doctors.com");
        caregiver.addPatient(user.username);
        ArrayList<String> caregiverPatients = caregiver.getPatients();

        //User exists in caregivers patient list
        assertTrue("User exists but was not deleted",caregiver.deletePatient(user.username));
        caregiverPatients = caregiver.getPatients();
        assertTrue("Invalid patient list length",caregiverPatients.size()==0); //Should be no patients in the caregiver's patient list after the deletion

        //User does not exist in the caregivers patient list
        assertFalse("Invalid deletion of non-existent user",caregiver.deletePatient(user.username));
        caregiverPatients = caregiver.getPatients();
        assertTrue("Invalid patient list length",caregiverPatients.size()==0); //Should be no patients in the caregiver's patient list after the deletion

    }
}
