import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

public class bullet extends Actor
{
    float dt;
    
    public void act()
    {
        move(5);
        dt += 0.1f;
        setLocation(getX(), getY() + (int)dt);
        
        if(Greenfoot.isKeyDown("e"))
        {
            Explode();
        }
    }
    
    void Explode()
    {        
        for(GameManager gm : getWorld().getObjects(GameManager.class))
        {
            gm.NextTurn();
        }

        getWorld().removeObject(this);
    }
}
