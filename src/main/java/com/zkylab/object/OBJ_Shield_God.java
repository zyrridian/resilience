package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Shield_God extends Entity {
    
    public static final String objName = "Perisai Dewa";

    public OBJ_Shield_God(GamePanel gamePanel) {
        super(gamePanel);
        type = type_shield_god;
        name = objName;
        down1 = setup("/objects/shield_god", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 999;
        description = "[" + name + "]\nPertahanan mutlak.";
        price = 1000;
    }
    
}
