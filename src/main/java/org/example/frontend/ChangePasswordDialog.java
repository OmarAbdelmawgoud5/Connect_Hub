package org.example.frontend;

import org.example.backend.Encryption;
import org.example.backend.User;
import org.example.backend.UserJson;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChangePasswordDialog extends JDialog {
    public ChangePasswordDialog(User user,Profile parent) {
        super(parent, "Change Password", true);
        setSize(300, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setLocationRelativeTo(parent);

        // Components for the dialog
        JLabel oldPasswordLabel = new JLabel("Old Password:");
        JLabel newPasswordLabel = new JLabel("New Password:");
        JPasswordField oldPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        // Add components
        add(oldPasswordLabel);
        add(oldPasswordField);
        add(newPasswordLabel);
        add(newPasswordField);
        add(submitButton);
        add(cancelButton);

        // Action listener for Submit button
        submitButton.addActionListener(e -> {
            if(!oldPasswordField.getText().equals("") && (!newPasswordField.getText().equals(""))) {

                if(Encryption.verifyPassword(oldPasswordField.getText(), user.getPassword()))
                {
                    System.out.println(newPasswordField.getText());
                    String g=newPasswordField.getText();
                    if (!g.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
                        JOptionPane.showMessageDialog(parent, "Invalid Password.", "Error", JOptionPane.ERROR_MESSAGE);
                        System.out.println("lolpo");
                        dispose();

                    }
                    else {
                        user.setPassword(newPasswordField.getText());
                        System.out.println("Yes");
                        JOptionPane.showMessageDialog(this, "Ok!", "Done", 1);
                        try {
                            UserJson.getdb().editUser(user);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Wrong Password", "Failed", 0);
                    System.out.println("No");
                }

            }

            dispose();
        });
        setVisible(true);
        // Action listener for Cancel button
        cancelButton.addActionListener(e -> dispose());
    }
}
