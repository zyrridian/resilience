package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Heart extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Nyawa";

    public OBJ_Heart(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_pickupOnly;
        name = objName;
        value = 2;

        down1 = setup("/objects/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image = setup("/objects/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects/heart_half", gamePanel.tileSize, gamePanel.tileSize);
        image3 = setup("/objects/heart_blank", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean use(Entity entity) {
        gamePanel.playSoundEffect(GamePanel.SFX_POWER_UP);
        gamePanel.ui.addMessage("Nyawa +" + value);
        entity.life += value;
        return true;
    }

}
