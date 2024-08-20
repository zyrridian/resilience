package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Key extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Kunci";

    public OBJ_Key(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        type = type_consumable;
        name = objName;
        down1 = setup("/objects/key", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nUntuk pintu.";
        price = 100;
        stackable = true;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "Kamu menggunakan " + name + " dan pintu terbuka.";
        dialogues[1][0] = "Apa yang kamu lakukan?";
    }

    public boolean use(Entity entity) {
        int objIndex = getDetected(entity, gamePanel.obj, "Pintu");
        if (objIndex != 999) { // use the key
            startDialogue(this, 0);
            gamePanel.playSoundEffect(3);
            gamePanel.obj[gamePanel.currentMap][objIndex] = null;
            return true;
        } else {
            startDialogue(this, 1);
            return false;
        }
    }
}
