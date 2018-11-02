package ca.ualberta.symptomaticapp;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

import ca.ualberta.symptomaticapp.User;
import ca.ualberta.symptomaticapp.Caregiver;

public class userTest extends TestCase {

    public void testUser() {
        //Test initial creation of a User Object
        User user = new User("ryan1234", "(780)481-3905", "ryan@hotmail.com");
        assertTrue("Username Incorrect", user.returnUsername().equals("ryan1234"));
        assertTrue("Phone Incorrect", user.returnPhone().equals("(780)481-3905"));
        assertTrue("Email Incorrect", user.returnEmail().equals("ryan@hotmail.com"));
        assertTrue("Usertype Incorrect", user.returnUserType().equals("Patient"));
        //Test modifying a users contact information
        user.changeEmail("freddy@hotmail.com");
        user.changePhone("(780)585-3905");
        assertTrue("Phone Incorrect", user.returnPhone().equals("(780)585-3905"));
        assertTrue("Email Incorrect", user.returnEmail().equals("freddy@hotmail.com"));
    }

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

    public void testCaregiver(){
        User user = new User("ryan1234", "(780)481-3905", "ryan@hotmail.com");

        //Add Caregiver
        Caregiver caregiver = new Caregiver("doctorphil","(780)911-9111","drphil@doctors.com");
        assertTrue("Caregiver Username Incorrect", caregiver.returnUsername().equals("doctorphil"));
        assertTrue("Caregiver Phone Incorrect", caregiver.returnPhone().equals("(780)911-9111"));
        assertTrue("Caregiver Email Incorrect", caregiver.returnEmail().equals("drphil@doctors.com"));
        assertTrue("Caregiver Usertype Incorrect", caregiver.returnUserType().equals("Caregiver"));

        //Add Patient to a Caregiver
        caregiver.addPatient(user);

        //Get Patients from Caregiver
        ArrayList<User> caregiverPatients = caregiver.getPatients();
        assertTrue("Invalid patient list length",caregiverPatients.size()==1);

        //Delete Patient from Caregiver
        caregiver.deletePatient(user);

        //Get Patients from Caregiver
        caregiverPatients = caregiver.getPatients();
        assertTrue("Invalid patient list length",caregiverPatients.size()==0);
    }
}
