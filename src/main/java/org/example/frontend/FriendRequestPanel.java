package org.example.frontend;

import org.example.backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FriendRequestPanel extends JPanel implements ActionListener {
    private User user1;
    private User user2;
    private Map<User,Integer>map1;
    private Map<User,Integer>map2;
    private JButton acceptButton;
    private JButton declineButton;
    public FriendRequestPanel(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        try {
            map1 = new FriendsJson(user1.getUserId()).getDb();
            map2 = new FriendsJson(user2.getUserId()).getDb();
        } catch ( IOException e) {
            System.out.println("hello");
        }
        this.setLayout(null);
        setSize(150,240);
        setVisible(true);

        ImageIcon originalIcon = new ImageIcon(user1.getProfilePhoto());
        Image resizedImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setBounds(0, 0, 150, 150);
        add(imageLabel);

        JLabel nameLabel=new JLabel(user1.getUserName());
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
              try {
                  FriendsManagerWriter.friendsWriter(user1,user2, UserAction.AcceptRequest);
              } catch (IOException ex) {
                  throw new RuntimeException(ex);
              }

          }
          else if(e.getSource()==declineButton){
              remove(acceptButton);
              declineButton.setText("Declined");
              declineButton.setBackground(Color.LIGHT_GRAY);
              declineButton.setEnabled(false);
              repaint();
              try {
                  FriendsManagerWriter.friendsWriter(user1,user2, UserAction.UnFriend);
              } catch (IOException ex) {
                  throw new RuntimeException(ex);
              }
          }

    }
}
