package in.learncodeonline.placementprep.models;

public class Question {
    private String description;
    private String solution;

    public Question(String description, String solution) {
        this.description = description;
        this.solution = solution;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "Question{" +
                "description='" + description + '\'' +
                ", solution='" + solution + '\'' +
                '}';
    }

}
