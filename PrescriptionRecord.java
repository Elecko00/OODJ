public class PrescriptionRecord {

    private String prescriptionID;
    private String patientID;
    private String doctorID;
    private String prescriptionDate;

    private String medicationName;
    private String dosage;
    private String frequency;
    private String duration;
    private String instructions;

    public PrescriptionRecord(
            String prescriptionID,
            String patientID,
            String doctorID,
            String prescriptionDate,
            String medicationName,
            String dosage,
            String frequency,
            String duration,
            String instructions) {

        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.prescriptionDate = prescriptionDate;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.instructions = instructions;
    }

    public String getPrescriptionID() {
        return prescriptionID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {

        return doctorID;
    }

    public String getPrescriptionDate() {

        return prescriptionDate;
    }

    public String getMedicationName() {

        return medicationName;
    }

     public String getDosage() {

        return dosage;
    }

     public String getFrequency() {

        return frequency;
    }

     public String getDuration() {

        return duration;
    }

    public String getInstructions() {

        return instructions;
    }

    public String toTextLine() {

        return getPrescriptionID()
                + ";" + getPatientID()
                + ";" + getDoctorID()
                + ";" + getPrescriptionDate()
                + ";" + getMedicationName()
                + ";" + getDosage()
                + ";" + getFrequency()
                + ";" + getDuration()
                + ";" + getInstructions();
        }
    }
    

