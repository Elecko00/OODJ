public class ConsultationRecord {

    private String consultationID;
    private String patientID;
    private String doctorID;
    private String consultationDate;

    private double temperature;
    private String bloodPressure;
    private int heartRate;

    private String consultationNotes;

    public ConsultationRecord(
        String consultationID,
        String patientID,
        String doctorID,
        String consultationDate,
        double temperature,
        String bloodPressure,
        int heartRate,
        String consultationNotes) 
        
        {
            this.consultationID = consultationID;
            this.patientID = patientID;
            this.doctorID = doctorID;
            this.consultationDate = consultationDate;
            this.temperature = temperature;
            this.bloodPressure = bloodPressure;
            this.heartRate = heartRate;
            this.consultationNotes = consultationNotes;
        }

    public String getConsultationID() {
        return consultationID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getConsultationDate() {
        return consultationDate;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public String toTextLine() {

        return getConsultationID() + ";"
                + getPatientID() + ";"
                + getDoctorID() + ";"
                + getConsultationDate() + ";"
                + getTemperature() + ";"
                + getBloodPressure() + ";"
                + getHeartRate() + ";"
                + getConsultationNotes();
    }
}
