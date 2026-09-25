import javax.swing.*;
import java.awt.*;
import java.io.*;

public class AddRoster extends JFrame {

    private JTextField doctorIdField;
    private JTextField doctorNameField;
    private JTextField dateField;
    private JComboBox<String> shiftBox;

    private final String FILE_NAME = "roster.txt";

    public AddRoster() {

        setTitle("Add Doctor Roster");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel doctorIdLabel = new JLabel("Doctor ID:");
        doctorIdField = new JTextField();

        JLabel doctorNameLabel = new JLabel("Doctor Name:");
        doctorNameField = new JTextField();

        JLabel dateLabel = new JLabel("Date:");
        dateField = new JTextField();

        JLabel shiftLabel = new JLabel("Shift:");

        String[] shifts = {
                "Morning",
                "Afternoon",
                "Evening",
                "Night"
        };

        shiftBox = new JComboBox<>(shifts);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        panel.add(doctorIdLabel);
        panel.add(doctorIdField);

        panel.add(doctorNameLabel);
        panel.add(doctorNameField);

        panel.add(dateLabel);
        panel.add(dateField);

        panel.add(shiftLabel);
        panel.add(shiftBox);

        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel);

        // Save roster
        saveButton.addActionListener(e -> {

            String doctorId = doctorIdField.getText().trim();
            String doctorName = doctorNameField.getText().trim();
            String date = dateField.getText().trim();
            String shift = (String) shiftBox.getSelectedItem();

            if (doctorId.isEmpty()
                    || doctorName.isEmpty()
                    || date.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill in all fields."
                );

                return;
            }

            try {

                BufferedReader reader =
                        new BufferedReader(new FileReader(FILE_NAME));

                String line;
                boolean duplicate = false;

                while ((line = reader.readLine()) != null) {

                    String[] data = line.split("\\|", -1);

                    if (data.length == 4
                            && data[0].equals(doctorId)
                            && data[2].equals(date)) {

                        duplicate = true;
                        break;
                    }
                }

                reader.close();

                if (duplicate) {

                    JOptionPane.showMessageDialog(
                            this,
                            "This doctor already has a roster on this date."
                    );

                    return;
                }

                FileWriter writer =
                        new FileWriter(FILE_NAME, true);

                writer.write(
                        doctorId + "|"
                        + doctorName + "|"
                        + date + "|"
                        + shift
                );

                writer.write(System.lineSeparator());

                writer.close();

                JOptionPane.showMessageDialog(
                        this,
                        "Roster added successfully!"
                );

                doctorIdField.setText("");
                doctorNameField.setText("");
                dateField.setText("");
                shiftBox.setSelectedIndex(0);

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error saving roster."
                );
            }
        });

        // Cancel
        cancelButton.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }
}
