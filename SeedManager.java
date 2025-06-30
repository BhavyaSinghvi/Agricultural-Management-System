package Resources.Seed;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SeedManager {
    private ObservableList<Seed> seedList = FXCollections.observableArrayList();

    // Constructor
    public SeedManager() {
        Seed[] seeds = {
            new Seed("Sunflower", 100, 10),
            new Seed("Maize", 200, 20),
            new Seed("Mustard", 150, 15),
            new Seed("Paddy", 300, 30)
        };

        // Add initial seeds to the observable list
        seedList.addAll(seeds);
    }

    // Getter for seedList (if needed)
    public ObservableList<Seed> getSeedList() {
        return seedList;
    }
}

