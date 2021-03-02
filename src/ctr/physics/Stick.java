package ctr.physics;
import java.awt.Color;
import java.awt.Graphics2D;

public class Stick 
{
    private Particle a;
    private Particle b;
    private double size;
    private double elasticity;
    private boolean visible;
    private final Line line;
    private final Vec2 vTmp = new Vec2();
    
    public Stick(Particle a, Particle b, double elasticity, boolean visible) 
    {
        this.a = a;
        this.b = b;
        this.elasticity = 1 - elasticity;
        this.visible = visible;
        vTmp.set(b.position);
        vTmp.sub(a.position);
        this.size = vTmp.getSize();
        this.line = new Line(a.position, b.position);
    }

    public Particle getA()  {   return a;   }

    public void setA(Particle a) 
    {
        this.a = a;
        line.setA(a.position);
    }

    public Particle getB()  {   return b;   }

    public void setB(Particle b) 
    {
        this.b = b;
        line.setB(b.position);
    }

    public double getSize() {   return size;    }

    public void setSize(double size)    {   this.size = size;   }

    public boolean isVisible()  {   return visible; }

    public void setVisible(boolean visible) {   this.visible = visible; }

    public Line getLine()   { return line;    }

    public void update()
    {
        vTmp.set(b.position);
        vTmp.sub(a.position);
        double currentSize = vTmp.getSize();
        double dif = (currentSize - size) * 0.5;
        vTmp.normalize();
        vTmp.scale(dif * elasticity);
        if(!a.isPinned())
            a.position.add(vTmp);
        if (!b.isPinned())
            b.position.sub(vTmp);
    }
    
    public void drawDebug(Graphics2D g) 
    {
        g.setColor(Color.BLUE);
        line.drawDebug(g);
    }
}
