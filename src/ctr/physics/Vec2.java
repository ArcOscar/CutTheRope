package ctr.physics;

public class Vec2 
{
    public double x;
    public double y;
    private static final Vec2 perpTmp = new Vec2();
    
    public Vec2()   { }

    public Vec2(double x, double y) 
    {
        this.x = x;
        this.y = y;
    }

    public Vec2(Vec2 v) 
    {
        this.x = v.x;
        this.y = v.y;
    }
    
    public void set(double x, double y) 
    {
        this.x = x;
        this.y = y;
    }

    public void set(Vec2 v) 
    {
        this.x = v.x;
        this.y = v.y;
    }
    
    public void add(Vec2 v) 
    {
        this.x += v.x;
        this.y += v.y;
    }

    public void sub(Vec2 v) 
    {
        this.x -= v.x;
        this.y -= v.y;
    }

    public void scale(double s) {   scale(s, s);    }
    
    public void scale(double sx, double sy) 
    {
        this.x *= sx;
        this.y *= sy;
    }
    
    public double getSize() {   return Math.sqrt(x * x + y * y);    }

    public void normalize() 
    {
        double sizeInv = 1 / getSize();
        scale(sizeInv, sizeInv);
    }

    public int getSign(Vec2 v)  {   return (y * v.x > x * v.y) ? -1 : 1;    }
    
    public double dot(Vec2 v)   {   return x * v.x + y * v.y;   }

    public double cross(Vec2 v) {   return x * v.y - y * v.x;   }
    
    public void setPerp(Vec2 v) {   v.set(-y, x);   }
    
    public double perpDot(Vec2 v) 
    {
        setPerp(perpTmp);
        return perpTmp.dot(v);
    }
    
    @Override
    public String toString()    {   return "Vec2{" + "x=" + x + ", y=" + y + '}';   } 
}