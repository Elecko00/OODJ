import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class UpdateRoster extends JFrame {

    private JTextField doctorIdField;
    private JTextField doctorNameField;
    private JTextField dateField;
    private JComboBox<String> shiftBox;

    private final String FILE_NAME = "data/roster.txt";

    public UpdateRoster() {

        setTitle("Update Doctor Roster");
        setSize(700, 400);
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

        JButton searchButton = new JButton("Search");
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

        panel.add(searchButton);
        panel.add(saveButton);

        add(panel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(cancelButton);

        add(bottomPanel, BorderLayout.SOUTH);

        doctorNameField.setEditable(false);
        dateField.setEditable(false);
        shiftBox.setEnabled(false);
        saveButton.setEnabled(false);

        searchButton.addActionListener(e -> {

            String doctorId = doctorIdField.getText().trim();

            if (doctorId.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Doctor ID."
                );

                return;
            }

            try {

                BufferedReader reader =
                        new BufferedReader(new FileReader(FILE_NAME));

                String line;
                boolean found = false;

                while ((line = reader.readLine()) != null) {

                    String[] data = line.split("\\|", -1);

                    if (data.length == 4
                            && data[0].equals(doctorId)) {

                        doctorNameField.setText(data[1]);
                        dateField.setText(data[2]);
                        shiftBox.setSelectedItem(data[3]);

                        found = true;

                        doctorNameField.setEditable(true);
                        dateField.setEditable(true);
                        shiftBox.setEnabled(true);
                        saveButton.setEnabled(true);

                        break;
                    }
                }

                reader.close();

                if (!found) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Doctor roster not found."
                    );

                    doctorNameField.setText("");
                    dateField.setText("");

                    doctorNameField.setEditable(false);
                    dateField.setEditable(false);
                    shiftBox.setEnabled(false);
                    saveButton.setEnabled(false);
                }

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error reading roster file."
                );
            }
        });

        saveButton.addActionListener(e -> {

            String doctorId = doctorIdField.getText().trim();
            String doctorName = doctorNameField.getText().trim();
            String date = dateField.getText().trim();
            String shift = (String) shiftBox.getSelectedItem();

            if (doctorName.isEmpty() || date.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill in all fields."
                );

                return;
            }

            try {

                ArrayList<String> rosters = new ArrayList<>();

                BufferedReader reader =
                        new BufferedReader(new FileReader(FILE_NAME));

                String line;

                while ((line = reader.readLine()) != null) {

                    String[] data = line.split("\\|", -1);

                    if (data.length == 4
                            && data[0].equals(doctorId)) {

                        rosters.add(
                                doctorId + "|"
                                + doctorName + "|"
                                + date + "|"
                                + shift
                        );

                    }
                    else {

                        rosters.add(line);
                    }
                }

                reader.close();

                BufferedWriter writer =
                        new BufferedWriter(new FileWriter(FILE_NAME));

                for (String roster : rosters) {

                    writer.write(roster);
                    writer.newLine();
                }

                writer.close();

                JOptionPane.showMessageDialog(
                        this,
                        "Roster updated successfully!"
                );

                doctorNameField.setEditable(false);
                dateField.setEditable(false);
                shiftBox.setEnabled(false);
                saveButton.setEnabled(false);

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error updating roster."
                );
            }
        });

        cancelButton.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }
}
