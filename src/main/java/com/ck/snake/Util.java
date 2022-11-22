package com.ck.snake;

import javax.swing.*;
import java.util.Objects;

public class Util {

    public static ImageIcon header = new ImageIcon(Objects.requireNonNull(Util.class.getResource("/static/header.png")));
    public static ImageIcon body = new ImageIcon(Objects.requireNonNull(Util.class.getResource("/static/body.png")));
    public static ImageIcon food = new ImageIcon(Objects.requireNonNull(Util.class.getResource("/static/food.png")));

    public static int MinBorder = 0;
    public static int MaxBorder = 500;
    public static int step = 25; // 步长
    public static int blockSize = (MaxBorder - MinBorder + 25)/step;

    public static int delay = 100;  // 时间间隔

    public static boolean isStart = true;

    public static void init() {
        delay = 100;
        isStart = true;
    }


}
