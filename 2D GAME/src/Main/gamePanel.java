package Main;

import Entity.Player;

import java.awt.*;
import javax.swing.JPanel;
import java.awt.Graphics2D;

public class gamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int originalTilesize = 16; //16x16 tile
    final int scale = 3; // 16x3 (scale) = 48

    public final int tileSize = originalTilesize * scale; //48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    //FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);


    //Set players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public gamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
//    sleep game loop
//    public void run() {
//
//        double drawInterval =1000000000/FPS; // 0.01666 seconds
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//        while (gameThread != null) {
//
//            long currentTime = System.nanoTime();
//            System.out.println("Current Time"+currentTime);
//
//            // as long as the loop exists this condition continues
//            update();
//            //1 UPDATE: information such as character positions
//
//            //2 DRAW:draw the screen with the updated information
//            repaint();
//
//            try {
//            double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime/1000000;
//
//                if(remainingTime < 0){
//                    remainingTime = 0;}
//
//                Thread.sleep((long) remainingTime);
//                nextDrawTime += drawInterval;
//            /* returns how much time is remaining
//             till the next draw time */
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//
//
//
//        }
//    }
//    }

    //delta method
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer>=1000000000) {
                System.out.println(drawCount);
                drawCount = 0;
                timer = 0;
            }

        }

        update();
        repaint();
    }
        public void update() {

        player.update();

        }
        public void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D)g;

            player.draw(g2);

            g2.dispose();
        }

    }

