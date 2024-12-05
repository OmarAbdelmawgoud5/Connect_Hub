package org.example.frontend;

import org.example.backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Profile extends JFrame {
    private final User myUser;
    public JLabel userBio;


    public Profile(User user) throws IOException {
        myUser = user;
        initComponents();
        setTitle("My Profile");
        setSize(820, 600);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupLayout();
        this.setVisible(true);
    }

    private void setupLayout() throws IOException {
        Container container = getContentPane();
        container.setLayout(null);

        // Cover Photo
        JLabel coverPhoto = new JLabel(new ImageIcon(
                new ImageIcon(myUser.getCoverPhoto()).getImage().getScaledInstance(800, 100, Image.SCALE_SMOOTH)));
        coverPhoto.setBounds(0, 0, 800, 100);


        // Profile Picture
        JLabel profilePhoto = new JLabel(new ImageIcon(
                new ImageIcon(myUser.getProfilePhoto()).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        profilePhoto.setBounds(360, 70, 80, 80);
        profilePhoto.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        container.add(profilePhoto);
        container.add(coverPhoto);


        // UserName
        JLabel userName = new JLabel(myUser.getUserName());
        userName.setFont(new Font("Arial", Font.BOLD, 18));
        userName.setHorizontalAlignment(SwingConstants.CENTER);
        userName.setBounds(0, 150, 800, 25);
        container.add(userName);

        //Bio
        userBio = new JLabel(myUser.getBio()); // Replace with dynamic bio if needed
        userBio.setFont(new Font("Arial", Font.ITALIC, 12));
        userBio.setHorizontalAlignment(SwingConstants.CENTER);
        userBio.setBounds(0, 170, 800, 20);
        container.add(userBio);

        // Buttons
        JButton backButton = createStyledButton("Back");
        JButton changePasswordButton = createStyledButton("Change Password");
        backButton.setBounds(10, 120, 160, 30);
        changePasswordButton.setBounds(10, 170, 160, 30);
        container.add(backButton);
        container.add(changePasswordButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBounds(620, 120, 160, 60);
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton changeBioButton = createStyledButton("Change Bio");
        JButton friendsListButton = createStyledButton("Friends");
        buttonPanel.add(changeBioButton);
        buttonPanel.add(friendsListButton);

        container.add(buttonPanel);

        // Content Area (Scrollable)
        JPanel contentArea = new JPanel();
        contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));
        contentArea.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBounds(0, 200, 800, 350);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        ArrayList<Content> posts = fetchPosts();
        for (Content post : posts) {
            contentArea.add(createPostCard(post));
        }
        container.add(scrollPane);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180)); // Steel Blue
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        button.setContentAreaFilled(true);

        // Hover effect
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    buttonaction(evt,text);
                } catch (IOException e) {

                     throw new RuntimeException(e);
                }
                System.out.println("lol");
            }
        });

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237)); // Light Steel Blue
            }



            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180)); // Steel Blue
            }
        });
        return button;
    }
private void buttonaction(ActionEvent evt, String k) throws IOException {
    switch (k) {
        case "Back":
        {
            // new newsfeed(myUser);
            break;

        }
        case "Friends":
        {
            new FriendManagementFrame(myUser);
            this.dispose();
            break;
        }
        case "Change Password":
        {
            new ChangePasswordDialog(myUser,this);
            break;
        }
        case "Change Bio":
        {
            new ChangeBioDialog(this,myUser);
            break;
        }

    }
}
    private JPanel createPostCard(Content post) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBackground(Color.WHITE);
        String path=post.getContent().getImage();
        if(path==null)
        panel.setPreferredSize(new Dimension(780, 100));
        else
        {
            panel.setPreferredSize(new Dimension(780, 200));

        }
        // Profile Photo in Post
        JLabel postProfilePhoto = new JLabel(new ImageIcon(
                new ImageIcon(myUser.getProfilePhoto()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        postProfilePhoto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(postProfilePhoto, BorderLayout.WEST);

        // Post Content
        String contentText = post.getContent().getText();
        JTextArea postContent = new JTextArea(contentText);
        //postContent.setEditable(false);
        postContent.setFont(new Font("Arial", Font.PLAIN, 15));
        postContent.setWrapStyleWord(true);
        postContent.setLineWrap(true);
        postContent.setOpaque(false);
        postContent.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        JScrollPane contentScrollPane = new JScrollPane(postContent);
        contentScrollPane.setBorder(BorderFactory.createEmptyBorder());

        if(path!=null) {
            JLabel c = new JLabel(new ImageIcon(
                    new ImageIcon(myUser.getProfilePhoto()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            c.setBounds(450, 50, 500, 100);
            panel.add(c);
        }panel.add(contentScrollPane, BorderLayout.CENTER);
        // "See More" Button if Text is Long
        if (contentText.length() > 100) {
            postContent.setText(contentText.substring(0,100));// Adjust threshold as needed
            JButton seeMoreButton = createStyledButton("See More");
            seeMoreButton.setFont(new Font("Arial", Font.PLAIN, 10));

            seeMoreButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog dialog = new JDialog();
                    dialog.setTitle("Post Details");
                    dialog.setSize(400, 300);
                    dialog.setLocationRelativeTo(null);

                    JTextArea fullText = new JTextArea(contentText);
                    fullText.setFont(new Font("Arial", Font.PLAIN, 12));
                    fullText.setEditable(false);
                    fullText.setWrapStyleWord(true);
                    fullText.setLineWrap(true);
                    fullText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    JScrollPane dialogScroll = new JScrollPane(fullText);
                    dialog.add(dialogScroll);

                    dialog.setVisible(true);
                }
            });

            panel.add(seeMoreButton, BorderLayout.SOUTH);
        }

        return panel;
    }

    private ArrayList<Content> fetchPosts() throws IOException {
        return ContentDatabaseLoader.loadContent(myUser.getUserId(), "post");
    }

    private void initComponents() {
        // Additional initialization if needed
    }
}
