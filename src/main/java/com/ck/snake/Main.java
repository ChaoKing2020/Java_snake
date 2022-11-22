package com.ck.snake;


import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main(String title) throws HeadlessException {
        super(title);
        setBounds(600, 240, Util.MaxBorder + 25 + 16, Util.MaxBorder + 25 + 39);


        Container container = getContentPane();

        JPanel panel1 = new GamePanel();
        panel1.setBounds(Util.MinBorder, Util.MinBorder, Util.MaxBorder + 25, Util.MaxBorder + 25);

        container.add(panel1);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(getOwner());
        setVisible(true);
    }

    public static void main(String[] args) {

        new Main("Ã∞≥‘…ﬂ");

    }
}

