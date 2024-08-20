package com.zkylab.pet;


import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;
import com.zkylab.object.OBJ_Key;
import com.zkylab.object.OBJ_Lantern;
import com.zkylab.object.OBJ_Potion_Red;
import com.zkylab.object.OBJ_Shield_God;
import com.zkylab.object.OBJ_Shield_Super;
import com.zkylab.object.OBJ_Sword_God;
import com.zkylab.object.OBJ_Sword_Super;
import com.zkylab.object.OBJ_Tent;

public class PET_Johnson extends Entity {

    public static final String npcName = "Johnson";
    GamePanel gamePanel;

    public PET_Johnson(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = npcName;
        direction = "left";
        speed = 0;

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        dialogueSet = -1;

        getImage();
        setDialogue();
        setItems();

    }

    public void getImage() {
        avatar = setup("/pet/avatar_johnson", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        up1 = setup("/pet/johnson_walk_left_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        up2 = setup("/pet/johnson_walk_left_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        left1 = setup("/pet/johnson_walk_left_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        left2 = setup("/pet/johnson_walk_left_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        down1 = setup("/pet/johnson_walk_left_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        down2 = setup("/pet/johnson_walk_left_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        right1 = setup("/pet/johnson_walk_left_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        right2 = setup("/pet/johnson_walk_left_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
    }

    public void setDialogue() {
        dialogues[0][0] = "Woof, woof!";
        dialogues[1][0] = "Woof.";
        dialogues[2][0] = "...";
        dialogues[3][0] = "Itu sudah cukup.";
        dialogues[3][1] = "Jangan memintaku menggonggong lagi.";
        dialogues[4][0] = "Kau butuh sesuatu?";
        dialogues[5][0] = "Pergilah.";
        dialogues[6][0] = "Koinmu tidak cukup.";
        dialogues[7][0] = "Tempat penyimpananmu penuh.";
        dialogues[8][0] = "Kau tidak bisa menjual barang yang sedang digunakan.";
    }


    public void setItems() {
        inventory.add(new OBJ_Potion_Red(gamePanel));
        inventory.add(new OBJ_Key(gamePanel));
        inventory.add(new OBJ_Tent(gamePanel));
        inventory.add(new OBJ_Lantern(gamePanel));
        inventory.add(new OBJ_Sword_Super(gamePanel));
        inventory.add(new OBJ_Shield_Super(gamePanel));
        inventory.add(new OBJ_Sword_God(gamePanel));
        inventory.add(new OBJ_Shield_God(gamePanel));
    }

    public void speak() {
        facePlayer();
        if (dialogueSet >= 4) {
            // dialogueSet--; // Dialogue will be stuck in the end state
            gamePanel.gameState = GamePanel.TRADE_STATE;
            gamePanel.ui.npc = this;
        } else {
            startDialogue(this, dialogueSet);
            dialogueSet++;
        }
    }
    
}
