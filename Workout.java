import java.util.ArrayList;

public class Workout {

    private int workoutId;
    private String workoutName;
    private ArrayList<Exercise> exercisesList = new ArrayList<>();

    public Workout() {

    }

    public Workout(int workoutId, String workoutName) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        exercisesList = new ArrayList<>();
    }

    public void addExercise(Exercise exercise) {
        exercisesList.add(exercise);
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public ArrayList<Exercise> getExercisesList() {
        return exercisesList;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "workoutId=" + workoutId +
                ", workoutName='" + workoutName + '\'';
    }

    public void showExercise() {

        for(Exercise exercise : exercisesList) {
            System.out.println(exercise.toString());
            exercise.showSets();
        }
    }
}
