package org.example.frontend;
import org.example.backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class NewsFeedFrame extends JFrame {
    private static int currentFriendIndex = 0;
    private NewsFeedPosts newsFeedPosts;
    private static ArrayList<User> friends;
    private NewsFeedStory newsFeedStory;
    private User user;
    public NewsFeedFrame(NewsFeedPosts newsFeedPosts,NewsFeedStory newsFeedStory,User user) throws IOException {
        this.newsFeedPosts = newsFeedPosts;
        friends=newsFeedPosts.getFriends();
        this.newsFeedStory= newsFeedStory;
        this.user=user;
        JFrame frame = new JFrame("NewsFeed");
        frame.setSize(600, 700);
        frame.setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        // =============================
        // 1. Side Panel
        // =============================
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(100, 0));
        sidePanel.setBorder(BorderFactory.createTitledBorder("Menu"));
        JButton button1 = new JButton("Friends");
        button1.setFocusable(false);
        button1.addActionListener(e -> {
            frame.setVisible(false);
            try {
                new FriendListStatus(user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton button2 = new JButton("Profile");
        button2.setFocusable(false);
        button2.addActionListener(e -> {
            frame.setVisible(false);
            try {
                new Profile(user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton button3 = new JButton("FriendSuggestion");
        button3.setFocusable(false);
        button3.addActionListener(e->{
            frame.setVisible(false);
            try {
                new FriendSuggestionFrame(user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        sidePanel.add(button1);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(button2);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(button3);
        mainPanel.add(sidePanel, BorderLayout.WEST);
        JPanel storiesPanel = new JPanel();
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.X_AXIS));
        storiesPanel.setPreferredSize(new Dimension(400, 100));
        storiesPanel.setBorder(BorderFactory.createTitledBorder("Stories"));
        JPanel storiesContainer = new JPanel(new BorderLayout());
        storiesContainer.add(storiesPanel, BorderLayout.CENTER);
        mainPanel.add(storiesContainer, BorderLayout.NORTH);
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBorder(BorderFactory.createTitledBorder("Posts"));
        loadPosts(postsPanel, friends.get(0));
        JButton loadMoreButton = new JButton("Refresh");
        loadMoreButton.setFocusable(false);
        setupLoadMoreButton(postsPanel, loadMoreButton, friends);
        JScrollPane postsScrollPane = new JScrollPane(postsPanel);
        postsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(postsScrollPane, BorderLayout.CENTER);
        mainPanel.add(loadMoreButton, BorderLayout.SOUTH);
        frame.add(mainPanel);
        frame.setVisible(true);
    }
    private void loadPosts(JPanel postsPanel, User friend) throws IOException {
        ArrayList<Content> posts = newsFeedPosts.getPostsForFriend(friend);
        for (int i = 0; i < posts.size(); i++) {
            Content post = posts.get(i);
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BorderLayout());
            postPanel.setPreferredSize(new Dimension(350, 150));
            postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            JLabel titleLabel = new JLabel(friend.getUserName(), JLabel.CENTER);
            JTextArea descriptionArea = new JTextArea(post.getContent().getText());
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setEditable(false);
            postPanel.add(titleLabel, BorderLayout.NORTH);
            postPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
            MediaDetails media = post.getContent();
            if (media != null && media.getImage() != null) {
                try {
                    ImageIcon originalIcon = new ImageIcon(media.getImage());
                    Image scaledImage = originalIcon.getImage().getScaledInstance(300, 100, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);

                    JLabel imageLabel = new JLabel(scaledIcon);
                    postPanel.add(imageLabel, BorderLayout.SOUTH);
                } catch (Exception e) {
                    System.err.println("Error resizing image: " + e.getMessage());
                }
            }
            postsPanel.add(postPanel);
            postsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }
    private  void setupLoadMoreButton(JPanel postsPanel, JButton loadMoreButton, ArrayList<User> friends) {
        loadMoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadPosts(postsPanel, friends.get(currentFriendIndex));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // Update the current friend index to the next friend
                currentFriendIndex = (currentFriendIndex + 1) % friends.size(); // Loops back to the first friend if we reach the end

                // Refresh the UI after adding new posts
                postsPanel.revalidate();
                postsPanel.repaint();
            }
        });
    }
//    private void loadStories(JPanel storiesPanel, User friend) throws IOException {
//        storiesPanel.removeAll(); // Clear existing stories
//
//
//        ArrayList<Content> stories = newsFeedStory.getStoriesForFriend(friend);
//
//        for (int i = 0; i < stories.size(); i++) {
//            Content story = stories.get(i);
//
//
//            JPanel storyPanel = new JPanel();
//            storyPanel.setPreferredSize(new Dimension(80, 90));
//            storyPanel.setLayout(new BorderLayout());
//
//
//            JLabel iconLabel;
//            MediaDetails media=story.getContent();
//            if (media.getImage() != null) {
//                try {
//                    ImageIcon originalIcon = new ImageIcon(media.getImage());
//                    Image scaledImage = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
//                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
//                    iconLabel = new JLabel(scaledIcon);
//                } catch (Exception e) {
//                    iconLabel = new JLabel("No Image", JLabel.CENTER);
//                    System.err.println("Error resizing story image: " + e.getMessage());
//                }
//            } else {
//                iconLabel = new JLabel("No Image", JLabel.CENTER);
//            }
//
//
//            JLabel nameLabel = new JLabel(friend.getUserName(), JLabel.CENTER);
//            nameLabel.setFont(new Font("Arial", Font.PLAIN, 10));
//
//            storyPanel.add(iconLabel, BorderLayout.CENTER);
//            storyPanel.add(nameLabel, BorderLayout.SOUTH);
//
//            storiesPanel.add(storyPanel);
//            storiesPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Spacing between stories
//        }
//
//
//        storiesPanel.revalidate();
//        storiesPanel.repaint();
//    }
//    private void setupLoadMoreStoriesButton(JPanel storiesPanel, JButton loadMoreStoriesButton, ArrayList<User> friends) {
//        loadMoreStoriesButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                try {
//                    loadStories(storiesPanel, friends.get(currentFriendIndex));
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//
//
//                currentFriendIndex = (currentFriendIndex + 1) % friends.size(); 
//            }
//        });
//    }

}
