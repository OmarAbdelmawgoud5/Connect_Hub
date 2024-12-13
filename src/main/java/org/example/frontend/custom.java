package org.example.frontend;

import org.example.backend.Content;
import org.example.backend.Group;
import org.example.backend.GroupDBWriter;
import org.example.backend.groupPostsJson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class custom extends JPanel implements ActionListener
{
    private JButton optionsButton;
    private JPopupMenu popupMenu;
    private JMenuItem item1;
    private Group g;
    private JFrame p;
    public void setG(Group g) {
        this.g = g;
    }

    public void setC(Content c) {
        this.c = c;
    }

    public void setP(JFrame p) {
        this.p = p;
    }

    private JMenuItem item2;
    private Content c;
    public void setOptionsButton(JButton optionsButton) {
        this.optionsButton = optionsButton;
    }

    public void setPopupMenu(JPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }

    public void setItem1(JMenuItem item1) {
        this.item1 = item1;
    }

    public void setItem2(JMenuItem item2) {
        this.item2 = item2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==optionsButton)
        {
            popupMenu.show(optionsButton,200,250);
        }
        else if(e.getSource()==item1)
        {
            try {
                groupPostsJson.delete(c);
                g.removeContent(c.getContentId());
                GroupDBWriter.addGroup(g);
                popupMenu.setVisible(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            new ContentCreationPage(p,c.getAuthorId(),g,c.getContent().getText(),c.getContent().getImage());
        }
        else if(e.getSource()==item2)
        {
            if(item2.getText().equals("Delete"))
            {
                item2.setText("Deleted");
                popupMenu.setVisible(false);
                try {
                    groupPostsJson.delete(c);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                g.removeContent(c.getContentId());
                GroupDBWriter.addGroup(g);

            }
            else if(item2.getText().equals("Deleted"))
            {

            }
        }
    }
}