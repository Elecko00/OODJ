import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

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

    public boolean updateContactNumber(String newContactNumber) {
        ArrayList<String> lines = new ArrayList<>();
        boolean found = false;

        try (Scanner scanner = new Scanner(new File("patients.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split("\\|", -1);

                if (fields.length >= 3 && fields[0].trim().equals(getUserId())) {
                    fields[2] = newContactNumber;
                    line = String.join("|", fields);
                    found = true;
                }

                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            return false;
        }

        if (!found) {
            return false;
        }

        try (FileWriter writer = new FileWriter("patients.txt")) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }

            setContactNumber(newContactNumber);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public void showInfo() {
        System.out.println("Patient ID: " + patientId);
        System.out.println("Name: " + getName());
    }

    public boolean bookAppointment(String doctorName, String dateTime) {
        File appointmentFile = new File("appointments.txt");

        try {
            if (appointmentFile.exists() && appointmentFile.length() > 0) {
                try (RandomAccessFile check =
                             new RandomAccessFile(appointmentFile, "r")) {
                    check.seek(check.length() - 1);
                    int lastCharacter = check.read();

                    if (lastCharacter != '\n' && lastCharacter != '\r') {
                        try (FileWriter separatorWriter =
                                     new FileWriter(appointmentFile, true)) {
                            separatorWriter.write(System.lineSeparator());
                        }
                    }
                }
            }

            try (FileWriter writer = new FileWriter(appointmentFile, true)) {
                writer.write(patientId + "|" + doctorName + "|" + dateTime);
                writer.write(System.lineSeparator());
            }

            appointments.add("Doctor: " + doctorName + " | Time: " + dateTime);
            return true;

        } catch (IOException e) {
            System.out.println("Failed to save appointment: " + e.getMessage());
            return false;
        }
    }
    
    public boolean hasTimeConflict(String doctorId, String dateTime) {
         try (Scanner scanner = new Scanner(new File("appointments.txt"))) {
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split("\\|", -1);

                if (fields.length >= 3
                        && fields[1].contains("(" + doctorId + ")")
                        && fields[2].trim().equals(dateTime.trim())) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
        
        }

        return false;
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

    public String getAppointmentsText() {
        if (appointments.isEmpty()) {
            return "There are no appointments.";
        }

        StringBuilder text = new StringBuilder();

        for (int i = 0; i < appointments.size(); i++) {
            text.append(i + 1)
                .append(". ")
                .append(appointments.get(i))
                .append("\n");
        }

        return text.toString();
    }

    public String[] getAppointmentOptions() {
        return appointments.toArray(new String[0]);
    }

    public boolean cancelAppointment(int index) {
        if (index < 0 || index >= appointments.size()) {
            return false;
        }

        String selected = appointments.get(index);
        int separator = selected.indexOf(" | Time: ");

        if (!selected.startsWith("Doctor: ") || separator < 0) {
           return false;
       }

       String doctorName = selected.substring("Doctor: ".length(), separator);
       String dateTime = selected.substring(separator + " | Time: ".length());
       ArrayList<String> remainingLines = new ArrayList<>();
       boolean found = false;

        try (Scanner scanner = new Scanner(new File("appointments.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split("\\|", -1);

                if (!found
                        && fields.length >= 3
                        && fields[0].trim().equals(patientId)
                        && fields[1].trim().equals(doctorName)
                        && fields[2].trim().equals(dateTime)) {
                    found = true;
                } else {
                    remainingLines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        } 

        if (!found) {
            return false;
        }

        try (FileWriter writer = new FileWriter("appointments.txt")) {
            for (String line : remainingLines) {
                writer.write(line + System.lineSeparator());
            }

            appointments.remove(index);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

           public boolean rescheduleAppointment(
                int index, String newDateTime, String doctorId) {

        if (index < 0 || index >= appointments.size()
                || newDateTime == null || newDateTime.trim().isEmpty()) {
            return false;
        }

        String selected = appointments.get(index);
        int separator = selected.indexOf(" | Time: ");

        if (!selected.startsWith("Doctor: ") || separator < 0) {
            return false;
        }

        String doctorName = selected.substring("Doctor: ".length(), separator);
        String oldDateTime = selected.substring(separator + " | Time: ".length());

        if (hasTimeConflict(doctorId, newDateTime)) {
            return false;
        }

        ArrayList<String> lines = new ArrayList<>();
        boolean found = false;

        try (Scanner scanner = new Scanner(new File("appointments.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split("\\|", -1);

                if (!found
                        && fields.length >= 3
                        && fields[0].trim().equals(patientId)
                        && fields[1].trim().equals(doctorName)
                        && fields[2].trim().equals(oldDateTime)) {
                    fields[2] = newDateTime.trim();
                    line = String.join("|", fields);
                    found = true;
                }

                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            return false;
        }

        if (!found) {
            return false;
        }

        try (FileWriter writer = new FileWriter("appointments.txt")) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }

            appointments.set(
                    index,
                    "Doctor: " + doctorName + " | Time: " + newDateTime.trim()
            );
            return true;
        } catch (IOException e) {
            return false;
        }
    }

        public boolean submitDoctorFeedback(
                String appointmentRecord, int rating, String comment) {

            if (appointmentRecord == null
                || !appointments.contains(appointmentRecord)
                || rating < 1 || rating > 5
                || comment == null || comment.trim().isEmpty()) {
            return false;
            }

            int timeMarker = appointmentRecord.indexOf(" | Time: ");

            if (!appointmentRecord.startsWith("Doctor: ") || timeMarker < 0) {
                return false;
            }

            String doctorText =
                    appointmentRecord.substring("Doctor: ".length(), timeMarker);
            int idStart = doctorText.lastIndexOf("(");
            int idEnd = doctorText.lastIndexOf(")");

            if (idStart < 0 || idEnd < idStart) {
                return false;
            }

            String doctorId = doctorText.substring(idStart + 1, idEnd);
            String appointmentDateTime =
                    appointmentRecord.substring(timeMarker + " | Time: ".length());
            String cleanComment = comment.trim();

            if (cleanComment.contains(";")
                    || cleanComment.contains("\n")
                    || cleanComment.contains("\r")) {
                return false;
            }

            try (FileWriter writer = new FileWriter("data/feedback.txt", true)) {
                writer.write(
                    patientId + ";" + doctorId + ";" + rating + ";"
                    + cleanComment + ";" + appointmentDateTime
                    + System.lineSeparator()
                );
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        public String getConsultationHistoryText() {
                StringBuilder result = new StringBuilder();

            try (Scanner scanner = new Scanner(new File("data/consultations.txt"))) {
                while (scanner.hasNextLine()) {
                    String[] fields = scanner.nextLine().split(";", -1);

                if (fields.length >= 8 && fields[1].trim().equals(patientId)) {
                    result.append("Date: ").append(fields[3])
                          .append("\nDoctor ID: ").append(fields[2])
                          .append("\nTemperature: ").append(fields[4])
                          .append("\nBlood pressure: ").append(fields[5])
                          .append("\nHeart rate: ").append(fields[6])
                          .append("\nNotes: ").append(fields[7])
                          .append("\n\n");
                    }
                }
            } catch (FileNotFoundException e) {
                return "Consultation file not found.";
            }

            return result.length() == 0
                    ? "No consultation history found."
                    : result.toString();
            }

    public String getPrescriptionsText() {
        StringBuilder result = new StringBuilder();

        try (Scanner scanner = new Scanner(new File("data/prescriptions.txt"))) {
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(";", -1);

                if (fields.length >= 9 && fields[1].trim().equals(patientId)) {
                    result.append("Date: ").append(fields[3])
                        .append("\nMedication: ").append(fields[4])
                        .append("\nDosage: ").append(fields[5])
                        .append("\nFrequency: ").append(fields[6])
                        .append("\nDuration: ").append(fields[7])
                        .append("\nInstructions: ").append(fields[8])
                        .append("\n\n");
                }
            }
        } catch (FileNotFoundException e) {
            return "Prescription file not found.";
        }

        return result.length() == 0
                ? "No prescriptions found."
                : result.toString();
    }
}
