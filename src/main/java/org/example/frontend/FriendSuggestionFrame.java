package org.example.frontend;

import org.example.backend.*;

import org.example.backend.FriendsStatus;
import org.example.backend.NewsFeedPosts;
import org.example.backend.NewsFeedStory;
import org.example.backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class FriendSuggestionFrame extends JFrame implements ActionListener {
    private User user;
    private JButton backButton;
    public FriendSuggestionFrame(User user)throws IOException {
        super("Friend Suggestion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        this.user = user;
        ArrayList<User> friends = FriendsManagerReader.getFriends(user.getUserId(), FriendsStatus.Friend);
        JLabel friendListTitle = new JLabel("Add Friends");
        friendListTitle.setFont(new Font("Arial", Font.BOLD, 22));
        friendListTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        JPanel friendListParentPanel = new JPanel();
        friendListParentPanel.setLayout(new BoxLayout(friendListParentPanel, BoxLayout.Y_AXIS));
        for (User friend : friends) {
            JPanel friendPanel = createFriendPanel(friend);
            friendListParentPanel.add(friendPanel);
            friendListParentPanel.add(Box.createVerticalStrut(10));
        }
        JScrollPane scrollPane = new JScrollPane(friendListParentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
        friendPanel.setPreferredSize(new Dimension(600, 80));
        friendPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        JLabel photoLabel = new JLabel();
        ImageIcon profileImage = new ImageIcon(friend.getProfilePhoto());
        photoLabel.setIcon(profileImage);
        photoLabel.setPreferredSize(new Dimension(60, 60));
        JLabel nameLabel = new JLabel(friend.getUserName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Arial", Font.PLAIN, 12));
        addButton.setBackground(new Color(59, 89, 182));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setMargin(new Insets(5, 15, 5, 15));
        addButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Friend request sent to: " + friend.getUserName());
        });
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(nameLabel, BorderLayout.CENTER);
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
                new NewsFeedFrame(new NewsFeedPosts(user.getUserId()), new NewsFeedStory(user.getUserId()), user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
