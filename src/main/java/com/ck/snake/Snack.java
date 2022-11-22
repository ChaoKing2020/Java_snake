package com.ck.snake;


import java.util.LinkedList;
import java.util.List;

public class Snack {

    public String dir;// 蛇头方向
    public List<Node> snackBody; // 蛇身

    // 初始化蛇身
    public Snack() {
        this.dir = "R";
        this.snackBody = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            this.snackBody.add(new Node(100 - i * 25, 100));
        }
    }

}
