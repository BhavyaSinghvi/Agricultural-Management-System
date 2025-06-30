package Resources;

import java.util.ArrayList;
import java.util.List;

public class SubsidyManager {
    private static final List<CropRate> crops = new ArrayList<>();
    
    public SubsidyManager(){
        crops.add(new CropRate("Rice", 20.0, 18.0));
        crops.add(new CropRate("Wheat", 22.0, 20.0));
        crops.add(new CropRate("Corn", 15.0, 12.0));
        
    } 
    
    public List<CropRate> getSubsidies(){
        return crops;
    }
    
}
