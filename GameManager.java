import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class GameManager extends Actor
{
    public int turn;
    
    public void act()
    {
        getWorld().showText("Turn: " + turn, 50, 50);
    }
    
    public void NextTurn()
    {
        turn = 1 - turn;
        for(player p : getWorld().getObjects(player.class))
        {
            if(p.id == turn)
                p.myTurn = true;
        }
    }
}
