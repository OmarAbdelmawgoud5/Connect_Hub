package org.example.frontend;

import org.example.backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class postCard {


    static public JPanel createPostCard(Content post, String name, String pf, String extraproprites, JFrame parent,Group g) {
        System.out.println("Post ID: " + post.getContentId());
        System.out.println("Post Content: " + post.getContent().getText());

        custom panel = new custom();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBackground(Color.WHITE);

        String path = post.getContent().getImage();
        panel.setPreferredSize(new Dimension(780, path == null ? 100 : 400));

        // Top section (Profile photo, name, options button)
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(Color.WHITE);

        // Profile Photo
        JLabel postProfilePhoto = new JLabel(new ImageIcon(new ImageIcon(pf).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        postProfilePhoto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topSection.add(postProfilePhoto, BorderLayout.WEST);

        // Name label
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller font
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        topSection.add(nameLabel, BorderLayout.CENTER);

        // Options Button (for admin/owner)
        if ("admin".equals(extraproprites) || "owner".equals(extraproprites)) {
            JButton optionsButton = new JButton("...");
            optionsButton.setFont(new Font("Arial", Font.BOLD, 12));
            optionsButton.setPreferredSize(new Dimension(30, 30));
            optionsButton.setOpaque(false);
            optionsButton.setContentAreaFilled(false);
            optionsButton.setBorderPainted(false);
            optionsButton.addActionListener(evt -> {

                    var popupMenu=new JPopupMenu() {
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(300, 200); // Set desired dimensions
                        }
                    };;
                    var item1=new JMenuItem("Edit");
                    var item2=new JMenuItem("Delete");
                    popupMenu.add(item1);
                    popupMenu.add(item2);
                    item1.addActionListener(panel);
                    item2.addActionListener(panel);
                    panel.setItem1(item1);
                    panel.setItem2(item2);
                    panel.setPopupMenu(popupMenu);
                    panel.setC(post);
                    panel.setG(g);
                    panel.setP(parent);
                    popupMenu.setLocation(600,300);
                    popupMenu.setVisible(true);
            });

            JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            buttonContainer.setOpaque(false);
            buttonContainer.add(optionsButton);
            topSection.add(buttonContainer, BorderLayout.EAST);
        }

        panel.add(topSection, BorderLayout.NORTH);

        // Post Content
        String contentText = post.getContent().getText();
        JTextArea postContent = new JTextArea(contentText);
        postContent.setFont(new Font("Arial", Font.PLAIN, 15));
        postContent.setWrapStyleWord(true);
        postContent.setLineWrap(true);
        postContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        postContent.setOpaque(false);

        JScrollPane contentScrollPane = new JScrollPane(postContent);
        contentScrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(contentScrollPane, BorderLayout.CENTER);

        // Post Image
        if (path != null) {
            JLabel postImage = new JLabel(new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
            postImage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.add(postImage, BorderLayout.EAST);
        }

        // "See More" Button for Long Content
        if (contentText.length() > 100) {
            postContent.setText(contentText.substring(0, 100) + "...");
            JButton seeMoreButton = createStyledButton("See More");
            seeMoreButton.addActionListener(e -> {
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
            });
            panel.add(seeMoreButton, BorderLayout.SOUTH);
        }

        return panel;
    }

    static public JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180)); // Steel Blue
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        button.setContentAreaFilled(true);
        // Hover effect
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
}
