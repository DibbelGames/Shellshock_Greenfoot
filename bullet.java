import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

public class bullet extends Actor
{
    
    public void act()
    {
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
