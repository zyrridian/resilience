package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Bread extends Entity {
    GamePanel gamePanel;
    public static final String objName = "Roti";

    public OBJ_Bread(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        type = type_consumable;
        name = objName;
        down1 = setup("/objects/bread", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nBagikan ke warga.";
        price = 0;
        stackable = true;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "Kamu memberikan " + name + ".";
        dialogues[1][0] = "Perutmu sudah penuh, bagikan roti ini ke warga.";
    }

    public boolean use(Entity entity) {
        int npcIndex = getDetected(entity, gamePanel.npc, "npc");
        if (npcIndex != 999) { // use the key
            startDialogue(this, 0);
            gamePanel.playSoundEffect(GamePanel.SFX_FANFARE);
            // gamePanel.npc[gamePanel.currentMap][npcIndex] = null;
            return true;
        } else {
            startDialogue(this, 1);
            return false;
        }
    }
}
