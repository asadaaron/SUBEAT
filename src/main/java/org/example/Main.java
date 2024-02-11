package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ProgressbarCls progressbarCls = new ProgressbarCls();
        JPanel jPanel = progressbarCls.getMainPanel();
        JProgressBar jProgressBar = progressbarCls.getProgressBar();
        jProgressBar.setStringPainted(true);
        progressbarCls.setContentPane(jPanel);
        progressbarCls.setTitle("Data Extraction and Anonymization");
        progressbarCls.setSize(700, 600);
        progressbarCls.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        progressbarCls.setVisible(true);
    }

}