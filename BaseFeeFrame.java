import javax.swing.*;
import java.awt.*;

public class BaseFeeFrame extends JFrame {

    public BaseFeeFrame() {

        setTitle("HMS - Base Fee");
        setSize(300, 180);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title =
                new JLabel("Base Fee Management");

        title.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        title.setBounds(17, 15, 250, 25);

        JButton viewButton =
                new JButton("View Base Fee");

        JButton modifyButton =
                new JButton("Modify Base Fee");

        viewButton.setBounds(17, 50, 250, 30);
        modifyButton.setBounds(17, 90, 250, 30);

        panel.add(title);
        panel.add(viewButton);
        panel.add(modifyButton);

        add(panel);

        viewButton.addActionListener(
                e -> viewFee()
        );

        modifyButton.addActionListener(
                e -> modifyFee()
        );
    }

    private void viewFee() {

        LoadBaseFee loader =
                new LoadBaseFee();

        String result =
                loader.readFee();

        if (result.equals("Base fee loaded")) {

            JOptionPane.showMessageDialog(
                    this,
                    String.format(
                            "Current base fee: RM %.2f",
                            loader.returnFee()
                    )
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    result
            );
        }
    }

    private void modifyFee() {

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "New base fee:",
                        "Modify Base Fee",
                        JOptionPane.QUESTION_MESSAGE
                );

        if (input == null) {
            return;
        }

        try {

            double fee =
                    Double.parseDouble(input.trim());

            if (fee < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Base fee cannot be negative."
                );

                return;
            }

            ModifyBaseFee modify =
                    new ModifyBaseFee();

            String result =
                    modify.writeFee(fee);

            JOptionPane.showMessageDialog(
                    this,
                    result
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid number."
            );
        }
    }
}