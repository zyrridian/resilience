package com.zkylab.entity;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.data.Progress;
import com.zkylab.object.OBJ_Cloud_Concern;

public class NPC_Chakara extends Entity {
    public static final String npcName = "Chakara";

    public NPC_Chakara(GamePanel gamePanel) {
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

        if (Progress.cutsceneWarningFinished) {
            sleep = false;
        } else {
            sleep = true;
        }

        onPath = false;

        getImage();
        setDialogue();

    }

    public void getImage() {
        avatar = setup("/npc/chakara_base_avatar", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/chakara_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/chakara_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/chakara_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/chakara_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/chakara_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/chakara_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/chakara_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/chakara_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Hei, kamu! Punya waktu sebentar?";
        dialogues[0][1] = "Sumber air minum kita hancur karena puing-puing\nini.";
        dialogues[0][2] = "Kalau kamu ingin tahu kenapa aku tampak haus,\nya, ini dia! Haha!";
        dialogues[0][3] = "Berkat puing-puing ini, sekarang air minum kami\ntercemar.";
        dialogues[0][4] = "Bisakah kau membantuku? Ada truk besar di sana,\nmungkin mereka sedang membagikan bantuan\nmakanan dan minuman.";
        dialogues[0][5] = "Aku sangat haus, tenggorokanku kering setelah\nmenghirup debu reruntuhan. (uhuk uhuk).";
        dialogues[1][0] = "(uhuk uhuk).";
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

    public void speak() {
        // remove cloud
        if (Progress.cutsceneWaterFinished) {
            for (int i = 0; i < gamePanel.obj.length; i++) {
                if (gamePanel.obj[gamePanel.currentMap][i] != null
                        && gamePanel.obj[gamePanel.currentMap][i].name.equals(OBJ_Cloud_Concern.objName)) {
                    gamePanel.obj[gamePanel.currentMap][i] = null;
                    break;
                }
            }
        }
        facePlayer();
        startDialogue(this, dialogueSet);
        dialogueSet++;
        if (dialogues[dialogueSet][0] == null) {
            // dialogueSet = 0; // Dialogue will be replayed again
            dialogueSet--; // Dialogue will be stuck in the end state
        }
    }
}
