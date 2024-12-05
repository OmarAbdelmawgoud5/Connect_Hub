package org.example.Frontend;

import org.example.Backend.Posts;
import org.example.Backend.User;

import  javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Profile extends javax.swing.JFrame {

    JLabel imagelabel;
    int x=0;
    JTextArea namelabel;
    public User myUser;
    public Profile(User user) {
        myUser=user;
        initComponents();
        setTitle("My Profile");
        setSize(500, 600);
        intialFriendsButton();
        intialCreatePostButton();

        intialSetName();
        intialSetProfile();
       intialSetCover();
        intialChangeBioButton();
        intialChangePasswordButton();
        intialBackButton();
        imagelabel =new JLabel();
        imagelabel.setIcon(new ImageIcon(new ImageIcon(myUser.getProfilePhoto()).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH))); //Sets the image to be displayed as an icon
        fetchposts();
        JLabel backgroundLabel = new JLabel(new ImageIcon("D:\\College\\Term 5\\Programming 2\\lab9\\ConnectHub\\src\\main\\resources\\background.jpg"));
        backgroundLabel.setBounds(0, 22, 500, 600);

        add(backgroundLabel);
        JLabel l= new JLabel(new ImageIcon("D:\\College\\Term 5\\Programming 2\\lab9\\ConnectHub\\src\\main\\resources\\whitebar.png"));
        l.setBounds(0,0,500,22);
        add(l);
        this.setVisible(true);
    }
    void intialBackButton()
    {

        var back = new javax.swing.JButton();
        back.setFont(new java.awt.Font("Times New Roman", 0, 12));
        back.setBackground(new java.awt.Color(255, 255, 255));

        back.setText("Back");
        back.setToolTipText("");
        back.setBorder(null);
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreatePostActionPerformed(evt);
            }
        });
        back.setVisible(true);
        back.setBounds(10,5 ,30 ,15);
        add(back);
    }
    void intialSetCover()
    {
        ImageIcon bannerIcon = new ImageIcon("D:\\lol.jpg");

        // Get the original dimensions of the banner image
        int bannerWidth = bannerIcon.getIconWidth();
        int bannerHeight = bannerIcon.getIconHeight();

        // Resize the image to fit the panel's width, maintaining aspect ratio
        int panelWidth = 500; // Example panel width
        int panelHeight = 600; // Example panel height
        float aspectRatio = (float) bannerWidth / bannerHeight;

        int newWidth, newHeight;
        if (bannerWidth > bannerHeight) { // Landscape
            newWidth = panelWidth;
            newHeight = Math.round(panelWidth / aspectRatio);
        } else { // Portrait or square
            newHeight = panelHeight;
            newWidth = Math.round(panelHeight * aspectRatio);
        }

        // Resize the image
        Image scaledImage = bannerIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);


        JLabel label2 = new JLabel(); //JLabel Creation
        label2.setIcon(new ImageIcon(scaledImage)); //Sets the image to be displayed as an icon
        label2.setBounds(0, 22, 500, 60); //Sets the location of the image
        add(label2);
}
    void intialSetProfile()
    {
        Container c=getContentPane();
        JLabel label = new JLabel(); //JLabel Creation
        label.setIcon(new ImageIcon(new ImageIcon(myUser.getCoverPhoto()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH))); //Sets the image to be displayed as an icon
        label.setBounds(225, 40, 50, 50); //Sets the location of the image
        c.add(label);

    }

    void intialCreatePostButton()
    {
        var createpost = new javax.swing.JButton();
        createpost.setBackground(new java.awt.Color(204, 204, 204));
        createpost.setText("Create New Post");
        createpost.setToolTipText("");
        createpost.setBorder(null);
        createpost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreatePostActionPerformed(evt);
            }
        });
        createpost.setVisible(true);
        createpost.setBounds(0,200 ,50 ,50);
        // add(createpost);
    }
    void intialFriendsButton()
    {
        var friends = new javax.swing.JButton();
        friends.setBackground(new java.awt.Color(204, 204, 204));
        friends.setText("Friends");
        friends.setToolTipText("");
        friends.setBorder(null);
        friends.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                friendsActionPerformed(evt);
            }
        });
        friends.setVisible(true);
        friends.setBounds(0,300 ,50 ,50);
        // add(friends);
    }
    void intialChangePasswordButton()
    {
        var ChangePasswaord = new javax.swing.JButton();
        ChangePasswaord.setBackground(new java.awt.Color(204, 204, 204));
        ChangePasswaord.setText("Change Password");
        ChangePasswaord.setToolTipText("");
        ChangePasswaord.setBorder(null);
        ChangePasswaord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout(evt);
            }
        });
        ChangePasswaord.setVisible(true);
        ChangePasswaord.setBounds(0,150 ,50 ,50);
        // add(ChangePasswaord);
    }
    void intialChangeBioButton()
    {
        var bio=new javax.swing.JTextArea();
        bio.setEditable(false);
        String s="Keep it Pure";
        int tenp=s.length();
        bio.setText(s);
        bio.setFont(new java.awt.Font("Times New Roman", 0, 12));

        bio.setBounds(250-(int)(2.25*tenp) ,115,(int)(5.5*tenp),20);
        //bio.setSelectionColor(new java.awt.Color(255, 255, 255));

        add(bio);
    }
    void intialSetName()
    {
        var name=new javax.swing.JTextArea();
        name.setEditable(false);
        String sx="Yousef";
        int t=sx.length();
        name.setText(sx);
        name.setFont(new java.awt.Font("Showcard Gothic", 0, 24));
        name.setBounds(250-(int)(7*t) ,92,15*t,25);
        add(name);
    }
    private JPanel getpanel(Posts s, int i)
    {
        var panel=new JPanel();
        panel.setBackground(new java.awt.Color(165,210,207));
        panel.setLayout(null);
        panel.setBounds(0,(i*75)+150,500,70);
        var locallabel=new javax.swing.JLabel();
        locallabel.setIcon(new ImageIcon(new ImageIcon(myUser.getProfilePhoto()).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH))); //Sets the image to be displayed as an icon
        locallabel.setBounds(-5,-10,30,40);
        panel.add(locallabel);
        int n=myUser.getUserName().length();
        var localname=new javax.swing.JTextArea();
        localname.setBounds(16,-3,7*n,17);
        localname.setEditable(false);
        localname.setText(myUser.getUserName());
        localname.setFont(new java.awt.Font("Segoe UI Variable", 0, 13));
        panel.add(localname);
        var localContent=new javax.swing.JTextArea();
        localContent.setBounds(240-4*s.getContent().length(),25,9*s.getContent().length(),20);
        localContent.setBackground(new java.awt.Color(165,210,207));

        System.out.println(s.getContent().length());
        localContent.setEditable(false);
        localContent.setText(s.getContent());
        localContent.setFont(new java.awt.Font("Lucida Console", 0, 15));
        panel.add(localContent);
        return panel;
    }
    private void fetchposts()
    {
        //ArrayList<Posts>=getposts(myUser.id);
        ArrayList<Posts> listt =new ArrayList<Posts>();
        var temp=new Posts("Omar is a good boy but have some issues");
        listt.add(temp);
        temp=new Posts("Yousef");
        listt.add(temp);
        temp=new Posts("Eyad");
        listt.add(temp);
        temp=new Posts("Khaled");
        listt.add(temp);
        temp=new Posts("More");
        listt.add(temp);
        var i=0;
        for(;i<listt.size();i++)
        {
            var p=getpanel(listt.get(i),i);
            add(p);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jButton1 = new javax.swing.JButton();


        jButton1.setText("jButton1");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Profile");
        //setBackground(new java.awt.Color(102, 153, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {

    }
    private void friendsActionPerformed(java.awt.event.ActionEvent evt) {
        new friends(myUser);
        this.dispose();

    }
    private void  CreatePostActionPerformed(java.awt.event.ActionEvent evt)
    {
        //new Createpost();
        //this.dispose();
    }
    private void  logout(java.awt.event.ActionEvent evt)
    {
            //new login();
        //this.dispose();

    }

    /**
     * @param args the command line arguments
     */

        /* Create and display the form */

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    // End of variables declaration
}
