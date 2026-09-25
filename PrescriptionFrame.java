import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PrescriptionFrame extends JFrame
        implements ActionListener { 

    private Doctor doctor;

    private JTextField prescriptionIDField;
    private JTextField patientIDField;
    private JTextField doctorIDField;
    private JTextField dateField;
    private JTextField medicationField;
    private JTextField dosageField;
    private JTextField frequencyField;
    private JTextField durationField;
    private JTextArea instructionsArea;
    private JButton saveButton;
    private JButton backButton;

    public PrescriptionFrame(Doctor doctor) {
        this.doctor = doctor;

        setTitle("Issue Medication Prescription");
        setSize(600,550);

        setDefaultCloseOperation(
            JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(new GridLayout(10,2,10,10));

        prescriptionIDField = new JTextField();
        patientIDField = new JTextField();
        doctorIDField = new JTextField(this.doctor.getUserID());

        dateField = new JTextField();
        medicationField = new JTextField();
        dosageField = new JTextField();
        frequencyField = new JTextField();
        durationField = new JTextField();
        instructionsArea = new JTextArea();

        doctorIDField.setEditable(false);

        saveButton = new JButton("Save Prescription");
        backButton = new JButton("Back");

        add(new JLabel("Prescription ID:"));
        add(prescriptionIDField);
        add(new JLabel("Patient ID:"));
        add(patientIDField);
        add(new JLabel("Doctor ID:"));
        add(doctorIDField);
        add(new JLabel("Date (YYYY-MM-DD):"));
        add(dateField);
        add(new JLabel("Medication Name:"));
        add(medicationField);
        add(new JLabel("Dosage:"));
        add(dosageField);
        add(new JLabel("Frequency:"));
        add(frequencyField);
        add(new JLabel("Duration:"));
        add(durationField);
        add(new JLabel("Instructions:"));
        add(instructionsArea);
        add(saveButton);
        add(backButton);

        saveButton.addActionListener(this);
        backButton.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override public void actionPerformed(ActionEvent event) {
        if (event.getSource() == saveButton) {

           savePrescription();

        } else if (event.getSource() == backButton) {
            dispose();
        }
    }

    private void savePrescription() {

        String prescriptionID =
            prescriptionIDField.getText().trim();

        String patientID =
            patientIDField.getText().trim();

        String doctorID =
            this.doctor.getUserID();

        String prescriptionDate =
            dateField.getText().trim();

        String medicationName =
            medicationField.getText().trim();

        String dosage =
            dosageField.getText().trim();

        String frequency =
            frequencyField.getText().trim();

        String duration =
            durationField.getText().trim();

        String instructions =
            instructionsArea.getText().trim();

        if (prescriptionID.isEmpty()
            || patientID.isEmpty()
            || doctorID.isEmpty()
            || prescriptionDate.isEmpty()
            || medicationName.isEmpty()
            || dosage.isEmpty()
            || frequency.isEmpty()
            || duration.isEmpty()
            || instructions.isEmpty()) {

        JOptionPane.showMessageDialog(
               this,
               "Please complete all prescription fiels.",
               "Input error",
               JOptionPane.ERROR_MESSAGE
        );

        return;

        }

        if (prescriptionID.contains(";")
            || patientID.contains(";")
            || prescriptionDate.contains(";")
            || medicationName.contains(";")
            || dosage.contains(";")
            || frequency.contains(";")
            || duration.contains(";")
            || instructions.contains(";")) {

        JOptionPane.showMessageDialog(
            this,
            "The semicolon chractor is not allowed.",
            "Input Error",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    if (!isValidDate(prescriptionDate)) {

        JOptionPane.showMessageDialog(
            this,
            "Enter the date using YYYY-MM-DD.",
            "Input error",
            JOptionPane.ERROR_MESSAGE
        );

        return;
    }

    instructions = instructions.replace("\r", " ");
    instructions = instructions.replace("\n"," ");

    PrescriptionRecord prescription = new PrescriptionRecord(
        prescriptionID,patientID, doctorID, prescriptionDate, medicationName, dosage, frequency, duration, instructions);

    PrescriptionFileManager fileManager = new PrescriptionFileManager();

    boolean saveSuccessful = fileManager.savePrescription(prescription);

    if (saveSuccessful == true) {
        JOptionPane.showMessageDialog(this,"Prescription save successfully","Prescription saved",
            JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Prescription cannot be saved.","File error",
            JOptionPane.ERROR_MESSAGE);
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

        for (int i = 0; i< date.length(); i++) {

            if (i != 4 && i != 7) {
                char currentCharacter =
                       date.charAt(i);
                if (currentCharacter < '0'
                       || currentCharacter > '9') {
                    return false;
                }
            }
        }

        int month = Integer.parseInt(date.substring(5,7));
        int day = Integer.parseInt(date.substring(8,10));

        if (month <1 || month > 12) {
            return false;
        }

        if (day < 1 || day >31) {
            return false;
        }
        return true;
    }   
    }    
