
public class Insurance {
    
    private int insuranceId;
    private String insuranceName;
    private boolean insuranceValidity;

    public Insurance(int insuranceId, String insuranceName, boolean insuranceValidity) {
        this.insuranceId = insuranceId;
        this.insuranceName = insuranceName;
        this.insuranceValidity = insuranceValidity;
    }

    public int returnInsuranceId() {
        return this.insuranceId;
    }

    public String returnInsuranceName() {
        return this.insuranceName;
    }

    public boolean returnInsuranceValidity() {
        return this.insuranceValidity;
    }
}
