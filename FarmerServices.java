import java.util.ArrayList;
import java.util.List;

import Farmer.CropProduce;
import Farmer.Farmer;
import Goverment.Scheme;
import Resources.CropRate;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class FarmerServices{
    Alerts alert = new Alerts();
    private List<Farmer> farmers;
    public FarmerServices(List<Farmer> farmers ){
        this.farmers = farmers;
    } 


    public Farmer findFarmer(String password) {
        for (Farmer farmer : farmers) {
            if (farmer.getPassword().equals(password)) {
                return farmer;
            }
        }
        return null;
    }
    
    public boolean authenticateFarmer(String username, String password) {
        return farmers.stream().anyMatch(farmer ->
                farmer.getUsername().equals(username) && farmer.getPassword().equals(password));
    }
    
    public boolean isFarmerRegistered(String username) {
        return farmers.stream().anyMatch(farmer -> farmer.getUsername().equalsIgnoreCase(username));
    }
    

    // Method to find the CropRate by crop type
    public CropRate findCropRate(String cropType, List<CropRate> cropRates) {
        for (CropRate cropRate : cropRates) {
            if (cropRate.getCropType().equalsIgnoreCase(cropType)) {
                return cropRate;
            }
        }
        return null; // Return null if no matching crop type is found
    }

    // Method to calculate subsidy based on crop type and quantity
    public double calculateSubsidy(String cropType, double quantityProduced,List<CropRate> cropRates) {
        CropRate cropRate = findCropRate(cropType, cropRates);
        if (cropRate == null) {
            return 0; // Return 0 subsidy if crop type is not found
        }

        double msp = cropRate.getMsp();
        double marketPrice = cropRate.getMarketPrice();
        
        return marketPrice < msp ? (msp - marketPrice) * quantityProduced : 0;
    }

    // Method to get recommendation based on production technique
    public String getRecommendation(String technique) {
        if (technique == null) {
            return "Please select a technique.";
        }

        switch (technique.toLowerCase()) {
            case "traditional":
                return "Consider using organic fertilizers and crop rotation to improve soil fertility and yield.";
            case "organic":
                return "Consider integrating pest management practices and advanced composting methods to improve productivity.";
            case "hydroponics":
                return "Try using nutrient monitoring systems and automation to maintain optimal growth conditions for better yield.";
            default:
                return "Unknown technique. Please consult with local agricultural experts for specialized recommendations.";
        }
    }

    public VBox createCropEntryLayout(ArrayList<CropProduce> cropProduceList) {
        GridPane cropGrid = new GridPane();
        cropGrid.setPadding(new Insets(10));
        cropGrid.setVgap(10);
        cropGrid.setHgap(10);

        Label cropLabel = new Label("Crop Type:");
        TextField cropField = new TextField();

        Label quantityLabel = new Label("Quantity (kg):");
        TextField quantityField = new TextField();

        Button submitButton = new Button("Submit");
        Label resultLabel = new Label();

        submitButton.setOnAction(e -> {
            String cropType = cropField.getText();
            double quantity;

            // Validate quantity input
            try {
                quantity = Double.parseDouble(quantityField.getText());
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter a valid quantity.");
                return;
            }

            // Create a new CropProduce object and add to the list
            CropProduce newProduce = new CropProduce(cropType, quantity);
            cropProduceList.add(newProduce);
            resultLabel.setText("Crop produce submitted: " + newProduce);
            cropField.clear();
            quantityField.clear();
        });

        // Adding elements to crop grid
        cropGrid.add(cropLabel, 0, 0);
        cropGrid.add(cropField, 1, 0);
        cropGrid.add(quantityLabel, 0, 1);
        cropGrid.add(quantityField, 1, 1);
        cropGrid.add(submitButton, 0, 2, 2, 1);
        cropGrid.add(resultLabel, 0, 3, 2, 1);

        return new VBox(10, new Label("Crop Produce Entry"), cropGrid);
    }

    

    // Method to create the layout for each scheme
    public VBox createSchemeBox(Scheme scheme, Farmer currentFarmer) {
        Label schemeLabel = new Label(scheme.getName());
        Label schemeDescription = new Label(scheme.getDescription());
        Button accessButton = new Button("Access " + scheme.getName());

        // Action for the access button
        accessButton.setOnAction(e -> {
            if (!scheme.getAccessedFarmers().contains(currentFarmer)) {
                scheme.addFarmer(currentFarmer);
                alert.showAlert(scheme.getName() + " Accessed", "You have accessed the " + scheme.getName() + " scheme.");
            } else {
               alert.showAlert("Already Accessed", "You have already accessed the " + scheme.getName() + " scheme.");
            }
        });

        VBox schemeBox = new VBox(5);
        schemeBox.getChildren().addAll(schemeLabel, schemeDescription, accessButton);
        schemeBox.setPadding(new Insets(10));
        return schemeBox;
    }

    // Method to show an alert with the list of schemes accessed by the farmer
    public void showAccessedSchemes(Farmer farmer, ObservableList<Scheme> schemes) {
        StringBuilder accessedSchemes = new StringBuilder("Schemes you've accessed:\n");
        boolean hasAccessedAny = false;

        for (Scheme scheme : schemes) {
            if (scheme.getAccessedFarmers().contains(farmer)) {
                accessedSchemes.append("- ").append(scheme.getName()).append("\n");
                hasAccessedAny = true;
            }
        }

        if (!hasAccessedAny) {
            alert.showAlert("No Schemes Accessed", "You have not accessed any schemes yet.");
        } else {
            alert.showAlert("Accessed Schemes", accessedSchemes.toString());
        }
    }
    
}
