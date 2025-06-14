import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Coin extends Actor
{
    int value = 5;
    
    public Coin(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    public void act()
    {
        if (isTouching(Avatar.class)) {
                ((MyWorld)getWorld()).addScore(this);
        }
    }
}
