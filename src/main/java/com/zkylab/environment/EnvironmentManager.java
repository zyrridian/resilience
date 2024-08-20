package com.zkylab.environment;

import java.awt.Graphics2D;

import com.zkylab.common.GamePanel;

public class EnvironmentManager {
    
    GamePanel gamePanel;
    public Lighting lighting;

    public EnvironmentManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setup() {
        lighting = new Lighting(gamePanel);
    }

    public void update() {
        lighting.update();
    }

    public void draw(Graphics2D g2) {
        lighting.draw(g2);
    }

}
