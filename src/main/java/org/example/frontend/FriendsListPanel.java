package org.example.frontend;

import org.example.backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendsListPanel extends JPanel implements ActionListener {
    private User user;
    private JButton optionsButton;
    private JPopupMenu popupMenu;
    private JMenuItem item1;
    private JMenuItem item2;
    private JPanel parentPanel;
    private JFrame mainFrame;
    public FriendsListPanel(User user) {
        this.user = user;
        this.parentPanel = parentPanel;
        this.mainFrame = mainFrame;
        this.setLayout(null);
        setSize(680,170);
        setVisible(true);

        ImageIcon originalIcon = new ImageIcon(user.getProfilePhoto());
        Image resizedImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setBounds(0, 0, 150, 150);
        add(imageLabel);

        JLabel nameLabel=new JLabel(user.getUserName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setBounds(180, 80, 200, 20);
        add(nameLabel);

         optionsButton=new JButton("...");
        optionsButton.setFont(new Font("Arial", Font.BOLD, 30));
        optionsButton.setBounds(630,60,30,30);
        optionsButton.setOpaque(false);
        optionsButton.setBackground(null);
        optionsButton.setBorderPainted(false);

        optionsButton.addActionListener(this);
       add(optionsButton);
        popupMenu=new JPopupMenu();
         item1=new JMenuItem("Unfriend");
         item2=new JMenuItem("Block");
        popupMenu.add(item1);
        popupMenu.add(item2);
        item1.addActionListener(this);
        item2.addActionListener(this);

        repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==optionsButton)
        {
            popupMenu.show(optionsButton,-50,0);
        }
        else if(e.getSource()==item1)
        {
            if(item1.getText().equals("Unfriend"))
            {
                item1.setText("Add friend");

            }
            else if(item1.getText().equals("Add friend"))
            {
                item1.setText("Sent Friend Request");
                item1.setEnabled(false);

            }
            else if(item1.getText().equals("Sent Friend Request"))
            {

            }
        }
        else if(e.getSource()==item2)
        {
            if(item2.getText().equals("Block"))
            {
                item2.setText("UnBlock");
            }
            else if(item2.getText().equals("UnBlock"))
            {
                item2.setText("Block");
            }
        }

    }
}
