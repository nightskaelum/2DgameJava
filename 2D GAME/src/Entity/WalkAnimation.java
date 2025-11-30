package Entity;

import java.awt.image.BufferedImage;

/**
 * WalkAnimation class handles walking animation logic for entities.
 * Manages animation frame sequences, timing, and frame selection.
 * Supports simple looping animations (up/down) and complex sequences with idle frames (left/right).
 */
public class WalkAnimation {
    // Animation frame arrays for each direction
    private BufferedImage[] upFrames;
    private BufferedImage[] downFrames;
    private BufferedImage[] leftFrames;
    private BufferedImage[] rightFrames;
    
    // Idle sprites for each direction
    private BufferedImage idleUp;
    private BufferedImage idleDown;
    private BufferedImage idleLeft;
    private BufferedImage idleRight;
    
    // Animation timing
    private int spriteCounter = 0;  // Counter for frame timing
    private int spriteNum = 0;       // Current frame index in the animation array
    
    // Per-direction frame delays (frames to wait before advancing animation)
    // At 60 FPS: frameDelay = 60 / desiredAnimationFPS
    private int upFrameDelay = 16;      // Up direction frame delay (keeps current speed)
    private int downFrameDelay = 16;    // Down direction frame delay (keeps current speed)
    private int leftFrameDelay = 5;      // Left direction frame delay (12 FPS: 60/12 = 5)
    private int rightFrameDelay = 5;    // Right direction frame delay (12 FPS: 60/12 = 5)
    
    // Current state
    private boolean moving = false;
    private String direction = "down";
    
    /**
     * Constructor for WalkAnimation.
     * Initializes the animation with frame arrays and idle sprites.
     * 
     * @param upFrames Array of up direction walking frames
     * @param downFrames Array of down direction walking frames
     * @param leftFrames Array of left direction walking frames (includes idle frames)
     * @param rightFrames Array of right direction walking frames (includes idle frames)
     * @param idleUp Idle sprite for up direction
     * @param idleDown Idle sprite for down direction
     * @param idleLeft Idle sprite for left direction
     * @param idleRight Idle sprite for right direction
     */
    public WalkAnimation(BufferedImage[] upFrames, BufferedImage[] downFrames,
                        BufferedImage[] leftFrames, BufferedImage[] rightFrames,
                        BufferedImage idleUp, BufferedImage idleDown,
                        BufferedImage idleLeft, BufferedImage idleRight) {
        this.upFrames = upFrames;
        this.downFrames = downFrames;
        this.leftFrames = leftFrames;
        this.rightFrames = rightFrames;
        this.idleUp = idleUp;
        this.idleDown = idleDown;
        this.idleLeft = idleLeft;
        this.idleRight = idleRight;
    }
    
    /**
     * Updates the animation state.
     * Should be called every frame to advance animation timing.
     * 
     * @param isMoving Whether the entity is currently moving
     * @param currentDirection The current facing direction ("up", "down", "left", "right")
     */
    public void update(boolean isMoving, String currentDirection) {
        this.moving = isMoving;
        this.direction = currentDirection;
        
        if (moving) {
            spriteCounter++;
            
            // Get the frame delay for the current direction
            int currentFrameDelay = getFrameDelayForDirection();
            
            // Advance animation frame after the direction-specific frame delay
            if (spriteCounter >= currentFrameDelay) {
                spriteCounter = 0;
                
                // Get the appropriate frames array for current direction
                BufferedImage[] currentFrames = getCurrentFramesArray();
                
                if (currentFrames != null) {
                    // Advance to next frame and wrap around using modulo
                    spriteNum = (spriteNum + 1) % currentFrames.length;
                }
            }
        } else {
            // Reset to idle when not moving
            spriteNum = 0;
            spriteCounter = 0;
        }
    }
    
    /**
     * Gets the current frame array based on the current direction.
     * 
     * @return The BufferedImage array for the current direction, or null if invalid direction
     */
    private BufferedImage[] getCurrentFramesArray() {
        switch (direction) {
            case "up":
                return upFrames;
            case "down":
                return downFrames;
            case "left":
                return leftFrames;
            case "right":
                return rightFrames;
            default:
                return null;
        }
    }
    
    /**
     * Gets the frame delay for the current direction.
     * Different directions can have different animation speeds.
     * 
     * @return The frame delay for the current direction
     */
    private int getFrameDelayForDirection() {
        switch (direction) {
            case "up":
                return upFrameDelay;
            case "down":
                return downFrameDelay;
            case "left":
                return leftFrameDelay;
            case "right":
                return rightFrameDelay;
            default:
                return 12; // Default frame delay
        }
    }
    
    /**
     * Gets the current sprite to display.
     * Returns the appropriate frame from the animation sequence or idle sprite.
     * 
     * @return The BufferedImage to display for the current animation state, or null if not available
     */
    public BufferedImage getCurrentFrame() {
        if (!moving) {
            // Return idle sprite when not moving
            switch (direction) {
                case "up":
                    return idleUp;
                case "down":
                    return idleDown;
                case "left":
                    return idleLeft;
                case "right":
                    return idleRight;
                default:
                    return idleDown;
            }
        }
        
        // Return current frame from animation sequence when moving
        BufferedImage[] currentFrames = getCurrentFramesArray();
        if (currentFrames != null && currentFrames.length > 0 && spriteNum >= 0) {
            // Use modulo to ensure index is within bounds
            int index = spriteNum % currentFrames.length;
            if (index >= 0 && index < currentFrames.length) {
                BufferedImage frame = currentFrames[index];
                // Return frame if it's not null, otherwise fall back to idle
                if (frame != null) {
                    return frame;
                }
            }
        }
        
        // Fallback to idle sprite if something goes wrong
        switch (direction) {
            case "up":
                return idleUp;
            case "down":
                return idleDown;
            case "left":
                return idleLeft;
            case "right":
                return idleRight;
            default:
                return idleDown;
        }
    }
    
    /**
     * Sets the frame delay (animation speed) for a specific direction.
     * Lower values = faster animation, higher values = slower animation.
     * At 60 FPS: frameDelay = 60 / desiredAnimationFPS
     * 
     * @param direction The direction to set ("up", "down", "left", "right")
     * @param delay Number of frames to wait before advancing animation
     */
    public void setFrameDelay(String direction, int delay) {
        switch (direction.toLowerCase()) {
            case "up":
                this.upFrameDelay = delay;
                break;
            case "down":
                this.downFrameDelay = delay;
                break;
            case "left":
                this.leftFrameDelay = delay;
                break;
            case "right":
                this.rightFrameDelay = delay;
                break;
        }
    }
    
    /**
     * Gets the frame delay for a specific direction.
     * 
     * @param direction The direction to get ("up", "down", "left", "right")
     * @return The frame delay value for that direction
     */
    public int getFrameDelay(String direction) {
        switch (direction.toLowerCase()) {
            case "up":
                return upFrameDelay;
            case "down":
                return downFrameDelay;
            case "left":
                return leftFrameDelay;
            case "right":
                return rightFrameDelay;
            default:
                return 12;
        }
    }
    
    /**
     * Resets the animation to the beginning.
     * Useful when changing directions or stopping movement.
     */
    public void reset() {
        spriteNum = 0;
        spriteCounter = 0;
    }
}

