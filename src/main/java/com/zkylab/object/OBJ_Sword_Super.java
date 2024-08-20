package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Sword_Super extends Entity {
    
    public static final String objName = "Pedang Super";

    public OBJ_Sword_Super(GamePanel gamePanel) {
        super(gamePanel);
        type = type_sword_super;
        name = objName;
        down1 = setup("/objects/sword_super", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 7;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nDamage yang besar.";
        price = 300;
        knockBackPower = 20;
        motion1_duration = 5;
        motion2_duration = 20;
    }

}