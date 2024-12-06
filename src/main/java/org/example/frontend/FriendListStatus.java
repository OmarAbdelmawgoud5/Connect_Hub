package org.example.frontend;
import org.example.backend.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class FriendListStatus extends JFrame implements ActionListener {
    private User user;
    private JButton backButton;
    public FriendListStatus(User user) throws IOException {
        super("Friend Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        this.user = user;
        ArrayList<User>friends=FriendsManagerReader.getFriends(user.getUserId(),FriendsStatus.Friend);
        JLabel friendRequests = new JLabel("Friend Status");
        friendRequests.setFont(new Font("Arial", Font.BOLD, 22));
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        JPanel friendListParentPanel = new JPanel();
        friendListParentPanel.setPreferredSize(new Dimension(500, 400));
        friendListParentPanel.setLayout(new BoxLayout(friendListParentPanel, BoxLayout.Y_AXIS));
        for (User friend : friends) {
            FriendsListPanel child = new FriendsListPanel(friend);
            child.setPreferredSize(new Dimension(500, 150));
            friendListParentPanel.add(child);
            friendListParentPanel.add(Box.createVerticalStrut(10));
        }
        JPanel combinedPanel = new JPanel();
        combinedPanel.setLayout(new BoxLayout(combinedPanel, BoxLayout.Y_AXIS));
        combinedPanel.add(backButton);
        combinedPanel.add(friendRequests);
        combinedPanel.add(Box.createVerticalStrut(10));
        combinedPanel.add(Box.createVerticalStrut(10));
        combinedPanel.add(friendListParentPanel);
        JScrollPane verticalScrollPane = new JScrollPane(combinedPanel);
        verticalScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        verticalScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(verticalScrollPane);
        verticalScrollPane.setBorder(null);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            try {
                new NewsFeedFrame(new NewsFeedPosts(user.getUserId()),new NewsFeedStory(user.getUserId()),user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
