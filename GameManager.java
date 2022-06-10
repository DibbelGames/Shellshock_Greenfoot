import greenfoot.*;
import java.lang.Math;

public class GameManager extends Actor
{
    // wer ist am Zug?
    public int turn;
    private boolean gameOver;
    
    //Liste an errechneten Koordinaten (Terrain)
    public Vector2[] positions = new Vector2[1280];
    public ground[] grounds = new ground[1280];
    
    //zufällige Parameter
    private int a, b ,c;
    private float d;
    
    public GameManager(int a, int b, int c, float d)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        
        //für jede X-Stelle wird die Y-Koordinate durch eine zufällige Sinus-Kurve erstellt
        for(int x = 0; x < 1280; x++)
        {
            positions[x] = new Vector2(x, (int)((Math.sin(Math.toRadians(d*(x + b))) * a) + c));
        }
    }
    
    public void act()
    {
        //sobald ein Spieler gestorben ist (also die Runde beendet wurde) kann "r" gedrückt werden, sodass eine neue Runde starten kann
        if(gameOver && Greenfoot.isKeyDown("r"))
        {
            Greenfoot.setWorld(new GameWorld());
        }
    }
    
    public void NextTurn()
    {
        turn = 1-turn; // 0->1; 1->0
        for(Player p : getWorld().getObjects(Player.class))
        {
            // jeder Spieler wird benachrichtigt, wer nun am Zug ist
            if(p.id == turn){
                p.myTurn = true;   
            }else{
                p.myTurn = false;
            }
        }
    }
    
    //wenn diese Methode gecallt wird, ist es möglich eine neue Runde zu starten. Ein Hilfetext wird angezeigt
    public void GameOver()
    {
        //um anzuzeigen dass nun eine neue Runde gestarted werden kann, wird ein trabsparentes greenfootimage mit einem hilfetext erstellt
        //dies wird mittig unter dem Siegestext angezeigt
        GreenfootImage rr = new GreenfootImage("Drücke -R- für eine Revanche.", 50, Color.WHITE, new Color(0, 0, 0, 0));
        getWorld().getBackground().drawImage(rr, (getWorld().getWidth()-rr.getWidth())/2, 160);
        
        gameOver = true;
    }
    
    //auf anfrage wird der Funktionswert zu einer gegeben X-Stelle zurückgegeben
    public int getYPos(int x)
    {
        return positions[x].Y;
    }
    //auf anfrage wird der Steigungswinkel der Funktion an der Stelle x errechnet und weitergegeben
    public int getRot(int x)
    {
        double derivation = a * d * Math.cos(Math.toRadians(d*x + b*d)); // ableitung
        double atan = Math.atan(Math.toRadians(derivation)); //arcus tangens der Ableitung
        
        int angle = (int)(50 * atan); // konvertierung in eine ganze Zahl
        
        return angle;
    }
}
