import greenfoot.*;

public class Menu extends Actor
{
    //Klasse die nur in der Menu-Welt zu finden ist: Falls die Leertaste gedrückt wird, wird die Spiel-Welt geladen
    
    public void act()
    {
        if(Greenfoot.isKeyDown("space"))
        {
            Greenfoot.setWorld(new MyWorld());
        }
    }
}
