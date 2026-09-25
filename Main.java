import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;

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

                            // 依角色分流：Patient / Doctor / Manager
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

    // ---------- Patient ----------
    // 去 patients.txt 找出这个userId对应的 patientId / contactNumber，
    // 再组成 Patient 物件，打开跟之前一样的病人主页
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
        dashboard.setSize(320, 200);
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

        bookBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e2) {
                String doctorName = JOptionPane.showInputDialog("Doctor name:");
                String dateTime = JOptionPane.showInputDialog("Date/Time:");

                boolean missing = doctorName == null || doctorName.trim().isEmpty()
                        || dateTime == null || dateTime.trim().isEmpty();

                if (missing) {
                    JOptionPane.showMessageDialog(null, "Booking cancelled.");
                } else {
                    p1.bookAppointment(doctorName, dateTime);
                    JOptionPane.showMessageDialog(null, "Appointment booked!");
                }
            }
        });

        viewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e2) {
                p1.viewAppointments();
            }
        });

        dashboard.setVisible(true);
    }

    // ---------- Doctor ----------
    // 用 DoctorFileManager 去 doctors.txt 找专科等资料，
    // 把登入时的username/password一起组成完整的Doctor物件，打开组员写好的DoctorDashboard
    private static void openDoctorDashboard(String userId, String username, String password) {
        DoctorFileManager fileManager = new DoctorFileManager();
        Doctor doctor = fileManager.loadDoctorByID(userId, username, password);

        if (doctor == null) {
            JOptionPane.showMessageDialog(null, "No doctor record found for ID: " + userId);
            return;
        }

        new DoctorDashboard(doctor);
    }

    // ---------- Manager ----------
    // 去 medicalManagers.txt 找 email/phone，组成Manager物件，
    // 打开组员写好的 medicalmanager 主画面（它只需要一个managerId字符串）
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

    // ---------- Admin ----------
    // 去 data/admin.txt 找出这个userId(=adminId)对应的 email，
    // 组成Admin物件，打开Darren写的AdminPage
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
