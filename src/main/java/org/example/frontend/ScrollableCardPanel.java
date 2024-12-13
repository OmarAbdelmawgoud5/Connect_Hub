package org.example.frontend;

import org.example.backend.Group;
import org.example.backend.UserJson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ScrollableCardPanel {
    public static void view(Group g) throws IOException {
        // Create the main frame
        JFrame frame = new JFrame("Scrollable Cards Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800); // Make the panel bigger

        // Create the scrollable panel
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add cards to the panel
        for (int i = 0; i < g.getMembersId().size(); i++) {

            cardPanel.add(createCard(new UserJson().LoadUser(g.getMembersId().get(i)).getUserName() ,new UserJson().LoadUser(g.getMembersId().get(i)).getProfilePhoto()));
        }

        // Add the scroll pane to the frame
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private static JPanel createCard(String name, String imagePath) {
        // Create a panel for the card
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card.setPreferredSize(new Dimension(550, 150)); // Make the panel bigger

        // Add photo
        JLabel photoLabel = new JLabel();
        photoLabel.setIcon(resizeImage(imagePath, 120, 120)); // Resize image to fit
        card.add(photoLabel, BorderLayout.WEST);

        // Add name and button in a panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(nameLabel, BorderLayout.CENTER);

        JButton button = new JButton("Action");
        button.setPreferredSize(new Dimension(60, 25)); // Smaller button size

        // Add styling to the button
        button.setBackground(new Color(70, 130, 180)); // Stylish blue background
        button.setForeground(Color.WHITE);            // White text
        button.setFocusPainted(false);                // Remove focus border
        button.setFont(new Font("Arial", Font.BOLD, 12)); // Set a custom font
        button.setBorder(BorderFactory.createLineBorder(new Color(30, 90, 140), 1));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Button clicked for " + name);
            }
        });

        centerPanel.add(button, BorderLayout.EAST);
        card.add(centerPanel, BorderLayout.CENTER);
        return card;
    }

    private static ImageIcon resizeImage(String imagePath, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage();
            Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImg);
        } catch (Exception e) {
            // Handle cases where image is not found
            System.out.println("Error loading image: " + e.getMessage());
            return new ImageIcon(); // Return empty icon as a fallback
        }
    }
}
