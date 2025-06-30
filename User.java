package Farmer;

public class User {
    protected String username;
    protected String password;
    protected static int count=0;


    // Constructor
    public User(String username, String password) {
        count++;
        this.username = username;
       this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    // Method to display user details
    public void displayUserInfo() {
        System.out.println("Username: " + username);
    }
}
