package Entity;

import Main.KeyHandler;
import Main.gamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Player class represents the playable character in the game.
 * Handles player movement, animation, and rendering.
 * Uses WalkAnimation class to manage walking animations.
 */
public class Player extends entity {
    // Reference to the game panel for accessing game settings
    gamePanel gp;
    
    // Reference to the key handler for reading keyboard input
    KeyHandler keyH;
    
    // Walking animation handler
    private WalkAnimation walkAnimation;
    
    // Walking animation sprites for each direction
    BufferedImage up1, up2;                    // Up direction walking frames
    BufferedImage down1, down2;                // Down direction walking frames
    BufferedImage left1, left2, left3, left4;   // Left direction walking frames
    BufferedImage right1, right2, right3, right4; // Right direction walking frames

    /**
     * Constructor for the Player class.
     * Initializes the player with default values and loads player images.
     * 
     * @param gp The game panel instance
     * @param keyH The key handler instance for input processing
     */
    public Player(gamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Sets the default starting values for the player.
     * Initializes position, movement speed, and starting direction.
     */
    public void setDefaultValues() {
        x = 100;              // Starting X position on screen
        y = 100;              // Starting Y position on screen
        speed = 4;            // Pixels moved per frame
        direction = "down";   // Initial facing direction
    }

    /**
     * Loads all player sprite images from the resources folder.
     * Creates animation frame arrays with proper sequences:
     * - Up/Down: Simple looping animations (up1, up2, up1, up2...)
     * - Left/Right: Complex sequences with idle frames (idle -> left1 -> left2 -> left1 -> idle -> left3 -> left4 -> idle -> left1...)
     * Initializes the WalkAnimation instance with these sequences.
     */
    public void getPlayerImage() {
        try {
            // Load idle sprites (standing still) for each direction
            idleup = ImageIO.read(getClass().getResourceAsStream("/player/up.png"));
            idledown = ImageIO.read(getClass().getResourceAsStream("/player/down.png"));
            idleleft = ImageIO.read(getClass().getResourceAsStream("/player/left.png"));
            idleright = ImageIO.read(getClass().getResourceAsStream("/player/right.png"));

            // Load walking animation frames for up direction
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));

            // Load walking animation frames for down direction
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));

            // Load walking animation frames for left direction
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/left3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/player/left4.png"));

            // Load walking animation frames for right direction
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/player/right4.png"));

            // Create animation frame arrays for each direction
            
            // Up/Down: Simple looping animations (no idle frames in sequence)
            // These will loop continuously: up1 -> up2 -> up1 -> up2...
            upFrames = new BufferedImage[]{up1, up2};
            downFrames = new BufferedImage[]{down1, down2};

            // Left: Complex sequence with idle frames
            // Sequence: idle -> left1 -> left2 -> left1 -> idle -> left3 -> left4 -> idle -> left1 -> repeat
            leftFrames = new BufferedImage[]{
                    idleleft, left1, left2, left1, idleleft, left3, left4, idleleft, left1
            };

            // Right: Complex sequence with idle frames (mirror of left)
            // Sequence: idle -> right1 -> right2 -> right1 -> idle -> right3 -> right4 -> idle -> right1 -> repeat
            rightFrames = new BufferedImage[]{
                    idleright, right1, right2, right1, idleright, right3, right4, idleright, right1
            };

            // Initialize WalkAnimation with the frame arrays and idle sprites
            // Only initialize if all required images loaded successfully
            if (idleup != null && idledown != null && idleleft != null && idleright != null &&
                up1 != null && up2 != null && down1 != null && down2 != null &&
                left1 != null && left2 != null && left3 != null && left4 != null &&
                right1 != null && right2 != null && right3 != null && right4 != null) {
                
                walkAnimation = new WalkAnimation(
                        upFrames, downFrames, leftFrames, rightFrames,
                        idleup, idledown, idleleft, idleright
                );
            } else {
                System.err.println("Warning: Some player images failed to load. WalkAnimation not initialized.");
            }

        } catch (Exception e) {
            System.err.println("Error loading player images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates the player's state each frame.
     * Handles movement based on keyboard input and updates animation.
     * Called every frame by the game loop.
     */
    public void update() {
        // Reset moving state - will be set to true if any movement key is pressed
        moving = false;

        // Check for movement input and update player position accordingly
        // Only one direction can be active at a time (using else-if chain)
        if (keyH.upPressed) {
            direction = "up";
            y -= speed;        // Move up by decreasing Y coordinate
            moving = true;
        } else if (keyH.downPressed) {
            direction = "down";
            y += speed;        // Move down by increasing Y coordinate
            moving = true;
        } else if (keyH.leftPressed) {
            direction = "left";
            x -= speed;        // Move left by decreasing X coordinate
            moving = true;
        } else if (keyH.rightPressed) {
            direction = "right";
            x += speed;        // Move right by increasing X coordinate
            moving = true;
        }

        // Update animation using WalkAnimation class
        // This handles frame timing and sequence progression
        // Always update animation state, even when not moving (so it knows to show idle)
        if (walkAnimation != null) {
            walkAnimation.update(moving, direction);
        }
    }

    /**
     * Draws the player sprite on the screen.
     * Gets the current animation frame from WalkAnimation and draws it.
     * 
     * @param g2 The Graphics2D object used for drawing
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // First, try to get the current frame from WalkAnimation if it's initialized
        if (walkAnimation != null) {
            image = walkAnimation.getCurrentFrame();
        }
        
        // Fallback to entity's idle sprites directly if WalkAnimation failed or isn't initialized
        if (image == null) {
            switch (direction) {
                case "up":
                    image = idleup;
                    break;
                case "down":
                    image = idledown;
                    break;
                case "left":
                    image = idleleft;
                    break;
                case "right":
                    image = idleright;
                    break;
                default:
                    image = idledown;
                    break;
            }
        }

        // Draw the sprite at the player's position
        // If image is still null, draw a white rectangle for debugging
        if (image != null) {
            g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        } else {
            // Debug fallback: Draw a white rectangle to verify positioning
            // If you see a white rectangle, the images aren't loading properly
            g2.setColor(java.awt.Color.WHITE);
            g2.fillRect(x, y, gp.tileSize, gp.tileSize);
            g2.setColor(java.awt.Color.RED);
            g2.drawRect(x, y, gp.tileSize, gp.tileSize);
        }
    }
}
