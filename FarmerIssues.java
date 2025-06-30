package Farmer;

public class FarmerIssues {
    private String farmerName;
    private String issueDescription;
    private String response;
    private String status;

    public FarmerIssues(String farmerName, String issueDescription) {
        this.farmerName = farmerName;
        this.issueDescription = issueDescription;
        this.response = ""; // Default to an empty response
        this.status = "Pending"; // Default status
    }

    // Getters and Setters
    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Farmer: " + farmerName + ", Issue: " + issueDescription + ", Status: " + status + ", Response: " + response;
    }
}
