/*
 * bodyPartDialog.java
 *
 * Version 2
 *
 * December, 3, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Activity that allows a user to select body parts form a paper doll.
 *
 * Issues:
 *
 */

package ca.ualberta.symptomaticapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class bodyPartDialog{

    private static boolean backRightForearmSelected, leftButtoxSelected, backHeadSelected, backRightFootSelected, backLeftHandSelected, backLeftAnkleSelected, backLeftKneeSelected, leftTricepSelected, backRightShoulderSelected, backLeftFootSelected, backLeftForearmSelected, backLeftShoulderSelected, backRightAnkleSelected, lowerBackSelected, upperBackSelected, rightButtoxSelected, backRightThighSelected, rightTricepSelected, midBackSelected, backLeftCalveSelected, backRightCalveSelected, backRightHandSelected, backRightKneeSelected, backLeftThighSelected;
    private static boolean frontRightHandSelected, leftShinSelected, frontLeftFootSelected, frontRightThighSelected, frontRightFootSelected, frontLeftThighSelected, abdomenSelected, frontRightForearmSelected, frontLeftForearmSelected, upperChestSelected, rightShinSelected, rightBicepSelected, groinSelected, leftBicepSelected, frontLeftKneeSelected, frontLeftHandSelected, faceSelected, frontRightKneeSelected, rightShoulderSelected, leftShoulderSelected;
    private static boolean foreheadSelected, eyesSelected, noseSelected, mouthSelected, chinSelected, rightCheekSelected, leftCheekSelected, rightEarSelected, leftEarSelected, neckSelected;

    Button addBackBodyPart,addFrontBodyPart;

    private static ArrayList<String> bodyPartsSelected;

    Activity thisActivity;

    Boolean clickOk;

    public bodyPartDialog(Activity activity,Boolean click){
        thisActivity = activity;

        bodyPartsSelected = new ArrayList<>();

        clickOk = click;

        backRightForearmSelected = false; leftButtoxSelected = false; backHeadSelected = false; backRightFootSelected = false; backLeftHandSelected = false; backLeftAnkleSelected = false; backLeftKneeSelected = false; leftTricepSelected = false; backRightShoulderSelected = false; backLeftFootSelected = false; backLeftForearmSelected = false; backLeftShoulderSelected = false; backRightAnkleSelected = false; lowerBackSelected = false; upperBackSelected = false; rightButtoxSelected = false; backRightThighSelected = false; rightTricepSelected = false; midBackSelected = false; backLeftCalveSelected = false; backRightCalveSelected = false; backRightHandSelected = false; backRightKneeSelected = false; backLeftThighSelected = false;
        frontRightHandSelected = false; leftShinSelected = false; frontLeftFootSelected = false; frontRightThighSelected = false; frontRightFootSelected = false; frontLeftThighSelected = false; abdomenSelected = false; frontRightForearmSelected = false; frontLeftForearmSelected = false; upperChestSelected = false; rightShinSelected = false; rightBicepSelected = false; groinSelected = false; leftBicepSelected = false; frontLeftKneeSelected = false; frontLeftHandSelected = false; faceSelected = false; frontRightKneeSelected = false; rightShoulderSelected = false; leftShoulderSelected = false;
        foreheadSelected = false; eyesSelected = false; noseSelected = false; mouthSelected = false; chinSelected = false; rightCheekSelected = false; leftCheekSelected = false; rightEarSelected = false; leftEarSelected = false; neckSelected = false;
    }

    public static ArrayList<String> returnPartsSelected(){
        return bodyPartsSelected;
    }

    public void toggleClickable(){
        if(this.clickOk){
            this.clickOk = false;
        } else {
            this.clickOk = true;
        }
    }

    public void frontBodyPartDialog(){
        final Dialog frontBodyPartDialog = new Dialog(thisActivity);
        frontBodyPartDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        frontBodyPartDialog.setContentView(R.layout.body_part_front);
        frontBodyPartDialog.setTitle("Select Body Parts");

        final Button toBack = frontBodyPartDialog.findViewById(R.id.linkToBack);
        toBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backBodyPartDialog();
                frontBodyPartDialog.dismiss();
            }
        });

        final Button close = frontBodyPartDialog.findViewById(R.id.saveclose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontBodyPartDialog.dismiss();
            }
        });

        //Gets a click for the Front Right Hand button
        final ImageButton frontRightHand = frontBodyPartDialog.findViewById(R.id.front_right_hand);
        frontRightHand.setEnabled(true);
        if(frontRightHandSelected){
            frontRightHand.setImageResource(R.drawable.front_right_hand_selected);
        }
        frontRightHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (frontRightHandSelected) {
                        frontRightHand.setImageResource(R.drawable.front_right_hand);
                        frontRightHandSelected = false;
                        bodyPartsSelected.remove("Front Right Hand");
                    } else {
                        frontRightHand.setImageResource(R.drawable.front_right_hand_selected);
                        frontRightHandSelected = true;
                        bodyPartsSelected.add("Front Right Hand");
                    }
                }
            }
        });


//Gets a click for the Left Shin button
        final ImageButton leftShin = frontBodyPartDialog.findViewById(R.id.left_shin);
        leftShin.setEnabled(true);
        if(leftShinSelected){
            leftShin.setImageResource(R.drawable.left_shin_selected);
        }
        leftShin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (leftShinSelected) {
                        leftShin.setImageResource(R.drawable.left_shin);
                        leftShinSelected = false;
                        bodyPartsSelected.remove("Left Shin");
                    } else {
                        leftShin.setImageResource(R.drawable.left_shin_selected);
                        leftShinSelected = true;
                        bodyPartsSelected.add("Left Shin");
                    }
                }
            }
        });


//Gets a click for the Front Left Foot button
        final ImageButton frontLeftFoot = frontBodyPartDialog.findViewById(R.id.front_left_foot);
        frontLeftFoot.setEnabled(true);
        if(frontLeftFootSelected){
            frontLeftFoot.setImageResource(R.drawable.front_left_foot_selected);
        }
        frontLeftFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (frontLeftFootSelected) {
                        frontLeftFoot.setImageResource(R.drawable.front_left_foot);
                        frontLeftFootSelected = false;
                        bodyPartsSelected.remove("Front Left Foot");
                    } else {
                        frontLeftFoot.setImageResource(R.drawable.front_left_foot_selected);
                        frontLeftFootSelected = true;
                        bodyPartsSelected.add("Front Left Foot");
                    }
                }
            }
        });


//Gets a click for the Front Right Thigh button
        final ImageButton frontRightThigh = frontBodyPartDialog.findViewById(R.id.front_right_thigh);
        frontRightThigh.setEnabled(true);
        if(frontRightThighSelected){
            frontRightThigh.setImageResource(R.drawable.front_right_thigh_selected);
        }
        frontRightThigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (frontRightThighSelected) {
                        frontRightThigh.setImageResource(R.drawable.front_right_thigh);
                        frontRightThighSelected = false;
                        bodyPartsSelected.remove("Front Right Thigh");
                    } else {
                        frontRightThigh.setImageResource(R.drawable.front_right_thigh_selected);
                        frontRightThighSelected = true;
                        bodyPartsSelected.add("Front Right Thigh");
                    }
                }
            }
        });


//Gets a click for the Front Right Foot button
        final ImageButton frontRightFoot = frontBodyPartDialog.findViewById(R.id.front_right_foot);
        frontRightFoot.setEnabled(true);
        if(frontRightFootSelected){
            frontRightFoot.setImageResource(R.drawable.front_right_foot_selected);
        }
        frontRightFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (frontRightFootSelected) {
                        frontRightFoot.setImageResource(R.drawable.front_right_foot);
                        frontRightFootSelected = false;
                        bodyPartsSelected.remove("Front Right Foot");
                    } else {
                        frontRightFoot.setImageResource(R.drawable.front_right_foot_selected);
                        frontRightFootSelected = true;
                        bodyPartsSelected.add("Front Right Foot");
                    }
                }
            }
        });


//Gets a click for the Front Left Thigh button
        final ImageButton frontLeftThigh = frontBodyPartDialog.findViewById(R.id.front_left_thigh);
        frontLeftThigh.setEnabled(true);
        if(frontLeftThighSelected){
            frontLeftThigh.setImageResource(R.drawable.front_left_thigh_selected);
        }
        frontLeftThigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (frontLeftThighSelected) {
                        frontLeftThigh.setImageResource(R.drawable.front_left_thigh);
                        frontLeftThighSelected = false;
                        bodyPartsSelected.remove("Front Left Thigh");
                    } else {
                        frontLeftThigh.setImageResource(R.drawable.front_left_thigh_selected);
                        frontLeftThighSelected = true;
                        bodyPartsSelected.add("Front Left Thigh");
                    }
                }
            }
        });


//Gets a click for the Abdomen button
        final ImageButton abdomen = frontBodyPartDialog.findViewById(R.id.abdomen);
        abdomen.setEnabled(true);
        if(abdomenSelected){
            abdomen.setImageResource(R.drawable.abdomen_selected);
        }
        abdomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (abdomenSelected) {
                        abdomen.setImageResource(R.drawable.abdomen);
                        abdomenSelected = false;
                        bodyPartsSelected.remove("Abdomen");
                    } else {
                        abdomen.setImageResource(R.drawable.abdomen_selected);
                        abdomenSelected = true;
                        bodyPartsSelected.add("Abdomen");
                    }
                }
            }
        });


//Gets a click for the Front Right Forearm button
        final ImageButton frontRightForearm = frontBodyPartDialog.findViewById(R.id.front_right_forearm);
        frontRightForearm.setEnabled(true);
        if(frontRightForearmSelected){
            frontRightForearm.setImageResource(R.drawable.front_right_forearm_selected);
        }
        frontRightForearm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (frontRightForearmSelected) {
                        frontRightForearm.setImageResource(R.drawable.front_right_forearm);
                        frontRightForearmSelected = false;
                        bodyPartsSelected.remove("Front Right Forearm");
                    } else {
                        frontRightForearm.setImageResource(R.drawable.front_right_forearm_selected);
                        frontRightForearmSelected = true;
                        bodyPartsSelected.add("Front Right Forearm");
                    }
                }
            }
        });


//Gets a click for the Front Left Forearm button
        final ImageButton frontLeftForearm = frontBodyPartDialog.findViewById(R.id.front_left_forearm);
        frontLeftForearm.setEnabled(true);
        if(frontLeftForearmSelected){
            frontLeftForearm.setImageResource(R.drawable.front_left_forearm_selected);
        }
        frontLeftForearm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (frontLeftForearmSelected) {
                        frontLeftForearm.setImageResource(R.drawable.front_left_forearm);
                        frontLeftForearmSelected = false;
                        bodyPartsSelected.remove("Front Left Forearm");
                    } else {
                        frontLeftForearm.setImageResource(R.drawable.front_left_forearm_selected);
                        frontLeftForearmSelected = true;
                        bodyPartsSelected.add("Front Left Forearm");
                    }
                }
            }
        });


//Gets a click for the Upper Chest button
        final ImageButton upperChest = frontBodyPartDialog.findViewById(R.id.upper_chest);
        upperChest.setEnabled(true);
        if(upperChestSelected){
            upperChest.setImageResource(R.drawable.upper_chest_selected);
        }
        upperChest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (upperChestSelected) {
                        upperChest.setImageResource(R.drawable.upper_chest);
                        upperChestSelected = false;
                        bodyPartsSelected.remove("Upper Chest");
                    } else {
                        upperChest.setImageResource(R.drawable.upper_chest_selected);
                        upperChestSelected = true;
                        bodyPartsSelected.add("Upper Chest");
                    }
                }
            }
        });


//Gets a click for the Right Shin button
        final ImageButton rightShin = frontBodyPartDialog.findViewById(R.id.right_shin);
        rightShin.setEnabled(true);
        if(rightShinSelected){
            rightShin.setImageResource(R.drawable.right_shin_selected);
        }
        rightShin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (rightShinSelected) {
                        rightShin.setImageResource(R.drawable.right_shin);
                        rightShinSelected = false;
                        bodyPartsSelected.remove("Right Shin");
                    } else {
                        rightShin.setImageResource(R.drawable.right_shin_selected);
                        rightShinSelected = true;
                        bodyPartsSelected.add("Right Shin");
                    }
                }
            }
        });


//Gets a click for the Right Bicep button
        final ImageButton rightBicep = frontBodyPartDialog.findViewById(R.id.right_bicep);
        rightBicep.setEnabled(true);
        if(rightBicepSelected){
            rightBicep.setImageResource(R.drawable.right_bicep_selected);
        }
        rightBicep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (rightBicepSelected) {
                        rightBicep.setImageResource(R.drawable.right_bicep);
                        rightBicepSelected = false;
                        bodyPartsSelected.remove("Right Bicep");
                    } else {
                        rightBicep.setImageResource(R.drawable.right_bicep_selected);
                        rightBicepSelected = true;
                        bodyPartsSelected.add("Right Bicep");
                    }
                }
            }
        });


//Gets a click for the Groin button
        final ImageButton groin = frontBodyPartDialog.findViewById(R.id.groin);
        groin.setEnabled(true);
        if(groinSelected){
            groin.setImageResource(R.drawable.groin_selected);
        }
        groin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (groinSelected) {
                        groin.setImageResource(R.drawable.groin);
                        groinSelected = false;
                        bodyPartsSelected.remove("Groin");
                    } else {
                        groin.setImageResource(R.drawable.groin_selected);
                        groinSelected = true;
                        bodyPartsSelected.add("Groin");
                    }
                }
            }
        });


//Gets a click for the Left Bicep button
        final ImageButton leftBicep = frontBodyPartDialog.findViewById(R.id.left_bicep);
        leftBicep.setEnabled(true);
        if(leftBicepSelected){
            leftBicep.setImageResource(R.drawable.left_bicep_selected);
        }
        leftBicep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (leftBicepSelected) {
                        leftBicep.setImageResource(R.drawable.left_bicep);
                        leftBicepSelected = false;
                        bodyPartsSelected.remove("Left Bicep");
                    } else {
                        leftBicep.setImageResource(R.drawable.left_bicep_selected);
                        leftBicepSelected = true;
                        bodyPartsSelected.add("Left Bicep");
                    }
                }
            }
        });


//Gets a click for the Front Left Knee button
        final ImageButton frontLeftKnee = frontBodyPartDialog.findViewById(R.id.front_left_knee);
        frontLeftKnee.setEnabled(true);
        if(frontLeftKneeSelected){
            frontLeftKnee.setImageResource(R.drawable.front_left_knee_selected);
        }
        frontLeftKnee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickOk) {
                    if (frontLeftKneeSelected) {
                        frontLeftKnee.setImageResource(R.drawable.front_left_knee);
                        frontLeftKneeSelected = false;
                        bodyPartsSelected.remove("Front Left Knee");
                    } else {
                        frontLeftKnee.setImageResource(R.drawable.front_left_knee_selected);
                        frontLeftKneeSelected = true;
                        bodyPartsSelected.add("Front Left Knee");
                    }
                }
            }
        });


//Gets a click for the Front Left Hand button
        final ImageButton frontLeftHand = frontBodyPartDialog.findViewById(R.id.front_left_hand);
        frontLeftHand.setEnabled(true);
        if(frontLeftHandSelected){
            frontLeftHand.setImageResource(R.drawable.front_left_hand_selected);
        }
        frontLeftHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (frontLeftHandSelected) {
                        frontLeftHand.setImageResource(R.drawable.front_left_hand);
                        frontLeftHandSelected = false;
                        bodyPartsSelected.remove("Front Left Hand");
                    } else {
                        frontLeftHand.setImageResource(R.drawable.front_left_hand_selected);
                        frontLeftHandSelected = true;
                        bodyPartsSelected.add("Front Left Hand");
                    }
                }
            }
        });


//Gets a click for the Face button
        final ImageButton face = frontBodyPartDialog.findViewById(R.id.face);
        face.setEnabled(true);
        if((foreheadSelected || eyesSelected || noseSelected || mouthSelected || chinSelected || rightCheekSelected || leftCheekSelected || rightEarSelected || leftEarSelected || neckSelected)){
            face.setImageResource(R.drawable.face_selected);
        } else {
            face.setImageResource(R.drawable.face);

        }
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                faceDialog();
                frontBodyPartDialog.dismiss();
            }
        });


//Gets a click for the Front Right Knee button
        final ImageButton frontRightKnee = frontBodyPartDialog.findViewById(R.id.front_right_knee);
        frontRightKnee.setEnabled(true);
        if(frontRightKneeSelected){
            frontRightKnee.setImageResource(R.drawable.front_right_knee_selected);
        }
        frontRightKnee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (frontRightKneeSelected) {
                        frontRightKnee.setImageResource(R.drawable.front_right_knee);
                        frontRightKneeSelected = false;
                        bodyPartsSelected.remove("Front Right Knee");
                    } else {
                        frontRightKnee.setImageResource(R.drawable.front_right_knee_selected);
                        frontRightKneeSelected = true;
                        bodyPartsSelected.add("Front Right Knee");
                    }
                }
            }
        });


//Gets a click for the Right Shoulder button
        final ImageButton rightShoulder = frontBodyPartDialog.findViewById(R.id.front_right_shoulder);
        rightShoulder.setEnabled(true);
        if(rightShoulderSelected){
            rightShoulder.setImageResource(R.drawable.front_right_shoulder_selected);
        }
        rightShoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (rightShoulderSelected) {
                        rightShoulder.setImageResource(R.drawable.front_right_shoulder);
                        rightShoulderSelected = false;
                        bodyPartsSelected.remove("Right Shoulder");
                    } else {
                        rightShoulder.setImageResource(R.drawable.front_right_shoulder_selected);
                        rightShoulderSelected = true;
                        bodyPartsSelected.add("Right Shoulder");
                    }
                }
            }
        });


//Gets a click for the Left Shoulder button
        final ImageButton leftShoulder = frontBodyPartDialog.findViewById(R.id.front_left_shoulder);
        leftShoulder.setEnabled(true);
        if(leftShoulderSelected){
            leftShoulder.setImageResource(R.drawable.front_left_shoulder_selected);
        }
        leftShoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (leftShoulderSelected) {
                        leftShoulder.setImageResource(R.drawable.front_left_shoulder);
                        leftShoulderSelected = false;
                        bodyPartsSelected.remove("Left Shoulder");
                    } else {
                        leftShoulder.setImageResource(R.drawable.front_left_shoulder_selected);
                        leftShoulderSelected = true;
                        bodyPartsSelected.add("Left Shoulder");
                    }
                }
            }
        });

        frontBodyPartDialog.show();
    }

    public void backBodyPartDialog() {
        final Dialog backBodyPartDialog = new Dialog(thisActivity);
        backBodyPartDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        backBodyPartDialog.setContentView(R.layout.body_part_back);
        backBodyPartDialog.setTitle("Select Back Body Parts");

        final Button toFront = backBodyPartDialog.findViewById(R.id.linkToFront);
        toFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontBodyPartDialog();
                backBodyPartDialog.dismiss();
            }
        });

        final Button close = backBodyPartDialog.findViewById(R.id.saveclose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backBodyPartDialog.dismiss();
            }
        });

        //Gets a click for the Back Right Forearm button
        final ImageButton backRightForearm = backBodyPartDialog.findViewById(R.id.back_right_forearm);
        backRightForearm.setEnabled(true);
        if(backRightForearmSelected){
            backRightForearm.setImageResource(R.drawable.back_right_forearm_selected);
        }
        backRightForearm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backRightForearmSelected) {
                        backRightForearm.setImageResource(R.drawable.back_right_forearm);
                        backRightForearmSelected = false;
                        bodyPartsSelected.remove("Back Right Forearm");
                    } else {
                        backRightForearm.setImageResource(R.drawable.back_right_forearm_selected);
                        backRightForearmSelected = true;
                        bodyPartsSelected.add("Back Right Forearm");
                    }
                }
            }
        });


//Gets a click for the Left Buttox button
        final ImageButton leftButtox = backBodyPartDialog.findViewById(R.id.left_buttox);
        leftButtox.setEnabled(true);
        if(leftButtoxSelected){
            leftButtox.setImageResource(R.drawable.left_buttox_selected);
        }
        leftButtox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (leftButtoxSelected) {
                        leftButtox.setImageResource(R.drawable.back_left_buttox);
                        leftButtoxSelected = false;
                        bodyPartsSelected.remove("Left Buttox");
                    } else {
                        leftButtox.setImageResource(R.drawable.left_buttox_selected);
                        leftButtoxSelected = true;
                        bodyPartsSelected.add("Left Buttox");
                    }
                }
            }
        });


//Gets a click for the Back Head button
        final ImageButton backHead = backBodyPartDialog.findViewById(R.id.back_head);
        backHead.setEnabled(true);
        if(backHeadSelected){
            backHead.setImageResource(R.drawable.back_head_selected);
        }
        backHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backHeadSelected) {
                        backHead.setImageResource(R.drawable.back_head);
                        backHeadSelected = false;
                        bodyPartsSelected.remove("Back Head");
                    } else {
                        backHead.setImageResource(R.drawable.back_head_selected);
                        backHeadSelected = true;
                        bodyPartsSelected.add("Back Head");
                    }
                }
            }
        });


//Gets a click for the Back Right Foot button
        final ImageButton backRightFoot = backBodyPartDialog.findViewById(R.id.back_right_foot);
        backRightFoot.setEnabled(true);
        if(backRightFootSelected){
            backRightFoot.setImageResource(R.drawable.back_right_foot_selected);
        }
        backRightFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backRightFootSelected) {
                        backRightFoot.setImageResource(R.drawable.back_right_foot);
                        backRightFootSelected = false;
                        bodyPartsSelected.remove("Back Right Foot");
                    } else {
                        backRightFoot.setImageResource(R.drawable.back_right_foot_selected);
                        backRightFootSelected = true;
                        bodyPartsSelected.add("Back Right Foot");
                    }
                }
            }
        });


//Gets a click for the Back Left Hand button
        final ImageButton backLeftHand = backBodyPartDialog.findViewById(R.id.back_left_hand);
        backLeftHand.setEnabled(true);
        if(backLeftHandSelected){
            backLeftHand.setImageResource(R.drawable.back_left_hand_selected);
        }
        backLeftHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backLeftHandSelected) {
                        backLeftHand.setImageResource(R.drawable.back_left_hand);
                        backLeftHandSelected = false;
                        bodyPartsSelected.remove("Back Left Hand");
                    } else {
                        backLeftHand.setImageResource(R.drawable.back_left_hand_selected);
                        backLeftHandSelected = true;
                        bodyPartsSelected.add("Back Left Hand");
                    }
                }
            }
        });


        //Gets a click for the Back Left Ankle button
        final ImageButton backLeftAnkle = backBodyPartDialog.findViewById(R.id.back_left_ankle);
        backLeftAnkle.setEnabled(true);
        if(backLeftAnkleSelected){
            backLeftAnkle.setImageResource(R.drawable.back_left_ankle_selected);
        }
        backLeftAnkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backLeftAnkleSelected) {
                        backLeftAnkle.setImageResource(R.drawable.back_left_ankle);
                        backLeftAnkleSelected = false;
                        bodyPartsSelected.remove("Back Left Ankle");
                    } else {
                        backLeftAnkle.setImageResource(R.drawable.back_left_ankle_selected);
                        backLeftAnkleSelected = true;
                        bodyPartsSelected.add("Back Left Ankle");
                    }
                }
            }
        });


        //Gets a click for the Back Left Knee button
        final ImageButton backLeftKnee = backBodyPartDialog.findViewById(R.id.back_left_knee);
        backLeftKnee.setEnabled(true);
        if(backLeftKneeSelected){
            backLeftKnee.setImageResource(R.drawable.back_left_knee_selected);
        }
        backLeftKnee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backLeftKneeSelected) {
                        backLeftKnee.setImageResource(R.drawable.back_left_knee);
                        backLeftKneeSelected = false;
                        bodyPartsSelected.remove("Back Left Knee");
                    } else {
                        backLeftKnee.setImageResource(R.drawable.back_left_knee_selected);
                        backLeftKneeSelected = true;
                        bodyPartsSelected.add("Back Left Knee");
                    }
                }
            }
        });


        //Gets a click for the Left Tricep button
        final ImageButton leftTricep = backBodyPartDialog.findViewById(R.id.left_tricep);
        leftTricep.setEnabled(true);
        if(leftTricepSelected){
            leftTricep.setImageResource(R.drawable.left_tricep_selected);
        }
        leftTricep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (leftTricepSelected) {
                        leftTricep.setImageResource(R.drawable.left_tricep);
                        leftTricepSelected = false;
                        bodyPartsSelected.remove("Left Tricep");
                    } else {
                        leftTricep.setImageResource(R.drawable.left_tricep_selected);
                        leftTricepSelected = true;
                        bodyPartsSelected.add("Left Tricep");
                    }
                }
            }
        });


        //Gets a click for the Back Right Shoulder button
        final ImageButton backRightShoulder = backBodyPartDialog.findViewById(R.id.back_right_shoulder);
        backRightShoulder.setEnabled(true);
        if(backRightShoulderSelected){
            backRightShoulder.setImageResource(R.drawable.back_right_shoulder_selected);
        }
        backRightShoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backRightShoulderSelected) {
                        backRightShoulder.setImageResource(R.drawable.back_right_shoulder);
                        backRightShoulderSelected = false;
                        bodyPartsSelected.remove("Back Right Shoulder");
                    } else {
                        backRightShoulder.setImageResource(R.drawable.back_right_shoulder_selected);
                        backRightShoulderSelected = true;
                        bodyPartsSelected.add("Back Right Shoulder");
                    }
                }
            }
        });


        //Gets a click for the Back Left Foot button
        final ImageButton backLeftFoot = backBodyPartDialog.findViewById(R.id.back_left_foot);
        backLeftFoot.setEnabled(true);
        if(backLeftFootSelected){
            backLeftFoot.setImageResource(R.drawable.back_left_foot_selected);
        }
        backLeftFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backLeftFootSelected) {
                        backLeftFoot.setImageResource(R.drawable.back_left_foot);
                        backLeftFootSelected = false;
                        bodyPartsSelected.remove("Back Left Foot");
                    } else {
                        backLeftFoot.setImageResource(R.drawable.back_left_foot_selected);
                        backLeftFootSelected = true;
                        bodyPartsSelected.add("Back Left Foot");
                    }
                }
            }
        });


        //Gets a click for the Back Left Forearm button
        final ImageButton backLeftForearm = backBodyPartDialog.findViewById(R.id.back_left_forearm);
        backLeftForearm.setEnabled(true);
        if(backLeftForearmSelected){
            backLeftForearm.setImageResource(R.drawable.back_left_forearm_selected);
        }
        backLeftForearm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backLeftForearmSelected) {
                        backLeftForearm.setImageResource(R.drawable.back_left_forearm);
                        backLeftForearmSelected = false;
                        bodyPartsSelected.remove("Back Left Forearm");
                    } else {
                        backLeftForearm.setImageResource(R.drawable.back_left_forearm_selected);
                        backLeftForearmSelected = true;
                        bodyPartsSelected.add("Back Left Forearm");
                    }
                }
            }
        });


        //Gets a click for the Back Left Shoulder button
        final ImageButton backLeftShoulder = backBodyPartDialog.findViewById(R.id.back_left_shoulder);
        backLeftShoulder.setEnabled(true);
        if(backLeftShoulderSelected){
            backLeftShoulder.setImageResource(R.drawable.back_left_shoulder_selected);
        }
        backLeftShoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backLeftShoulderSelected) {
                        backLeftShoulder.setImageResource(R.drawable.back_left_shoulder);
                        backLeftShoulderSelected = false;
                        bodyPartsSelected.remove("Back Left Shoulder");
                    } else {
                        backLeftShoulder.setImageResource(R.drawable.back_left_shoulder_selected);
                        backLeftShoulderSelected = true;
                        bodyPartsSelected.add("Back Left Shoulder");
                    }
                }
            }
        });


        //Gets a click for the Back Right Ankle button
        final ImageButton backRightAnkle = backBodyPartDialog.findViewById(R.id.back_right_ankle);
        backRightAnkle.setEnabled(true);
        if(backRightAnkleSelected){
            backRightAnkle.setImageResource(R.drawable.back_right_ankle_selected);
        }
        backRightAnkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backRightAnkleSelected) {
                        backRightAnkle.setImageResource(R.drawable.back_right_ankle);
                        backRightAnkleSelected = false;
                        bodyPartsSelected.remove("Back Right Ankle");
                    } else {
                        backRightAnkle.setImageResource(R.drawable.back_right_ankle_selected);
                        backRightAnkleSelected = true;
                        bodyPartsSelected.add("Back Right Ankle");
                    }
                }
            }
        });


        //Gets a click for the Lower Back button
        final ImageButton lowerBack = backBodyPartDialog.findViewById(R.id.lower_back);
        lowerBack.setEnabled(true);
        if(lowerBackSelected){
            lowerBack.setImageResource(R.drawable.lower_back_selected);
        }
        lowerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (lowerBackSelected) {
                        lowerBack.setImageResource(R.drawable.lower_back);
                        lowerBackSelected = false;
                        bodyPartsSelected.remove("Lower Back");
                    } else {
                        lowerBack.setImageResource(R.drawable.lower_back_selected);
                        lowerBackSelected = true;
                        bodyPartsSelected.add("Lower Back");
                    }
                }
            }
        });


        //Gets a click for the Upper Back button
        final ImageButton upperBack = backBodyPartDialog.findViewById(R.id.upper_back);
        upperBack.setEnabled(true);
        if(upperBackSelected){
            upperBack.setImageResource(R.drawable.upper_back_selected);
        }
        upperBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (upperBackSelected) {
                        upperBack.setImageResource(R.drawable.upper_back);
                        upperBackSelected = false;
                        bodyPartsSelected.remove("Upper Back");
                    } else {
                        upperBack.setImageResource(R.drawable.upper_back_selected);
                        upperBackSelected = true;
                        bodyPartsSelected.add("Upper Back");
                    }
                }
            }
        });


        //Gets a click for the Right Buttox button
        final ImageButton rightButtox = backBodyPartDialog.findViewById(R.id.right_buttox);
        rightButtox.setEnabled(true);
        if(rightButtoxSelected){
            rightButtox.setImageResource(R.drawable.right_buttox_selected);
        }
        rightButtox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (rightButtoxSelected) {
                        rightButtox.setImageResource(R.drawable.back_right_buttox);
                        rightButtoxSelected = false;
                        bodyPartsSelected.remove("Right Buttox");
                    } else {
                        rightButtox.setImageResource(R.drawable.right_buttox_selected);
                        rightButtoxSelected = true;
                        bodyPartsSelected.add("Right Buttox");
                    }
                }
            }
        });


        //Gets a click for the Back Right Thigh button
        final ImageButton backRightThigh = backBodyPartDialog.findViewById(R.id.back_right_thigh);
        backRightThigh.setEnabled(true);
        if(backRightThighSelected){
            backRightThigh.setImageResource(R.drawable.back_right_thigh_selected);
        }
        backRightThigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backRightThighSelected) {
                        backRightThigh.setImageResource(R.drawable.back_right_thigh);
                        backRightThighSelected = false;
                        bodyPartsSelected.remove("Back Right Thigh");
                    } else {
                        backRightThigh.setImageResource(R.drawable.back_right_thigh_selected);
                        backRightThighSelected = true;
                        bodyPartsSelected.add("Back Right Thigh");
                    }
                }
            }
        });


        //Gets a click for the Right Tricep button
        final ImageButton rightTricep = backBodyPartDialog.findViewById(R.id.right_tricep);
        rightTricep.setEnabled(true);
        if(rightTricepSelected){
            rightTricep.setImageResource(R.drawable.right_tricep_selected);
        }
        rightTricep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickOk) {
                    if (rightTricepSelected) {
                        rightTricep.setImageResource(R.drawable.right_tricep);
                        rightTricepSelected = false;
                        bodyPartsSelected.remove("Right Tricep");
                    } else {
                        rightTricep.setImageResource(R.drawable.right_tricep_selected);
                        rightTricepSelected = true;
                        bodyPartsSelected.add("Right Tricep");
                    }
                }
            }
        });


        //Gets a click for the Mid Back button
        final ImageButton midBack = backBodyPartDialog.findViewById(R.id.mid_back);
        midBack.setEnabled(true);
        if(midBackSelected){
            midBack.setImageResource(R.drawable.mid_back_selected);
        }
        midBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (midBackSelected) {
                        midBack.setImageResource(R.drawable.mid_back);
                        midBackSelected = false;
                        bodyPartsSelected.remove("Mid Back");
                    } else {
                        midBack.setImageResource(R.drawable.mid_back_selected);
                        midBackSelected = true;
                        bodyPartsSelected.add("Mid Back");
                    }
                }
            }
        });


        //Gets a click for the Back Left Calve button
        final ImageButton backLeftCalve = backBodyPartDialog.findViewById(R.id.back_left_calve);
        backLeftCalve.setEnabled(true);
        if(backLeftCalveSelected){
            backLeftCalve.setImageResource(R.drawable.back_left_calve_selected);
        }
        backLeftCalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backLeftCalveSelected) {
                        backLeftCalve.setImageResource(R.drawable.back_left_calve);
                        backLeftCalveSelected = false;
                        bodyPartsSelected.remove("Back Left Calve");
                    } else {
                        backLeftCalve.setImageResource(R.drawable.back_left_calve_selected);
                        backLeftCalveSelected = true;
                        bodyPartsSelected.add("Back Left Calve");
                    }
                }
            }
        });


        //Gets a click for the Back Right Calve button
        final ImageButton backRightCalve = backBodyPartDialog.findViewById(R.id.back_right_calve);
        backRightCalve.setEnabled(true);
        if(backRightCalveSelected){
            backRightCalve.setImageResource(R.drawable.back_right_calve_selected);
        }
        backRightCalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backRightCalveSelected) {
                        backRightCalve.setImageResource(R.drawable.back_right_calve);
                        backRightCalveSelected = false;
                        bodyPartsSelected.remove("Back Right Calve");
                    } else {
                        backRightCalve.setImageResource(R.drawable.back_right_calve_selected);
                        backRightCalveSelected = true;
                        bodyPartsSelected.add("Back Right Calve");
                    }
                }
            }
        });


        //Gets a click for the Back Right Hand button
        final ImageButton backRightHand = backBodyPartDialog.findViewById(R.id.back_right_hand);
        backRightHand.setEnabled(true);
        if(backRightHandSelected){
            backRightHand.setImageResource(R.drawable.back_right_hand_selected);
        }
        backRightHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backRightHandSelected) {
                        backRightHand.setImageResource(R.drawable.back_right_hand);
                        backRightHandSelected = false;
                        bodyPartsSelected.remove("Back Right Hand");
                    } else {
                        backRightHand.setImageResource(R.drawable.back_right_hand_selected);
                        backRightHandSelected = true;
                        bodyPartsSelected.add("Back Right Hand");
                    }
                }
            }
        });


        //Gets a click for the Back Right Knee button
        final ImageButton backRightKnee = backBodyPartDialog.findViewById(R.id.back_right_knee);
        backRightKnee.setEnabled(true);
        if(backRightKneeSelected){
            backRightKnee.setImageResource(R.drawable.back_right_knee_selected);
        }
        backRightKnee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backRightKneeSelected) {
                        backRightKnee.setImageResource(R.drawable.back_right_knee);
                        backRightKneeSelected = false;
                        bodyPartsSelected.remove("Back Right Knee");
                    } else {
                        backRightKnee.setImageResource(R.drawable.back_right_knee_selected);
                        backRightKneeSelected = true;
                        bodyPartsSelected.add("Back Right Knee");
                    }
                }
            }
        });


        //Gets a click for the Back Left Thigh button
        final ImageButton backLeftThigh = backBodyPartDialog.findViewById(R.id.back_left_thigh);
        backLeftThigh.setEnabled(true);
        if(backLeftThighSelected){
            backLeftThigh.setImageResource(R.drawable.back_left_thigh_selected);
        }
        backLeftThigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (backLeftThighSelected) {
                        backLeftThigh.setImageResource(R.drawable.back_left_thigh);
                        backLeftThighSelected = false;
                        bodyPartsSelected.remove("Back Left Thigh");
                    } else {
                        backLeftThigh.setImageResource(R.drawable.back_left_thigh_selected);
                        backLeftThighSelected = true;
                        bodyPartsSelected.add("Back Left Thigh");
                    }
                }
            }
        });

        backBodyPartDialog.show();
    }

    private void faceDialog() {
        final Dialog faceDialog = new Dialog(thisActivity);
        faceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        faceDialog.setContentView(R.layout.face);
        faceDialog.setTitle("Select Face Parts");

        final Button close = faceDialog.findViewById(R.id.faceSave);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontBodyPartDialog();
                faceDialog.dismiss();
            }
        });

        //Gets a click for the Right Ear button
        final ImageButton rightEar = faceDialog.findViewById(R.id.right_ear);
        rightEar.setEnabled(true);
        if(rightEarSelected){
            rightEar.setImageResource(R.drawable.right_ear_selected);
        }
        rightEar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (rightEarSelected) {
                        rightEar.setImageResource(R.drawable.right_ear);
                        rightEarSelected = false;
                        bodyPartsSelected.remove("Right Ear");
                    } else {
                        rightEar.setImageResource(R.drawable.right_ear_selected);
                        rightEarSelected = true;
                        bodyPartsSelected.add("Right Ear");
                    }
                }
            }
        });


//Gets a click for the Mouth button
        final ImageButton mouth = faceDialog.findViewById(R.id.mouth);
        mouth.setEnabled(true);
        if(mouthSelected){
            mouth.setImageResource(R.drawable.mouth_selected);
        }
        mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (mouthSelected) {
                        mouth.setImageResource(R.drawable.mouth);
                        mouthSelected = false;
                        bodyPartsSelected.remove("Mouth");
                    } else {
                        mouth.setImageResource(R.drawable.mouth_selected);
                        mouthSelected = true;
                        bodyPartsSelected.add("Mouth");
                    }
                }
            }
        });


//Gets a click for the Nose button
        final ImageButton nose = faceDialog.findViewById(R.id.nose);
        nose.setEnabled(true);
        if(noseSelected){
            nose.setImageResource(R.drawable.nose_selected);
        }
        nose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (noseSelected) {
                        nose.setImageResource(R.drawable.nose);
                        noseSelected = false;
                        bodyPartsSelected.remove("Nose");
                    } else {
                        nose.setImageResource(R.drawable.nose_selected);
                        noseSelected = true;
                        bodyPartsSelected.add("Nose");
                    }
                }
            }
        });


//Gets a click for the Right Cheek button
        final ImageButton rightCheek = faceDialog.findViewById(R.id.right_cheek);
        rightCheek.setEnabled(true);
        if(rightCheekSelected){
            rightCheek.setImageResource(R.drawable.right_cheek_selected);
        }
        rightCheek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (rightCheekSelected) {
                        rightCheek.setImageResource(R.drawable.right_cheek);
                        rightCheekSelected = false;
                        bodyPartsSelected.remove("Right Cheek");
                    } else {
                        rightCheek.setImageResource(R.drawable.right_cheek_selected);
                        rightCheekSelected = true;
                        bodyPartsSelected.add("Right Cheek");
                    }
                }
            }
        });


//Gets a click for the Neck button
        final ImageButton neck = faceDialog.findViewById(R.id.neck);
        neck.setEnabled(true);
        if(neckSelected){
            neck.setImageResource(R.drawable.neck_selected);
        }
        neck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (neckSelected) {
                        neck.setImageResource(R.drawable.neck);
                        neckSelected = false;
                        bodyPartsSelected.remove("Neck");
                    } else {
                        neck.setImageResource(R.drawable.neck_selected);
                        neckSelected = true;
                        bodyPartsSelected.add("Neck");
                    }
                }
            }
        });


//Gets a click for the Chin button
        final ImageButton chin = faceDialog.findViewById(R.id.chin);
        chin.setEnabled(true);
        if(chinSelected){
            chin.setImageResource(R.drawable.chin_selected);
        }
        chin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (chinSelected) {
                        chin.setImageResource(R.drawable.chin);
                        chinSelected = false;
                        bodyPartsSelected.remove("Chin");
                    } else {
                        chin.setImageResource(R.drawable.chin_selected);
                        chinSelected = true;
                        bodyPartsSelected.add("Chin");
                    }
                }
            }
        });


//Gets a click for the Eyes button
        final ImageButton eyes = faceDialog.findViewById(R.id.eyes);
        eyes.setEnabled(true);
        if(eyesSelected){
            eyes.setImageResource(R.drawable.eyes_selected);
        }
        eyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickOk) {
                    if (eyesSelected) {
                        eyes.setImageResource(R.drawable.eyes);
                        eyesSelected = false;
                        bodyPartsSelected.remove("Eyes");
                    } else {
                        eyes.setImageResource(R.drawable.eyes_selected);
                        eyesSelected = true;
                        bodyPartsSelected.add("Eyes");
                    }
                }
            }
        });


//Gets a click for the Forehead button
        final ImageButton forehead = faceDialog.findViewById(R.id.forehead);
        forehead.setEnabled(true);
        if(foreheadSelected){
            forehead.setImageResource(R.drawable.forehead_selected);
        }
        forehead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (foreheadSelected) {
                        forehead.setImageResource(R.drawable.forehead);
                        foreheadSelected = false;
                        bodyPartsSelected.remove("Forehead");
                    } else {
                        forehead.setImageResource(R.drawable.forehead_selected);
                        foreheadSelected = true;
                        bodyPartsSelected.add("Forehead");
                    }
                }
            }
        });


//Gets a click for the Left Cheek button
        final ImageButton leftCheek = faceDialog.findViewById(R.id.left_cheek);
        leftCheek.setEnabled(true);
        if(leftCheekSelected){
            leftCheek.setImageResource(R.drawable.left_cheek_selected);
        }
        leftCheek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (leftCheekSelected) {
                        leftCheek.setImageResource(R.drawable.left_cheek);
                        leftCheekSelected = false;
                        bodyPartsSelected.remove("Left Cheek");
                    } else {
                        leftCheek.setImageResource(R.drawable.left_cheek_selected);
                        leftCheekSelected = true;
                        bodyPartsSelected.add("Left Cheek");
                    }
                }
            }
        });


//Gets a click for the Left Ear button
        final ImageButton leftEar = faceDialog.findViewById(R.id.left_ear);
        leftEar.setEnabled(true);
        if(leftEarSelected){
            leftEar.setImageResource(R.drawable.left_ear_selected);
        }
        leftEar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clickOk) {
                    if (leftEarSelected) {
                        leftEar.setImageResource(R.drawable.left_ear);
                        leftEarSelected = false;
                        bodyPartsSelected.remove("Left Ear");
                    } else {
                        leftEar.setImageResource(R.drawable.left_ear_selected);
                        leftEarSelected = true;
                        bodyPartsSelected.add("Left Ear");
                    }
                }
            }
        });

        faceDialog.show();


    }

    public void readList(ArrayList<String> bodyparts){

        bodyPartDialog.bodyPartsSelected = bodyparts;

        for (String location: bodyparts){
            if("Front Right Hand".equals(location)){
                frontRightHandSelected = true;
            } else if("Left Shin".equals(location)){
                frontRightHandSelected = true;
            } else if("Front Left Foot".equals(location)){
                frontLeftFootSelected = true;
            } else if("Front Right Thigh".equals(location)){
                frontRightThighSelected = true;
            } else if("Front Right Foot".equals(location)){
                frontRightFootSelected = true;
            } else if("Front Left Thigh".equals(location)){
                frontLeftThighSelected = true;
            } else if("Abdomen".equals(location)){
                abdomenSelected = true;
            } else if("Front Right Forearm".equals(location)){
                frontRightForearmSelected = true;
            } else if("Front Left Forearm".equals(location)){
                frontLeftForearmSelected = true;
            } else if("Upper Chest".equals(location)){
                upperChestSelected = true;
            } else if("Right Shin".equals(location)){
                rightShinSelected = true;
            } else if("Right Bicep".equals(location)){
                rightBicepSelected = true;
            } else if("Groin".equals(location)){
                groinSelected = true;
            } else if("Left Bicep".equals(location)){
                leftBicepSelected = true;
            } else if("Front Left Knee".equals(location)){
                frontLeftKneeSelected = true;
            } else if("Front Left Hand".equals(location)){
                frontLeftHandSelected = true;
            } else if("Front Right Knee".equals(location)){
                frontRightKneeSelected = true;
            } else if("Right Shoulder".equals(location)){
                rightShoulderSelected = true;
            } else if("Left Shoulder".equals(location)){
                leftShoulderSelected = true;
            } else if ("Right Ear".equals(location)) {
                rightEarSelected = true;
            } else if ("Mouth".equals(location)) {
                mouthSelected = true;
            } else if ("Nose".equals(location)) {
                noseSelected = true;
            } else if ("Right Cheek".equals(location)) {
                rightCheekSelected = true;
            } else if ("Neck".equals(location)) {
                neckSelected = true;
            } else if ("Chin".equals(location)) {
                chinSelected = true;
            } else if ("Eyes".equals(location)) {
                eyesSelected = true;
            } else if ("Forehead".equals(location)) {
                foreheadSelected = true;
            } else if ("Left Cheek".equals(location)) {
                leftCheekSelected = true;
            } else if ("Left Ear".equals(location)) {
                leftEarSelected = true;
            } else if("Back Right Forearm".equals(location)){
                backRightForearmSelected = true;
            } else if("Left Buttox".equals(location)){
                leftButtoxSelected = true;
            } else if("Back Head".equals(location)){
                backHeadSelected = true;
            } else if("Back Right Foot".equals(location)){
                backRightFootSelected = true;
            } else if("Back Left Hand".equals(location)){
                backLeftHandSelected = true;
            } else if("Back Left Ankle".equals(location)){
                backLeftAnkleSelected = true;
            } else if("Back Left Knee".equals(location)){
                backLeftKneeSelected = true;
            } else if("Left Tricep".equals(location)){
                leftTricepSelected = true;
            } else if("Back Right Shoulder".equals(location)){
                backRightShoulderSelected = true;
            } else if("Back Left Foot".equals(location)){
                backLeftFootSelected = true;
            } else if("Back Left Forearm".equals(location)){
                backLeftForearmSelected = true;
            } else if("Back Left Shoulder".equals(location)){
                backLeftShoulderSelected = true;
            } else if("Back Right Ankle".equals(location)){
                backRightAnkleSelected = true;
            } else if("Lower Back".equals(location)){
                lowerBackSelected = true;
            } else if("Upper Back".equals(location)){
                upperBackSelected = true;
            } else if("Right Buttox".equals(location)){
                rightButtoxSelected = true;
            } else if("Back Right Thigh".equals(location)){
                backRightThighSelected = true;
            } else if("Right Tricep".equals(location)){
                rightTricepSelected = true;
            } else if("Mid Back".equals(location)){
                midBackSelected = true;
            } else if("Back Left Calve".equals(location)){
                backLeftCalveSelected = true;
            } else if("Back Right Calve".equals(location)){
                backRightCalveSelected = true;
            } else if("Back Right Hand".equals(location)){
                backRightHandSelected = true;
            } else if("Back Right Knee".equals(location)){
                backRightKneeSelected = true;
            } else if("Back Left Thigh".equals(location)){
                backLeftThighSelected = true;
            }

        }
    }

}
