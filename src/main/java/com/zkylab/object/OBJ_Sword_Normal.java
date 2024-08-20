package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Sword_Normal extends Entity {
    
    public static final String objName = "Pedang Normal";

    public OBJ_Sword_Normal(GamePanel gamePanel) {
        super(gamePanel);
        type = type_sword;
        name = objName;
        down1 = setup("/objects/sword_normal", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nSebuah pedang tua.";
        price = 20;
        knockBackPower = 2;
        motion1_duration = 5;
        motion2_duration = 25;
    }

}