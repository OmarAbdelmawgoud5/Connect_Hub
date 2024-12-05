package org.example.frontend;

import org.example.backend.User;
import org.example.backend.UserJson;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChangeBioDialog extends JDialog {
    public ChangeBioDialog(Profile parent, User user) {
        super(parent, "Change Bio", true);
        setSize(300, 150);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        // Components for the dialog
        JLabel bioLabel = new JLabel("Enter new bio:");
        JTextArea bioTextArea = new JTextArea();
        bioTextArea.setLineWrap(true);
        bioTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(bioTextArea);

        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        // Add components
        add(bioLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for Submit button
        submitButton.addActionListener(e -> {
            String newBio = bioTextArea.getText();
            user.setBio(newBio);
            try {
                UserJson.getdb().editUser(user);
                parent.userBio.setText(newBio );
                JOptionPane.showMessageDialog(this, "Ok!", "Done", 1);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });
        // Action listener for Cancel button
        cancelButton.addActionListener(e -> this.dispose());
        setVisible(true);

    }
}
