import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private static final Color BG = new Color(221, 230, 237);
    private static final Color NAVY = new Color(39, 55, 77);

    public DashboardPanel(Admin admin) {

        setLayout(new BorderLayout());
        setBackground(BG);

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        header.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 20, 30)
        );

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(NAVY);

        JLabel welcome = new JLabel(
                "Welcome back, " + admin.returnName()
        );
        welcome.setFont(new Font("Arial", Font.PLAIN, 15));

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(welcome);

        add(header, BorderLayout.NORTH);

        JPanel cards = new JPanel(
                new GridLayout(2, 2, 20, 20)
        );
        cards.setOpaque(false);

        cards.setBorder(
                BorderFactory.createEmptyBorder(10, 30, 30, 30)
        );

        cards.add(createCard(
                "Admin Management",
                "Manage administrator records"
        ));

        cards.add(createCard(
                "Room Management",
                "Manage hospital rooms"
        ));

        cards.add(createCard(
                "Insurance Management",
                "Manage insurance providers"
        ));

        cards.add(createCard(
                "Base Fee",
                "View and update consultation base fee"
        ));

        add(cards, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, String description) {

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 19)
        );

        JLabel descLabel = new JLabel(
                "<html>" + description + "</html>"
        );
        descLabel.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(descLabel);

        return card;
    }
}