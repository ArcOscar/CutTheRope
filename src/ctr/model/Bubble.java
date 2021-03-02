package ctr.model;
import ctr.physics.Vec2;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Bubble 
{
    private final Model model;
    private final Vec2 position = new Vec2();
    private double radius;
    private boolean visible = true;
    private Candy candy;
    private final Vec2 upForce;
    private final Vec2 vTmp = new Vec2();
    private final List<BubbleListener> listeners = new ArrayList<BubbleListener>();
    
    public Bubble(Model model, double x, double y, double radius) 
    {
        this.model = model;
        this.radius = radius;
        this.position.set(x, y);
        this.upForce = new Vec2(0, -1);
    }

    public Model getModel() {   return model;   }

    public Vec2 getPosition()   {   return position;    }

    public double getRadius()   {   return radius;  }

    public boolean isVisible()  {   return visible; }

    public Candy getCandy() {   return candy;   }
    
    public void addListener(BubbleListener listener)    {   listeners.add(listener);    }
    
    public void update()   
    {
        if(!visible)
            return;
        else if(candy != null) 
        {
            if(!candy.isVisible()) 
            {
                burst();
                return;
            }
            if(candy.getAttachedRopes().isEmpty())
                upForce.y = -0.6;
            else if(candy.getPoints()[0].getVelocity().y > 0.1)
                upForce.y -= 0.02;
            candy.addForce(upForce);
            Vec2 candyPivot = candy.getPivot();
            position.x += (candyPivot.x - position.x) * 0.95;
            position.y += (candyPivot.y - position.y) * 0.95;
        }
        else 
        {
            Vec2 candyPivot = model.getCandy().getPivot();
            vTmp.set(candyPivot);
            vTmp.sub(position);
            if (candy == null && vTmp.getSize() <= radius && model.getCandy().isVisible()) 
            {
                candy = model.getCandy();
                fireOnCandyCaught();
            }
        }
    }
    
    public void tryToBurst(double x, double y) 
    {
        if(!visible)
            return;
        vTmp.set(x, y);
        vTmp.sub(position);
        if(model.isPlaying() && candy != null && vTmp.getSize() <= radius)
            burst();
    }
    
    private void burst() 
    {
        visible = false;
        fireOnBurst();
    }
    
    public void drawDebug(Graphics2D g) 
    {
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        g.setColor(Color.CYAN);
        g.drawOval((int) (-radius), (int) (-radius), (int) (2 * radius), (int) (2 * radius));
        g.setTransform(at);
    }

    private void fireOnBurst() 
    {
        for(BubbleListener listener : listeners)
            listener.onBurst();
    }

    private void fireOnCandyCaught() 
    {
        for (BubbleListener listener : listeners)
            listener.onCandyCaught();
    }
}