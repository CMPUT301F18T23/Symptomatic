package ca.ualberta.symptomaticapp;

import java.util.ArrayList;
import java.util.Collection;

public class ProblemList {
    protected ArrayList<Problem> problemList;

    public ProblemList(){
        problemList = new ArrayList<Problem>();
    }

    public Collection<Problem> getProblems() {
        return problemList;
    }

    public void addProblem(Problem problem) {
        problemList.add(problem);
    }

    public void deleteProblem(Problem problem) {
        problemList.remove(problem);
    }

    public Problem getProblem(Problem problem) {
        int index = problemList.indexOf(problem);
        return problemList.get(index);
    }

    public int getSize() {
        return problemList.size();
    }

}
