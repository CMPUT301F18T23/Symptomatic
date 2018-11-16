package ca.ualberta.symptomaticapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * A user is a user of the application who will have an account, which
 * has contact information including an email, a phone number, and a username.
 * Furthermore, it will have a list of problems associated with the account
 */
@IgnoreExtraProperties
public class User {

    String email = " ",phone = "",username = "",userType = ""; //The contact information of the user, as well as their userType

    public ArrayList<Problem> problems = new ArrayList<>(); //An list of all problems associated to the user

    /**
     * Creates the instance of a user object
     * @param input_name: The username inputted by the user
     * @param input_phone: The phone number inputted by the user
     * @param input_email: The email inputted by the user
     */
    public User (String input_name,String input_phone,String input_email){
        this.username = input_name; //Sets the username to the inputted value
        changePhone(input_phone); //Sets the phone number, and checks its validity
        changeEmail(input_email); //Sets the email, and checks its validity
        this.userType = "Patient"; //Defaults the user to a Patient userType
    }

    public User(){}

    public static User createNewUser(String username, String phone, String email, String userType){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference newUser = db.collection("users")
                .document();

        User user = new User (username,phone,email);

        newUser.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                int x = 1;
            }
        });

        return user;
    }

    /**
     *Gets the username of the user
     * @return The username of the user
     */
    public String returnUsername(){
        return this.username;
    }

    /**
     *Gets the phone number of the user
     * @return The phone number of the user
     */
    public String returnPhone(){
        return this.phone;
    }

    /**
     *Gets the email of the user
     * @return The email of the user
     */
    public String returnEmail(){
        return this.email;
    }

    /**
     *Gets the type of user
     * @return The userType of the user
     */
    public String returnUserType(){
        return this.userType;
    }

    /**
     *Changes/Sets the phone number
     * @param input_phone: The phone number inputted by the user
     * @return True if the phone was valid and therefore changed, False if the phone was invalid and was not changed
     */
    public boolean changePhone(String input_phone){
        if (validatePhone(input_phone)) { //Checks the validity of the inputted phone number
            this.phone = input_phone; //Sets the phone number to the inputted value
            return true;
        } else {
            return false;
        }
    }

    /**
     *Changes/Sets the email
     * @param input_email: The email inputted by the user
     * @return True if the email was valid and therefore changed, False if the email was invalid and was not changed
     */
    public boolean changeEmail(String input_email){
        if (validateEmail(input_email)) { //checks the validity of the email
            this.email = input_email; //sets the new email from the user input
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateUser(String username){
        //TODO: create boolean whether or not the username exists in the database
        return true;
    }

    /**
     *Validates a phone number to a specific format (XXX)XXX-XXXX
     * @param input_phone: The phone number to be validated
     * @return True if the format is correct, False if the format is not correct
     */

    public static boolean validatePhone(String input_phone){
        Pattern pattern = Pattern.compile("^\\((\\d{3})\\)(\\d{3})[- ](\\d{4})$"); //An open bracket, followed by 3 numbers, then a closed bracket, then 3 more numbers, then a dash, then 4 more numbers
        return (pattern.matcher(input_phone).matches()); //returns whether or not the format matches
    }

    /**
     *Validates an email to a specific format XXXXXXXX@XXXX.XXX
     * @param input_email: The email to be validated
     * @return rue if the format is correct, False if the format is not correct
     */
    public static boolean validateEmail(String input_email){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{1,6}$"); //A various number of numbers or letters, followed by an '@' followed by a various number of numbers or letters, followed by a '.' followed by 1 to 6 letters
        return (pattern.matcher(input_email).matches()); //returns whether or not the format matches
    }

    /**
     * Gets the problem list of the user
     * @return The problem list of the user
     */
    public ArrayList<Problem> returnProblemList(){
        return this.problems;
    }

}