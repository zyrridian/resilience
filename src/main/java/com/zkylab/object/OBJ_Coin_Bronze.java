package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Coin_Bronze extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Koin";

    public OBJ_Coin_Bronze(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        type = type_pickupOnly;
        name = objName;
        value = 20;
        down1 = setup("/objects/coin_bronze", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean use(Entity entity) {
        gamePanel.playSoundEffect(GamePanel.SFX_COIN);
        gamePanel.ui.addMessage("Koin +" + value);
        gamePanel.player.coin += value;
        return true;
    }
    
}
