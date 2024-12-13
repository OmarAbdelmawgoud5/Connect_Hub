package org.example.frontend;

import org.example.backend.Group;
import org.example.backend.GroupDBWriter;
import org.example.backend.User;
import org.example.backend.UserJson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class createnewgroup extends JDialog {
    private JTextField groupNameField;
    private JTextArea groupDescriptionArea;
    private JLabel groupPhotoLabel;

    public Group getG() {
        return g;
    }

    private String groupPhotoPath;
    private Group g;
    public createnewgroup(Frame parent, User u) {
        super(parent, "Create New Group", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Group Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Group Name:"), gbc);

        groupNameField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(groupNameField, gbc);

        // Group Description
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Group Description:"), gbc);

        groupDescriptionArea = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(groupDescriptionArea);
        gbc.gridx = 1;
        inputPanel.add(scrollPane, gbc);

        // Group Photo
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Group Photo:"), gbc);

        JButton choosePhotoButton = new JButton("Choose Photo");
        gbc.gridx = 1;
        inputPanel.add(choosePhotoButton, gbc);

        groupPhotoLabel = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 3;
        inputPanel.add(groupPhotoLabel, gbc);

        choosePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(createnewgroup.this) == JFileChooser.APPROVE_OPTION) {
                    groupPhotoPath = fileChooser.getSelectedFile().getAbsolutePath();
                    groupPhotoLabel.setText("Photo selected: " + fileChooser.getSelectedFile().getName());
                }
            }
        });

        add(inputPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            if (validateInput()) {
                 g=new Group(getGroupName(),getGroupPhotoPath(),getGroupDescription());
                 g.addMember(u.getUserId());
                 GroupDBWriter.addGroup(g);
                 u.addGroup(g.getGroupId(),"Owner");
                try {
                    new UserJson().editUser(u);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dispose(); // Close the dialog
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        add(buttonsPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private boolean validateInput() {
        if (groupNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Group name is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (groupDescriptionArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Group description is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (groupPhotoPath == null || groupPhotoPath.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Group photo is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public String getGroupName() {
        return groupNameField.getText().trim();
    }

    public String getGroupDescription() {
        return groupDescriptionArea.getText().trim();
    }

    public String getGroupPhotoPath() {
        return groupPhotoPath;
    }
}
