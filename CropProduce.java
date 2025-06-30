package Farmer;
import Resources.Crop;

public class CropProduce extends Crop{
    private double quantity;

    public CropProduce(String cropType, double quantity) {
        super(cropType);
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("Crop: %s, Quantity: %.2f kg", super .getCropType(), quantity);
    }
}