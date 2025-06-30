package Farmer;

import java.util.ArrayList;

public class Farmer extends User {
    private String name;
    private String state;    // State where the farm is located
    private String area;     // Area within the state where the farm is located
    private double farmSize;
    private String soilType; // in acres
    private int ID;
    ArrayList<CropProduce> cropProduceList;

    // Constructor
    public Farmer(String name,String username,String password, double farmSize, String soilType, String state, String area) {
        super(username,password);  // Call to the parent class constructor
        this.name=name;
        this.state = state;
        this.area = area;
        this.farmSize = farmSize;
        this.soilType = soilType;
        this.ID = (2024000 + count); 
        this.cropProduceList = new ArrayList<>();
    }

    // Getters and Setters for Farmer-specific attributes
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getFarmSize() {
        return farmSize;
    }

    public void setFarmSize(double farmSize) {
        this.farmSize = farmSize;
    }

    public int getId(){
        return this.ID;
    }

    public String getName(){
        return this.name;
    }

    public String getSoilType() {
        return soilType;
    }
    
    public ArrayList<CropProduce> getCropProduces(){
        return this.cropProduceList;
    }

    @Override
    public void displayUserInfo() {
        super.displayUserInfo();
        System.out.println("State: " + state);
        System.out.println("Area: " + area);
        System.out.println("Farm Size: " + farmSize + " acres");
        System.out.println("ID: " + ID);
    }
}
