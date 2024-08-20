package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_DoorIron extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Pintu Besi";

    public OBJ_DoorIron(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_obstacle;
        name = objName;
        down1 = setup("/objects/door_iron", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "Tidak bisa dibuka!";
    }

    public void interact() {
        startDialogue(this, 0);
    }
    
}
