package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Mana_Crystal extends Entity{

    GamePanel gamePanel;
    public static final String objName = "Mana Crystal";

    public OBJ_Mana_Crystal(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_pickupOnly;
        name = objName;
        value = 1;

        down1 = setup("/objects/manacrystal_full", gamePanel.tileSize, gamePanel.tileSize);
        image = setup("/objects/manacrystal_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects/manacrystal_blank", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean use(Entity entity) {
        gamePanel.playSoundEffect(GamePanel.SFX_POWER_UP);
        gamePanel.ui.addMessage("Mana +" + value);
        entity.mana += value;
        return true;
    }
    
}
