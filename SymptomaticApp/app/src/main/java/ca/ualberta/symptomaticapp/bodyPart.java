package ca.ualberta.symptomaticapp;

import java.util.ArrayList;

/**
 * Creates the instance of a body part, which will have a name, a front photo and a back photo
 * of the body part.
 */
public class bodyPart {

    String name; //The name of the body part
    String frontPhoto,backPhoto; //the front and back photo of the body part, respectively

    /**
     * Creates the instance of a body part object, with a default blank front and back photo
     * @param input_name: The bodyPart name specified
     */
    public bodyPart(String input_name){

        //set the name to the inputted name
        this.name = input_name;

        //default the front and back photos to a blank photo
        this.frontPhoto = "BLANK";
        this.backPhoto = "BLANK";
    }

    /**
     * Replaces the front photo with a new photo
     * @param new_photo: The photo to replace the front photo
     */
    public void changeFrontPhoto(String new_photo){
        //Changes the front photo to a photo taken by the user
        this.frontPhoto = new_photo;
    }

    /**
     * Replaces the back photo with a new photo
     * @param new_photo: The photo to replace the back photo
     */
    public void changeBackPhoto(String new_photo){
        //Changes the back photo to a photo taken by the user
        this.backPhoto = new_photo;
    }

    /**
     *Gets the name of the body part
     * @return The name of the body part
     */
    public String returnName(){
        //Returns the body part's name
        return this.name;
    }

    /**
     *Gets the front photo of the body part
     * @return The front photo of the body part
     */
    public String returnFrontPhoto(){
        //Returns the body part's front photo
        return this.frontPhoto;
    }

    /**
     * Gets the back photo of the body part
     * @return The back photo of the body part
     */
    public String returnBackPhoto(){
        //Returns the body part's back photo
        return this.backPhoto;
    }
}
