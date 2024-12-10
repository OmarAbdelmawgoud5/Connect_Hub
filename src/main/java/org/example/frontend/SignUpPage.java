package org.example.frontend;

import org.example.backend.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUpPage extends JDialog {

    private String profilePhotoPath = "No file chosen";  
    private String coverPhotoPath = "No file chosen"; 

    public SignUpPage(JFrame parent) {
        super(parent, "Connect Hub Sign Up", true);
        setSize(490, 650);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel backgroundLabel = new JLabel(new ImageIcon("src/main/resources/hi.jpg"));
        backgroundLabel.setBounds(0, 0, 490, 650);

        JLabel nameLabel = new JLabel("User name");
        nameLabel.setBounds(100, 70, 150, 30);
        JTextField nameField = new JTextField();
        nameField.setBounds(100, 100, 300, 30);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(100, 140, 150, 30);
        JTextField emailField = new JTextField();
        emailField.setBounds(100, 170, 300, 30);

        JLabel birthdayLabel = new JLabel("Birthday (YYYY-MM-DD)");
        birthdayLabel.setBounds(100, 225, 180, 30);

        JTextField yearField = new JTextField();
        yearField.setBounds(100, 255, 50, 25);

        JTextField monthField = new JTextField();
        monthField.setBounds(160, 255, 30, 25);

        JTextField dayField = new JTextField();
        dayField.setBounds(200, 255, 30, 25);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 310, 150, 30);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(100, 340, 300, 30);

        JLabel profilePhotoLabel = new JLabel("Profile Photo");
        profilePhotoLabel.setBounds(100, 380, 150, 30);
        JButton profilePhotoButton = new JButton("Choose File");
        profilePhotoButton.setBounds(250, 380, 150, 25);
        JLabel profilePathLabel = new JLabel("No file chosen");
        profilePathLabel.setBounds(100, 410, 300, 20);

        JLabel coverPhotoLabel = new JLabel("Cover Photo");
        coverPhotoLabel.setBounds(100, 440, 150, 30);
        JButton coverPhotoButton = new JButton("Choose File");
        coverPhotoButton.setBounds(250, 440, 150, 25);
        JLabel coverPathLabel = new JLabel("No file chosen");
        coverPathLabel.setBounds(100, 470, 300, 20);

        profilePhotoButton.addActionListener(e -> {
            profilePhotoPath = ImageAttachment.attachImage(profilePathLabel);
        });

        coverPhotoButton.addActionListener(e -> {
            coverPhotoPath = ImageAttachment.attachImage(coverPathLabel);
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(200, 500, 100, 40);
        signUpButton.setBackground(new Color(59, 89, 152));
        signUpButton.setForeground(Color.WHITE);

        JButton backButton = new JButton("Back");
        backButton.setBounds(20, 550, 70, 25);
        backButton.setBackground(new Color(0, 0, 0));
        backButton.setForeground(Color.WHITE);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(birthdayLabel);
        panel.add(yearField);
        panel.add(monthField);
        panel.add(dayField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(profilePhotoLabel);
        panel.add(profilePhotoButton);
        panel.add(profilePathLabel);
        panel.add(coverPhotoLabel);
        panel.add(coverPhotoButton);
        panel.add(coverPathLabel);
        panel.add(signUpButton);
        panel.add(backButton);
        panel.add(backgroundLabel);

        add(panel);

        signUpButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String year = yearField.getText();
            String month = monthField.getText();
            String day = dayField.getText();
            String password = new String(passwordField.getPassword());
            if (!name.matches("^[a-zA-Z ]+$")) {
                JOptionPane.showMessageDialog(this, "Invalid Name", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(this, "Invalid Email", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!year.matches("^(\\d{4})$") || !month.matches("^(0[1-9]|1[0-2])$") || !day.matches("^(0[1-9]|[12][0-9]|3[01])$")) {
                JOptionPane.showMessageDialog(this, "Invalid date. Enter valid numeric values for year, month, and day.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
                JOptionPane.showMessageDialog(this, "Invalid Password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (profilePhotoPath==null || coverPhotoPath==null) {
                JOptionPane.showMessageDialog(this, "You have to choose Pics.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (profilePhotoPath.equals("No file chosen") || coverPhotoPath.equals("No file chosen")) {
                JOptionPane.showMessageDialog(this, "You have to choose Pics.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                LocalDate dob = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
                User newUser = new User(name, email, dob, profilePhotoPath, coverPhotoPath, null,password, "Online");
                UserSignUp userSignUp = null;
                try {
                    userSignUp = new UserSignUp();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                boolean added = userSignUp.signUp(newUser);

                String message = (added ? "User added successfully!" : "User already exists!");
                JOptionPane.showMessageDialog(null, message);
                if (added) {
                    try {
                        new NewsFeedFrame(new NewsFeedPosts(newUser.getUserId()),newUser);
                    } catch (IOException ex) {
                        Logger.getLogger(SignUpPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    dispose();
                }
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new LoginPage().setVisible(true);
        });

        setVisible(true);
    }
}
