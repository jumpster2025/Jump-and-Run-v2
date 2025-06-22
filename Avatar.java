import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Avatar extends Actor
{
    private final int speed = 4;
    private final int jumpPower = 20;
    private final int gravity = 1;
    private final int groundCheckOffset = getImage().getHeight() / 2 + 1;
    private int xVelocity = 0;
    private int yVelocity = 0;
    private int score = 0;
    
    private GreenfootImage originalImage;
    private GreenfootImage flippedImage;
    
    public Avatar() {
        originalImage = getImage();
        flippedImage = new GreenfootImage(originalImage);
        flippedImage.mirrorHorizontally();
    }
    
    public void act() 
    {
        input();
        collisionAbove();
        moveHorizontally();
        moveVertically();
        gravity();
    }
    
    public void gravity() {
        if (getObjectsAtOffset(0, groundCheckOffset, Ground.class).isEmpty()) {
            yVelocity += gravity;
        } else {
            yVelocity = 0;
            // Snap to the ground
            while (!getObjectsAtOffset(0, groundCheckOffset - 1, Ground.class).isEmpty()) {
                setLocation(getX(), getY() - 1); // Nudge upward out of the ground
            }
        }
    }
    
    public void collisionAbove() {
        if (!getObjectsAtOffset(0, -groundCheckOffset, Ground.class).isEmpty()) {
            yVelocity = 0;
            yVelocity += gravity;
        }
    }
    
    public void moveHorizontally() {
        setLocation(getX() + xVelocity * speed, getY());
        if (!getObjectsAtOffset((int)Math.signum(xVelocity) * groundCheckOffset, 0, Ground.class).isEmpty()) {
            // Undo the move
            setLocation(getX() - xVelocity * speed, getY());
            xVelocity = 0;
        }
    }
    
    public boolean getIsTouchingGround() {
        return !getObjectsAtOffset(0, groundCheckOffset, Ground.class).isEmpty();
    }
    
    public void addScore(int value) {
        score += value;
    }
    
    public int getScore() {
        return score;
    }
    
    public void moveVertically() {
        setLocation(getX(), getY() + yVelocity);
    }

    // Get the input of the player
    public void input() {
        // Move the player according to the directions
        if (Greenfoot.isKeyDown("a")) {
            xVelocity = -1;
            setImage(flippedImage);
        } else if (Greenfoot.isKeyDown("d")) {
            xVelocity = 1;
            setImage(originalImage);
        }
        else {
            xVelocity = 0;
        }

        // Let the player jump.
        if (!getObjectsAtOffset(0, groundCheckOffset, Ground.class).isEmpty()) {
            if (Greenfoot.isKeyDown("space")) {
                // setImage("Jumpster_Jump.png"); // Change image for jumping avatar
                yVelocity -= jumpPower;
            }
        }
    }
}
