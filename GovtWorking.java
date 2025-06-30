
import Resources.CropRate;

import javafx.scene.control.Alert;

import javafx.scene.control.ListView;

import java.util.List;


import Farmer.FarmerIssues;


public class GovtWorking {
    private static final double EXAMPLE_QUANTITY = 1000;
    

    public void updateSubsidyListView(ListView<String> subsidyListView, List<CropRate> crops) {
        subsidyListView.getItems().clear();
        for (CropRate crop : crops) {
            double subsidyAmount = 0;
            if (crop.getMarketPrice() < crop.getMsp()) {
                subsidyAmount = (crop.getMsp() - crop.getMarketPrice()) * EXAMPLE_QUANTITY;
            }
            
            subsidyListView.getItems().add(
                    String.format("Crop: %s, MSP: %.2f, Market Price: %.2f, Quantity: %.2f kg, Subsidy: %.2f", 
                    crop.getCropType(), crop.getMsp(), crop.getMarketPrice(), EXAMPLE_QUANTITY, subsidyAmount));
        }
    }

    public void updateIssuesListView(ListView<FarmerIssues> listView) {
        listView.refresh(); // Refresh the ListView to show updated data
    }
    
    public void sendReply(FarmerIssues issue, String response) {
        issue.setResponse(response);  // Set the response text
        issue.setStatus("Replied");   // Update status to "Replied"

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reply Sent");
        alert.setHeaderText(null);
        alert.setContentText("Your reply to " + issue.getFarmerName() + " has been sent.\nIssue: " + issue.getIssueDescription() + "\nResponse: " + response);
        alert.showAndWait();
    } 

        
    
}
