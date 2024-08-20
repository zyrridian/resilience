package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Lantern extends Entity {
    
    public static final String objName = "Lentera";

    public OBJ_Lantern(GamePanel gamePanel) {
        super(gamePanel);
        
        type = type_light;
        name = objName;
        down1 = setup("/objects/lantern", gamePanel.tileSize, gamePanel.tileSize);
        description = "[Lantern]\nMenerangi keadaan\nsekitarmu.";
        price = 150;
        lightRadius = 300;
    }
    
}
