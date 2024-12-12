package org.example.frontend;

import org.example.backend.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class NewsFeedFrame {

    private NewsFeedPosts newsFeedPosts;
    private User user;

    public NewsFeedFrame(NewsFeedPosts newsFeedPosts, User user) throws IOException {
        this.newsFeedPosts = newsFeedPosts;
        ArrayList<User> friends = newsFeedPosts.getFriends();
        this.user = user;
        JFrame frame = new JFrame("NewsFeed");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.setSize(600, 700);
        frame.setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //Side Panel
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(100, 0));
        sidePanel.setBorder(BorderFactory.createTitledBorder("Menu"));
        JButton friends1 = new JButton("Friends");
        friends1.setFocusable(false);
        friends1.addActionListener(e -> {
            frame.setVisible(false);
            try {
                new FriendListStatus(user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton profile = new JButton("Profile");
        profile.setFocusable(false);
        profile.addActionListener(e -> {
            frame.setVisible(false);
            try {
                new Profile(user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton suggestion = new JButton("Suggestion");
        suggestion.setFocusable(false);
        suggestion.addActionListener(e -> {
            frame.setVisible(false);
            try {
                new FriendSuggestionFrame(user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton reload = new JButton("Reload");
        reload.setFocusable(false);
        reload.addActionListener(e -> {
            frame.setVisible(false);
            try {
                frame.dispose();
                new NewsFeedFrame(new NewsFeedPosts(user.getUserId()), user);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton post1 = new JButton("Create Post");
        post1.setFocusable(false);
        post1.addActionListener(e -> {
            new ContentCreationPage(frame, user.getUserId(), " ", " ");
        });

        JButton logout = new JButton("Logout");
        logout.setFocusable(false);
        logout.addActionListener(e -> {
            frame.setVisible(false);
            UserLogout temp = null;
            try {
                temp = new UserLogout();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            temp.logout(user);
            new LoginPage();
            frame.dispose();
        });

        JButton buttonNotifications = new JButton("Notifications");
        buttonNotifications.setFocusable(false);
        buttonNotifications.addActionListener(e -> {
            try {
                new NotificationPage(user.getUserId());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        sidePanel.add(friends1);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(profile);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(suggestion);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(buttonNotifications);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(reload);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(post1);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(logout);
        mainPanel.add(sidePanel, BorderLayout.WEST);

        JPanel storiesPanel = new JPanel();
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.X_AXIS));
        storiesPanel.setBorder(BorderFactory.createTitledBorder("Stories"));

        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBorder(BorderFactory.createTitledBorder("Posts"));

        postsDisplay t = new postsDisplay(friends);
        ArrayList<Content> c = t.getContent();
        var mp = t.getMp();
        if (c.isEmpty()) {
            JPanel noPostsPanel = new JPanel();
            noPostsPanel.setPreferredSize(new Dimension(350, 200));
            noPostsPanel.setBackground(new Color(240, 240, 240));
            JLabel noPostsLabel = new JLabel("No posts available");
            noPostsLabel.setFont(new Font("Arial", Font.BOLD, 14));
            noPostsLabel.setForeground(new Color(100, 100, 100));
            noPostsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noPostsPanel.setLayout(new GridBagLayout());
            noPostsPanel.add(noPostsLabel);
            postsPanel.add(noPostsPanel);
        } else {
            for (int i = 0; i < c.size() && i < 20; i++) {
                Content post = c.get(i);
                User friend = mp.get(post.getAuthorId());
                JPanel postPanel = new JPanel();
                postPanel.setLayout(new BorderLayout());
                postPanel.setPreferredSize(new Dimension(350, 200));
                postPanel.setBackground(new Color(245, 245, 245)); // Light neutral background
                postPanel.setBorder(new CompoundBorder(
                        new LineBorder(new Color(200, 200, 200), 1, true), // Rounded border
                        BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding
                ));

// Title Label
                JLabel titleLabel = new JLabel(friend.getUserName(), JLabel.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Modern font
                titleLabel.setForeground(new Color(50, 50, 50)); // Dark text
                postPanel.add(titleLabel, BorderLayout.NORTH);

// Description Area
                JTextArea descriptionArea = new JTextArea(post.getContent().getText());
                descriptionArea.setLineWrap(true);
                descriptionArea.setWrapStyleWord(true);
                descriptionArea.setEditable(false);
                descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
                descriptionArea.setBackground(new Color(255, 255, 255));
                descriptionArea.setForeground(new Color(70, 70, 70)); // Soft gray text
                descriptionArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                JScrollPane scrollPane = new JScrollPane(descriptionArea);
                scrollPane.setBorder(BorderFactory.createEmptyBorder()); // No border for scroll
                postPanel.add(scrollPane, BorderLayout.CENTER);

// Media Section
                MediaDetails media = post.getContent();
                if (media != null && media.getImage() != null) {
                    try {
                        ImageIcon originalIcon = new ImageIcon(media.getImage());
                        Image scaledImage = originalIcon.getImage().getScaledInstance(300, 100, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaledImage);

                        JLabel imageLabel = new JLabel(scaledIcon);
                        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Padding
                        postPanel.add(imageLabel, BorderLayout.SOUTH);
                    } catch (Exception e) {
                        System.err.println("Error resizing image: " + e.getMessage());
                    }
                }

// Add Spacing Between Posts
                postPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                postsPanel.add(postPanel);
                postsPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Add space
            }
        }

        JScrollPane postsScrollPane = new JScrollPane(postsPanel);
        postsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Vertical scrolling
        postsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // No horizontal scrolling

        var storiesDisplay = new StoriesDisplay(friends);
        storiesDisplay.addStories(storiesPanel);

        JScrollPane storiesScrollPane = new JScrollPane(storiesPanel);
        storiesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        storiesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        mainPanel.add(storiesScrollPane, BorderLayout.NORTH);
        mainPanel.add(postsScrollPane);
        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }

}
