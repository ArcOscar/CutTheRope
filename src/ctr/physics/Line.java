package ctr.physics;
import java.awt.Graphics2D;

public class Line 
{
    private Vec2 a;
    private Vec2 b;
    private Vec2 normal = new Vec2();
    private Vec2 v = new Vec2();
    private final Vec2 vTmp = new Vec2();
    private final Vec2 p1cl = new Vec2();
    private final Vec2 p2cl = new Vec2();
    private final Vec2 perp = new Vec2();
    
    
    public Line(Vec2 a, Vec2 b) 
    {
        this.a = a;
        this.b = b;
    }

    public Line(double x1, double y1, double x2, double y2) 
    {
        a = new Vec2(x1, y1);
        b = new Vec2(x2, y2);
    }

    public Vec2 getA()  {   return a;   }

    public void setA(Vec2 a)    {  this.a = a;  }

    public Vec2 getB()  {   return b;   }

    public void setB(Vec2 b)    {   this.b = b; }

    public Vec2 getNormal() 
    {
        getV();
        normal.set(-v.y, v.x);
        normal.normalize();
        return normal;
    }

    public Vec2 getV() 
    {
        v.set(b);
        v.sub(a);
        return v;
    }
    
    public void drawDebug(Graphics2D g) {   g.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y); }

    public Vec2 getSegIntersectionPoint(Line l2) 
    {
        vTmp.set(l2.a);
        vTmp.sub(a);
        double s1 = getNormal().dot(vTmp);
        vTmp.set(l2.b);
        vTmp.sub(a);
        double s2 = getNormal().dot(vTmp);
        vTmp.set(a);
        vTmp.sub(l2.a);
        double s3 = l2.getNormal().dot(vTmp);
        vTmp.set(b);
        vTmp.sub(l2.a);
        double s4 = l2.getNormal().dot(vTmp);
        if (s1 * s2 > 0 || s3 * s4 > 0)
            return null;
        return getIntersectionPoint(l2);
    }
    
    public Vec2 getIntersectionPoint(Line l2) 
    {
        vTmp.set(l2.b);
        vTmp.sub(a);
        double d1 = l2.getNormal().dot(vTmp);
        vTmp.set(getV());
        double d2 = l2.getNormal().dot(vTmp);
        if (d1 == 0)
            return null;
        vTmp.scale(d1 / d2);
        vTmp.add(a);
        return vTmp;
    }
    
    public boolean intersectsWithCircle(Vec2 circlePivot, double circleRadius) 
    {
        p1cl.set(a);
        p1cl.sub(circlePivot);
        p2cl.set(b);
        p2cl.sub(circlePivot);
        getV().setPerp(perp);
        if(perp.getSign(p1cl) * perp.getSign(p2cl) < 0) 
        {
            v.normalize();
            return (Math.abs(p1cl.perpDot(v)) <= circleRadius);
        } else 
            return (p1cl.getSize() <= circleRadius) || (p2cl.getSize() <= circleRadius);
    }        
}