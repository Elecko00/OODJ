import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DoctorProfileFrame extends JFrame
         implements ActionListener {

    private Doctor doctor;

    private JTextField doctorIDField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField specializationField;

    private JButton saveButton;
    private JButton backButton;

    public DoctorProfileFrame(Doctor doctor) {

        this.doctor = doctor;

        setTitle("Edit Doctor Profile");
        setSize(500, 350);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(6, 2, 10, 10));

        doctorIDField = new JTextField(this.doctor.getUserID());
        nameField = new JTextField(this.doctor.getName());
        emailField = new JTextField(this.doctor.getEmail());
        phoneField = new JTextField(this.doctor.getPhoneNumber());
        specializationField = new JTextField(this.doctor.getSpecialization());

        doctorIDField.setEditable(false);

        saveButton = new JButton("Save");
        backButton = new JButton("Back");

        add(new JLabel("Doctor ID:"));
        add(doctorIDField);

        add(new JLabel("Name:"));
        add(nameField);

        add(new JLabel("Email:"));
        add(emailField);

        add(new JLabel("Phone Number:"));
        add(phoneField);

        add(new JLabel("Specialization:"));
        add(specializationField);

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

            saveProfile();

        } else if (event.getSource() == backButton) {
            dispose();
        }
    }

    private void saveProfile() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phoneNumber = phoneField.getText().trim();
        String specialization = specializationField.getText().trim();

        if (name.isEmpty()
                || email.isEmpty()
                || phoneNumber.isEmpty()
                || specialization.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in everything!",
                    "Error!",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (!email.contains("@")
                || !email.contains(".")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address!",
                    "Error!",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (!containsOnlyNumbers(phoneNumber)
                || phoneNumber.length() != 10) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid 10-digit phone number!",
                    "Error!",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (name.contains(";")
                || email.contains(";")
                || phoneNumber.contains(";")
                || specialization.contains(";")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please do not use the character ';' in any field!",
                    "Error!",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        this.doctor.setName(name);
        this.doctor.setEmail(email);
        this.doctor.setPhoneNumber(phoneNumber);
        this.doctor.setSpecialization(specialization);

        DoctorFileManager fileManager = new DoctorFileManager();
        boolean updateSuccessful = fileManager.updateDoctor(this.doctor);

        if (updateSuccessful == true) {

            JOptionPane.showMessageDialog(
                    this,
                    "Profile updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Error updating profile. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }


    }

    private boolean containsOnlyNumbers(String value) {
        for (int i= 0; i<value.length(); i++) {
            
            char currentChracter = value.charAt(i);

            if (currentChracter < '0'
                    || currentChracter > '9') {
                return false;
            }
        }
        return true;
    }
}
