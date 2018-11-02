package ca.ualberta.symptomaticapp;

import java.util.regex.Pattern;

public class User {

    String email = " ",phone = " ",username = " ",userType = " ";


    //Creates the instance of a User object
    public User (String input_name,String input_phone,String input_email){
        this.username = input_name;
        changePhone(input_phone);
        changeEmail(input_email);
        this.userType = "Patient";
    }

    //Changes the Phone
    public boolean changePhone(String input_phone){
        if (validatePhone(input_phone)) {
            this.phone = input_phone;
            return true;
        } else {
            return false;
        }
    }

    public String returnUsername(){
        return this.username;
    }

    public String returnPhone(){
        return this.phone;
    }

    public String returnEmail(){
        return this.email;
    }

    public String returnUserType(){
        return this.userType;
    }

    //Changes the Email
    public boolean changeEmail(String input_email){
        if (validateEmail(input_email)) {
            this.email = input_email;
            return true;
        } else {
            return false;
        }
    }

    //checks to ensure the phone format is (XXX)XXX-XXXX
    private boolean validatePhone(String input_phone){
        Pattern pattern = Pattern.compile("^\\((\\d{3})\\)(\\d{3})[- ](\\d{4})$");
        return (pattern.matcher(input_phone).matches());
    }

    //checks to ensure the phone format is XXXXXXXX@XXXX.XXX
    private boolean validateEmail(String input_email){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{1,6}$");
        return (pattern.matcher(input_email).matches());
        //return true;
    }


}
