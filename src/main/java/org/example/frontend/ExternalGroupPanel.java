package org.example.frontend;

import org.example.backend.Group;
import org.example.backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ExternalGroupPanel extends JPanel {
    public ExternalGroupPanel(Group group, User user) {
        super();
        setPreferredSize(new Dimension(500, 200));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
       // setBackground(Color.WHITE);

        ImageIcon icon = new ImageIcon(group.getPhotoPath());
        Image image = icon.getImage();
        image = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        JLabel groupImageLabel = new JLabel(icon);
        groupImageLabel.setPreferredSize(new Dimension(150, 150));

        JPanel ndPanel = new JPanel();
       // ndPanel.setBackground(Color.WHITE);
        ndPanel.setLayout(new BoxLayout(ndPanel, BoxLayout.Y_AXIS));
        JLabel groupNameLabel = new JLabel(group.getName());
        groupNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel groupDescriptionLabel = new JLabel(group.getDescription());
        groupDescriptionLabel.setFont(new Font("Arial", Font.ITALIC, 13));
        groupDescriptionLabel.setForeground(Color.GRAY);
        ndPanel.add(groupNameLabel);
        ndPanel.add(groupDescriptionLabel);

        add(groupImageLabel);
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(ndPanel);
        this.setVisible(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    new groupsPage(user,group);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


    }
}
