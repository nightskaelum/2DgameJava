package Entity;

import java.awt.image.BufferedImage;

/**
 * Base entity class that provides common properties and functionality
 * for all game entities (player, NPCs, enemies, etc.).
 * Contains position, movement, and animation-related fields.
 */
public class entity {
    // Position coordinates on the screen
    public int x, y;

    // Movement speed in pixels per frame
    public int speed;

    // Idle sprites for each direction (when entity is not moving)
    public BufferedImage idleup, idledown, idleleft, idleright;

    // Arrays for animation frames per direction
    // These arrays contain the sequence of sprites to display during movement
    public BufferedImage[] upFrames;
    public BufferedImage[] downFrames;
    public BufferedImage[] leftFrames;
    public BufferedImage[] rightFrames;

    // Current facing direction of the entity
    // Possible values: "up", "down", "left", "right"
    public String direction;

    // Animation timing counter
    // Increments each frame to control when to change animation frames
    public int spriteCounter = 0;

    // Current animation frame number
    // Used to select which sprite from the animation array to display
    public int spriteNum = 1;

    // Whether the entity is currently moving
    // Used to determine if walking or idle animation should be displayed
    public boolean moving = false;
}
