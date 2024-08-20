package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Moon extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Moon";

    public OBJ_Moon(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        // type = type_pickupOnly;
        name = objName;
        // value = 2;

        down1 = setup("/objects/night", gamePanel.tileSize, gamePanel.tileSize);
    }

    // public boolean use(Entity entity) {
    //     gamePanel.playSoundEffect(2);
    //     gamePanel.ui.addMessage("Life +" + value);
    //     entity.life += value;
    //     return true;
    // }

}
