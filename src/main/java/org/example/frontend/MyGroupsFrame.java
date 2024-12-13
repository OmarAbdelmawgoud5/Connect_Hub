package org.example.frontend;

import org.example.backend.Group;
import org.example.backend.GroupDBReader;
import org.example.backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

public class MyGroupsFrame extends JFrame implements ActionListener {
   JButton backButton;
   User user;
    public MyGroupsFrame(User user) {
        super("My Groups");
        this.user = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(800,800);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        Map<String,String> groups=user.getGroups();



        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        for(Map.Entry<String,String> entry:groups.entrySet()){
            Group group= GroupDBReader.getInstance().readGroups(entry.getKey());
            ExternalGroupPanel groupPanel = new ExternalGroupPanel(group,user,this);
            groupPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(groupPanel);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        }
        panel.setPreferredSize(panel.getMaximumSize());
        panel.setVisible(true);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //scrollPane.setBounds(0,0,780,800);

        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        backButton.addActionListener(this);
        backButton.setSize(backButton.getPreferredSize());

        add(backButton);
        add(scrollPane);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==backButton)
        {
            this.setVisible(false);
            try {
                new Profile(user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
