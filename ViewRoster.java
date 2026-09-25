import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ViewRoster extends JFrame {

    private final String FILE_NAME = "roster.txt";

    public ViewRoster() {

        setTitle("View Doctor Roster");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {
                "Doctor ID",
                "Doctor Name",
                "Date",
                "Shift"
        };

        javax.swing.table.DefaultTableModel model =
                new javax.swing.table.DefaultTableModel(
                        columnNames,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        JTable table = new JTable(model);

        table.setRowHeight(30);

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        table.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(FILE_NAME)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data =
                        line.split("\\|", -1);

                if (data.length == 4) {

                    model.addRow(
                            new Object[]{
                                    data[0],
                                    data[1],
                                    data[2],
                                    data[3]
                            }
                    );
                }
            }

            reader.close();

        } catch (FileNotFoundException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No roster file found."
            );

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error reading roster file."
            );
        }

        JScrollPane scrollPane =
                new JScrollPane(table);

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        JButton closeButton =
                new JButton("Close");

        closeButton.addActionListener(e -> {
            dispose();
        });

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.add(closeButton);

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        setVisible(true);
    }
}