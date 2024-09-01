package com.zkylab.entity;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.data.Progress;
import com.zkylab.object.OBJ_Bread;
import com.zkylab.object.OBJ_Key;
import com.zkylab.object.OBJ_Lantern;
import com.zkylab.object.OBJ_Potion_Red;
import com.zkylab.object.OBJ_Shield_God;
import com.zkylab.object.OBJ_Shield_Super;
import com.zkylab.object.OBJ_Sword_God;
import com.zkylab.object.OBJ_Sword_Super;
import com.zkylab.object.OBJ_Tent;
import com.zkylab.object.OBJ_Water;

public class NPC_Aksa extends Entity {

    public static final String npcName = "Aksa";

    public NPC_Aksa(GamePanel gamePanel) {
        super(gamePanel);

        name = npcName;
        isNPC = true;
        direction = "down";
        speed = 1;
        solidArea.x = 9;
        solidArea.y = 18;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        dialogueSet = -1;

        // if (Progress.cutsceneWarningFinished) {
        //     sleep = false;
        // } else {
            sleep = true;
        // }

        onPath = false;

        getImage();
        setDialogue();
        setItems();

    }

    public void getImage() {
        avatar = setup("/npc/aksa_base_avatar", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/aksa_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/aksa_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/aksa_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/aksa_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/aksa_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/aksa_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/aksa_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/aksa_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Halo!";
        dialogues[0][1] = "Aku adalah salah satu relawan siaga bencana.";
        dialogues[0][2] = "Ah, kamu butuh air minum?";
        dialogues[0][3] = "Ini dia, bantulah aku membagikan air minum dan\nroti ini ke penduduk sekitar.";
        dialogues[0][4] = "Dua orang temanku dari Tim SAR sedang berusaha\nmengecek apabila ada yang terjebak di dalam\ngedung.";
        dialogues[1][0] = "Tenang saja, mereka berdua sudah ahli.";
        dialogues[1][1] = "Lebih baik kau membantuku membagikan makanan\ndan minuman ini.";
        dialogues[2][0] = "Makanan dan minumannya ada di inventorimu.\nTekan [C].";
        dialogues[3][0] = "Kosong? Tentu saja, barangnya masih di sini.";
        dialogues[4][0] = "Apa yang kau butuhkan?";
        dialogues[5][0] = "Sampai bertemu lagi.";
        dialogues[6][0] = "Koinmu tidak cukup.";
        dialogues[7][0] = "Tempat penyimpananmu penuh.";
        dialogues[8][0] = "Kau tidak bisa menjual barang yang sedang digunakan.";
    }

    public void setAction() {

        if (onPath) {

            // NPC path with goal
            int goalCol = 25;
            int goalRow = 26;

            // // NPC path follow player
            // // int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.x) /
            // gamePanel.tileSize;
            // // int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.y) /
            // gamePanel.tileSize;
            // // if (nextCol == goalCol && nextRow == goalRow) onPath = false;

            searchPath(goalCol, goalRow, "left");

        } else {

            if (Progress.cutsceneWarningFinished) {
                actionLockCounter++;

                if (actionLockCounter == 120) { // Giving delay 2 second every movement

                    Random random = new Random();
                    int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

                    if (i <= 25)
                        direction = "up";
                    if (i > 25 && i <= 50)
                        direction = "down";
                    if (i > 50 && i <= 75)
                        direction = "left";
                    if (i > 75 && i <= 100)
                        direction = "right";

                    actionLockCounter = 0;

                }
            }

        }

    }
    
    public void setItems() {
        inventory.add(new OBJ_Bread(gamePanel));
        inventory.add(new OBJ_Water(gamePanel));
        inventory.add(new OBJ_Potion_Red(gamePanel));
        inventory.add(new OBJ_Key(gamePanel));
        inventory.add(new OBJ_Tent(gamePanel));
        inventory.add(new OBJ_Lantern(gamePanel));
        // inventory.add(new OBJ_Sword_Super(gamePanel));
        // inventory.add(new OBJ_Shield_Super(gamePanel));
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
