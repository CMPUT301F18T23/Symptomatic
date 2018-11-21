package ca.ualberta.symptomaticapp;

import junit.framework.TestCase;

public class bodyPartTest extends TestCase {

    /**
     * Tests the creation of a new body part, with a specified name,
     * and ensures the two blank photos are added
     */
    public void testConstructor(){
        bodyPart bodypart = new bodyPart("Chest");

        assertTrue("Name Incorrect", bodypart.returnName().equals("Chest"));
        assertTrue("Default Front Photo Incorrect", bodypart.returnBackPhoto().equals("BLANK"));
        assertTrue("Default Back Photo Incorrect", bodypart.returnFrontPhoto().equals("BLANK"));
    }

    /**
     * Tests the changing of both the front and back photo
     */
    public void testAddPhoto(){
        bodyPart bodypart = new bodyPart("Chest");

        //Add a back photo
        bodypart.changeBackPhoto("new back photo");
        assertTrue("Back Photo Incorrect", bodypart.returnBackPhoto().equals("new back photo"));

        //Add a front photo
        bodypart.changeFrontPhoto("new front photo");
        assertTrue("Front Photo Incorrect", bodypart.returnFrontPhoto().equals("new front photo"));
    }

}