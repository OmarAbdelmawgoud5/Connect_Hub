package org.example.frontend;

import org.example.backend.FriendsJson;
import org.example.backend.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class FriendManagementFrame extends JFrame {
    User user;
    FriendsJson x;
    public FriendManagementFrame(User myUser) throws IOException {
        super("Friend Management");
        String id=myUser.getUserId();
        user=myUser;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel friendRequests = new JLabel("Friend Requests");
        friendRequests.setFont(new Font("Arial", Font.BOLD, 22));


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

        combinedPanel.add(Box.createVerticalStrut(10));
        combinedPanel.add(horizontalScrollPane);
        combinedPanel.add(Box.createVerticalStrut(20));
        combinedPanel.add(friendsLabel);combinedPanel.add(friendRequests);
        combinedPanel.add(Box.createVerticalStrut(10));
        combinedPanel.add(friendListParentPanel);

        JScrollPane verticalScrollPane = new JScrollPane(combinedPanel);
        verticalScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        verticalScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        verticalScrollPane.setBorder(null);
        horizontalScrollPane.setBorder(null);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(friendRequests);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(horizontalScrollPane);
        add(topPanel, BorderLayout.NORTH);
        add(verticalScrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

    }


}