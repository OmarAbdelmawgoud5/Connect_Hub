package org.example.frontend;

import org.example.backend.FriendsJson;
import org.example.backend.FriendsManagerReader;
import org.example.backend.FriendsStatus;
import org.example.backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendManagementFrame extends JFrame implements ActionListener {
    User user;
    JButton backButton;
    public FriendManagementFrame(User user)  throws IOException {
        super("Friend Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        this.user = user;
        ArrayList<User>pending= FriendsManagerReader.getFriends(user.getUserId(), FriendsStatus.Received);
        ArrayList<User>friends=FriendsManagerReader.getFriends(user.getUserId(),FriendsStatus.Friend);
        // Title for friend requests
        JLabel friendRequests = new JLabel("Friend Requests");
        friendRequests.setFont(new Font("Arial", Font.BOLD, 22));
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        // Horizontal friend request panel
        JPanel friendRequestParentPanel = new JPanel();
        friendRequestParentPanel.setLayout(new BoxLayout(friendRequestParentPanel, BoxLayout.X_AXIS));
       //friendRequestParentPanel.setPreferredSize(new Dimension(1200, 100));
        for (User pendingFriend : pending) {
            FriendRequestPanel child = new FriendRequestPanel(pendingFriend,user);
            child.setPreferredSize(new Dimension(150, 240));
            friendRequestParentPanel.add(child);
           friendRequestParentPanel.add(Box.createHorizontalStrut(5));
        }



        JScrollPane horizontalScrollPane = new JScrollPane(friendRequestParentPanel);
        horizontalScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        horizontalScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        horizontalScrollPane.setPreferredSize(new Dimension(100, 265));


        // Title for friends list
        JLabel friendsLabel = new JLabel("Friends");
        friendsLabel.setFont(new Font("Arial", Font.BOLD, 22));

        // Vertical friends list panel
        JPanel friendListParentPanel = new JPanel();
        friendListParentPanel.setPreferredSize(new Dimension(500, 400));
        friendListParentPanel.setLayout(new BoxLayout(friendListParentPanel, BoxLayout.Y_AXIS));
        for (User friend : friends) {
            FriendsListPanel child = new FriendsListPanel(friend,user);
            child.setPreferredSize(new Dimension(500, 150));
            friendListParentPanel.add(child);
            friendListParentPanel.add(Box.createVerticalStrut(10));
        }

        // Combined panel: holds the horizontal pane and the friends list
        JPanel combinedPanel = new JPanel();
        combinedPanel.setLayout(new BoxLayout(combinedPanel, BoxLayout.Y_AXIS));
        combinedPanel.add(backButton);
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
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==backButton){
            setVisible(false);
            try {
                new Profile(user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}