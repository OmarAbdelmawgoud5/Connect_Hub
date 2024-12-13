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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new java.awt.Dimension(570, 1000));

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

        // Handle different types of notifications
        if (notification instanceof FriendRequestNotification) {
            handleFriendRequest(notificationPanel, (FriendRequestNotification) notification, messageLabel, markAsReadButton);
        } else if (notification instanceof GroupActivityNotification) {
            handleGroupActivity(notificationPanel, (GroupActivityNotification) notification, messageLabel, markAsReadButton);
        } else if (notification instanceof NewPostNotification) {
            handleNewPost(notificationPanel, (NewPostNotification) notification, messageLabel, markAsReadButton);
        }

        return notificationPanel;
    }

    private void handleFriendRequest(JPanel notificationPanel, FriendRequestNotification friendRequestNotification, JLabel messageLabel, JButton markAsReadButton) throws IOException {
        GettingUserByUserId gettingUser = new GettingUserByUserId();
        User recipientUser = gettingUser.getUser(userId);
        User senderUser = gettingUser.getUser(friendRequestNotification.getSenderId());

        JButton acceptButton = new JButton("Accept");
        JButton declineButton = new JButton("Decline");
        acceptButton.setBounds(10, 50, 100, 30);
        declineButton.setBounds(120, 50, 100, 30);
        messageLabel.setText(friendRequestNotification.getMessage());
        markAsReadButton.setEnabled(true);

        acceptButton.addActionListener(e -> {
            friendRequestNotification.markAsRead();
            messageLabel.setText("Friend request accepted.");
            disableButtons(acceptButton, declineButton, markAsReadButton);
            try {
                FriendsManagerWriter.friendsWriter(senderUser, recipientUser, UserAction.AcceptRequest);
                NotificationDatabaseSaver.updateNotificationStatus(friendRequestNotification.getNotificationId(), friendRequestNotification.getRecipientId(), true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        declineButton.addActionListener(e -> {
            friendRequestNotification.markAsRead();
            messageLabel.setText("Friend request declined.");
            disableButtons(acceptButton, declineButton, markAsReadButton);
            try {
                FriendsManagerWriter.friendsWriter(senderUser, recipientUser, UserAction.UnFriend);
                NotificationDatabaseSaver.updateNotificationStatus(friendRequestNotification.getNotificationId(), friendRequestNotification.getRecipientId(), true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        notificationPanel.add(acceptButton);
        notificationPanel.add(declineButton);
    }

    private void handleGroupActivity(JPanel notificationPanel, GroupActivityNotification groupActivityNotification, JLabel messageLabel, JButton markAsReadButton) throws IOException {
        messageLabel.setText(groupActivityNotification.getMessage());
        markAsReadButton.setEnabled(true);

        JButton moveToGroupButton = new JButton("Move to Group");
        moveToGroupButton.setBounds(10, 50, 150, 30);
        notificationPanel.add(moveToGroupButton);

        moveToGroupButton.addActionListener(e -> {
            // try {
            Group group = new GettingGroupByGroupId().getGroup(groupActivityNotification.getRecipientId());
/////////////////////////////////////////////////////////////////////عايزين هنا نروح لصفحة الجروب 
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
        });
    }

    private void handleNewPost(JPanel notificationPanel, NewPostNotification newPostNotification, JLabel messageLabel, JButton markAsReadButton) throws IOException {
        messageLabel.setText(newPostNotification.getMessage());
        markAsReadButton.setEnabled(true);

        JButton showButton = new JButton("Show");
        showButton.setBounds(10, 50, 100, 30);
        notificationPanel.add(showButton);

        showButton.addActionListener(e -> {
            // try {
            Group group = new GettingGroupByGroupId().getGroup(newPostNotification.getSenderId());
/////////////////////////////////////////////////////////////////////عايزين هنا نروح لصفحة الجروب 

//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
        });
    }

    private void disableButtons(JButton acceptButton, JButton declineButton, JButton markAsReadButton) {
        acceptButton.setEnabled(false);
        declineButton.setEnabled(false);
        markAsReadButton.setEnabled(false);
        acceptButton.setBackground(Color.LIGHT_GRAY);
        declineButton.setBackground(Color.LIGHT_GRAY);
        markAsReadButton.setBackground(Color.LIGHT_GRAY);
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        try {
            new NotificationPage("d4805154-8f3d-42b4-affe-609395e309de"); // Use a valid userId here
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
