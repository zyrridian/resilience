package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Boots extends Entity {
    
    public static final String objName = "Boots";

    public OBJ_Boots(GamePanel gamePanel) {
        super(gamePanel);
        name = objName;
        down1 = setup("/objects/boots", gamePanel.tileSize, gamePanel.tileSize);
    }
}
