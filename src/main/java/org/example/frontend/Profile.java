package org.example.frontend;

import org.example.backend.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Profile extends JFrame {
    private final User myUser;
    public JLabel userBio;


    public Profile(User user) throws IOException {
        myUser = user;
        setTitle("My Profile");
        setSize(820, 600);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setupLayout(this);
        this.setVisible(true);
    }

    private void setupLayout(Profile profile) throws IOException {
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
        JButton backButton = postCard.createStyledButton("Back");
        JButton groups = postCard.createStyledButton("My groups");
        JButton newgroup = postCard.createStyledButton("Create Group");
        JButton changePasswordButton = postCard.createStyledButton("Change password");
        backButton.setBounds(10, 120, 140, 30);
        changePasswordButton.setBounds(10, 170, 140, 30);
        groups.setBounds(170, 120, 140, 30);
        newgroup.setBounds(170,170,140,30);
        container.add(newgroup);
        container.add(backButton);
        container.add(changePasswordButton);
        container.add(groups);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBounds(620, 120, 160, 60);
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton changeBioButton = postCard.createStyledButton("Change Bio");
        JButton friendsListButton = postCard.createStyledButton("Friends");
        buttonPanel.add(changeBioButton);
        buttonPanel.add(friendsListButton);
        container.add(buttonPanel);

        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                try {
                    buttonaction("Back");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        newgroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                var x=new createnewgroup(profile,myUser);

            }
        });


        groups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                try {
                    buttonaction("Groups");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });



        changePasswordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                try {
                    buttonaction("Change password");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        changeBioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                try {
                    buttonaction("Change Bio");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        friendsListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                try {
                    buttonaction("Friends");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });





        // Content Area (Scrollable)
        JPanel contentArea = new JPanel();
        contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));
        contentArea.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBounds(0, 200, 800, 350);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ArrayList<Content> posts=null;
        try {
           posts = fetchPosts();
        }
        catch (RuntimeException e) {
            ;
        }
        if(posts!=null) {
            for (Content post : posts) {
                System.out.println("ahmed "+post.getAuthorId());

                contentArea.add(postCard.createPostCard(post,myUser.getUserName(),myUser.getProfilePhoto(),null,null));
            }
        }
        container.add(scrollPane);
    }


private void buttonaction(String k) throws IOException {
    switch (k) {
        case "Back":
        {
           // System.out.println(myUser.getUserId());
            try {
                new NewsFeedFrame(new NewsFeedPosts(myUser.getUserId()),myUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.dispose();
            break;
        }
        case "Friends":
        {
            try {
                new FriendManagementFrame(myUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //System.out.println(myUser.getUserId());
            this.dispose();
            break;
        }
        case "Change password":
        {
            new ChangePasswordDialog(myUser,this);
            break;
        }
        case "Change Bio":
        {
            new ChangeBioDialog(this,myUser);
            break;
        }
        case "Groups":
        {

           new MyGroupsFrame(myUser,new ArrayList<>(myUser.getGroups().keySet()),"My Groups");
            this.dispose();
            break;
        }

    }
}


    private ArrayList<Content> fetchPosts() throws IOException {
       // System.out.println(myUser.getUserId());
        return ContentDatabaseLoader.loadContent(myUser.getUserId(), "post");
    }


}
