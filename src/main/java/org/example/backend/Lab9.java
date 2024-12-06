package org.example.backend;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.example.frontend.*;

public class Lab9 {

    public static void main(String[] args) {

        FlatLightLaf.setup();
        new LoginPage();
    }
}
