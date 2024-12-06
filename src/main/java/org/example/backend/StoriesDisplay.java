package org.example.backend;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoriesDisplay {
    private ArrayList<Content> stories = new ArrayList<>();
    private Map<String, User> friendsMap = new HashMap<>();

    public void addStories(JPanel panel) {
        stories.removeIf(story -> {
            LocalDate storyTime = LocalDate.from(story.getTimeStamp());
            LocalDate currentDate = LocalDate.now();
            return storyTime.until(currentDate, ChronoUnit.DAYS) >= 1;
        });

        if (stories.isEmpty()) {
            JPanel noStoriesPanel = new JPanel();
            noStoriesPanel.setPreferredSize(new Dimension(350, 200));
            noStoriesPanel.setBackground(new Color(240, 240, 240));
            JLabel noStoriesLabel = new JLabel("No stories available");
            noStoriesLabel.setFont(new Font("Arial", Font.BOLD, 14));
            noStoriesLabel.setForeground(new Color(100, 100, 100));
            noStoriesLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noStoriesPanel.setLayout(new GridBagLayout());
            noStoriesPanel.add(noStoriesLabel);
            panel.add(noStoriesPanel);
            return;
        }

        for (Content story : stories) {
            System.out.println(story.toString());
            User friend = friendsMap.get(story.getAuthorId());

            JPanel storyPanel = new JPanel();
            storyPanel.setLayout(new BorderLayout());
            storyPanel.setPreferredSize(new Dimension(150, 150));
            storyPanel.setBackground(new Color(245, 245, 245));
            storyPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

            // Name Label
            JLabel nameLabel = new JLabel(friend.getUserName(), JLabel.CENTER);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            storyPanel.add(nameLabel, BorderLayout.NORTH);

            // Content Panel (Text + Image)
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.setBackground(new Color(245, 245, 245));

            // Story Text Content
            String storyText = story.getContent() != null ? story.getContent().getText() : "No content available";
            JTextArea storyTextArea = new JTextArea(storyText);
            storyTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
            storyTextArea.setEditable(false);
            storyTextArea.setLineWrap(true);
            storyTextArea.setWrapStyleWord(true);
            storyTextArea.setBackground(new Color(245, 245, 245));
            JScrollPane scrollPane = new JScrollPane(storyTextArea);
            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // No border for scroll
            contentPanel.add(scrollPane, BorderLayout.CENTER);

            // Story Image (if available)
            MediaDetails media = story.getContent();
            if (media != null && media.getImage() != null) {
                try {
                    ImageIcon originalIcon = new ImageIcon(media.getImage());
                    Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                    imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Padding
                    contentPanel.add(imageLabel, BorderLayout.SOUTH);
                } catch (Exception e) {
                    System.err.println("Error resizing image: " + e.getMessage());
                }
            }

            storyPanel.add(contentPanel, BorderLayout.CENTER);
            panel.add(storyPanel);
            panel.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing between stories
        }
    }

    public StoriesDisplay(ArrayList<User> friends) throws IOException {
        for (User friend : friends) {
            friendsMap.put(friend.getUserId(), friend);

            stories.addAll(ContentDatabaseLoader.loadContent(friend.getUserId(), "story"));
        }
    }
}
