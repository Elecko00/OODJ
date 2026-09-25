import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConsultationFrame extends JFrame
        implements ActionListener {

            private Doctor doctor;

            private JTextField consultationIDField;
            private JTextField patientIDField;
            private JTextField doctorIDField;
            private JTextField dateField;
            private JTextField temperatureField;
            private JTextField bloodPressureField;
            private JTextField heartRateField;
            private JTextArea notesArea;
            private JButton saveButton;
            private JButton backButton;

            public ConsultationFrame(Doctor doctor) {
                this.doctor = doctor;

                setTitle("Record Patient Consultation");
                setSize(600,500);

                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                setLayout(new GridLayout(9,2,10,10));

                consultationIDField = new JTextField();
                patientIDField = new JTextField();

                doctorIDField = new JTextField(this.doctor.getUserID());
                dateField = new JTextField();
                temperatureField = new JTextField();
                bloodPressureField = new JTextField();
                heartRateField = new JTextField();
                notesArea = new JTextArea();

                doctorIDField.setEditable(false);

                saveButton = new JButton("Save Consultation");
                backButton = new JButton("Back");

                add(new JLabel("Consultation ID: "));
                add(consultationIDField);

                add(new JLabel("Patient ID:"));
                add(patientIDField);

                add(new JLabel("Doctor ID:"));
                add(doctorIDField);

                add(new JLabel("Date (YYYY-MM-DD):"));
                add(dateField);

                add(new JLabel("Temperature:"));
                add(temperatureField);

                add(new JLabel("Blood Pressure:"));
                add(bloodPressureField);

                add(new JLabel("Heart Rate:"));
                add(heartRateField);

                add(new JLabel("Consultation Notes:"));
                add(notesArea);

                add(saveButton);
                add(backButton);

                saveButton.addActionListener(this);
                backButton.addActionListener(this);

                setLocationRelativeTo(null);
                setVisible(true);

            }

            @Override 
            public void actionPerformed(ActionEvent event) {
                if (event.getSource() == saveButton) {

                    saveConsultation();

                } else if (event.getSource() == backButton) {

                    dispose();
                }
            }

            private void saveConsultation() {
                String consultationID = 
                consultationIDField.getText().trim();

                String patientID = 
                patientIDField.getText().trim();

                String doctorID =
                this.doctor.getUserID();

                String consultationDate =
                dateField.getText().trim();

                String temperatureText =
                temperatureField.getText().trim();

                String bloodPressure =
                bloodPressureField.getText().trim();

                String heartRateText =
                heartRateField.getText().trim();

                String consultationNotes =
                notesArea.getText().trim();

                if (consultationID.isEmpty()
                || patientID.isEmpty()
                || doctorID.isEmpty()
                || consultationDate.isEmpty()
                || temperatureText.isEmpty()
                || bloodPressure.isEmpty()
                || heartRateText.isEmpty()
                || consultationNotes.isEmpty()) {

                JOptionPane.showMessageDialog(
                    this,
                    "Please complete all consultation fields.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
                }

                if (consultationID.contains(";")
                || patientID.contains(";")
                || consultationDate.contains(";")
                || temperatureText.contains(";")
                || bloodPressure.contains(";")
                || heartRateText.contains(";")
                || consultationNotes.contains(";")) {

                     JOptionPane.showMessageDialog(
                    this,
                    "The semicolon character is not allowed.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

                 if (!isValidDate(consultationDate)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter the date using YYYY-MM-DD.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        double temperature;
        int heartRate;

        try {
            temperature = Double.parseDouble(temperatureText);

            heartRate = Integer.parseInt(heartRateText);
        } catch (NumberFormatException error) {

            JOptionPane.showMessageDialog(
                this,
                "Temperature and heart rate must be number.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (temperature < 30.0
               || temperature > 45.0) {
            JOptionPane.showMessageDialog(
                this,
                "Temperature must between 30.0 and 45.0.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );

            return;

        }

        if (heartRate <20
               || heartRate > 250) {

                JOptionPane.showMessageDialog(
                    this,
                    "Heart rate must between 20 and 250.",
                    "Input error",
                    JOptionPane.ERROR_MESSAGE
                );

                return;
            }

        String[] pressureParts = bloodPressure.split("/");

        if (pressureParts.length != 2) {

            JOptionPane.showMessageDialog(
                this,
                "Blood pressure must use format like 120/80.",
                "Input error",
                JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        int systolicPressure;
        int diastolicPressure;

        try {

            systolicPressure = Integer.parseInt(pressureParts[0]);
            diastolicPressure = Integer.parseInt(pressureParts[1]);

        } catch (NumberFormatException error) {

            JOptionPane.showMessageDialog(
                this,
                "Blood pressure must have numbers.",
                "Input error",
                JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (systolicPressure <= 0
                || diastolicPressure <= 0) {
            JOptionPane.showMessageDialog(
                this,
                "Blood Pressure values must greater than zero.",
                "Input error",
                JOptionPane.ERROR_MESSAGE
            );

            return;
            }

        
        consultationNotes = consultationNotes.replace("\r"," ");
        consultationNotes = consultationNotes.replace("\n"," ");

        ConsultationFileManager duplicateChecker = new ConsultationFileManager();

        if (duplicateChecker.consultationIDExists(consultationID)) {
            JOptionPane.showMessageDialog(this, "This consultation ID already exists.","Duplicate ID", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ConsultationRecord consultation = new ConsultationRecord(
            consultationID, patientID, doctorID, consultationDate, temperature, bloodPressure, heartRate, consultationNotes);

        ConsultationFileManager fileManager = new ConsultationFileManager();

        boolean saveSuccessful = fileManager.saveConsultation(consultation);

        if(saveSuccessful == true) {
            JOptionPane.showMessageDialog(
                this,
                "Consultation saved successfully.",
                "Consultation saved",
                JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Consultation could not be saved",
                "File error",
                JOptionPane.ERROR_MESSAGE
            );
        }
            
    }

     private boolean isValidDate(String date) {

        if (date.length() != 10) {

            return false;
        }

        if (date.charAt(4) != '-'
                || date.charAt(7) != '-') {

            return false;
        }

        for (int i = 0; i < date.length(); i++) {

            if (i != 4 && i != 7) {

                char currentCharacter = date.charAt(i);

                if (currentCharacter < '0'
                        || currentCharacter > '9') {

                    return false;
                }
            }
        }

        int month = Integer.parseInt(
                date.substring(5, 7)
        );

        int day = Integer.parseInt(
                date.substring(8, 10)
        );

        if (month < 1 || month > 12) {

            return false;
        }

        if (day < 1 || day > 31) {

            return false;
        }

        return true;
    }
}






            

                
            
        

