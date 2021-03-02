package ctr.entity;

import ctr.Entity;
import ctr.FontRenderer;
import ctr.Scene;
import ctr.Scene.GameState;
import java.awt.Graphics2D;

public class BackgroundEntity extends Entity 
{
    public BackgroundEntity(Scene scene)    {   super(scene);   }

    @Override
    public void start() {   loadImageFromResource("/res/background.png");   }

    @Override
    public void gameStateChanged(GameState newGameState)   
    {
        visible = (newGameState != GameState.INITIALIZING) && (newGameState != GameState.OL_PRESENTS)&& (newGameState != GameState.TITLE);
    }
}