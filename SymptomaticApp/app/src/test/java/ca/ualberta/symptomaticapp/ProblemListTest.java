package ca.ualberta.symptomaticapp;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class ProblemListTest extends TestCase {

    public void testConstructor(){
        ProblemList problemList = new ProblemList();
        assertTrue("Not constructed", problemList != null);

    }

    public void testEmptyProblemList(){
        ProblemList problemList = new ProblemList();
        Collection<Problem> problems = problemList.getProblems();
        assertTrue("Empty Problem List", problems.size() == 0);
    }

    public void testAddProblem(){
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

    public void testDeleteProblem(){
        String problemTitle = "Rash";
        Date problemDate = Calendar.getInstance().getTime();
        String problemComment = "I have a rash";
        Problem problem = new Problem(problemTitle, problemDate, problemComment);
        ProblemList problemList = new ProblemList();
        problemList.addProblem(problem);
        Collection<Problem> problems = problemList.getProblems();
        assertTrue("Problem Delete List Size not big enough", problems.size() == 1);
        assertTrue("Problem Delete Not Contained", problems.contains(problem));

        problemList.deleteProblem(problem);
        problems = problemList.getProblems();
        assertTrue("Problem Deletion List Size isn't small enough", problems.size() == 0);
        assertFalse("Problem Deletion Still Contained", problems.contains(problem));


    }

    public void testDeleteNonExistentProblem(){
        String problemTitle = "Rash";
        Date problemDate = Calendar.getInstance().getTime();
        String problemComment = "I have a rash";
        Problem problem = new Problem(problemTitle, problemDate, problemComment);
        ProblemList problemList = new ProblemList();
        Collection<Problem> problems = problemList.getProblems();

        problemList.deleteProblem(problem);
        problems = problemList.getProblems();
        assertTrue("Deleting Non-Existent Problem", problems.contains(problem));


    }

    public void testManyAddProblem(){
        String problemTitle1 = "Rash";
        Date problemDate1 = Calendar.getInstance().getTime();
        String problemComment1 = "I have a rash";
        Problem problem1 = new Problem(problemTitle1, problemDate1, problemComment1);
        ProblemList problemList = new ProblemList();
        problemList.addProblem(problem1);
        Collection<Problem> problems = problemList.getProblems();
        assertTrue("Problem1 List Size not big enough", problems.size() == 1);
        assertTrue("Problem1 Not Contained", problems.contains(problem1));

        String problemTitle2 = "Scar";
        Date problemDate2 = Calendar.getInstance().getTime();
        String problemComment2 = "I have a scar";
        Problem problem2 = new Problem(problemTitle2, problemDate2, problemComment2);
        problemList.addProblem(problem2);
        problems = problemList.getProblems();
        assertTrue("Problem2 List Size not big enough", problems.size() == 2);
        assertTrue("Problem2 Not Contained", problems.contains(problem2));

        String problemTitle3 = "Bruise";
        Date problemDate3 = Calendar.getInstance().getTime();
        String problemComment3 = "I have a bruise";
        Problem problem3 = new Problem(problemTitle3, problemDate3, problemComment3);
        problemList.addProblem(problem3);
        problems = problemList.getProblems();
        assertTrue("Problem3 List Size not big enough", problems.size() == 3);
        assertTrue("Problem3 Not Contained", problems.contains(problem3));

    }

    public void testSpecificDeleteProblem(){
        String problemTitle1 = "Rash";
        Date problemDate1 = Calendar.getInstance().getTime();
        String problemComment1 = "I have a rash";
        Problem problem1 = new Problem(problemTitle1, problemDate1, problemComment1);
        ProblemList problemList = new ProblemList();
        problemList.addProblem(problem1);
        Collection<Problem> problems = problemList.getProblems();
        assertTrue("Problem1 Delete List Size not big enough", problems.size() == 1);
        assertTrue("Problem1 Delete Not Contained", problems.contains(problem1));

        String problemTitle2 = "Scar";
        Date problemDate2 = Calendar.getInstance().getTime();
        String problemComment2 = "I have a scar";
        Problem problem2 = new Problem(problemTitle2, problemDate2, problemComment2);
        problemList.addProblem(problem2);
        problems = problemList.getProblems();
        assertTrue("Problem2 Delete List Size not big enough", problems.size() == 2);
        assertTrue("Problem2 Delete Not Contained", problems.contains(problem2));

        String problemTitle3 = "Bruise";
        Date problemDate3 = Calendar.getInstance().getTime();
        String problemComment3 = "I have a bruise";
        Problem problem3 = new Problem(problemTitle3, problemDate3, problemComment3);
        problemList.addProblem(problem3);
        problems = problemList.getProblems();
        assertTrue("Problem3 Delete List Size not big enough", problems.size() == 3);
        assertTrue("Problem3 Delete Not Contained", problems.contains(problem3));

        problemList.deleteProblem(problem2);
        problems = problemList.getProblems();
        assertTrue("Problem2 Deletion List Size isn't small enough", problems.size() == 2);
        assertFalse("Problem2 Deletion Still Contained", problems.contains(problem2));

        problemList.deleteProblem(problem1);
        problems = problemList.getProblems();
        assertTrue("Problem1 Deletion List Size isn't small enough", problems.size() == 1);
        assertFalse("Problem1 Deletion Still Contained", problems.contains(problem1));

    }

    public void testHasProblem(){
        String problemTitle1 = "Rash";
        Date problemDate1 = Calendar.getInstance().getTime();
        String problemComment1 = "I have a rash";
        Problem problem1 = new Problem(problemTitle1, problemDate1, problemComment1);
        ProblemList problemList = new ProblemList();
        problemList.addProblem(problem1);

        String problemTitle2 = "Scar";
        Date problemDate2 = Calendar.getInstance().getTime();
        String problemComment2 = "I have a scar";
        Problem problem2 = new Problem(problemTitle2, problemDate2, problemComment2);
        problemList.addProblem(problem2);
        Collection<Problem> problems = problemList.getProblems();

        assertTrue("HasProblem List Size not big enough", problems.size() == 2);
        assertTrue("HasProblem2 Not Contained", problems.contains(problem2));
        assertTrue("HasProblem1 Not Contained", problems.contains(problem1));

    }

    public void testGetProblem(){
        String problemTitle1 = "Rash";
        Date problemDate1 = Calendar.getInstance().getTime();
        String problemComment1 = "I have a rash";
        Problem problem1 = new Problem(problemTitle1, problemDate1, problemComment1);
        ProblemList problemList = new ProblemList();
        problemList.addProblem(problem1);

        String problemTitle2 = "Scar";
        Date problemDate2 = Calendar.getInstance().getTime();
        String problemComment2 = "I have a scar";
        Problem problem2 = new Problem(problemTitle2, problemDate2, problemComment2);
        problemList.addProblem(problem2);
        Collection<Problem> problems = problemList.getProblems();

        Problem returnProblem = problemList.getProblem(problem1);

        assertTrue("Problem1 does not match", problems.iterator().next() == returnProblem);

    }


}
