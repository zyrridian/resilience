
package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Background extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Koin";

    public OBJ_Background(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        type = type_pickupOnly;
        name = objName;
        value = 20;
        down1 = setup("/background/challenge", gamePanel.screenWidth, gamePanel.screenHeight);
    }

    public boolean use(Entity entity) {
        // gamePanel.playSoundEffect(1);
        gamePanel.ui.addMessage("Koin +" + value);
        gamePanel.player.coin += value;
        return true;
    }
    
}

