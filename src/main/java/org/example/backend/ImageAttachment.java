package org.example.backend;

import javax.swing.*;
import java.io.File;

public class ImageAttachment {

    public static String attachImage(JLabel imagePathLabel) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "gif", "jpeg"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            imagePathLabel.setText(filePath);
            return filePath;
        } else {
            imagePathLabel.setText("No image attached");
            return null;
        }
    }
}
