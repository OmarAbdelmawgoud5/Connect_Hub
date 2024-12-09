package org.example.frontend;

import org.example.backend.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class groupsPage extends JFrame {

    public groupsPage(User user,Group group) throws IOException {
        this.setTitle("Groups.json");
        setTitle("Group");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#f0f2f5"));
        setContentPane(mainPanel);

        // Header Section
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#4267B2"));
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel groupName = new JLabel(group.getName(), JLabel.LEFT);
        groupName.setFont(new Font("SansSerif", Font.BOLD, 24));
        groupName.setForeground(Color.WHITE);
        headerPanel.add(groupName, BorderLayout.WEST);


        JButton joinButton = new JButton("Join Group");
        styleButton(joinButton);
        headerPanel.add(joinButton, BorderLayout.EAST);

        // Navigation Bar
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navPanel.setBackground(Color.WHITE);
        navPanel.setPreferredSize(new Dimension(1000, 50));
        String[] navItems = {"Home", "About", "Photos", "Events"};
        for (String item : navItems) {
            JButton navButton = new JButton(item);
            styleNavButton(navButton);
            navPanel.add(navButton);
        }

        // Post Creation Section
        JPanel postSection = new JPanel(new BorderLayout());
        postSection.setBackground(Color.WHITE);
        postSection.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextArea postInput = new JTextArea("What's on your mind?");
        postInput.setFont(new Font("SansSerif", Font.PLAIN, 14));
        postInput.setLineWrap(true);
        postInput.setWrapStyleWord(true);
        postInput.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        postInput.setPreferredSize(new Dimension(800, 60)); // Smaller size for post input
        postSection.add(postInput, BorderLayout.CENTER);

        JButton postButton = new JButton("Post");
        styleButton(postButton);
        postButton.setPreferredSize(new Dimension(100, 40));
        postSection.add(postButton, BorderLayout.EAST);

        // Content Area for Posts
        JPanel contentArea = new JPanel();
        contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));
        contentArea.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Adding dummy posts for demonstration
        MediaDetails mediaDetails = new MediaDetails("Lol", null);
        Post samplePost = new Post("Sample post content", "85", LocalDateTime.now(), mediaDetails);
        ArrayList<Content> c=groupPostsJson.loadPosts("123");

       // contentArea.add( postCard.createPostCard(group.getContentId().get(0),"",""));
        var temp=new UserJson();
        for (Content c1 : c) {
            var tempuser=temp.LoadUser(c1.getAuthorId());
            contentArea.add(postCard.createPostCard(c1, tempuser.getUserName(),
                    tempuser.getProfilePhoto()));
        }

        // Adding Components to Main Panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(navPanel, BorderLayout.CENTER);
        mainPanel.add(postSection, BorderLayout.SOUTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.decode("#4267B2"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleNavButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.decode("#4267B2"));
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
