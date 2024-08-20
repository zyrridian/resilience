package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Shield_Super extends Entity {
    
    public static final String objName = "Perisai Super";

    public OBJ_Shield_Super(GamePanel gamePanel) {
        super(gamePanel);
        type = type_shield_super;
        name = objName;
        down1 = setup("/objects/shield_super", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 5;
        description = "[" + name + "]\nDamage musuh berkurang\nmenjadi 1.";
        price = 300;
    }
    
}
