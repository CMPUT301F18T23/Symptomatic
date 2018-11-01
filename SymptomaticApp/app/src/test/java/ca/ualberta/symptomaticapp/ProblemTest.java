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


}
