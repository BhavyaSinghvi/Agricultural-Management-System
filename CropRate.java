package Resources;

public class CropRate extends Crop{
    private final double msp;
    private final double marketPrice;

    public CropRate(String cropType, double msp, double marketPrice) {
        super(cropType);
        this.msp = msp;
        this.marketPrice = marketPrice;
    }

    public double getMsp() {
        return msp;
    }

    public double getMarketPrice() {
        return marketPrice;
    }
}
