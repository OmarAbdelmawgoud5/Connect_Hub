package org.example.backend;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public  class postsDisplay {
    ArrayList<Content> c=new ArrayList<>() ;
    Map<String ,User> mp=new HashMap<String ,User>() ;
    static int index;
    public void addposts(JPanel panel)
    {
        if(c.size()==0)
        {
            JPanel noPostsPanel = new JPanel();
            noPostsPanel.setPreferredSize(new Dimension(350, 200));
            noPostsPanel.setBackground(new Color(240, 240, 240));

            JLabel noPostsLabel = new JLabel("No posts available");
            noPostsLabel.setFont(new Font("Arial", Font.BOLD, 14));
            noPostsLabel.setForeground(new Color(100, 100, 100));
            noPostsLabel.setHorizontalAlignment(SwingConstants.CENTER);

            noPostsPanel.setLayout(new GridBagLayout());
            noPostsPanel.add(noPostsLabel);
            panel.add(noPostsPanel);
            return;
        }
        for (int i = 0;i<c.size() && i<100  ; i++) {
            Content post = c.get(i);
            User friend=mp.get(post.getAuthorId());
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
            panel.add(postPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 15))); // Add space
        }
        index+=10;
    }
    public postsDisplay (ArrayList<User> friends) throws IOException {
        index=0;
        for(int i=0;i<friends.size();i++) {
            mp.put(friends.get(i).getUserId(), friends.get(i));
            c.addAll(ContentDatabaseLoader.loadContent(friends.get(i).getUserId(), "post"));
            Collections.shuffle(c);
        }
    }
}
