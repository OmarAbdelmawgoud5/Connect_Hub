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
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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
        JButton button3 = new JButton("Friend Suggestion");
        button3.setFont(new Font("Arial", Font.PLAIN, 7));

        button3.setFocusable(false);
        button3.addActionListener(e->{
            frame.setVisible(false);
            try {
                new FriendSuggestionFrame(user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton button4 = new JButton("Reload");
        button4.setFocusable(false);
        button4.addActionListener(e->{
            frame.setVisible(false);
            try {
                this.dispose();
                new NewsFeedFrame(new NewsFeedPosts(user.getUserId()),new NewsFeedStory(user.getUserId()),user);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton button5 = new JButton("Create Post");
        //button5.setFont(new Font("Arial", Font.PLAIN, 7));
        button5.setFocusable(false);
        button5.addActionListener(e->{
            // frame.setVisible(false);
            new ContentCreationPage(this,user.getUserId());
        });
        JButton button6 = new JButton("Logout");
        button6.setFocusable(false);
        button6.addActionListener(e->{
            frame.setVisible(false);

            UserLogout temp= null;
            try {
                temp = new UserLogout();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            temp.logout(user);
            new LoginPage();
            this.dispose();

        });
        sidePanel.add(button1);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(button2);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(button3);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(button4);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(button4);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(button5);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(button6);

        mainPanel.add(sidePanel, BorderLayout.WEST);

        JPanel storiesPanel = new JPanel();
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.X_AXIS));
       // storiesPanel.setPreferredSize(new Dimension(300, 100)); // Adjust the dimensions
        storiesPanel.setBorder(BorderFactory.createTitledBorder("Stories"));

        /*
        JPanel storiesContainer = new JPanel(new BorderLayout());
        storiesContainer.add(storiesPanel, BorderLayout.CENTER);
      //  mainPanel.add(storiesContainer, BorderLayout.NORTH);
*/


        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBorder(BorderFactory.createTitledBorder("Posts"));
        var storiesDisplay = new StoriesDisplay(friends);
        storiesDisplay.addStories(storiesPanel);

        var t=new postsDisplay(friends);
        t.addposts(postsPanel);

        JScrollPane postsScrollPane = new JScrollPane(postsPanel);
        postsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Vertical scrolling
        postsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // No horizontal scrolling





        JScrollPane storiesScrollPane = new JScrollPane(storiesPanel);
        storiesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        storiesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        mainPanel.add(storiesScrollPane, BorderLayout.NORTH);
        mainPanel.add(postsScrollPane);
        frame.add(mainPanel);
        frame.setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }
    private void loadPosts(JPanel postsPanel, User friend) throws IOException {
        ArrayList<Content> posts = newsFeedPosts.getPostsForFriend(friend);
        for (int i = 0; i < posts.size(); i++) {
            Content post = posts.get(i);
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