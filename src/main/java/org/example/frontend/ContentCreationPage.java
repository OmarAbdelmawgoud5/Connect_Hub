package org.example.frontend;

import org.example.backend.*;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContentCreationPage extends JDialog {

    private String userId;
    MediaDetails mediaDetails;
    JButton storyButton;
    JPanel panel ;
    public ContentCreationPage(JFrame parent, String userId,Group g,String t,String path) {
        super(parent, "Create Post or Story", true);
        this.userId = userId;
        setSize(600, 450);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel backgroundLabel = new JLabel(new ImageIcon("src/main/resources/CreateContent.jpeg"));
        backgroundLabel.setBounds(0, 0, 600, 450);

        JLabel contentLabel = new JLabel("What's on your mind?");
        contentLabel.setBounds(20, 20, 200, 30);
        panel.add(contentLabel);

        JTextArea contentTextArea = new JTextArea();
        if(t!=null)
            contentTextArea.setText(t);
        contentTextArea.setBounds(20, 60, 540, 100);
        contentTextArea.setLineWrap(true);
        contentTextArea.setWrapStyleWord(true);
        contentTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(contentTextArea);

        JButton attachImageButton = new JButton("Attach Image");
        attachImageButton.setBounds(20, 180, 150, 30);
        attachImageButton.setBackground(Color.LIGHT_GRAY);
        panel.add(attachImageButton);
        JLabel imagePathLabel;
        if(path==null)
            imagePathLabel = new JLabel("No image attached");
        else
            imagePathLabel = new JLabel(path);
        imagePathLabel.setBounds(200, 180, 360, 30);
        panel.add(imagePathLabel);

        JButton postButton = new JButton("Post");
        postButton.setBounds(150, 250, 100, 40);
        postButton.setBackground(new Color(59, 89, 152));
        postButton.setForeground(Color.WHITE);
        panel.add(postButton);

        storyButton = new JButton("Create Story");
        storyButton.setBounds(300, 250, 150, 40);
        storyButton.setBackground(new Color(59, 89, 152));
        storyButton.setForeground(Color.WHITE);
        panel.add(storyButton);

        panel.add(backgroundLabel);

        add(panel);

        attachImageButton.addActionListener(e -> {
            ImageAttachment.attachImage(imagePathLabel);
        });

        postButton.addActionListener(e -> {

            String text = contentTextArea.getText();
            String imagePath = imagePathLabel.getText();
            LocalDateTime timeStamp = LocalDateTime.now();
            if (text.isEmpty() && imagePath.equals("No image attached")) {
                JOptionPane.showMessageDialog(this, "Cannot post an empty content!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(imagePath.equals("No image attached"))
            {
                imagePath=null;
            }
            mediaDetails = new MediaDetails(text, imagePath);
            try {
                ContentFactory contentFactory = ContentFactoryRegistry.getInstance().getContentFactory("post");
                Content post = contentFactory.createContent(userId, timeStamp, mediaDetails);
                User user=new UserJson().LoadUser(userId);
                if(g!=null)
                {
                    groupPostsJson.editContent(post);
                    groupPostsJson.SaveJson();
                    g.addContent(post.getContentId());
                    GroupDBWriter.addGroup(g);
                    GroupDBWriter.notifytoAddingPostToGroup(g.getGroupId(), userId);
                }
                else
                     ContentDatabaseSaver.saveContent(post);
            } catch (IOException ex) {
                Logger.getLogger(ContentCreationPage.class.getName()).log(Level.SEVERE, null, ex);
            }

            JOptionPane.showMessageDialog(this, "Post created successfully!");
            dispose();
        });

        storyButton.addActionListener(e -> {
            String text = contentTextArea.getText();
            String imagePath = imagePathLabel.getText();
            LocalDateTime timeStamp = LocalDateTime.now();
            if (text.isEmpty() && imagePath.equals("No image attached")) {
                JOptionPane.showMessageDialog(this, "Cannot make story with empty content!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(imagePath.equals("No image attached"))
            {
                imagePath=null;
            }
            mediaDetails = new MediaDetails(text, imagePath);

            try {
                ContentFactory contentFactory = ContentFactoryRegistry.getInstance().getContentFactory("story");
                Content story = contentFactory.createContent(userId, timeStamp, mediaDetails);
                ContentDatabaseSaver.saveContent(story);
            } catch (IOException ex) {
                Logger.getLogger(ContentCreationPage.class.getName()).log(Level.SEVERE, null, ex);
            }

            JOptionPane.showMessageDialog(this, "Story created successfully!");
            dispose();
        });
        this.setVisible(true);

    }


}
