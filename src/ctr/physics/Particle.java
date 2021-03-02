package ctr.physics;
import java.awt.Color;
import java.awt.Graphics2D;

public class Particle 
{
    public final World world;
    public final Vec2 position = new Vec2();
    public final Vec2 previousPosition = new Vec2();
    public final Vec2 velocity = new Vec2();
    public final Vec2 force = new Vec2();
    public double restitution = 0.9;
    public double friction = 1.0;
    public boolean pinned = false;
    
    public Particle(World world, double x, double y)    {  this(world, x, y, x, y);    }
    
    public Particle(World view, double x, double y, double prevX, double prevY) 
    {
        this.world = view;
        position.set(x, y);
        previousPosition.set(prevX, prevY);
    }

    public World getWorld() {   return world;   }

    public Vec2 getVelocity()   { return velocity;    }

    public double getRestitution()  {   return restitution; }

    public double getFriction() {   return friction;    }

    public boolean isPinned()   {   return pinned;  }

    public void setPinned(boolean pinned)   { this.pinned = pinned;   }
    
    public void addForce(Vec2 a)    {   force.add(a);   }
    
    public void update()
    {
        if (pinned)
            return;
        velocity.set(position);
        velocity.sub(previousPosition);
        velocity.scale(friction);
        previousPosition.set(position);
        position.add(velocity);
        position.add(force);
        force.set(world.getGravity());
    }
    
    public void updateConstrain() 
    {
        if (pinned)
            return;
        velocity.set(position);
        velocity.sub(previousPosition);
        velocity.scale(friction);
        if (position.x > world.getWidth()) 
        {
            position.x = world.getWidth();
            previousPosition.x = position.x + velocity.x * restitution;
        }
        else if (position.x < 0) 
        {
            position.x = 0;
            previousPosition.x = position.x + velocity.x * restitution;
        }
    }
    
    public void drawDebug(Graphics2D g) 
    {
        g.setColor(Color.RED);
        g.fillOval((int) (position.x - 3), (int) (position.y - 3), 6, 6);
    }
}