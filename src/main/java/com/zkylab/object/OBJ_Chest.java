package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Chest extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Peti";

    public OBJ_Chest(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_obstacle;
        name = objName;
        image = setup("/objects/chest", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects/chest_opened", gamePanel.tileSize, gamePanel.tileSize);
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void setLoot(Entity loot) {
        this.loot = loot;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "Kamu menemukan sebuah " + loot.name + "!" + "\n... Tapi kamu tidak bisa membawanya!";
        dialogues[1][0] = "Kamu menemukan sebuah " + loot.name + "!" + "\nKamu mendapatkan " + loot.name + "!";
        dialogues[2][0] = "Peti ini kosong.";
    }

    public void interact() {
        if (!opened) {
            gamePanel.playSoundEffect(3);
            if (!gamePanel.player.canObtainItem(loot)) {
                startDialogue(this, 0);
            } else {
                startDialogue(this, 1);
                down1 = image2;
                opened = true;
            }
        } else {
            startDialogue(this, 2);
        }
    }

}
