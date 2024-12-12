package org.example.frontend;

import org.example.backend.GettingUserByUserId;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import org.example.backend.*;

public class NotificationPage extends JDialog {

    private NotificationDatabaseLoader notificationLoader;
    private JPanel panel;
    private String userId;

    public NotificationPage(String userId) throws IOException {
        notificationLoader = new NotificationDatabaseLoader();
        this.userId = userId;

        setTitle("Notifications");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(10, 50, 570, 500);
        add(scrollPane);

        JButton backButton = new JButton("Back");
        backButton.setBounds(5, 5, 70, 25);
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(backButton);
        backButton.addActionListener(e -> {
            try {
                this.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        try {
            List<NotificationAbstract> notifications = notificationLoader.loadNotifications(userId);
            int yPosition = 10;
            for (NotificationAbstract notification : notifications) {
                JPanel notificationPanel = createNotificationPanel(notification, yPosition);
                panel.add(notificationPanel);
                yPosition += 120;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    private JPanel createNotificationPanel(NotificationAbstract notification, int yPosition) throws IOException {
        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(null);
        notificationPanel.setBounds(10, yPosition, 540, 100);
        notificationPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel messageLabel = new JLabel(notification.getMessage());
        messageLabel.setBounds(10, 10, 500, 40);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        notificationPanel.add(messageLabel);

        JButton markAsReadButton = new JButton("Mark as Read");
        markAsReadButton.setBounds(230, 50, 150, 30);
        notificationPanel.add(markAsReadButton);

        if (notification instanceof FriendRequestNotification) {
            GettingUserByUserId gettingUser = new GettingUserByUserId();
            User recipientUser = gettingUser.getUser(userId);
            User senderUser = gettingUser.getUser(notification.getSenderId());

            JButton acceptButton = new JButton("Accept");
            JButton declineButton = new JButton("Decline");
            acceptButton.setBounds(10, 50, 100, 30);
            declineButton.setBounds(120, 50, 100, 30);
            notificationPanel.add(acceptButton);
            notificationPanel.add(declineButton);

            acceptButton.addActionListener(e -> {
                notification.markAsRead();
                messageLabel.setText("Friend request accepted.");
                disableButtons(acceptButton, declineButton, markAsReadButton);
                try {
                    FriendsManagerWriter.friendsWriter(senderUser, recipientUser, UserAction.AcceptRequest);
                    NotificationDatabaseSaver.updateNotificationStatus(notification.getNotificationId(), notification.getRecipientId(), true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            declineButton.addActionListener(e -> {
                notification.markAsRead();
                messageLabel.setText("Friend request declined.");
                disableButtons(acceptButton, declineButton, markAsReadButton);
                try {
                    FriendsManagerWriter.friendsWriter(senderUser, recipientUser, UserAction.UnFriend);
                    NotificationDatabaseSaver.updateNotificationStatus(notification.getNotificationId(), notification.getRecipientId(), true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        markAsReadButton.addActionListener(e -> {
            notification.markAsRead();
            messageLabel.setText("Notification marked as read.");
            markAsReadButton.setEnabled(false);
            markAsReadButton.setBackground(Color.LIGHT_GRAY);
            try {
                NotificationDatabaseSaver.updateNotificationStatus(notification.getNotificationId(), notification.getRecipientId(), true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        return notificationPanel;
    }

    private void disableButtons(JButton acceptButton, JButton declineButton, JButton markAsReadButton) {
        acceptButton.setEnabled(false);
        declineButton.setEnabled(false);
        markAsReadButton.setEnabled(false);
        acceptButton.setBackground(Color.LIGHT_GRAY);
        declineButton.setBackground(Color.LIGHT_GRAY);
        markAsReadButton.setBackground(Color.LIGHT_GRAY);
    }

}
