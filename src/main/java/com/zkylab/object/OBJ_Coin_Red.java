package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Coin_Red extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Koin merah";

    public OBJ_Coin_Red(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        type = type_pickupOnly;
        name = objName;
        value = 100;
        down1 = setup("/objects/coin_red", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean use(Entity entity) {
        gamePanel.playSoundEffect(1);
        gamePanel.ui.addMessage("Koin +" + value);
        gamePanel.player.coin += value;
        return true;
    }
    
}
