package ctr.physics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class World 
{
    private final int width;
    private final int height;
    private final Vec2 gravity = new Vec2(0, 0.5);
    private final List<Particle> particles = new ArrayList<Particle>();
    private final List<Stick> sticks = new ArrayList<Stick>();
    
    public World(int width, int height) 
    {
        this.width = width;
        this.height = height;
    }

    public int getWidth()   {   return width;   }

    public int getHeight()  {   return height;  }

    public Vec2 getGravity()    {   return gravity; }

    public List<Particle> getParticles()    {   return particles;   }

    public List<Stick> getSticks()  {   return sticks;  }

    public void update() 
    {
        for(Particle particle : particles)
            particle.update();
        for(int i = 0; i < 5; i++) 
        {
            for(Stick stick : sticks)
                stick.update();
            for (Particle particle : particles)
                particle.updateConstrain();
        }
    }
    
    public void clear() 
    {
        particles.clear();
        sticks.clear();
    }
    
    public void addParticle(Particle point) {   particles.add(point);   }

    public void addStick(Stick stick)   {   sticks.add(stick);  }

    public void drawDebug(Graphics2D g) 
    {
        for(Particle particle : particles)
            particle.drawDebug(g);
        for(Stick stick : sticks)
        {
            if(stick.isVisible())
                stick.drawDebug(g);
        }
    }
}