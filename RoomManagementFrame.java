import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RoomManagementFrame extends JFrame {

    public RoomManagementFrame() {

        setTitle("HMS - Room Management");
        setSize(300, 280);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title =
                new JLabel("Room Management");

        title.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        title.setBounds(17, 15, 250, 25);

        JButton addButton =
                new JButton("Add Room");

        JButton viewButton =
                new JButton("View Rooms");

        JButton updateButton =
                new JButton("Update Room");

        JButton deleteButton =
                new JButton("Delete Room");

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

        addButton.addActionListener(e -> addRoom());
        viewButton.addActionListener(e -> viewRooms());
        updateButton.addActionListener(e -> updateRoom());
        deleteButton.addActionListener(e -> deleteRoom());
    }

    private void addRoom() {

        JTextField name =
                new JTextField();

        JTextField type =
                new JTextField();

        JTextField role =
                new JTextField();

        JTextField userId =
                new JTextField();

        Object[] message = {
                "Room name:", name,
                "Room type:", type,
                "Assigned role:", role,
                "Assigned user ID:", userId
        };

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        message,
                        "Add Room",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (option == JOptionPane.OK_OPTION) {

            try {

                int assignedId =
                        Integer.parseInt(
                                userId.getText().trim()
                        );

                ModifyRooms modify =
                        new ModifyRooms();

                int roomId =
                        modify.returnLatestRoomId() + 1;

                String result =
                        modify.writeRoom(
                                roomId,
                                name.getText().trim(),
                                type.getText().trim(),
                                role.getText().trim(),
                                assignedId,
                                "add"
                        );

                JOptionPane.showMessageDialog(
                        this,
                        result
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "User ID must be a number."
                );
            }
        }
    }

    private void viewRooms() {

        LoadRooms loader =
                new LoadRooms();

        loader.readRooms();

        List<Room> rooms =
                loader.returnRooms();

        StringBuilder result =
                new StringBuilder();

        for (Room room : rooms) {

            result.append("Room ID: ")
                    .append(room.returnRoomId())
                    .append("\n");

            result.append("Name: ")
                    .append(room.returnRoomName())
                    .append("\n");

            result.append("Type: ")
                    .append(room.returnRoomType())
                    .append("\n");

            result.append("Assigned Role: ")
                    .append(
                            room.returnAssignmentUserRole()
                    )
                    .append("\n");

            result.append("Assigned User ID: ")
                    .append(
                            room.returnAssignmentUserId()
                    )
                    .append("\n");

            result.append("--------------------\n");
        }

        JTextArea area =
                new JTextArea(result.toString());

        area.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(area);

        scroll.setPreferredSize(
                new Dimension(350, 250)
        );

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Room List",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void updateRoom() {

        String idText =
                JOptionPane.showInputDialog(
                        this,
                        "Room ID:",
                        "Update Room",
                        JOptionPane.QUESTION_MESSAGE
                );

        if (idText == null) {
            return;
        }

        try {

            int roomId =
                    Integer.parseInt(idText.trim());

            JTextField name =
                    new JTextField();

            JTextField type =
                    new JTextField();

            JTextField role =
                    new JTextField();

            JTextField userId =
                    new JTextField();

            Object[] fields = {
                    "Room name:", name,
                    "Room type:", type,
                    "Assigned role:", role,
                    "Assigned user ID:", userId
            };

            int option =
                    JOptionPane.showConfirmDialog(
                            this,
                            fields,
                            "Update Room",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );

            if (option == JOptionPane.OK_OPTION) {

                int assignedId =
                        Integer.parseInt(
                                userId.getText().trim()
                        );

                ModifyRooms modify =
                        new ModifyRooms();

                String result =
                        modify.writeRoom(
                                roomId,
                                name.getText().trim(),
                                type.getText().trim(),
                                role.getText().trim(),
                                assignedId,
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
                    "ID must be a number."
            );
        }
    }

    private void deleteRoom() {

        String idText =
                JOptionPane.showInputDialog(
                        this,
                        "Room ID:",
                        "Delete Room",
                        JOptionPane.QUESTION_MESSAGE
                );

        if (idText == null) {
            return;
        }

        try {

            int id =
                    Integer.parseInt(idText.trim());

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Delete Room ID " + id + "?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirm ==
                    JOptionPane.YES_OPTION) {

                ModifyRooms modify =
                        new ModifyRooms();

                String result =
                        modify.writeRoom(
                                id,
                                "",
                                "",
                                "",
                                0,
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
                    "Invalid Room ID."
            );
        }
    }
}