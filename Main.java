import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("HMS - Login");
        frame.setSize(320, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("Username: ");
        userLabel.setBounds(20, 20, 100, 25);
        frame.add(userLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(120, 20, 150, 25);
        frame.add(usernameField);

        JLabel passLabel = new JLabel("Password: ");
        passLabel.setBounds(20, 60, 100, 25);
        frame.add(passLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(120, 60, 150, 25);
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(120, 100, 100, 30);
        frame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    Scanner fileScanner = new Scanner(new File("users.txt"));

                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        String[] field = line.split("\\|");

                        if (field.length < 5) {
                            continue;
                        }

                        String fileUsername = field[0];
                        String filePassword = field[1];
                        String userId = field[2];
                        String name = field[3];
                        String role = field[4];

                        if (fileUsername.equals(username) && filePassword.equals(password)) {

                            frame.dispose();

                            if (role.equals("Patient")) {
                                openPatientDashboard(userId, username, password, name);
                            } else if (role.equals("Doctor")) {
                                openDoctorDashboard(userId, username, password);
                            } else if (role.equals("Manager")) {
                                openManagerDashboard(userId, username, password, name);
                            } else if (role.equals("Admin")) {
                                openAdminDashboard(userId, username, password, name);
                            } else {
                                JOptionPane.showMessageDialog(null, "Unknown role: " + role);
                            }

                            fileScanner.close();
                            return;
                        }
                    }
                    fileScanner.close();
                    JOptionPane.showMessageDialog(null, "Incorrect Username or Password");

                } catch (FileNotFoundException ex) {
                    System.out.println("Can't find users.txt");
                }
            }
        });

        frame.setVisible(true);
    }

    private static JComboBox<String> createRosterCombo(String doctorId, Patient patient) {
        JComboBox<String> rosterBox = new JComboBox<>();
        LocalDate today = LocalDate.now();

        try (BufferedReader reader =
                    new BufferedReader(new FileReader("data/roster.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|", -1);

                if (fields.length < 4) {
                    continue;
                }

                String rosterDoctorId = fields[0].trim();
                String dateText = fields[2].trim();
                String shift = fields[3].trim();

                if (!rosterDoctorId.equalsIgnoreCase(doctorId)) {
                    continue;
                }

                LocalDate rosterDate;
                try {
                    rosterDate = LocalDate.parse(dateText);
                } catch (DateTimeParseException e) {
                    continue;
                }

                if (rosterDate.isBefore(today)) {
                    continue;
                }

            LocalTime start;
            LocalTime end;

            if (shift.equalsIgnoreCase("Morning")) {
                start = LocalTime.of(9, 0);
                end = LocalTime.of(12, 0);
            } else if (shift.equalsIgnoreCase("Afternoon")) {
                start = LocalTime.of(12, 0);
                end = LocalTime.of(14, 0);
            } else if (shift.equalsIgnoreCase("Evening")) {
                start = LocalTime.of(14, 0);
                end = LocalTime.of(17, 0);
            } else if (shift.equalsIgnoreCase("Night")) {
                start = LocalTime.of(17, 0);
                end = LocalTime.of(20, 0);
            } else {
                continue;
            }

            while (!start.plusMinutes(30).isAfter(end)) {
                String slot = rosterDate + " - " + start;

                if (!patient.hasTimeConflict(doctorId, slot)) {
                    rosterBox.addItem(slot);
                }

                start = start.plusMinutes(30);
            }
            
        }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Could not read data/roster.txt: " + e.getMessage()
                );
            }

        return rosterBox;

        }

    private static JComboBox<String> createDoctorCombo(
        Map<String, String> doctorIdByLabel) {

    JComboBox<String> doctorBox = new JComboBox<>();

    try (BufferedReader reader =
                new BufferedReader(new FileReader("data/doctors.txt"))) {

        String line;

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(";", -1);

            if (fields.length < 5) {
                continue;
            }

            String doctorId = fields[0].trim();
            String doctorName = fields[1].trim();

            if (doctorId.isEmpty() || doctorName.isEmpty()) {
                continue;
            }

            String label = doctorName + " (" + doctorId + ")";
            doctorIdByLabel.put(label, doctorId);
            doctorBox.addItem(label);
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(
                null,
                "Could not read data/doctors.txt: " + e.getMessage()
        );
    }

    return doctorBox;
}

    private static void openPatientDashboard(String userId, String username, String password, String name) {
        String patientId = null;
        String contactNumber = null;

        try {
            Scanner sc = new Scanner(new File("patients.txt"));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] f = line.split("\\|");
                if (f.length >= 3 && f[0].equals(userId)) {
                    patientId = f[1];
                    contactNumber = f[2];
                    break;
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Can't find patients.txt");
            return;
        }

        if (patientId == null) {
            JOptionPane.showMessageDialog(null, "No patient record found for this account.");
            return;
        }

        Patient p1 = new Patient(userId, username, password, name, patientId, contactNumber);
        p1.loadAppointments();

        JFrame dashboard = new JFrame("HMS - Patient Dashboard");
        dashboard.setSize(320, 370);
        dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboard.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome, " + p1.getName());
        welcomeLabel.setBounds(20, 20, 250, 25);
        dashboard.add(welcomeLabel);

        JButton bookBtn = new JButton("Book Appointment");
        bookBtn.setBounds(20, 60, 180, 30);
        dashboard.add(bookBtn);

        JButton viewBtn = new JButton("View Appointments");
        viewBtn.setBounds(20, 100, 180, 30);
        dashboard.add(viewBtn);

        JButton editProfileBtn = new JButton("Edit Profile");
        editProfileBtn.setBounds(20, 140, 180, 30);
        dashboard.add(editProfileBtn);

        editProfileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newPhone = JOptionPane.showInputDialog(
                    dashboard,
                    "Enter your new contact number:",
                    p1.getContactNumber()
            );

            if (newPhone == null) {
                return;
            }

            newPhone = newPhone.trim();

            if (newPhone.isEmpty()) {
                JOptionPane.showMessageDialog(dashboard, "Contact number cannot be empty.");
            } else if (p1.updateContactNumber(newPhone)) {
                JOptionPane.showMessageDialog(dashboard, "Profile updated.");
            } else {
                JOptionPane.showMessageDialog(dashboard, "Could not update patients.txt.");
            }
        }
    });

        bookBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e2) {
                Map<String, String> doctorIdByLabel = new LinkedHashMap<>();
                JComboBox<String> doctorBox = createDoctorCombo(doctorIdByLabel);

            if (doctorBox.getItemCount() == 0) {
                JOptionPane.showMessageDialog(null, "No doctors are available.");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                null,
                doctorBox, 
                "Choose a doctor",
                JOptionPane.OK_CANCEL_OPTION
            );

            if (choice != JOptionPane.OK_OPTION) {
                return;
            }

        String selectedLabel = (String) doctorBox.getSelectedItem();
        String selectedDoctorId = doctorIdByLabel.get(selectedLabel);
        String doctorName = selectedLabel;
                
        JComboBox<String> rosterBox =
            createRosterCombo(selectedDoctorId, p1);
            
            if (rosterBox.getItemCount() == 0) {
                JOptionPane.showMessageDialog(null, "No available roster for this doctor.");
                return;
            }

            int slotChoice = JOptionPane.showConfirmDialog(
                null,
                rosterBox,
                "Choose a date and shift",
                JOptionPane.OK_CANCEL_OPTION
            );

            if (slotChoice != JOptionPane.OK_OPTION) {
                return;
            }

            String dateTime = (String) rosterBox.getSelectedItem();
                
                boolean missing = doctorName == null || doctorName.trim().isEmpty() 
                        || dateTime == null || dateTime.trim().isEmpty();

                if (missing) {
                    JOptionPane.showMessageDialog(null, "Booking cancelled.");
                } else if (p1.hasTimeConflict(selectedDoctorId, dateTime)) {
                    JOptionPane.showMessageDialog(
                        null,
                        "This doctor is already booked for that date and shift."
                    );
                } else if (p1.bookAppointment(doctorName, dateTime)) {
                    JOptionPane.showMessageDialog(null, "Appointment booked!");
                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        "Could not save appointment. "
                    );
                } 
            }                           
        });

        viewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e2) {
                JTextArea appointmentArea =
                    new JTextArea(p1.getAppointmentsText());

            appointmentArea.setEditable(false);
            appointmentArea.setLineWrap(true);
            appointmentArea.setWrapStyleWord(true);

            JFrame appointmentsFrame = new JFrame("My Appointments");
            appointmentsFrame.add(new JScrollPane(appointmentArea));
            appointmentsFrame.setSize(450, 300);
            appointmentsFrame.setLocationRelativeTo(dashboard);
            appointmentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                appointmentsFrame.setVisible(true);
            }
        });

        JButton recordsBtn = new JButton("Medical Records");
        recordsBtn.setBounds(20, 180, 180, 30);
        dashboard.add(recordsBtn);

        recordsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTabbedPane tabs = new JTabbedPane();

                JTextArea consultationArea =
                        new JTextArea(p1.getConsultationHistoryText());
                consultationArea.setEditable(false);

                JTextArea prescriptionArea =
                        new JTextArea(p1.getPrescriptionsText());
                prescriptionArea.setEditable(false);

                tabs.addTab("Consultations", new JScrollPane(consultationArea));
                tabs.addTab("Prescriptions", new JScrollPane(prescriptionArea));

                JFrame recordsFrame = new JFrame("My Medical Records");
                recordsFrame.add(tabs);
                recordsFrame.setSize(600, 400);
                recordsFrame.setLocationRelativeTo(dashboard);
                recordsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                recordsFrame.setVisible(true);
            }
        }); 

        JButton cancelBtn = new JButton("Cancel Appointment");
        cancelBtn.setBounds(20, 220, 180, 30);
        dashboard.add(cancelBtn);

        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] options = p1.getAppointmentOptions();

                if (options.length == 0) {
                    JOptionPane.showMessageDialog(dashboard, "There are no appointments to cancel.");
                    return;
                }

                JComboBox<String> appointmentBox = new JComboBox<>(options);

                int choice = JOptionPane.showConfirmDialog(
                        dashboard,
                        appointmentBox,
                        "Choose an appointment to cancel",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (choice != JOptionPane.OK_OPTION) {
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        dashboard,
                        "Cancel the selected appointment?",
                        "Confirm cancellation",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    if (p1.cancelAppointment(appointmentBox.getSelectedIndex())) {
                        JOptionPane.showMessageDialog(dashboard, "Appointment cancelled.");
                } else {
                        JOptionPane.showMessageDialog(dashboard, "Could not cancel the appointment.");
                }
            }
        }
    });

    JButton rescheduleBtn = new JButton("Reschedule Appointment");
    rescheduleBtn.setBounds(20, 260, 200, 30);
    dashboard.add(rescheduleBtn);

    rescheduleBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String[] options = p1.getAppointmentOptions();

            if (options.length == 0) {
                JOptionPane.showMessageDialog(dashboard, "There are no appointments to reschedule.");
                return;
            }

            JComboBox<String> appointmentBox = new JComboBox<>(options);

            int appointmentChoice = JOptionPane.showConfirmDialog(
                    dashboard,
                    appointmentBox,
                    "Choose an appointment",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (appointmentChoice != JOptionPane.OK_OPTION) {
                return;
            }

            String selected = (String) appointmentBox.getSelectedItem();
            int timeMarker = selected.indexOf(" | Time: ");

            if (timeMarker < 0) {
                JOptionPane.showMessageDialog(dashboard, "Could not read the selected appointment.");
                return;
            }

            String doctorText = selected.substring("Doctor: ".length(), timeMarker);
            int idStart = doctorText.lastIndexOf("(");
            int idEnd = doctorText.lastIndexOf(")");

            if (idStart < 0 || idEnd < idStart) {
                JOptionPane.showMessageDialog(dashboard, "This appointment has no doctor ID.");
                return;
            }

            String doctorId = doctorText.substring(idStart + 1, idEnd);
            JComboBox<String> rosterBox = createRosterCombo(doctorId, p1);

            if (rosterBox.getItemCount() == 0) {
                JOptionPane.showMessageDialog(dashboard, "No available slots for this doctor.");
                return;
            }

            int slotChoice = JOptionPane.showConfirmDialog(
                    dashboard,
                    rosterBox,
                    "Choose a new date and time",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (slotChoice != JOptionPane.OK_OPTION) {
                return;
            }

            String newDateTime = (String) rosterBox.getSelectedItem();

            if (p1.rescheduleAppointment(
                    appointmentBox.getSelectedIndex(), newDateTime, doctorId)) {
                JOptionPane.showMessageDialog(dashboard, "Appointment rescheduled.");
            } else {
                JOptionPane.showMessageDialog(
                        dashboard,
                        "Could not reschedule. The selected slot may already be booked."
                );
            }
        }
    });

    JButton feedbackBtn = new JButton("Rate Doctor");
    feedbackBtn.setBounds(20, 300, 200, 30);
    dashboard.add(feedbackBtn);

    feedbackBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String[] appointmentOptions = p1.getAppointmentOptions();

        if (appointmentOptions.length == 0) {
            JOptionPane.showMessageDialog(
                    dashboard,
                    "There are no appointments to rate."
            );
            return;
        }

        JComboBox<String> appointmentBox =
                new JComboBox<>(appointmentOptions);

        int appointmentChoice = JOptionPane.showConfirmDialog(
                dashboard,
                appointmentBox,
                "Choose an appointment to rate",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (appointmentChoice != JOptionPane.OK_OPTION) {
            return;
        }

        String appointmentRecord =
                (String) appointmentBox.getSelectedItem();

        JComboBox<String> ratingBox =
                new JComboBox<>(new String[] {"1", "2", "3", "4", "5"});
        JTextField commentField = new JTextField(20);

        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(
                new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS)
        );
        feedbackPanel.add(new JLabel("Rating (1 = lowest, 5 = highest):"));
        feedbackPanel.add(ratingBox);
        feedbackPanel.add(new JLabel("Comment:"));
        feedbackPanel.add(commentField);

        int feedbackChoice = JOptionPane.showConfirmDialog(
                dashboard,
                feedbackPanel,
                "Rate the selected appointment",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (feedbackChoice != JOptionPane.OK_OPTION) {
            return;
        }

        int rating =
                Integer.parseInt((String) ratingBox.getSelectedItem());
        String comment = commentField.getText().trim();

        if (p1.submitDoctorFeedback(
                appointmentRecord, rating, comment)) {
            JOptionPane.showMessageDialog(
                    dashboard,
                    "Thank you for your feedback."
            );
        } else {
            JOptionPane.showMessageDialog(
                    dashboard,
                    "Please enter a comment without semicolons."
            );
        }
    }
    });
        dashboard.setVisible(true);
    }

    private static void openDoctorDashboard(String userId, String username, String password) {
        DoctorFileManager fileManager = new DoctorFileManager();
        Doctor doctor = fileManager.loadDoctorByID(userId, username, password);

        if (doctor == null) {
            JOptionPane.showMessageDialog(null, "No doctor record found for ID: " + userId);
            return;
        }

        new DoctorDashboard(doctor);
    }

    private static void openManagerDashboard(String userId, String username, String password, String name) {
        String email = null;
        String phone = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/medicalManagers.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] f = line.split("\\|");
                if (f.length >= 4 && f[0].equals(userId)) {
                    email = f[2];
                    phone = f[3];
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Can't find data/medicalManagers.txt");
            return;
        }

        if (email == null) {
            JOptionPane.showMessageDialog(null, "No manager record found for ID: " + userId);
            return;
        }

        Manager manager = new Manager(userId, username, password, name, email, phone);
        new medicalmanager(manager.getUserId());
    }

    private static void openAdminDashboard(String userId, String username, String password, String name) {
        String email = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/admin.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] f = line.split(",\\s*");
                if (f.length >= 4 && f[0].trim().equals(userId)) {
                    email = f[2].trim();
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Can't find data/admin.txt");
            return;
        }

        if (email == null) {
            JOptionPane.showMessageDialog(null, "No admin record found for ID: " + userId);
            return;
        }

        Admin admin = new Admin(Integer.parseInt(userId), username, password, name, email);
        AdminPage ap = new AdminPage(admin);
        ap.setVisible(true);
    }
}
