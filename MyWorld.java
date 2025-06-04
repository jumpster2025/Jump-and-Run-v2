import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

public class MyWorld extends World
{
    private Avatar avatar;
    private Ground ground;

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(400, 1000, 1);

        // Create a new avatar
        avatar = new Avatar();
        addObject(avatar, 200, 800);

        // Create a new ground
        ground = new Ground();

        // Place the ground throughout the width of the map.
        for (int i = 0; i <= getWidth() /  ground.getImage().getWidth(); i++) {
            ground = new Ground();
            addObject(ground, i * ground.getImage().getWidth() + ground.getImage().getWidth() / 2, getHeight() - ground.getImage().getHeight() / 2);
        }

        placeObstacles();
    }
    
    public void act() {
        showText("Score: " + avatar.getScore(), 50, 100);
    }

    public void placeObstacles() {
        for (int i = 0; i < 10; i++) {
            Ground ground = new Ground();
            Random random = new Random();
            
            int x = random.nextInt(getWidth());
            int y = random.nextInt(getHeight() - 175);

            System.out.println("X: " + x + " Y: " + y);

            addObject(ground, x, y);
        }
    }
    
    public void addScore(Coin coin) {
        removeObject(coin);
        avatar.addScore(coin.getValue());
    }
}
