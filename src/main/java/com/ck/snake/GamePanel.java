package com.ck.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private Snack snack;  // 蛇
    private Timer timer;  // 定时器
    private Node food;  // 食物

    public GamePanel() {
        timer = new Timer(Util.delay, this);
        timer.start();
        init();
        setFocusable(true);  // 获得焦点事件
        // 为容器添加键盘事件
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyPressed1(e);
            }
        });
    }

    // 初始化游戏参数
    public void init() {
        Util.init();
        snack = new Snack();
        food = produceFood(snack);
        timer.setDelay(Util.delay);
    }

    // 为组件画图
    @Override
    protected void paintComponent(Graphics g) {
        paintPanel(g);
    }

    // 每刷新一次，就执行一次
    @Override
    public void actionPerformed(ActionEvent e) {
        // 移动前的蛇尾，蛇吃完食物后将作为蛇身
        Node snackTail = snack.snackBody.get(snack.snackBody.size() - 1);

        move(snack);  // 蛇移动

        if(isEatFood(snack)) {
            snack.snackBody.add(snackTail);
            food = produceFood(snack);
        }

        if(isDead(snack)) {
            init();  // 蛇已死，初始化参数
        }

        // 再次刷新
        repaint();
    }

    // 画图
    public void paintPanel(Graphics g) {
        g.fillRect(Util.MinBorder, Util.MinBorder, Util.MaxBorder + Util.step, Util.MaxBorder + Util.step);
        g.setColor(new Color(0, 0, 0));
        for (int x = Util.MinBorder; x <= Util.MaxBorder; x += Util.step) {
            for (int y = Util.MinBorder; y <= Util.MaxBorder; y += Util.step) {
                g.drawRect(x, y, Util.step, Util.step);
            }
        }
        // 蛇头
        Util.header.paintIcon(this, g, snack.snackBody.get(0).x, snack.snackBody.get(0).y);

        //蛇尾
        for (int i = 1; i < snack.snackBody.size(); i++) {
            Util.body.paintIcon(this, g, snack.snackBody.get(i).x, snack.snackBody.get(i).y);
        }

        // 食物
        Util.food.paintIcon(this, g, food.x, food.y);
    }

    // 键盘监听事件
    public void keyPressed1(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP && !snack.dir.equals("D")) {
            snack.dir = "U";
        } else if (keyCode == KeyEvent.VK_DOWN && !snack.dir.equals("U")) {
            snack.dir = "D";
        } else if (keyCode == KeyEvent.VK_LEFT && !snack.dir.equals("R")) {
            snack.dir = "L";
        } else if (keyCode == KeyEvent.VK_RIGHT && !snack.dir.equals("L")) {
            snack.dir = "R";
        } else if (keyCode == KeyEvent.VK_W) {
            if(Util.delay < 1000) {
                Util.delay += 50;
                System.out.println(Util.delay);
            }
            timer.setDelay(Util.delay);
        } else if (keyCode == KeyEvent.VK_S) {
            if(Util.delay > 50) {
                Util.delay -= 50;
            }
            timer.setDelay(Util.delay);
        } else if (keyCode == KeyEvent.VK_SPACE) {
            Util.isStart = !Util.isStart;
            if(Util.isStart) {
                timer.start();
            } else {
                timer.stop();
            }
        }

    }

    // 蛇移动
    public void move(Snack snack) {
        // 先蛇身移动，再蛇头移动
        // 蛇身移动
        for (int i = snack.snackBody.size() - 1; i > 0; i--) {
            int x = snack.snackBody.get(i - 1).x;
            int y = snack.snackBody.get(i - 1).y;
            snack.snackBody.set(i, new Node(x, y));
        }

        // 蛇头移动
        // 蛇头要进行特判，因为会碰到边界
        String dir = snack.dir;
        if ("R".equals(dir)) {
            snack.snackBody.get(0).x += Util.step;
            if (snack.snackBody.get(0).x > Util.MaxBorder) {
                snack.snackBody.get(0).x = Util.MinBorder;
            }
        } else if ("L".equals(dir)) {
            snack.snackBody.get(0).x -= Util.step;
            if (snack.snackBody.get(0).x < Util.MinBorder) {
                snack.snackBody.get(0).x = Util.MaxBorder;
            }
        } else if ("U".equals(dir)) {
            snack.snackBody.get(0).y -= Util.step;
            if (snack.snackBody.get(0).y < Util.MinBorder) {
                snack.snackBody.get(0).y = Util.MaxBorder;
            }
        } else if ("D".equals(dir)) {
            snack.snackBody.get(0).y += Util.step;
            if (snack.snackBody.get(0).y > Util.MaxBorder) {
                snack.snackBody.get(0).y = Util.MinBorder;
            }
        }
    }

    // 吃食物
    public boolean isEatFood(Snack snack) {
        Node head = snack.snackBody.get(0);
        // 吃到食物
        if(head.equals(food)) {
            return true;
        }
        return false;
    }

    // 判断是否死亡
    public boolean isDead(Snack snack) {
        Node head = snack.snackBody.get(0);
        for (int i = 1; i < snack.snackBody.size(); i++) {
            if (head.equals(snack.snackBody.get(i))) {
                System.out.println("蛇已死");
                JOptionPane.showMessageDialog(this,"snack is dead");
                return true;
            }
        }
        return false;
    }

    // 生产食物
    public Node produceFood(Snack snack) {
        while(true) {  // 直到生成一个合法的食物
            int x = Util.step * new Random().nextInt(Util.blockSize);
            int y = Util.step * new Random().nextInt(Util.blockSize);
            Node food = new Node(x, y);
            // 判断食物是否生成在蛇身的位置
            if (!snack.snackBody.contains(food)) {
                System.out.println(food);
                return food;
            }
        }
    }

}
