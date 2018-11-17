package ca.ualberta.symptomaticapp;

import java.util.ArrayList;
import java.util.Collection;

public class ProblemList {
    protected ArrayList<Problem> problemList;
    protected transient ArrayList<Listener> listeners;

    public ProblemList(){
        problemList = new ArrayList<Problem>();
        listeners = new ArrayList<Listener>();
    }

    private ArrayList<Listener> getListeners(){
        if (listeners == null) {
            listeners = new ArrayList<Listener>();
        }
        return listeners;
    }

    public Collection<Problem> getProblems() {
        return problemList;
    }

    public void addProblem(Problem problem) {
        problemList.add(problem);
        notifyListeners();
    }

    public void deleteProblem(Problem problem) {
        problemList.remove(problem);
        notifyListeners();
    }

    public Problem getProblem(Problem problem) {
        int index = problemList.indexOf(problem);
        return problemList.get(index);
    }

    public int getSize() {
        return problemList.size();
    }

    public void notifyListeners(){
        for (Listener listener: getListeners()) {
            listener.update();
        }
    }

    public void addListener(Listener l) {
        getListeners().add(l);
    }

    public void removeListener(Listener l) {
        getListeners().remove(l);
    }


}
