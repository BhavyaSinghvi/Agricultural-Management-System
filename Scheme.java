package Goverment;

import Farmer.Farmer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Define a Scheme class for schemes with names and descriptions
public class Scheme {
    private String name;
    private String description;
    private ObservableList<Farmer> accessedFarmers;

    public Scheme(String name, String description) {
        this.name = name;
        this.description = description;
        this.accessedFarmers = FXCollections.observableArrayList();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ObservableList<Farmer> getAccessedFarmers() {
        return accessedFarmers;
    }

    // Method to add a farmer who has accessed this scheme
    public void addFarmer(Farmer farmer) {
        accessedFarmers.add(farmer);
    }
}