package org.example.frontend;

import org.example.backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class SearchUser extends JFrame implements ActionListener {
    private User currentUser;
    private ArrayList<User> filteredUsers; // Pre-searched user list
    private ArrayList<User> friendsList;
    private JButton backButton;
    private JPanel resultsPanel;
    private JDialog parent;

    public SearchUser(JDialog parent, User currentUser, ArrayList<User> filteredUsers) throws IOException {
        super("View Users");
        this.currentUser = currentUser;
        this.filteredUsers = filteredUsers;
        this.friendsList = FriendsManagerReader.getFriends(currentUser.getUserId(), FriendsStatus.Friend);
        this.parent = parent;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Users");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(this);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Results panel
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        displayUsers();

        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Combine panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            parent.setVisible(true);
        }
    }

    // Display the provided list of users
    private void displayUsers() {
        for (User user : filteredUsers) {
            resultsPanel.add(createUserPanel(user));
            resultsPanel.add(Box.createVerticalStrut(10));
        }
    }

    private boolean isFriend(User user) {
        for (User friend : friendsList) {
            if (friend.getUserId().equals(user.getUserId())) {
                return true;
            }
        }
        return false;
    }

    private JPanel createUserPanel(User user) {
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userPanel.setPreferredSize(new Dimension(650, 60));
        userPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel nameLabel = new JLabel(user.getUserName());
        nameLabel.setPreferredSize(new Dimension(300, 30));
        userPanel.add(nameLabel);

        // Check if the user is a friend
        if (isFriend(user)) {
            // If user is already a friend
            JButton removeButton = new JButton("Remove Friend");
            removeButton.addActionListener(e -> {
                try {
                    FriendsManagerWriter.friendsWriter(currentUser, user, UserAction.UnFriend);
                    JOptionPane.showMessageDialog(this, "Friend removed successfully.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            userPanel.add(removeButton);
        } else {
            // If user is not a friend
            JButton addButton = new JButton("Add Friend");
            addButton.addActionListener(e -> {
                try {
                    FriendsManagerWriter.friendsWriter(currentUser, user, UserAction.SendRequest);
                    JOptionPane.showMessageDialog(this, "Friend request sent successfully.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            userPanel.add(addButton);
        }

        // Block and view profile buttons
        JButton blockButton = new JButton("Block");
        blockButton.addActionListener(e -> {
            try {
                FriendsManagerWriter.friendsWriter(currentUser, user, UserAction.BlockFriend);
                JOptionPane.showMessageDialog(this, "User blocked successfully.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(e -> {
            try {
                new Profile(user);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        userPanel.add(blockButton);
        userPanel.add(viewProfileButton);

        return userPanel;
    }

}

