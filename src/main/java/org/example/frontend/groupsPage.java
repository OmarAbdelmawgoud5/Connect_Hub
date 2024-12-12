package org.example.frontend;

import org.example.backend.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class groupsPage extends JFrame {
    private User currentUser;

    public groupsPage(User user, Group group) throws IOException {
        this.currentUser = user;
        this.setTitle("Groups");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#f0f2f5"));
        setContentPane(mainPanel);

        // Header Section
        JPanel headerPanel = createHeaderPanel(user, group);

        // Navigation Bar
        JPanel navPanel = createNavPanel(user,group.getGroupId());

        // Content Area for Posts
        JScrollPane scrollPane = createContentScrollPane(group);

        // Adding Components to Main Panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(navPanel, BorderLayout.SOUTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createHeaderPanel(User user, Group group) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#4267B2"));
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JButton backButton = createButton("Back", 12, Color.WHITE, Color.decode("#4267B2"));
        backButton.setPreferredSize(new Dimension(80, 30));
        backButton.addActionListener(evt -> navigateToProfile(user));
        headerPanel.add(backButton, BorderLayout.WEST);

        JLabel groupName = new JLabel(group.getName(), JLabel.CENTER);
        groupName.setFont(new Font("SansSerif", Font.BOLD, 24));
        groupName.setForeground(Color.WHITE);
        headerPanel.add(groupName, BorderLayout.CENTER);

        JButton joinButton = createButton("Join Group", 14, Color.WHITE, Color.decode("#4267B2"));
        headerPanel.add(joinButton, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createNavPanel(User user,String gpid) {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navPanel.setBackground(Color.WHITE);
        navPanel.setPreferredSize(new Dimension(1000, 50));

        JButton addPostButton = createNavButton("Add Post");
        addPostButton.addActionListener(evt -> openContentCreationPage(user,gpid));
        navPanel.add(addPostButton);

        JButton membersButton = createNavButton("Members");
        membersButton.addActionListener(evt -> navigateToProfile(user));
        navPanel.add(membersButton);

        return navPanel;
    }

    private JScrollPane createContentScrollPane(Group group) {
        JPanel contentArea = new JPanel();
        contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));
        contentArea.setBackground(Color.WHITE);

        try {
            System.out.println("A7A ");
            Content j= ContentDatabaseLoaderByID.loadPost("c74258b5-6a7f-4c66-9441-9a1d9cc33599");
            ArrayList<Content> posts =new ArrayList<>();
            posts.add(j);
            System.out.printf(posts.toString());
            UserJson userJson = new UserJson();
            if(posts!=null) {
                for (Content post : posts) {
                    User postAuthor = userJson.LoadUser(post.getAuthorId());
                    contentArea.add(postCard.createPostCard(post, postAuthor.getUserName(), postAuthor.getProfilePhoto(), "admin", this::callback));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return scrollPane;
    }

    private JButton createButton(String text, int fontSize, Color textColor, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        button.setForeground(textColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.decode("#4267B2"));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void navigateToProfile(User user) {
        try {
            new Profile(user);
            dispose();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openContentCreationPage(User user,String gpid) {
        try {
            ContentCreationPage contentCreationPage = new ContentCreationPage(this, user.getUserId(),"group",gpid);
            contentCreationPage.panel.remove(contentCreationPage.storyButton);
            contentCreationPage.panel.revalidate();
            contentCreationPage.getContentPane().removeAll();
            contentCreationPage.getContentPane().add(contentCreationPage.panel, BorderLayout.CENTER);
            contentCreationPage.revalidate();
            contentCreationPage.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callback() throws IOException {
        navigateToProfile(currentUser);
    }
}