package org.example.frontend;

import javax.swing.*;
import java.awt.*;

public class ChangePassword {
    JDialog x= new JDialog();
    ChangePassword(){
        x.setTitle("Change Password");

        x.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JTextField OldPassword= new JTextField();
        JPasswordField Newpassword= new JPasswordField();
        OldPassword.setEditable(false);
        OldPassword.setBounds(200,175,100,50);
        OldPassword.setBackground(Color.BLACK);
        Newpassword.setEditable(false);
        Newpassword.setBounds(200,250,100,50);


        OldPassword.setVisible(true);
        Newpassword.setVisible(true);
        x.add(OldPassword);
        x.add(Newpassword);
        x.setSize(500,500);
        x.setVisible(true);
    }
}
