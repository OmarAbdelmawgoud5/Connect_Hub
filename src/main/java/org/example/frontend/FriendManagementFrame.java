package org.example.frontend;

import org.example.backend.FriendsJson;
import org.example.backend.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class FriendManagementFrame extends JFrame {
    User user;
    FriendsJson x;
    public FriendManagementFrame(User myUser) throws IOException {

        super("Friend Management");
        String id = myUser.getUserId();
        user=myUser;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create a "Back" button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setBackground(new Color(220, 220, 220));
        backButton.setFocusPainted(false);

        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    onBackButtonClicked();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // Title for friend requests
        JLabel friendRequests = new JLabel("Friend Requests");
        friendRequests.setFont(new Font("Arial", Font.BOLD, 22));

        // Horizontal friend request panel
        JPanel friendRequestParentPanel = new JPanel();
        friendRequestParentPanel.setLayout(new BoxLayout(friendRequestParentPanel, BoxLayout.X_AXIS));
        x = new FriendsJson(id);
        Map<User, Integer> map = x.getDb();
        for (Map.Entry<User,Integer> entry : map.entrySet())
        {
            FriendRequestPanel child = new FriendRequestPanel(entry.getKey());
            child.setPreferredSize(new Dimension(150, 250));
            friendRequestParentPanel.add(child);
            friendRequestParentPanel.add(Box.createHorizontalStrut(5));
        }

        JScrollPane horizontalScrollPane = new JScrollPane(friendRequestParentPanel);
        horizontalScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        horizontalScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        horizontalScrollPane.setPreferredSize(new Dimension(680, 265));

        // Title for friends list
        JLabel friendsLabel = new JLabel("Friends");
        friendsLabel.setFont(new Font("Arial", Font.BOLD, 22));

        // Vertical friends list panel
        JPanel friendListParentPanel = new JPanel();
        friendListParentPanel.setLayout(new BoxLayout(friendListParentPanel, BoxLayout.Y_AXIS));
        for (Map.Entry<User,Integer> entry : map.entrySet())
        {
            FriendsListPanel child = new FriendsListPanel(entry.getKey());
            child.setPreferredSize(new Dimension(500, 150));
            friendListParentPanel.add(child);
            friendListParentPanel.add(Box.createVerticalStrut(10));
        }

        // Combined panel: holds the horizontal pane and the friends list
        JPanel combinedPanel = new JPanel();
        combinedPanel.setLayout(new BoxLayout(combinedPanel, BoxLayout.Y_AXIS));
        combinedPanel.add(friendRequests);
        combinedPanel.add(Box.createVerticalStrut(10)); // Spacer
        combinedPanel.add(horizontalScrollPane);
        combinedPanel.add(Box.createVerticalStrut(20)); // Spacer
        combinedPanel.add(friendsLabel);
        combinedPanel.add(Box.createVerticalStrut(10)); // Spacer
        combinedPanel.add(friendListParentPanel);

        // Vertical scroll pane for the entire combined panel
        JScrollPane verticalScrollPane = new JScrollPane(combinedPanel);
        verticalScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        verticalScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        verticalScrollPane.setBorder(null);
        horizontalScrollPane.setBorder(null);

        // Main layout for the frame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(backButton, BorderLayout.NORTH); // Add the back button at the top
        mainPanel.add(verticalScrollPane, BorderLayout.CENTER); // Add the scrollable content

        // Add main panel to the frame
        add(mainPanel);
        x.editfirend(map);
        setVisible(true);
    }

    private void onBackButtonClicked() throws IOException {

       /* new Profile(user);
        this.dispose();*/
    }

    public static ArrayList<User> extractKeys(ArrayList<Map<User, Integer>> db) {
        ArrayList<User> keys = new ArrayList<>();

        for (Map<User, Integer> map : db) {
            keys.addAll(map.keySet()); // Add all keys (Users) from the current map
        }

        return keys;
    }
}
