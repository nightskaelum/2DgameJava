package Entity;

import java.awt.image.BufferedImage;

public class entity {
    public int x, y;
    public int speed;

    public BufferedImage idleup, idledown, idleleft, idleright;

    // Arrays for animation frames per direction
    public BufferedImage[] upFrames;
    public BufferedImage[] downFrames;
    public BufferedImage[] leftFrames;
    public BufferedImage[] rightFrames;

    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean moving = false;
}
