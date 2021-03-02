package ctr.entity;
import ctr.Entity;
import ctr.Scene;
import ctr.model.Rope;
import ctr.physics.Stick;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class RopeEntity extends Entity 
{    
    private static final Stroke ROPE_STROKE = new BasicStroke(2);
    private static final Stroke OUTLINE_STROKE = new BasicStroke(5);   
    private Rope rope;   
    private static Color[] OUTLINE_COLOR = { Color.BLACK } ;
    private static Color[] ROPE_COLOR = { new Color(250, 180, 180), new Color(200, 110, 110) };
    
    public RopeEntity(Scene scene, Rope rope) 
    {
        super(scene);
        this.rope = rope;
        loadImageFromResource("/res/pin2.png");
    }
    
    @Override
    public void update()    {   }
    
    @Override
    public void draw(Graphics2D g) 
    {
        Stroke originalStroke = g.getStroke();
        g.setStroke(OUTLINE_STROKE);
        drawRope(g, OUTLINE_COLOR);
        g.setStroke(ROPE_STROKE);
        drawRope(g, ROPE_COLOR);
        g.setStroke(originalStroke);
        int x = (int) (rope.getFirstParticle().position.x - image.getWidth() / 2);
        int y = (int) (rope.getFirstParticle().position.y - image.getHeight() / 2);
        g.drawImage(image, x, y, null);
    }

    private void drawRope(Graphics2D g, Color[] colors) 
    {
        int colorIndex = 0;
        for(Stick stick : rope.getSticks()) 
        {
            if(stick != null && stick.isVisible()) 
            {
                g.setColor(colors[colorIndex]);
                colorIndex = (colorIndex + 1) % colors.length;
                stick.getLine().drawDebug(g);
            }
        }
    }

    @Override
    public void gameStateChanged(Scene.GameState newGameState)  {   visible = newGameState == Scene.GameState.PLAYING;  }    
}