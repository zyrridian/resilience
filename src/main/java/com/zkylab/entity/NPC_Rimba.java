package com.zkylab.entity;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.data.Progress;

public class NPC_Rimba extends Entity {
    public static final String npcName = "Rimba";

    public NPC_Rimba(GamePanel gamePanel) {
        super(gamePanel);

        name = npcName;
        isNPC = true;
        direction = "left";
        speed = 1;
        solidArea.x = 9;
        solidArea.y = 18;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        dialogueSet = 0;

        // if (Progress.cutsceneWarningFinished) {
        //     sleep = false;
        // } else {
            sleep = true;
        // }

        onPath = false;

        getImage();
        setDialogue();

    }
    

    public void getImage() {
        avatar = setup("/npc/rimba_base_avatar", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/rimba_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/rimba_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/rimba_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/rimba_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/rimba_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/rimba_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/rimba_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/rimba_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Hei, kau!";
        dialogues[0][1] = "Aku sedang memeriksa kondisi pohon-pohon\ndi sekitar sini setelah gempa.";
        dialogues[0][2] = "Tahu tidak? Pohon-pohon ini mungkin lebih\nkaget daripada kita!";
        dialogues[1][0] = "Beberapa pohon besar terlihat miring,\nkita harus memastikan mereka tidak roboh.";
        dialogues[1][1] = "Oh, dan hati-hati dengan ranting jatuh,\nmereka mungkin lebih berbahaya daripada\nmakan siang yang kau lewatkan!";
        dialogues[2][0] = "Kau butuh bantuan? Coba carikan aku\nbeberapa benih pohon, kita mungkin perlu\nmenanam beberapa lagi.";
        dialogues[2][1] = "Ingat, pohon adalah penyelamat kita.\nTanpa mereka, kita sudah tersapu angin kencang!";
        dialogues[3][0] = "Hutan ini mungkin rusak, tapi dengan\nsedikit usaha, kita bisa membuatnya\nlebih kuat dari sebelumnya.";
        dialogues[4][0] = "Baiklah, aku kembali ke pekerjaanku.\nPastikan tidak ada yang menebang pohon\ndengan sembarangan!";
        dialogues[5][0] = "Oh, hampir lupa! Jika kau melihat\nhewan liar yang kebingungan, coba laporkan\nke petugas setempat.";
        dialogues[6][0] = "Kau punya peta? Mungkin ada baiknya\ndiperiksa kembali, jalan setapak mungkin\ntelah berubah.";
        dialogues[7][0] = "Jangan lupa, hutan ini juga rumah kita.\nMari jaga bersama!";
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
        facePlayer();
        startDialogue(this, dialogueSet);
        dialogueSet++;
        if (dialogues[dialogueSet][0] == null) {
            // dialogueSet = 0; // Dialogue will be replayed again
            dialogueSet--; // Dialogue will be stuck in the end state
        }
    }
}
