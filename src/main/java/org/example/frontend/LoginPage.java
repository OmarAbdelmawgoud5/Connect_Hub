package org.example.frontend;

import org.example.backend.UserLogin;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.example.backend.GettingUserByPass;
import org.example.backend.NewsFeedPosts;
import org.example.backend.NewsFeedStory;
import org.example.backend.User;

public class LoginPage extends JFrame {

    public LoginPage() {
        setTitle("Connect Hub Login");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel backgroundLabel = new JLabel(new ImageIcon("src/main/resources/Login.jpeg"));
        backgroundLabel.setBounds(0, 0, 500, 600);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(100, 150, 150, 30);
        JTextField emailField = new JTextField();
        emailField.setBounds(100, 180, 300, 30);
        emailField.setForeground(Color.BLACK);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 220, 150, 30);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(100, 250, 300, 30);

        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(200, 300, 100, 40);
        loginButton.setBackground(new Color(59, 89, 152));
        loginButton.setForeground(Color.WHITE);

        JButton signUpButton = new JButton("SignUp");
        signUpButton.setBounds(200, 350, 100, 40);
        signUpButton.setBorderPainted(false);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setForeground(Color.BLACK);
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(signUpButton);
        panel.add(backgroundLabel);

        add(panel);

        signUpButton.addActionListener(e -> {
            this.dispose();
            new SignUpPage(this);
        });

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both email and password!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                UserLogin userLogin = new UserLogin();

                if (userLogin.login(email, password)) {
                    try {
                        JOptionPane.showMessageDialog(this, "Login Successful");
                        GettingUserByPass getUserBypass = new GettingUserByPass();
                        User user = getUserBypass.getUser(password);

                        NewsFeedPosts newsFeedPosts = new NewsFeedPosts(user.getUserId());

                        NewsFeedStory newsFeedStory;
                        newsFeedStory = new NewsFeedStory(user.getUserId());

                        NewsFeedFrame newsFeedFrame;
                        newsFeedFrame = new NewsFeedFrame(newsFeedPosts, newsFeedStory, user);

                    } catch (IOException ex) {
                        Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    this.dispose();
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);

    }
}
