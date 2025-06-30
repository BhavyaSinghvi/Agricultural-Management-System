import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import Farmer.*;
import Goverment.*;
import Resources.CropRate;
import Resources.Seed.Seed;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application {
    Government Govt = new Government();
    private List<Farmer> farmers = new ArrayList<>();
    private ObservableList<FarmerIssues> issues = FXCollections.observableArrayList();
    private FarmerServices farmerServices = new FarmerServices(farmers);
    private GovtWorking govtWorking = new GovtWorking();
    private Farmer loggedInFarmer;
    Alerts alert =new Alerts();
    

    @Override
    //Welcome Page. User sees this when he starts the app 
    public void start(Stage primaryStage) {
        Label welcomeLabel = new Label("Welcome to Agriculture Management System");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    
        Button registerFarmerButton = new Button("Register Farmer");
        Button farmerLoginButton = new Button("Farmer Login");
        Button govtLoginButton = new Button("Government Login");
    
        registerFarmerButton.setOnAction(e -> Register(primaryStage));
        farmerLoginButton.setOnAction(e -> showFarmerLogin(primaryStage));
        govtLoginButton.setOnAction(e -> showGovernmentLogin(primaryStage));
    
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);
        gridPane.setHgap(10);
    
        gridPane.add(welcomeLabel, 0, 0, 3, 1);
        gridPane.add(registerFarmerButton, 0, 1);
        gridPane.add(farmerLoginButton, 1, 1);
        gridPane.add(govtLoginButton, 2, 1);
    
        Scene scene = new Scene(gridPane, 400, 200);
        primaryStage.setTitle("Welcome to AMS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //Register page where Farmer can register
    public void Register(Stage primaryStage) {
        primaryStage.setTitle("Farmer Registration");
    
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label farmSizeLabel = new Label("Farm Size (in acres):");
        TextField farmSizeField = new TextField();
        Label soilTypeLabel = new Label("Soil Type:");
        TextField soilTypeField = new TextField();
        Label stateLabel = new Label("State:");
        TextField stateField = new TextField();
        Label areaLabel = new Label("Area:");
        TextField areaField = new TextField();
        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);
    
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(farmSizeLabel, 0, 3);
        gridPane.add(farmSizeField, 1, 3);
        gridPane.add(soilTypeLabel, 0, 4);
        gridPane.add(soilTypeField, 1, 4);
        gridPane.add(stateLabel, 0, 5);
        gridPane.add(stateField, 1, 5);
        gridPane.add(areaLabel, 0, 6);
        gridPane.add(areaField, 1, 6);
        gridPane.add(registerButton, 1, 7);
        gridPane.add(backButton, 0, 7);
    
        registerButton.setOnAction(e -> {
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String farmSize = farmSizeField.getText();
            String soilType = soilTypeField.getText();
            String state = stateField.getText();
            String area = areaField.getText();
    
            if (name.isEmpty() || username.isEmpty() || password.isEmpty() ||
                    farmSize.isEmpty() || soilType.isEmpty() || state.isEmpty() || area.isEmpty()) {
                alert.showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please fill in all fields.", null);
                return;
            }
    
            try {
                Double.parseDouble(farmSize);
            } catch (NumberFormatException ex) {
                alert.showAlert(Alert.AlertType.WARNING, "Invalid Input", "Farm Size must be a number.", null);
                return;
            }
    
            if (farmerServices.isFarmerRegistered(username)) {
                alert.showAlert(Alert.AlertType.WARNING, "Registration Failed", "Username already taken!", null);
            } else {
                Farmer newFarmer = new Farmer(name, username, password, Double.parseDouble(farmSize), soilType, state, area);
                farmers.add(newFarmer);
                alert.showAlert(Alert.AlertType.INFORMATION, "Registration Successful",
                        "Farmer ID: " + newFarmer.getId() + "\nName: " + name, null);
            }
            showHomePage(primaryStage);
        });
    
        backButton.setOnAction(e -> showHomePage(primaryStage));
    
        Scene scene = new Scene(gridPane, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Farmer Login Page
    public void showFarmerLogin(Stage primaryStage) {
        primaryStage.setTitle("Farmer Login");
    
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
    
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.add(backButton, 0, 2);
    
        loginButton.setOnAction(e -> {
            String enteredUsername = usernameField.getText();
            String enteredPassword = passwordField.getText();
    
            if (farmerServices.authenticateFarmer(enteredUsername, enteredPassword)) {
                loggedInFarmer = farmerServices.findFarmer(enteredPassword);
                alert.showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + enteredUsername + "!", null);
                farmerHomePage(primaryStage);
            } else {
                alert.showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect Username or Password.", null);
                usernameField.clear();
                passwordField.clear();
            }
        });
    
        backButton.setOnAction(e -> showHomePage(primaryStage));
    
        Scene scene = new Scene(gridPane, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
   
    
    public void showHomePage(Stage primaryStage) {
        start(primaryStage);
    }
    

    public void showGovernmentLogin(Stage primaryStage) {
        primaryStage.setTitle("Government Login");
    
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
    
        gridPane.add(passwordLabel, 0, 0);
        gridPane.add(passwordField, 1, 0);
        gridPane.add(loginButton, 1, 1);
    
        loginButton.setOnAction(e -> {
            String enteredPassword = passwordField.getText();
            
            if (authenticate(enteredPassword)) {
                alert.showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, Government Official!", null);
                govtHomePage(primaryStage);
            } else {
                alert.showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect Password. Please try again.", null);
                passwordField.clear();
            }
        });
    
        Scene scene = new Scene(gridPane, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private boolean authenticate(String enteredPassword) {
        return Govt.getPassword().equals(enteredPassword);
    }
    
    public void farmerHomePage(Stage primaryStage) {
        primaryStage.setTitle("Farmer Home Page");
    
        Button checkGovInfoButton = new Button("Check Government Information");
        Button raiseIssueButton = new Button("Raise an Issue");
        Button enterCropProduceButton = new Button("Enter Crop Produce");
        Button logoutButton = new Button("Logout");
    
        checkGovInfoButton.setOnAction(e -> showGovtInfo(primaryStage));
        raiseIssueButton.setOnAction(e -> raiseIssue(primaryStage));
        enterCropProduceButton.setOnAction(e -> enterCropProduce(primaryStage, loggedInFarmer));
        logoutButton.setOnAction(e -> confirmLogout(primaryStage));
    
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(checkGovInfoButton, raiseIssueButton, enterCropProduceButton, logoutButton);
    
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void showGovtInfo(Stage primaryStage) {
        primaryStage.setTitle("Government Information for Farmers");
    
        Button seedsButton = new Button("Seed Information");
        seedsButton.setOnAction(e -> showSeedsInfo(primaryStage));
    
        Button schemesButton = new Button("Farming Schemes");
        schemesButton.setOnAction(e -> showSchemesInfo(primaryStage, loggedInFarmer, Govt.getSchemes()));
    
        Button subsidiesButton = new Button("Subsidies And Crop Recommendation");
        subsidiesButton.setOnAction(e -> calculateSubsidiesandGetCropRec(primaryStage, Govt.getSubsidies().getSubsidies()));
    
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> farmerHomePage(primaryStage));
    
        VBox layout = new VBox(10);
        layout.getChildren().addAll(seedsButton, schemesButton, subsidiesButton, backButton);
    
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void calculateSubsidiesandGetCropRec(Stage primaryStage, List<CropRate> cropRates) {
        primaryStage.setTitle("Subsidy Calculator and Crop Recommendation");
    
        GridPane subsidyGrid = new GridPane();
        subsidyGrid.setPadding(new Insets(10));
        subsidyGrid.setVgap(10);
        subsidyGrid.setHgap(10);
        
        Label cropLabel = new Label("Select Crop:");
        ComboBox<String> cropComboBox = new ComboBox<>();
        cropComboBox.getItems().addAll("Rice", "Wheat", "Corn");
        
        Label quantityLabel = new Label("Quantity (kg):");
        TextField quantityField = new TextField();
        
        Button calculateButton = new Button("Calculate Subsidy");
        Label subsidyResultLabel = new Label();
        
        calculateButton.setOnAction(e -> {
            String cropType = cropComboBox.getValue();
            double quantityProduced;
            try {
                quantityProduced = Double.parseDouble(quantityField.getText());
            } catch (NumberFormatException ex) {
                subsidyResultLabel.setText("Please enter a valid quantity.");
                return;
            }
            
            double subsidyAmount = farmerServices.calculateSubsidy(cropType, quantityProduced, cropRates);
            if (subsidyAmount > 0) {
                subsidyResultLabel.setText(String.format("Subsidy for %s: %.2f", cropType, subsidyAmount));
            } else {
                subsidyResultLabel.setText("No subsidy applicable; market price meets or exceeds MSP.");
            }
        });
    
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showGovtInfo(primaryStage));
        
        subsidyGrid.add(cropLabel, 0, 0);
        subsidyGrid.add(cropComboBox, 1, 0);
        subsidyGrid.add(quantityLabel, 0, 1);
        subsidyGrid.add(quantityField, 1, 1);
        subsidyGrid.add(calculateButton, 0, 2, 2, 1);
        subsidyGrid.add(subsidyResultLabel, 0, 3, 2, 1);
        
        GridPane recommendationGrid = new GridPane();
        recommendationGrid.setPadding(new Insets(10));
        recommendationGrid.setVgap(10);
        recommendationGrid.setHgap(10);
        
        Label techniqueLabel = new Label("Production Technique:");
        ComboBox<String> techniqueComboBox = new ComboBox<>();
        techniqueComboBox.getItems().addAll("Traditional", "Organic", "Hydroponics");
        
        Button recommendButton = new Button("Get Recommendation");
        Label recommendationLabel = new Label();
        
        recommendButton.setOnAction(e -> {
            String technique = techniqueComboBox.getValue();
            String recommendation = farmerServices.getRecommendation(technique);
            recommendationLabel.setText(recommendation);
        });
        
        recommendationGrid.add(techniqueLabel, 0, 0);
        recommendationGrid.add(techniqueComboBox, 1, 0);
        recommendationGrid.add(recommendButton, 0, 1, 2, 1);
        recommendationGrid.add(recommendationLabel, 0, 2, 2, 1);
        
        VBox mainLayout = new VBox(15, new Label("MSP-Based Subsidy Calculator"), subsidyGrid, 
                                   new Label("Crop Production Recommendation"), recommendationGrid, backButton);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(mainLayout, 500, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void showSchemesInfo(Stage primaryStage, Farmer loggedInFarmer, ObservableList<Scheme> schemes) {
        primaryStage.setTitle("Farmer Scheme Access");
        Farmer currentFarmer = loggedInFarmer;
    
        VBox schemesBox = new VBox(10);
        schemesBox.setPadding(new Insets(10));
        for (Scheme scheme : schemes) {
            VBox schemeBox = farmerServices.createSchemeBox(scheme, currentFarmer);
            schemesBox.getChildren().add(schemeBox);
        }
    
        Button viewAccessedSchemesButton = new Button("View Schemes You've Accessed");
        viewAccessedSchemesButton.setOnAction(e -> {
            farmerServices.showAccessedSchemes(currentFarmer, schemes);
        });
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showGovtInfo(primaryStage));
    
        GridPane mainLayout = new GridPane();
        mainLayout.setPadding(new Insets(15));
        mainLayout.setVgap(20);
        mainLayout.add(schemesBox, 0, 0);
        mainLayout.add(viewAccessedSchemesButton, 0, 1);
        mainLayout.add(backButton, 0, 2);
    
        Scene scene = new Scene(mainLayout, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void showSeedsInfo(Stage primaryStage) {
        primaryStage.setTitle("Buy Seeds");
    
        ListView<Seed> seedListView = new ListView<>(Govt.getSeedAccess().getSeedList());
        seedListView.setCellFactory(lv -> new ListCell<Seed>() {
            @Override
            protected void updateItem(Seed item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " - Count: " + item.getCount() + ", Cost: $" + item.getCost());
                }
            }
        });
    
        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");
    
        Button purchaseButton = new Button("Purchase Seed");
        purchaseButton.setOnAction(e -> {
            Seed selectedSeed = seedListView.getSelectionModel().getSelectedItem();
            String quantityText = quantityField.getText();
    
            if (selectedSeed != null && !quantityText.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityText);
                    if (quantity > 0 && quantity <= selectedSeed.getCount()) {
                        double totalCost = selectedSeed.getCost() * quantity;
                        alert.showAlert(Alert.AlertType.INFORMATION, "Purchase Successful",
                                "You have purchased " + quantity + " " + selectedSeed.getName() +
                                "(s) for $" + totalCost, null);
                        quantityField.clear();
                        updateSeedCount(selectedSeed, quantity);
                    } else {
                        alert.showAlert(Alert.AlertType.WARNING, "Invalid Quantity", 
                                "Please enter a valid quantity (1 to " + selectedSeed.getCount() + ").", null);
                    }
                } catch (NumberFormatException ex) {
                    alert.showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please enter a number for the quantity.", null);
                }
            } else {
                alert.showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select a seed and enter a quantity.", null);
            }
        });
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showGovtInfo(primaryStage));
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);
        gridPane.add(new Label("Available Seeds:"), 0, 0);
        gridPane.add(seedListView, 0, 1);
        gridPane.add(new Label("Quantity:"), 1, 1);
        gridPane.add(quantityField, 1, 2);
        gridPane.add(purchaseButton, 1, 3);
        gridPane.add(backButton, 1, 4);
    
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void updateSeedCount(Seed seed, int quantityPurchased) {
        int newCount = seed.getCount() - quantityPurchased;
        Govt.getSeedAccess().getSeedList().remove(seed);
        Govt.getSeedAccess().getSeedList().add(new Seed(seed.getName(), newCount, seed.getCost()));
    }
    
    public void raiseIssue(Stage primaryStage) {
        primaryStage.setTitle("Raise an Issue");
    
        Label nameLabel = new Label("Your Name:");
        TextField nameField = new TextField();
        
        Label issueLabel = new Label("Issue Description:");
        TextArea issueField = new TextArea();
        
        Button submitButton = new Button("Submit Issue");
        Button viewIssuesButton = new Button("View All Issues");
        Button backButton = new Button("Back");
        ListView<FarmerIssues> listView = new ListView<>(issues);
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);
    
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(issueLabel, 0, 1);
        gridPane.add(issueField, 1, 1);
        gridPane.add(submitButton, 2, 2);
        gridPane.add(viewIssuesButton, 1, 2);
        gridPane.add(backButton, 0, 4);
    
        submitButton.setOnAction(e -> {
            String farmerName = nameField.getText();
            String issueDescription = issueField.getText();
            
            if (farmerName.isEmpty() || issueDescription.isEmpty()) {
                alert.showAlert(Alert.AlertType.WARNING, "Submission Failed", "Please fill in all fields.", null);
            } else {
                FarmerIssues newIssue = new FarmerIssues(farmerName, issueDescription);
                issues.add(newIssue);
                alert.showAlert(Alert.AlertType.INFORMATION, "Submission Successful", "Your issue has been submitted.", null);
                nameField.clear();
                issueField.clear();
            }
        });
    
        backButton.setOnAction(e -> farmerHomePage(primaryStage));
        viewIssuesButton.setOnAction(e -> {
            gridPane.add(listView, 0, 0, 3, 3);
        });
    
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void enterCropProduce(Stage primaryStage, Farmer loggedInFarmer) {
        primaryStage.setTitle("Enter Crop Produce");

        // Layout for crop produce entry
        Button backButton = new Button("Back");
        backButton.setOnAction(e->farmerHomePage(primaryStage));
        VBox layout = farmerServices.createCropEntryLayout(loggedInFarmer.getCropProduces());

        layout.getChildren().add(backButton);
        
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void confirmLogout(Stage primaryStage) {
        App A = new App();
        // Create a confirmation alert
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        // Show the alert and wait for the user’s response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed logout
            System.out.println("Logging out...");
            A.showHomePage(primaryStage);  // Close the application window
        }
    } 

    

    public void govtHomePage(Stage primaryStage) {
        primaryStage.setTitle("Government Home Page");
    
        Button viewSchemesButton = new Button("View Existing Schemes");
        Button viewSubsidiesButton = new Button("View Existing Subsidies");
        Button seedMnagerButton = new Button("Seed Manager");
        Button logoutButton = new Button("Logout");
        Button viewFarmerIssuesButton = new Button("View Farmer Issues Button");
    
        viewSchemesButton.setOnAction(e -> showSchemeView(primaryStage, Govt.getSchemes()));
        viewSubsidiesButton.setOnAction(e -> viewSubsidies(primaryStage, Govt.getSubsidies().getSubsidies()));
        seedMnagerButton.setOnAction(e -> showSeedManager(primaryStage));
        logoutButton.setOnAction(e -> confirmLogout(primaryStage));
        viewFarmerIssuesButton.setOnAction(e -> viewFarmerIssues(primaryStage));
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
    
        gridPane.add(viewSchemesButton, 0, 0);
        gridPane.add(viewSubsidiesButton, 0, 1);
        gridPane.add(seedMnagerButton, 0, 2);
        gridPane.add(viewFarmerIssuesButton, 0, 3);
        gridPane.add(logoutButton, 0, 4);
    
        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void viewSubsidies(Stage primaryStage, List<CropRate> crops) {
        primaryStage.setTitle("Government View: Subsidy Calculation");
    
        ListView<String> subsidyListView = new ListView<>();
        govtWorking.updateSubsidyListView(subsidyListView, crops);
    
        TextField newCropNameField = new TextField();
        newCropNameField.setPromptText("Enter New Crop Name");
    
        TextField newCropMSPField = new TextField();
        newCropMSPField.setPromptText("Enter MSP");
    
        TextField newCropMarketPriceField = new TextField();
        newCropMarketPriceField.setPromptText("Enter Market Price");
    
        Button addCropButton = new Button("Add Crop");
        addCropButton.setOnAction(event -> {
            try {
                String cropName = newCropNameField.getText();
                double msp = Double.parseDouble(newCropMSPField.getText());
                double marketPrice = Double.parseDouble(newCropMarketPriceField.getText());
    
                CropRate newCrop = new CropRate(cropName, msp, marketPrice);
                crops.add(newCrop);
                govtWorking.updateSubsidyListView(subsidyListView, crops);
                newCropNameField.clear();
                newCropMSPField.clear();
                newCropMarketPriceField.clear();
            } catch (NumberFormatException e) {
                alert.showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "Please enter valid numerical values for MSP and Market Price.");
            }
        });
    
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> govtHomePage(primaryStage));
    
        VBox layout = new VBox(15, new Label("Calculated Subsidies"), subsidyListView,
                new Label("Add New Crop"), newCropNameField, newCropMSPField, newCropMarketPriceField, addCropButton, backButton);
        layout.setPadding(new Insets(20));
    
        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void showSchemeView(Stage primaryStage, ObservableList<Scheme> schemes) {
        primaryStage.setTitle("Government Scheme Management");        
    
        ListView<Scheme> schemeListView = new ListView<>(schemes);
        schemeListView.setCellFactory(param -> new ListCell<Scheme>() {
            @Override
            protected void updateItem(Scheme scheme, boolean empty) {
                super.updateItem(scheme, empty);
                setText(empty ? "" : scheme.getName());
            }
        });
    
        Label schemeDescriptionLabel = new Label();
        ListView<String> farmersListView = new ListView<>();
    
        schemeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedScheme) -> {
            if (selectedScheme != null) {
                schemeDescriptionLabel.setText("Description: " + selectedScheme.getDescription());
                ObservableList<String> farmerNames = FXCollections.observableArrayList();
                selectedScheme.getAccessedFarmers().forEach(farmer -> farmerNames.add(farmer.getName()));
                farmersListView.setItems(farmerNames);
            } else {
                schemeDescriptionLabel.setText("");
                farmersListView.setItems(FXCollections.observableArrayList());
            }
        });
    
        Button addSchemeButton = new Button("Add Scheme");
        addSchemeButton.setOnAction(e -> showAddSchemeDialog(Govt.getSchemes()));
    
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> govtHomePage(primaryStage));
    
        VBox schemeDetailsBox = new VBox(10, schemeDescriptionLabel, new Label("Farmers Accessed:"), farmersListView, backButton);
        schemeDetailsBox.setPadding(new Insets(10));
    
        GridPane mainLayout = new GridPane();
        mainLayout.setPadding(new Insets(15));
        mainLayout.setHgap(20);
        mainLayout.setVgap(10);
        mainLayout.add(new Label("Schemes:"), 0, 0);
        mainLayout.add(schemeListView, 0, 1);
        mainLayout.add(schemeDetailsBox, 1, 1);
        mainLayout.add(addSchemeButton, 0, 2);
    
        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    

    private void showAddSchemeDialog(ObservableList<Scheme> schemes) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Add New Scheme");
    
        Label nameLabel = new Label("Scheme Name:");
        TextField nameField = new TextField();
    
        Label descriptionLabel = new Label("Scheme Description:");
        TextArea descriptionField = new TextArea();
        descriptionField.setPrefRowCount(3);
    
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String schemeName = nameField.getText().trim();
            String schemeDescription = descriptionField.getText().trim();
    
            if (!schemeName.isEmpty() && !schemeDescription.isEmpty()) {
                schemes.add(new Scheme(schemeName, schemeDescription));
                dialog.close();
            } else {
                alert.showAlert("Error", "Scheme name and description cannot be empty.");
            }
        });
    
        VBox dialogLayout = new VBox(10, nameLabel, nameField, descriptionLabel, descriptionField, addButton);
        dialogLayout.setPadding(new Insets(15));
    
        Scene dialogScene = new Scene(dialogLayout, 400, 300);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }
    
    public void showSeedManager(Stage primaryStage) {
        primaryStage.setTitle("Seed Manager");
    
        Label nameLabel = new Label("Seed Name:");
        TextField nameField = new TextField();
        Label countLabel = new Label("Seed Count:");
        TextField countField = new TextField();
        Label costLabel = new Label("Seed Cost:");
        TextField costField = new TextField();
    
        Button addButton = new Button("Add Seed");
        Button deleteButton = new Button("Delete Seed");
    
        ListView<Seed> listView = new ListView<>(Govt.getSeedAccess().getSeedList());
        Button backButton = new Button("Back");
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);
    
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(countLabel, 0, 1);
        gridPane.add(countField, 1, 1);
        gridPane.add(costLabel, 0, 2);
        gridPane.add(costField, 1, 2);
        gridPane.add(addButton, 0, 3);
        gridPane.add(deleteButton, 1, 3);
        gridPane.add(listView, 0, 4, 3, 2);
        gridPane.add(backButton, 0, 6);
    
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String countText = countField.getText();
            String costText = costField.getText();
            if (!name.isEmpty() && !countText.isEmpty() && !costText.isEmpty()) {
                try {
                    int count = Integer.parseInt(countText);
                    int cost = Integer.parseInt(costText);
                    Seed newSeed = new Seed(name, count, cost);
                    Govt.getSeedAccess().getSeedList().add(newSeed);
                    nameField.clear();
                    countField.clear();
                    costField.clear();
                } catch (NumberFormatException ex) {
                    alert.showAlert("Invalid Input", "Count must be a valid number.");
                }
            } else {
                alert.showAlert("Invalid Input", "Please enter both seed name and count.");
            }
        });
    
        deleteButton.setOnAction(e -> {
            Seed selectedSeed = listView.getSelectionModel().getSelectedItem();
            if (selectedSeed != null) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Delete Seed");
                confirmation.setHeaderText("Are you sure you want to delete this seed?");
                confirmation.setContentText(selectedSeed.toString());
    
                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Govt.getSeedAccess().getSeedList().remove(selectedSeed);
                }
            } else {
                alert.showAlert("No Selection", "Please select a seed to delete.");
            }
        });
    
        backButton.setOnAction(e -> govtHomePage(primaryStage));
    
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void viewFarmerIssues(Stage primaryStage) {
        primaryStage.setTitle("Farmer Issues Management");
    
        ListView<FarmerIssues> issuesListView = new ListView<>(issues);
        issuesListView.setPrefHeight(200);
        issuesListView.setCellFactory(lv -> new ListCell<FarmerIssues>() {
            @Override
            protected void updateItem(FarmerIssues item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
    
        TextArea responseTextArea = new TextArea();
        responseTextArea.setPromptText("Type your response here...");
        responseTextArea.setWrapText(true);
    
        Button submitButton = new Button("Send Reply");
        submitButton.setOnAction(e -> {
            FarmerIssues selectedIssue = issuesListView.getSelectionModel().getSelectedItem();
            if (selectedIssue != null && !responseTextArea.getText().isEmpty()) {
                govtWorking.sendReply(selectedIssue, responseTextArea.getText());
                responseTextArea.clear();
                govtWorking.updateIssuesListView(issuesListView);
            } else {
                alert.showAlert("No Issue Selected or Empty Response", "Please select an issue and enter a response before sending.");
            }
        });
    
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> govtHomePage(primaryStage));
    
        VBox responseBox = new VBox(10, new Label("Response"), responseTextArea, submitButton, backButton);
        responseBox.setPadding(new Insets(10));
    
        BorderPane layout = new BorderPane();
        layout.setCenter(issuesListView);
        layout.setBottom(responseBox);
    
        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}    
