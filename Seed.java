package Resources.Seed;

public class Seed {
    private String name;
    private int count;
    private int cost;

    public Seed(String name, int count, int cost) {
        this.name = name;
        this.count = count;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getCost(){
        return cost;
    }

    @Override
    public String toString() {
        return "Seed{name='" + name + "', count=" + count + "', cost=" + cost + '}';
    }
}

