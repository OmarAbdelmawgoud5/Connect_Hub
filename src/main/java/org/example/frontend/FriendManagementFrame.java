package org.example.frontend;

import org.example.backend.User;

import javax.swing.*;
import java.awt.*;

public class FriendManagementFrame extends JFrame {
    public FriendManagementFrame() {
        super("Friend Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        // Title for friend requests
        JLabel friendRequests = new JLabel("Friend Requests");
        friendRequests.setFont(new Font("Arial", Font.BOLD, 22));

        // Horizontal friend request panel
        JPanel friendRequestParentPanel = new JPanel();
        friendRequestParentPanel.setLayout(new BoxLayout(friendRequestParentPanel, BoxLayout.X_AXIS));
        for (int i = 0; i < 10; i++) {
            FriendRequestPanel child = new FriendRequestPanel(new User("omar Abdelmawgoud",null,null,"C:\\Users\\Etijah\\IdeaProjects\\Connect_Hub\\src\\main\\resources\\user.png\\",null,null,null,null));
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
        for (int i = 0; i < 10; i++) {
            FriendsListPanel child = new FriendsListPanel(new User("omar Abdelmawgoud"+i,null,null,"C:\\Users\\Etijah\\IdeaProjects\\Connect_Hub\\src\\main\\resources\\user.png\\",null,null,null,null));
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

        // Add main scroll pane to the frame
        add(verticalScrollPane);
        verticalScrollPane.setBorder(null);
        horizontalScrollPane.setBorder(null);

        setVisible(true);
    }

}