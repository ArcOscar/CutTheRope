package ctr.entity;
import ctr.Entity;
import ctr.Scene;
import ctr.model.Spikes;
import java.awt.Graphics2D;

public class SpikesEntity extends Entity 
{
    private Spikes spikes;
    public SpikesEntity(Scene scene, Spikes spikes) 
    {
        super(scene);
        this.spikes = spikes;
        int sw = spikes.getRectangle().width;
        int n = 0;
        switch (sw) 
        {
            case 95: n = 1; break;
            case 170: n = 2; break;
            case 255: n = 3; break;
            case 325: n = 4; break;
            default : throw new RuntimeException("spikes width " + sw + " not valid !");
        }
        loadImageFromResource("/res/spikes_" + n + ".png");
    }

    @Override
    public void update()    {   }

    @Override
    public void draw(Graphics2D g) 
    {
        int x = (int) (spikes.getRectangle().x - 20);
        int y = (int) (spikes.getRectangle().y - 10);
        g.drawImage(image, x, y, null);
    }

    @Override
    public void gameStateChanged(Scene.GameState newGameState)  {   visible = newGameState == Scene.GameState.PLAYING;  }    
}
