package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Sword_God extends Entity {
    
    public static final String objName = "Pedang Dewa";

    public OBJ_Sword_God(GamePanel gamePanel) {
        super(gamePanel);
        type = type_sword_god;
        name = objName;
        down1 = setup("/objects/sword_god", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 999;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nMenyerang dengan cepat.";
        price = 1000;
        knockBackPower = 0;
        motion1_duration = 5;
        motion2_duration = 6;
    }

}