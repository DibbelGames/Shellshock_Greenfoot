import greenfoot.*;
import java.lang.Math;

public class Player extends Actor
{
    public int id;
    public boolean myTurn;
    
    private Vector2 position;
    private int rotation;
    
    private int moveSpeed = 4;
    
    private Barrel barrel;
    
    private int angle = 90;
    private int force = 100;
    private int fuel;
    private int maxFuel = 150;
    private int health = 100;
    
    public Player(int id, Barrel b, Vector2 position)
    {
        //fals dies Spieler 1 (0) sein sollte, beginnt dieser
        this.id = id;
        this.barrel = b;
        this.position = position;
        
        if(id == 0)
            myTurn = true;
        
        fuel = maxFuel;
    }
    
    public void act()
    {
        if(myTurn)
        {
            //horizontale bewegung (Änderung der aktuellen X-Koordinate) & abnahme des Tankinhaltes
            if(Greenfoot.isKeyDown("a") && fuel > 0)
            {
                position.X -= 1;
                fuel--;
            }
            else if(Greenfoot.isKeyDown("d") && fuel > 0)
            {
                position.X += 1;   
                fuel--;
            }
        
            //Rotation des Rohres
            if(Greenfoot.isKeyDown("left"))
                angle--;
            else if(Greenfoot.isKeyDown("right"))
                angle++;
                
            //Veränderung des Schusskraft
            if(Greenfoot.isKeyDown("up") && force < 150)
                force++;
            else if(Greenfoot.isKeyDown("down") && force > 1)
                force--; 
                
            //Schuss !
            if(Greenfoot.isKeyDown("space"))
            {
                Shoot();
            
                //der Zug endet mit dem Schuss. Der Tank füllt sich
                myTurn = false;
                fuel = maxFuel;
            }
        }
        
        //abfrage der Y-Koordinate und der steigung
        position.Y = getWorld().getObjects(GameManager.class).get(0).getYPos(position.X);
        rotation = getWorld().getObjects(GameManager.class).get(0).getRot(position.X);
        
        setLocation(position.X, position.Y - 40);
        setRotation(rotation);
        
        //das Rohr wird mitbewegt und gedreht
        barrel.setLocation(getX(), getY());
        barrel.setRotation(angle);
        
        /*
         * Die Informationen zum Spieler werden auf dem Bildschirm angezeigt:
         * - die Infos über Spieler 0 links
         * - die Infos über Spieler 1 rechts
         */
        if(id == 0)
        {
            getWorld().showText ("Leben: " + health, 80, 50);
            getWorld().showText ("Power: " + force, 80, 75);
            getWorld().showText ("Tank: " + fuel, 80, 100);
        }
        else if(id == 1)
        {
            getWorld().showText ("Leben: " + health, 1200, 50);
            getWorld().showText ("Power: " + force, 1200, 75);
            getWorld().showText ("Tank: " + fuel, 1200, 100);
        }
            
        //Falls der Panzer ein Projektil berührt, wird dieser getroffen und das Projektil explodiert
        for(Bullet b : getIntersectingObjects(Bullet.class))
        {
            //Folgendes geschieht nur wenn die Kugel nicht selbst geschossen wurde
            if(b.id != id)
            {
                Hit(10);
                b.Explode();   
            }
        }
    }
    
    private void Hit(int amount)
    {
        //dem Spieler werden eine bestimmte Anzahl an Leben abgezogen
        health -= amount;
        
        if(health <= 0)
        {
            //falls der Spieler 0 oder weniger Leben besitzt, stirbt dieser
            Greenfoot.playSound("explosion.mp3");
            
            int otherId = 1 - id;
            getWorld().showText("Spieler " + otherId + " hat gewonnen!", 400, 300);
            
            getWorld().removeObject(barrel);
            getWorld().removeObject(this);
        }
    }
    
    private void Shoot()
    {
        // ein neues Projektil wird erstellt, welches von Beginn an die rotation des Schusswinkels besitzt
        Bullet b = new Bullet(id, force);
        b.setRotation(angle -90); // -90 da das Rohr zu beginn nach rechts guckt, also mit einem Winkel von 90° beginnt
        
        /*
         * die Kugel soll aus dem Rohr fliegen:
         * der horizontale, sowie der vertikale abstand von Rohrende und Panzermitte wird berechnet
         * die Rohrlänge c und der winkel alpha sind bekannt
        */
        double xOff = 50 * Math.cos(Math.toRadians(angle-90)); // cos(alpha)= b/c
        double yOff = 50 * Math.sin(Math.toRadians(angle-90)); // sin(alpha)= a/c
        
        //das Projektil wird der Welt hinzugefügt
        getWorld().addObject(b, getX() + (int)xOff, getY() + (int)yOff);
    }
}
