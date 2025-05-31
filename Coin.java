import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Coin extends Actor
{
    public void act()
    {
        if (isTouching(Avatar.class)) {
            	((MyWorld)getWorld()).removeObject(this);
        }
    }
}
