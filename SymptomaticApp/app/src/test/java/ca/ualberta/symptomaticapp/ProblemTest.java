package ca.ualberta.symptomaticapp;

import junit.framework.TestCase;
import ca.ualberta.symptomaticapp.Problem;
import java.util.Calendar;
import java.util.Date;

public class ProblemTest extends TestCase {


    public void testProblemTitle (){
        String problemTitle = "Rash";
        Date problemDate = Calendar.getInstance().getTime();
        String problemComment = "I have a rash";
        Problem problem = new Problem(problemTitle, problemDate, problemComment);
        assertTrue("Problem title is not equal", problemTitle.equals(problem.getTitle()));

    }

    public void testProblemDate(){
        String problemTitle = "Rash";
        Date problemDate = Calendar.getInstance().getTime();
        String problemComment = "I have a rash";
        Problem problem = new Problem(problemTitle, problemDate, problemComment);
        assertTrue("Problem date is not equal", problemDate.equals(problem.getDate()));
    }

    public void testProblemComment() {
        String problemTitle = "Rash";
        Date problemDate = Calendar.getInstance().getTime();
        String problemComment = "I have a rash";
        Problem problem = new Problem(problemTitle, problemDate, problemComment);
        assertTrue("Problem comment is not equal", problemComment.equals(problem.getComment()));

    }

    public void testEditTitle() {
        String problemTitle = "Rash";
        Date problemDate = Calendar.getInstance().getTime();
        String problemComment = "I have a rash";
        Problem problem = new Problem(problemTitle, problemDate, problemComment);
        assertTrue("Problem title is not equal", problemTitle.equals(problem.getTitle()));

        String problemTitle2 = "Rash2";
        problem.setTitle(problemTitle2);
        assertTrue("update Problem title is not equal", problemTitle2.equals(problem.getTitle()));

    }

    public void testEditDate(){
        String problemTitle = "Rash";
        Date problemDate = Calendar.getInstance().getTime();
        String problemComment = "I have a rash";
        Problem problem = new Problem(problemTitle, problemDate, problemComment);
        assertTrue("Problem date is not equal", problemDate.equals(problem.getDate()));

        Date problemDate2 = Calendar.getInstance().getTime();
        problem.setDate(problemDate2);
        assertTrue("update Problem date is not equal", problemDate2.equals(problem.getDate()));

    }

    public void testEditComment(){
        String problemTitle = "Rash";
        Date problemDate = Calendar.getInstance().getTime();
        String problemComment = "I have a rash";
        Problem problem = new Problem(problemTitle, problemDate, problemComment);
        assertTrue("Problem comment is not equal", problemComment.equals(problem.getComment()));

        String problemComment2 = "I have a rash2";
        problem.setComment(problemComment2);
        assertTrue("update Problem date is not equal", problemComment2.equals(problem.getComment()));
    }

    public void testDeleteTitle(){
        String problemTitle = "Rash";
        Date problemDate = Calendar.getInstance().getTime();
        String problemComment = "I have a rash";
        Problem problem = new Problem(problemTitle, problemDate, problemComment);
        assertTrue("Problem title is not equal", problemTitle.equals(problem.getTitle()));

        String problemTitle2 = "";
        problem.setTitle(problemTitle2);
        assertTrue("update Problem title is not equal", problemTitle2.equals(problem.getTitle()));
    }

    public void testDeleteDate(){
        String problemTitle = "Rash";
        Date problemDate = Calendar.getInstance().getTime();
        String problemComment = "I have a rash";
        Problem problem = new Problem(problemTitle, problemDate, problemComment);
        assertTrue("Problem date is not equal", problemDate.equals(problem.getDate()));

        Date problemDate2 = new Date();
        problem.setDate(problemDate2);
        assertTrue("update Problem date is not equal", problemDate2.equals(problem.getDate()));
    }


    public void testDeleteComment(){
        String problemTitle = "Rash";
        Date problemDate = Calendar.getInstance().getTime();
        String problemComment = "I have a rash";
        Problem problem = new Problem(problemTitle, problemDate, problemComment);
        assertTrue("Problem comment is not equal", problemComment.equals(problem.getComment()));

        String problemComment2 = "";
        problem.setComment(problemComment2);
        assertTrue("update Problem date is not equal", problemComment2.equals(problem.getComment()));
    }


}
