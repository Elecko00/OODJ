import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminManagementFrame extends JFrame {

    public AdminManagementFrame() {

        setTitle("HMS - Admin Management");
        setSize(300, 280);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title =
                new JLabel("Admin Management");

        title.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        title.setBounds(17, 15, 250, 25);

        JButton addButton =
                new JButton("Add Admin");

        JButton viewButton =
                new JButton("View Admins");

        JButton updateButton =
                new JButton("Update Admin");

        JButton deleteButton =
                new JButton("Delete Admin");

        addButton.setBounds(17, 50, 250, 30);
        viewButton.setBounds(17, 90, 250, 30);
        updateButton.setBounds(17, 130, 250, 30);
        deleteButton.setBounds(17, 170, 250, 30);

        panel.add(title);
        panel.add(addButton);
        panel.add(viewButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        add(panel);

        addButton.addActionListener(e -> addAdmin());
        viewButton.addActionListener(e -> viewAdmins());
        updateButton.addActionListener(e -> updateAdmin());
        deleteButton.addActionListener(e -> deleteAdmin());
    }

    private void addAdmin() {

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField =
                new JPasswordField();

        Object[] message = {
                "Name:", nameField,
                "Email:", emailField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(
                this,
                message,
                "Add Admin",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            String password =
                    new String(passwordField.getPassword());

            if (name.isEmpty()
                    || email.isEmpty()
                    || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill in all fields."
                );

                return;
            }

            ModifyAdmin modify =
                    new ModifyAdmin();

            int newId =
                    modify.getLatestId() + 1;

            String result =
                    modify.writeAdmin(
                            newId,
                            name,
                            email,
                            password,
                            "append"
                    );

            JOptionPane.showMessageDialog(
                    this,
                    result
            );
        }
    }

    private void viewAdmins() {

        LoadAdmin loader = new LoadAdmin();

        loader.readAdmin();

        List<Admin> admins =
                loader.returnAdmin();

        if (admins.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No admins found."
            );

            return;
        }

        StringBuilder result =
                new StringBuilder();

        for (Admin admin : admins) {

            result.append("ID: ")
                    .append(admin.returnID())
                    .append("\n");

            result.append("Name: ")
                    .append(admin.returnName())
                    .append("\n");

            result.append("Email: ")
                    .append(admin.returnEmail())
                    .append("\n");

            result.append("--------------------\n");
        }

        JTextArea textArea =
                new JTextArea(result.toString());

        textArea.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(textArea);

        scrollPane.setPreferredSize(
                new Dimension(350, 250)
        );

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Admin List",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void updateAdmin() {

        String idInput =
                JOptionPane.showInputDialog(
                        this,
                        "Admin ID:",
                        "Update Admin",
                        JOptionPane.QUESTION_MESSAGE
                );

        if (idInput == null) {
            return;
        }

        try {

            int id =
                    Integer.parseInt(idInput.trim());

            JTextField nameField =
                    new JTextField();

            JTextField emailField =
                    new JTextField();

            JPasswordField passwordField =
                    new JPasswordField();

            Object[] message = {
                    "New name:", nameField,
                    "New email:", emailField,
                    "New password:", passwordField
            };

            int option =
                    JOptionPane.showConfirmDialog(
                            this,
                            message,
                            "Update Admin",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );

            if (option == JOptionPane.OK_OPTION) {

                ModifyAdmin modify =
                        new ModifyAdmin();

                String result =
                        modify.writeAdmin(
                                id,
                                nameField.getText().trim(),
                                emailField.getText().trim(),
                                new String(
                                        passwordField.getPassword()
                                ),
                                "update"
                        );

                JOptionPane.showMessageDialog(
                        this,
                        result
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Admin ID."
            );
        }
    }

    private void deleteAdmin() {

        String idInput =
                JOptionPane.showInputDialog(
                        this,
                        "Admin ID:",
                        "Delete Admin",
                        JOptionPane.QUESTION_MESSAGE
                );

        if (idInput == null) {
            return;
        }

        try {

            int id =
                    Integer.parseInt(idInput.trim());

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Delete Admin ID " + id + "?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirm ==
                    JOptionPane.YES_OPTION) {

                ModifyAdmin modify =
                        new ModifyAdmin();

                String result =
                        modify.writeAdmin(
                                id,
                                "",
                                "",
                                "",
                                "delete"
                        );

                JOptionPane.showMessageDialog(
                        this,
                        result
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Admin ID."
            );
        }
    }
}