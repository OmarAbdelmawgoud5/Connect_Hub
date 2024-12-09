package org.example.frontend;

import org.example.backend.Content;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class postCard {

    static  public JPanel createPostCard(Content post,String name,String pf) {
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
        JLabel postProfilePhoto = new JLabel(new ImageIcon(new ImageIcon(pf).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
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
                    new ImageIcon(post.getContent().getImage()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            c.setBounds(450, 50, 500, 100);
            panel.add(c);
        }panel.add(contentScrollPane, BorderLayout.CENTER);
        // "See More" Button if Text is Long
        if (contentText.length() > 100) {
            postContent.setText(contentText.substring(0,100));// Adjust threshold as needed
            JButton seeMoreButton = createStyledButton("See More");
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
            seeMoreButton.setFont(new Font("Arial", Font.PLAIN, 10));
            panel.add(seeMoreButton, BorderLayout.SOUTH);
        }

        return panel;
    }


    static  public JButton createStyledButton(String text)
    {
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
