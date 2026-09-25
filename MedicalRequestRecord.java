public class MedicalRequestRecord {
    
    private String requestID;
    private String patientID;
    private String doctorID;
    private String requestDate; 
    private String requestType;
    private String requestDetails;
    private String requestStatus;

    public MedicalRequestRecord(
        String requestID,
        String patientID,
        String doctorID,
        String requestDate,
        String requestType,
        String requestDetails,
        String requestStatus) {

    this.requestID = requestID;
    this.patientID = patientID;
    this.doctorID = doctorID;
    this.requestDate = requestDate;
    this.requestType = requestType;
    this.requestDetails = requestDetails;
    this.requestStatus = requestStatus;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }
    
    public String getRequestDate() {
        return requestDate;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getRequestDetails() {
        return requestDetails;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public String toTextLine() {
        return getRequestID()
               + ";" + getPatientID()
               + ";" + getDoctorID()
               + ";" + getRequestDate()
               + ";" + getRequestType()
               + ";" + getRequestDetails()
               + ":" + getRequestStatus();
    }
}