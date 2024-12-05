package org.example.frontend;

import org.example.backend.FriendsJson;
import org.example.backend.User;
import org.example.backend.UserJson;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FriendRequestPanel extends JPanel implements ActionListener {
    private FriendsJson user1;
    private User user2;
    private JButton acceptButton;
    private JButton declineButton;
    public FriendRequestPanel(User user) {
        this.user2 = user;
        
        this.setLayout(null);
        setSize(150,240);
        setVisible(true);

        ImageIcon originalIcon = new ImageIcon(user.getProfilePhoto());
        Image resizedImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setBounds(0, 0, 150, 150);
        add(imageLabel);

        JLabel nameLabel=new JLabel(user.getUserName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setBounds(5, 160, 145, 20);
        add(nameLabel);
         acceptButton=new JButton("Confirm");
        acceptButton.setBackground(new Color(11, 103, 254));
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setFont(new Font("Arial", Font.BOLD, 16));
        acceptButton.setBounds(5, 185, 140, 30);
        add(acceptButton);
        acceptButton.addActionListener(this);
         declineButton=new JButton("Decline");
        declineButton.setBackground(new Color(79, 81, 82));
        declineButton.setForeground(Color.WHITE);
        declineButton.setFont(new Font("Arial", Font.BOLD, 16));
        declineButton.setBounds(5, 220, 140, 30);
        add(declineButton);
        declineButton.addActionListener(this);

        repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
          if(e.getSource()==acceptButton)
          {
              remove(acceptButton);
              declineButton.setText("Accepted");
              declineButton.setBackground(Color.LIGHT_GRAY);
              declineButton.setEnabled(false);

              repaint();
          }
          else if(e.getSource()==declineButton){
              remove(acceptButton);
              declineButton.setText("Declined");
              declineButton.setBackground(Color.LIGHT_GRAY);
              declineButton.setEnabled(false);
              repaint();
          }

    }
}
