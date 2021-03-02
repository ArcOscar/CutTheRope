package ctr;
import static ctr.Scene.GameState.*;
import ctr.entity.AirCushionEntity;
import ctr.entity.BackgroundEntity;
import ctr.entity.BubbleEntity;
import ctr.entity.CandyEntity;
import ctr.entity.CurtainEntity;
import ctr.entity.FadeEffectEntity;
import ctr.entity.GameOverEntity;
import ctr.entity.InitializerEntity;
import ctr.entity.LevelClearedEntity;
import ctr.entity.PetEntity;
import ctr.entity.PinRopeEntity;
import ctr.entity.RopeEntity;
import ctr.entity.SpikesEntity;
import ctr.entity.StarEntity;
import ctr.entity.TitleEntity;
import ctr.model.AirCushion;
import ctr.model.Bubble;
import ctr.model.Model;
import ctr.model.PinRope;
import ctr.model.Rope;
import ctr.model.Spikes;
import ctr.model.Star;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Scene
{
    private final Model model = new Model(800, 600, 10);
    protected List<Entity> entities = new ArrayList<Entity>();
    protected List<Entity> levelEntities = new ArrayList<Entity>();
    public static enum GameState { INITIALIZING, OL_PRESENTS, TITLE, LEVEL_SELECT, READY, PLAYING, LEVEL_CLEARED, GAME_OVER }
    private GameState gameState = GameState.INITIALIZING;
    private int currentLevel = 1;
    private FadeEffectEntity fadeEffect;
    private CurtainEntity curtain;
    
    public Scene()  {   }
    
    public Model getModel() {   return model;   }

    public GameState getGameState() {   return gameState;   }

    public void setState(GameState gameState) 
    {
        if(this.gameState != gameState) 
        {
            this.gameState = gameState;
            for (Entity entity : levelEntities)
                entity.gameStateChanged(gameState);
            for (Entity entity : entities)
                entity.gameStateChanged(gameState);
        }
    }
    
    public void start()
    {
        createAllEntities();
        startAllEntities();
    }
    
    private void createAllEntities()
    {
        fadeEffect = new FadeEffectEntity(this);
        curtain = new CurtainEntity(this);
        entities.add(new InitializerEntity(this, fadeEffect));
        entities.add(new TitleEntity(this, fadeEffect, curtain));
        entities.add(curtain);
        entities.add(new GameOverEntity(this, fadeEffect, curtain));
        entities.add(new LevelClearedEntity(this, fadeEffect, curtain));
        entities.add(fadeEffect);
    }
    
    private void createAllLevelEntities()
    {
        levelEntities.add(new BackgroundEntity(this));
        levelEntities.add(new PetEntity(this, curtain));
        for (AirCushion airCushion : model.getAirCushions())
            levelEntities.add(new AirCushionEntity(this, airCushion));  //Air Cushion
        for (Rope rope : model.getRopes())
            levelEntities.add(new RopeEntity(this, rope));              //Ropes
        for (PinRope pinRope : model.getPinRopes())
            levelEntities.add(new PinRopeEntity(this, pinRope));        //Pin Ropes
        for (Spikes spikes : model.getSpikesList())
            levelEntities.add(new SpikesEntity(this, spikes));          //Spikes
        levelEntities.add(new CandyEntity(this));                       //Candy
        for (Star star : model.getStars())
            levelEntities.add(new StarEntity(this, star));              //Stars
        for (Bubble bubble : model.getBubbles())
            levelEntities.add(new BubbleEntity(this, bubble));          //Bubbles
    }

    private void startAllEntities() 
    {
        for (Entity entity : entities)
            entity.start();
    }
    
    private void startAllLevelEntities() 
    {
        for(Entity entity : levelEntities)
            entity.start();
    }

    public void update() 
    {
        for(Entity entity : levelEntities)
            entity.update();
        for(Entity entity : entities)
            entity.update();
    }
    
    public void updateFixed() 
    {
        if(Mouse.pressed) 
        {
            List<Point> trail = model.getSlashTrail().getTrail();
            if(trail.size() > 0) {
                Point p = trail.get(trail.size() - 1);
                if (p != null && p.x >= 0 && p.y >= 0)
                    model.addSlashTrail((int) (p.x + 0.5 * (Mouse.x - p.x)), (int) (p.y + 0.5 * (Mouse.y - p.y)));
                model.addSlashTrail((int) Mouse.x, (int) Mouse.y);
            }
        }
        else
            model.addSlashTrail(-1, -1);
        for(Entity entity : levelEntities)
            entity.updateFixed();
        for(Entity entity : entities)
            entity.updateFixed();
        model.update();
    }
    
    public void draw(Graphics2D g) 
    {
        for(Entity entity : levelEntities) 
        {
            if(entity.isVisible())
                entity.draw(g);
        }
        for(Entity entity : entities) 
        {
            if(entity.isVisible())
                entity.draw(g);
        }
        model.getSlashTrail().drawDebug(g);
    }
    
        //Inicia control de gameflow
    public void startLevel(int level) 
    {
        currentLevel = level;
        model.startLevel("/res/level_" + level + ".txt");
        levelEntities.clear();
        createAllLevelEntities();
        startAllLevelEntities();
        setState(PLAYING);
    }

    public void replayLevel()   { startLevel(currentLevel);   }

    public void backToTitle()   { setState(TITLE);  }

    public void nextLevel() {   startLevel(currentLevel + 1);   }
}