import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MedicalManagerLogin extends JFrame {

    private JTextField idField;
    private JPasswordField passwordField;

    private final String FILE_NAME = "medicalManagers.txt";

    public MedicalManagerLogin() {

        setTitle("Medical Manager Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel idLabel = new JLabel("Manager ID:");
        idField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton clearButton = new JButton("Clear");

        panel.add(idLabel);
        panel.add(idField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(loginButton);
        panel.add(clearButton);

        add(panel);

        loginButton.addActionListener(e -> login());

        clearButton.addActionListener(e -> {
            idField.setText("");
            passwordField.setText("");
        });

        setVisible(true);
    }

    private void login() {

        String managerId = idField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (managerId.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Manager ID and Password.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(new FileReader(FILE_NAME));

            String line;
            boolean loginSuccessful = false;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|");

                if (data.length == 5) {

                    String storedId = data[0];
                    String storedPassword = data[4];

                    if (storedId.equals(managerId)
                            && storedPassword.equals(password)) {

                        loginSuccessful = true;
                        break;
                    }
                }
            }

            reader.close();

            if (loginSuccessful) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();
                
                new medicalmanager(managerId);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Manager ID or Password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (FileNotFoundException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "medicalManagers.txt was not found.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error reading manager data.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        new MedicalManagerLogin();
    }
}
