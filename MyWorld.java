import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class MyWorld extends World
{
    private Avatar avatar;
    
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
        Ground ground = new Ground();
        
        
        // Place the ground throughout the width of the map.
        for (int i = 0; i <= getWidth() /  ground.getImage().getWidth(); i++) {
            ground = new Ground();
            addObject(ground, i * ground.getImage().getWidth() + ground.getImage().getWidth() / 2, getHeight() - ground.getImage().getHeight() / 2);
        }
    }
}
