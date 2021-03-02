package ctr.model;
import ctr.physics.Line;
import ctr.physics.Vec2;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class AirCushion 
{
    private final Model model;
    private final Vec2 position = new Vec2();
    private final double radius = 30;
    private final int direction;    // 0=derecha, 1=abajo, 2=izquierda, 3=arriba
    private final Polygon influenceArea = new Polygon();
    private final Line line = new Line(0, 0, 0, 0);
    private final Vec2 wind = new Vec2();
    private long firedStartTime;
    private final Vec2 vTmp = new Vec2();
    private final List<AirCushionListener> listeners = new ArrayList<AirCushionListener>();
    
    public AirCushion(Model model, int x, int y, int direction) 
    {
        this.model = model;
        this.position.set(x, y);
        this.direction = direction;
        createWind();
        createInfluenceArea();
    }
    
    private void createWind() 
    {
        double wx = 0.75 * Math.cos(Math.toRadians(90 * direction));
        double wy = 0.75 * Math.sin(Math.toRadians(90 * direction));
        this.wind.set(wx, wy);
    }
    
    private void createInfluenceArea() 
    {
        //Area afectada
        influenceArea.addPoint((int) radius, (int) radius);
        influenceArea.addPoint((int) (5 * radius), (int) (2 * radius));
        influenceArea.addPoint((int) (5 * radius), (int) (-2 * radius));
        influenceArea.addPoint((int) radius, (int) -radius);
        //Mueve y rota
        Point2D ps = new Point2D.Double();
        Point2D pd = new Point2D.Double();
        AffineTransform transform = new AffineTransform();
        transform.translate(position.x, position.y);
        transform.rotate(direction * Math.toRadians(90));
        for(int p = 0; p < influenceArea.npoints; p++) 
        {
            ps.setLocation(influenceArea.xpoints[p], influenceArea.ypoints[p]);
            ps.setLocation(influenceArea.xpoints[p], influenceArea.ypoints[p]);
            transform.transform(ps, pd);
            influenceArea.xpoints[p] = (int) pd.getX();
            influenceArea.ypoints[p] = (int) pd.getY();
        }
    }
    
    public Model getModel() {   return model;   }

    public Vec2 getPosition()   {   return position;    }

    public double getRadius()   {   return radius;  }

    public int getDirection()   {   return direction;   }

    public Polygon getInfluenceArea()   {   return influenceArea;   }

    public boolean isFired()    {   return System.currentTimeMillis() - firedStartTime < 200;   }

    public void addListener(AirCushionListener listener)    {   listeners.add(listener);    }
    
    public void update()    
    {
        for(int i = 0; i < influenceArea.npoints; i++) 
        {
            line.getA().set(influenceArea.xpoints[i], influenceArea.ypoints[i]);
            int nextIndex = (i + 1) % influenceArea.npoints;
            line.getB().set(influenceArea.xpoints[nextIndex], influenceArea.ypoints[nextIndex]);
            Vec2 candyPivot = model.getCandy().getPivot();
            if((influenceArea.contains(candyPivot.x, candyPivot.y) || line.intersectsWithCircle(candyPivot, model.getCandy().getRadius())) && isFired()) 
            {
                model.getCandy().addForce(wind);
                return;
            }
        }
    }
    
    public void drawDebug(Graphics2D g) 
    {
        if(isFired())
            g.setColor(Color.RED);
        else
            g.setColor(Color.GREEN);
        g.drawOval((int) (position.x - radius), (int) (position.y - radius), (int) (2 * radius), (int) (2 * radius));
        g.draw(influenceArea);
    }
    
    public void tryToFire(double x, double y) 
    {
        vTmp.set(x, y);
        vTmp.sub(position);
        if (model.isPlaying() && vTmp.getSize() <= radius) 
        {
            fire();
            fireOnAirCushionFire();
        }
    }
    
    private void fire() {   firedStartTime = System.currentTimeMillis();    }

    private void fireOnAirCushionFire() 
    {
        for(AirCushionListener listener : listeners)
            listener.onAirCushionFire();
    }    
}