import greenfoot.*;
import java.lang.Math;

public class Player extends Actor
{
    public int id;
    public boolean myTurn;
    public boolean gameOver;
    
    private Vector2 position;
    private int rotation;
    
    private int moveSpeed = 4;
    
    private Barrel barrel;
    
    private int angle = 90;
    private int force = 100;
    private int fuel;
    private int maxFuel = 150;
    private int health = 1;
        
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
         * Die Informationen zum Spieler werden auf dem Bildschirm angezeigt: (GreenfootImage) [Nur falls das Spiel noch im Gange ist. Andernfalls sollen die Informationen nicht aktualisiert werden]
         * - die Infos über Spieler 0 links
         * - die Infos über Spieler 1 rechts
         */
        if(id == 0 && !gameOver)
        {
            /*getWorld().showText ("Leben: " + health, 80, 50);
            getWorld().showText ("Power: " + force, 80, 75);
            getWorld().showText ("Tank: " + fuel, 80, 100);*/
            
            GreenfootImage infos = new GreenfootImage(  "Leben: " + health + "\n  Power: " + force + "  \nTank: " + fuel, 30, Color.WHITE, new Color(40, 40, 40, 255));
            getWorld().getBackground().drawImage(infos, 60, 50);
        }
        else if(id == 1 && !gameOver)
        {
            /*getWorld().showText ("Leben: " + health, 1200, 50);
            getWorld().showText ("Power: " + force, 1200, 75);
            getWorld().showText ("Tank: " + fuel, 1200, 100);*/
            
            GreenfootImage infos = new GreenfootImage("  Leben: " + health + "\n  Power: " + force + "  \nTank: " + fuel, 30, Color.WHITE, new Color(40, 40, 40, 255));
            getWorld().getBackground().drawImage(infos, 1080, 50);
        }
        
         //Falls der Panzer ein Projektil berührt, wird dieser getroffen und das Projektil explodiert
        for(Bullet b : getIntersectingObjects(Bullet.class))
        {
            //Folgendes geschieht nur wenn die Kugel nicht selbst geschossen wurde
            if(b.id != id)
            {
                Hit(1, b);
            }
        }
    }
    
    private void Hit(int amount, Bullet b)
    {
        //dem Spieler werden eine bestimmte Anzahl an Leben abgezogen
        health -= amount;
        
        if(health <= 0)
        {
            //falls der Spieler 0 oder weniger Leben besitzt, stirbt dieser
            //Das Projektil wird zerstört, sodass der Spieler die Animation ausführt und nicht das Projektil selbst
            for(Player p : getWorld().getObjects(Player.class))
                p.gameOver = true;
            getWorld().removeObject(b);
            Die();
        }else //sollte der Spieler noch nicht verloren haben, ist der andere Spieler am Zug
        {
            b.Explode();
            //getWorld().getObjects(GameManager.class).get(0).NextTurn();
        }
    }
    
    int explosions = 0;
    private void Die()
    {   
        //eine Sounddatei wird abgespielt
        Greenfoot.playSound("explosion.mp3");
        
        /*ein Text wird angezeigt: "der jeweils andere Spieler hat gewonnen!"
         *dafür wird ein trabsparentes greenfootimage erstellt, welches einen text enthält
         *dies wird mittig-oben angezeigt
         */
        int otherId = (1 - id) + 1;    
        GreenfootImage gg = new GreenfootImage("                 Spieler " + otherId  + " hat gewonnen!               ", 100, Color.WHITE, new Color(0, 0, 0, 255));
        getWorld().getBackground().drawImage(gg, (getWorld().getWidth()-gg.getWidth())/2, 45);
        
        //der GameManagaer wird informiert, dass das Spiel vorbei ist
        getWorld().getObjects(GameManager.class).get(0).GameOver();
        
        //das rohr verschwindet, sodass der spieler die animations bilder annehmen kann
        getWorld().removeObject(barrel);
        
        //explosions animation, welche 
        //if(explosions == 0)
            DeathAnimation();
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
    
    //Die Animation soll nur einmal ausgeführt werden
    void DeathAnimation()
    {
        explosions++;
        //9 bilder der explosions-animation werden nacheinander abgespielt
        for(int i = 0; i < 10; i++)
        {
            //die bild dateien heißen: "explosion_00, explosion_01" usw.
            this.setImage("explosion-big_0" + i + ".png");
            Greenfoot.delay(3);
        }
        //der Spieler verschwindet
        getWorld().removeObject(this);
    }
}
