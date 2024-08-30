package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Door extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Pintu";

    public OBJ_Door(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_obstacle;
        name = objName;
        down1 = setup("/objects/door", gamePanel.tileSize, gamePanel.tileSize);
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
        dialogues[0][0] = "Kamu butuh kunci untuk membuka pintu ini.";
        dialogues[0][1] = "Bantulah penduduk di kota untuk mendapatkannya.";
    }

    public void interact() {
        startDialogue(this, 0);
    }

}
