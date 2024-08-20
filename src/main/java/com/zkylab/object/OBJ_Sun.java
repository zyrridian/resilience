package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Sun extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Sun";

    public OBJ_Sun(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        // type = type_pickupOnly;
        name = objName;
        // value = 2;

        down1 = setup("/objects/day", gamePanel.tileSize, gamePanel.tileSize);
    }

    // public boolean use(Entity entity) {
    //     gamePanel.playSoundEffect(2);
    //     gamePanel.ui.addMessage("Life +" + value);
    //     entity.life += value;
    //     return true;
    // }

}
