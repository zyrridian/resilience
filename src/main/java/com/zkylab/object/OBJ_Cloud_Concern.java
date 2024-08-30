package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Cloud_Concern extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Cloud Concern";

    public OBJ_Cloud_Concern(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        // type = type_pickupOnly;
        name = objName;
        // value = 2;

        down1 = setup("/objects/cloud_concern", gamePanel.tileSize / 2, gamePanel.tileSize / 2);
    }

    // public boolean use(Entity entity) {
    //     gamePanel.playSoundEffect(2);
    //     gamePanel.ui.addMessage("Life +" + value);
    //     entity.life += value;
    //     return true;
    // }

}
