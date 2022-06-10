import greenfoot.*;
import java.util.Map;

public class Bullet extends Actor
{
    //anzahl der pixel um die sich die kugel pro bild nach unten bewegt
    float dt;
    //schusskraft
    int force;
    //wer hat geschossen?
    int id;
    
    public Bullet(int id, int force)
    {
        this.id = id;
        this.force = force;
    }
    
    public void act()
    {
        //horizontale Bewegung
        move(force/10);
        //vertikale Bewegung
        dt += 0.1f;
        setLocation(getX(), getY() + (int)dt);
        
        //das Projektil explodiert, falls es den Rand der Welt oder den Boden berührt
        if(getY() <= 30 || getX() >= 1250 || getX() <= 30 || getY() >= 690 || isTouching(ground.class))
            Explode();
    }
    
    int explosions = 0;
    void Explode()
    {       
        Greenfoot.playSound("damage.wav");
        //der nächste Zug beginnt
        if(explosions == 0)
            Animation();
    }
    
    //Die Animation soll nur einmal ausgeführt werden
    void Animation()
    {
        explosions++;
        //9 bilder der explosions-animation werden nacheinander abgespielt
        for(int i = 0; i < 10; i++)
        {
            //die bild dateien heißen: "explosion_00, explosion_01" usw.
            this.setImage("explosion_0" + i + ".png");
            Greenfoot.delay(3);
        }
        //Der nächste Zug beginnt
        getWorld().getObjects(GameManager.class).get(0).NextTurn();
        //Projektil wird entfernt
        getWorld().removeObject(this);
    }
}
