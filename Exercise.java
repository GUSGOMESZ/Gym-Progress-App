import java.util.ArrayList;

public class Exercise {

    private String exerciseName;
    private ArrayList<Set> setsList;

    public Exercise(String exerciseName) {
        this.exerciseName = exerciseName;
        setsList = new ArrayList<>();
    }

    public void addSet(Set set) {
        setsList.add(set);
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public ArrayList<Set> getSetsList() {
        return setsList;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "exerciseName='" + exerciseName + '\'';
    }

    public void showSets() {

        for(Set set : setsList) {
            System.out.println(set.toString());
        }
    }
}
