package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatLightLaf;
import org.example.frontend.LoginPage;
import org.example.frontend.NewsFeedFrame;
import org.example.frontend.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import  org.example.backend.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws IOException {
       FlatLightLaf.setup();
        UIManager.put( "Button.arc", 15 );
        new LoginPage();



    }
}