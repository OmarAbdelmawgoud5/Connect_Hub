package org.example.frontend;
import org.example.backend.Group;
import org.example.backend.Search;
import org.example.backend.SearchGroups;
import org.example.backend.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SearchDialog extends JDialog {
    private User user;
    public SearchDialog(JFrame parent,User user) {
        super(parent, "Search Content", true);
        this.user = user;
        setSize(600, 450);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel backgroundLabel = new JLabel(new ImageIcon("src/main/resources/SearchBackground.jpeg"));
        backgroundLabel.setBounds(0, 0, 600, 450);
        JLabel searchLabel = new JLabel("Enter search query:");
        searchLabel.setBounds(20, 20, 200, 30);
        panel.add(searchLabel);
        JTextArea searchTextArea = new JTextArea();
        searchTextArea.setBounds(20, 60, 540, 50);
        searchTextArea.setLineWrap(true);
        searchTextArea.setWrapStyleWord(true);
        searchTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(searchTextArea);
        JButton searchUsersButton = new JButton("Search Users");
        searchUsersButton.setBounds(150, 150, 150, 40);
        searchUsersButton.setBackground(new Color(59, 89, 152));
        searchUsersButton.setForeground(Color.WHITE);
        panel.add(searchUsersButton);
        JButton searchGroupsButton = new JButton("Search Groups");
        searchGroupsButton.setBounds(320, 150, 150, 40);
        searchGroupsButton.setBackground(new Color(59, 89, 152));
        searchGroupsButton.setForeground(Color.WHITE);
        panel.add(searchGroupsButton);
        panel.add(backgroundLabel);
        add(panel);
        searchUsersButton.addActionListener(e -> {
            String query = searchTextArea.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Search query cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                performUserSearch(query);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Search Groups button logic
        searchGroupsButton.addActionListener(e -> {
            String query = searchTextArea.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Search query cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            performGroupSearch(query);
            this.dispose();
        });

        this.setVisible(true);
    }

    // Search logic for users
    private void performUserSearch(String query) throws IOException {
        Search search=Search.getInstance();
        ArrayList<User> users=search.getUsers(query);
        if (users.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No users found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new SearchUser(this,user,users);
    }
    private void performGroupSearch(String query) {
        SearchGroups search=SearchGroups.getInstance();
        ArrayList<Group> groups=search.getGroups(query);
        if (groups.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No groups found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

    }
}
