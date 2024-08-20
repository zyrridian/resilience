package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Shield_Normal extends Entity {

    public static final String objName = "Perisai";

    public OBJ_Shield_Normal(GamePanel gamePanel) {
        super(gamePanel);
        type = type_shield;
        name = objName;
        down1 = setup("/objects/shield_normal", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nTerbuat dari besi.";
        price = 20;
    }

}