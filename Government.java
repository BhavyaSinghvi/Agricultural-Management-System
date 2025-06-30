package Goverment;

import Resources.SubsidyManager;
import Resources.Seed.SeedManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Government {
    private String password = "1234";
    private SeedManager seedManager;
    private SubsidyManager subsidyManager; 
    private ObservableList<Scheme> schemes;
     
        public Government(){
            seedManager = new SeedManager();
            subsidyManager = new SubsidyManager();
            schemes = FXCollections.observableArrayList();
            initScheme(schemes);
            
        }

        public void initScheme(ObservableList<Scheme> schemes){
            Scheme pmKisan = new Scheme("PM-KISAN", "Provides direct income support of ₹6,000 per year.");
            Scheme kcc = new Scheme("Kisan Credit Card (KCC)", "Provides low-interest credit for agricultural needs.");
            schemes.addAll(pmKisan, kcc);
        }

        public ObservableList<Scheme> getSchemes(){
            return schemes;
        }
     
        public SubsidyManager getSubsidies() {
            return subsidyManager;
        }
    
        public SeedManager getSeedAccess() {
           return seedManager;
        }  
    
        public String getPassword(){
            return password;
        }
    
}
