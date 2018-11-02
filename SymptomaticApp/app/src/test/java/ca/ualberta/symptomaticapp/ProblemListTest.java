package ca.ualberta.symptomaticapp;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class ProblemListTest extends TestCase {

    public void testEmptyProblemList(){
        ProblemList problemList = new ProblemList();
        Collection<Problem> problems = problemList.getProblems();
        assertTrue("Empty Problem List", problems.size() == 0);
    }

    public void testAddProblemList(){
        String problemTitle = "Rash";
        Date problemDate = Calendar.getInstance().getTime();
        String problemComment = "I have a rash";
        Problem problem = new Problem(problemTitle, problemDate, problemComment);
        ProblemList problemList = new ProblemList();
        problemList.addProblem(problem);
        Collection<Problem> problems = problemList.getProblems();
        assertTrue("Problem List Size", problems.size() == 1);
        assertTrue("Problem Not Contained", problems.contains(problem));
    }

    public void testDeleteProblemList(){
        String problemTitle = "Rash";
        Date problemDate = Calendar.getInstance().getTime();
        String problemComment = "I have a rash";
        Problem problem = new Problem(problemTitle, problemDate, problemComment);
        ProblemList problemList = new ProblemList();
        problemList.addProblem(problem);
        Collection<Problem> problems = problemList.getProblems();
        assertTrue("Problem List Size not big enough", problems.size() == 1);
        assertTrue("Problem Not Contained", problems.contains(problem));

        problemList.deleteProblem(problem);
        problems = problemList.getProblems();
        assertTrue("Problem List Size isn't small enough", problems.size() == 0);
        assertFalse("Problem Still Contained", problems.contains(problem));


    }

    


}
