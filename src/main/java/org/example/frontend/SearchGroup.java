package org.example.frontend;

import org.example.backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SearchGroup extends JFrame implements ActionListener {
    private User currentUser;
    private ArrayList<Group> groupsList; // List of groups
    private Map<String,String> Groups;
    private ArrayList<Group> enrolledGroups;
    private JButton backButton;
    private JPanel resultsPanel;
    private JDialog parent;

    public SearchGroup(JDialog parent, User currentUser, ArrayList<Group> groupsList) throws IOException {
        super("View Groups");
        this.currentUser = currentUser;
        this.groupsList = groupsList;
        this.Groups = currentUser.getGroups();
        this.parent = parent;
        enrolledGroups=getGroupsList();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Groups");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(this);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Results panel for displaying groups
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        displayGroups();

        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Combine panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            parent.setVisible(true);
        }
    }
    private ArrayList<Group> getGroupsList() {
        ArrayList<Group>groups=new ArrayList<>();
        GettingGroupByGroupId gettingGroupByGroupId=new GettingGroupByGroupId();
        for (String groupId : Groups.keySet()) {
            groups.add(gettingGroupByGroupId.getGroup(groupId));
        }
        return groups;
    }
    // Display the provided list of groups
    private void displayGroups() {
        for (Group group : groupsList) {
            resultsPanel.add(createGroupPanel(group));
            resultsPanel.add(Box.createVerticalStrut(10));
        }
    }

    private boolean isEnrolledInGroup(Group group) {
        for (Group enrolledGroup : enrolledGroups) {
            if (enrolledGroup.getGroupId().equals(group.getGroupId())) {
                return true;
            }
        }
        return false;
    }

    private JPanel createGroupPanel(Group group) {
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        groupPanel.setPreferredSize(new Dimension(650, 60));
        groupPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel groupNameLabel = new JLabel(group.getName());
        groupNameLabel.setPreferredSize(new Dimension(300, 30));
        groupPanel.add(groupNameLabel);

        // Check if the user is already enrolled in the group
        if (isEnrolledInGroup(group)) {
            // If the user is already a member
            JButton leaveButton = new JButton("Leave Group");
            leaveButton.addActionListener(e -> {
                currentUser.removeGroup(group.getGroupId());
                JOptionPane.showMessageDialog(this, "You have left the group.");
            });
            groupPanel.add(leaveButton);
        } else {
            // If the user is not a member
            JButton joinButton = new JButton("Join Group");
            joinButton.addActionListener(e -> {
                currentUser.addGroup(group.getGroupId(),"member");
                JOptionPane.showMessageDialog(this, "You have joined the group.");
            });
            groupPanel.add(joinButton);
        }

        // View group button
        JButton viewGroupButton = new JButton("View Group");
        viewGroupButton.addActionListener(e -> {
            try {
                new groupsPage(currentUser, group);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        groupPanel.add(viewGroupButton);

        return groupPanel;
    }
}

