import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Patient extends User {
    private String patientId;
    private String contactNumber;
    private ArrayList<String> appointments;

    public Patient(String userId, String username, String password, String name, String patientId, String contactNumber) {
        super(userId, username, password, name);
        this.patientId = patientId;
        this.contactNumber = contactNumber;
        this.appointments = new ArrayList<String>();
    }

    public String getPatientId() {
        return patientId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void showInfo() {
        System.out.println("Patient ID: " + patientId);
        System.out.println("Name: " + getName());
    }

    public void bookAppointment(String doctorName, String dateTime) {
        String record = "Doctor: " + doctorName + " | Time: " + dateTime;
        this.appointments.add(record);

        try {
            FileWriter writer = new FileWriter("appointments.txt", true);
            writer.write(patientId + "|" + doctorName + "|" + dateTime);
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to save appointment.");
        }
    }

    public void loadAppointments() {
        try {
            Scanner fileScanner = new Scanner(new File("appointments.txt"));

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] fields = line.split("\\|");

                if (fields.length < 3) {
                    continue;
                }

                String filePatientId = fields[0];

                if (filePatientId.equals(patientId)) {
                    String doctorName = fields[1];
                    String dateTime = fields[2];
                    String record = "Doctor: " + doctorName + " | Time: " + dateTime;
                    this.appointments.add(record);
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("There are no Appointments.");
        }
    }

    public void viewAppointments() {
        System.out.println("--My Reservation--");
        if (appointments.isEmpty()) {
            System.out.println("There are no reservation.");
        }
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println("[" + i + "]" + appointments.get(i));
        }
    }
}
