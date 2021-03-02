package ctr.entity;
import ctr.Entity;
import ctr.Scene;
import ctr.model.PinRope;
import ctr.model.PinRopeListener;
import ctr.model.Rope;
import ctr.physics.Stick;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class PinRopeEntity extends Entity implements PinRopeListener 
{    
    private static final Stroke ROPE_STROKE = new BasicStroke(2);
    private static final Stroke OUTLINE_STROKE = new BasicStroke(5);
    private static final Stroke DASHED_STROKE = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] { 10.0f }, 0.0f);     
    private PinRope pinRope;
    private Rope rope;
    private static Color[] OUTLINE_COLOR = { Color.BLACK } ;
    private static Color[] ROPE_COLOR = { new Color(250, 180, 180), new Color(200, 110, 110) };
    
    public PinRopeEntity(Scene scene, PinRope pinRope) 
    {
        super(scene);
        this.pinRope = pinRope;
        this.pinRope.addListener(this);
        loadImageFromResource("/res/pin2.png");
    }

    @Override
    public void update()    {   }
    
    @Override
    public void draw(Graphics2D g) 
    {
        Stroke originalStroke = g.getStroke();
        int x = (int) (pinRope.getPosition().x - image.getWidth() / 2);
        int y = (int) (pinRope.getPosition().y - image.getHeight() / 2);
        g.setColor(Color.BLACK);
        g.setStroke(DASHED_STROKE);
        double radius = pinRope.getRadius();
        g.drawOval((int) (pinRope.getPosition().x - radius), (int) (pinRope.getPosition().y - radius), (int) (2 * radius), (int) (2 * radius));
        if (rope != null) 
        {
            g.setStroke(OUTLINE_STROKE);
            drawRope(g, OUTLINE_COLOR);
            g.setStroke(ROPE_STROKE);
            drawRope(g, ROPE_COLOR);
        }
        g.drawImage(image, x, y, null);
        g.setStroke(originalStroke);
    }

    private void drawRope(Graphics2D g, Color[] colors) 
    {
        int colorIndex = 0;
        for (Stick stick : rope.getSticks()) {
            if (stick != null && stick.isVisible()) {
                g.setColor(colors[colorIndex]);
                colorIndex = (colorIndex + 1) % colors.length;
                stick.getLine().drawDebug(g);
            }
        }
    }

    @Override
    public void onRopeCreated(Rope rope)    {   this.rope = rope;   }

    @Override
    public void gameStateChanged(Scene.GameState newGameState)  {   visible = newGameState == Scene.GameState.PLAYING;  }    
}