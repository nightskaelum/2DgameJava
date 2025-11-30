package Entity;


import Main.KeyHandler;
import Main.gamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends entity {
    gamePanel gp;
    KeyHandler keyH;

    public Player(gamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            idleup = ImageIO.read(getClass().getResourceAsStream("/player/up.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));

            idledown = ImageIO.read(getClass().getResourceAsStream("/player/down.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));

            idleleft = ImageIO.read(getClass().getResourceAsStream("/player/left.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/left3.png"));

            idleright = ImageIO.read(getClass().getResourceAsStream("/player/right.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/right3.png"));

            upFrames = new BufferedImage[]{ up1, up2 };
            downFrames = new BufferedImage[]{ down1, down2 };

            leftFrames = new BufferedImage[]{ //creating the animation sequence
                    left1, left2, left3, left2, left1, left3, left2, left3, left1
            };
            rightFrames = new BufferedImage[]{
                    right1, right2, right3, right2, right1, right3, right2, right3, right1
            };


        }catch(Exception e) {
            e.printStackTrace();
        }

    }
    public void update() {

        moving = false;
        if (keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true){ //this is what makes sure the character isn't always moving
        }
        if(keyH.upPressed == true) {
            direction = "up";
            y -= speed;
            moving = true;
        }
        else if(keyH.downPressed == true) {
            direction = "down";
            y += speed;
            moving = true;
        }
        else if(keyH.leftPressed == true) {
            direction = "left";
            x -= speed;
            moving = true;
        }
        else if(keyH.rightPressed == true) {
            direction = "right";
            x += speed;
            moving = true;
        }
//making the walk animation only work when moving
        if(moving == true) {spriteCounter++;
            if(spriteCounter > 20) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 3;

                }
                else if (spriteNum == 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            else {
                spriteNum = 1; // resets to idle frame

        }

        }
        }




    public void draw(Graphics2D g2) {

       // g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize,gp.tileSize);
        BufferedImage image = null;

        if(moving == true) {
            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }


        }
        else{
            switch (direction) {
                case "up": image = idleup; break;
                case "down": image = idledown; break;
                case "left": image = idleleft; break;
                case "right": image = idleright; break;
            }

        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);


    }
}
