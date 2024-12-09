package org.example.frontend;

import org.example.backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class FriendSuggestionFrame extends JFrame implements ActionListener {
    private User user;
    private JButton backButton;

    public FriendSuggestionFrame(User user) throws IOException {
        super("Friend Suggestion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        this.user = user;

        // Generate the list of suggested friends
        ArrayList<User> friends = FriendsSuggestionsGenerator.generate(user);

        // Title label
        JLabel friendListTitle = new JLabel("Add Friends");
        friendListTitle.setFont(new Font("Arial", Font.BOLD, 22));
        friendListTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Back button
        backButton = new JButton("Back");
        backButton.addActionListener(this);

        // Parent panel for the list of friends
        JPanel friendListParentPanel = new JPanel();
        friendListParentPanel.setLayout(new BoxLayout(friendListParentPanel, BoxLayout.Y_AXIS));

        // Add each friend's panel to the parent panel
        for (User friend : friends) {
            JPanel friendPanel = createFriendPanel(friend);
            friendListParentPanel.add(friendPanel);
            friendListParentPanel.add(Box.createVerticalStrut(10)); // Spacing between friends
        }

        // Scroll pane for the list of friends
        JScrollPane scrollPane = new JScrollPane(friendListParentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smoother scrolling

        // Main panel layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(friendListTitle, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createFriendPanel(User friend) {
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BorderLayout());
        friendPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); // Allow width to stretch
        friendPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Profile picture
        JLabel photoLabel = new JLabel();
        ImageIcon profileImage = new ImageIcon(friend.getProfilePhoto());
        Image scaledImage = profileImage.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        photoLabel.setIcon(new ImageIcon(scaledImage));
        photoLabel.setPreferredSize(new Dimension(60, 60));

        // Friend's name
        JLabel nameLabel = new JLabel(friend.getUserName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Add friend button
        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Arial", Font.PLAIN, 12));
        addButton.setBackground(new Color(59, 89, 182));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setMargin(new Insets(5, 15, 5, 15));

        addButton.addActionListener(e -> {
            addButton.setEnabled(false);
            addButton.setBackground(new Color(180, 201, 236, 117));
            addButton.setText("Sent");

            // Add the friend using the FriendsManagerWriter
            try {
                FriendsManagerWriter.friendsWriter(user, friend, UserAction.SendRequest);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Layout for friend's details
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(nameLabel, BorderLayout.CENTER);

        // Add components to the friend panel
        friendPanel.add(photoLabel, BorderLayout.WEST);
        friendPanel.add(textPanel, BorderLayout.CENTER);
        friendPanel.add(addButton, BorderLayout.EAST);

        return friendPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            try {
                new NewsFeedFrame(new NewsFeedPosts(user.getUserId()), user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
