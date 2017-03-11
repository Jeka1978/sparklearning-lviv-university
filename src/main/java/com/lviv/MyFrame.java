package com.lviv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Evegeny on 11/03/2017.
 */
public class MyFrame extends JFrame {
    public MyFrame() throws HeadlessException {
        JButton button = new JButton("click me");

//        button.addActionListener(e -> button.setText("already clicked"));
        button = new JButton();
    }
}
