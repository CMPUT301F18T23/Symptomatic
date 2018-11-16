package ca.ualberta.symptomaticapp;

public class ProblemListController {
    private static ProblemList problemList = null;
    public static ProblemList getProblemList(){
        if (problemList == null){
            problemList = new ProblemList();
        }
        return problemList;
    }

    public void addProblem(Problem problem) {
        getProblemList().addProblem(problem);
    }

    public void getSize() {
        getProblemList().getSize();
    }


}
