public class Set {

    private int order;
    private int reps;
    private int weight;

    public Set(int order, int reps, int weight) {
        this.order = order;
        this.reps = reps;
        this.weight = weight;
    }

    public int getOrder() {
        return order;
    }

    public int getReps() {
        return reps;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Set{" +
                "reps=" + reps +
                ", weight=" + weight +
                '}';
    }
}
