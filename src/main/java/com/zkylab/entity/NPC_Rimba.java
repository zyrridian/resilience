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
        direction = "right";
        speed = 1;
        solidArea.x = 9;
        solidArea.y = 18;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        dialogueSet = 0;

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
        dialogues[0][0] = "!";
        dialogues[1][0] = "Wah, akhirnya keluar juga!";
        dialogues[2][0] = "Kupikir kau sudah berubah jadi tanaman pot\nkarena warna hijau itu.";
        dialogues[2][1] = "Jangan sampai aku harus menyiram dan merawatmu\nseperti pohon di kebun belakang!";
        dialogues[2][2] = "Tapi lihatlah, Nak. Seluruh kota berantakan,\nsementara rumahmu cuma bergoyang sedikit.\nMungkin rumahmu punya jimat anti-gempa... atau\nbisa jadi efek samping dari obat yang kau minum.";
        dialogues[2][3] = "Namun, aku tahu satu hal pasti, ini bukan gempa\nbiasa. Ada sesuatu yang lebih besar, lebih\nmengerikan di bawah sana... sesuatu yang tak\nbisa kita lihat dengan mata telanjang.";
        dialogues[2][4] = "Terkadang yang terlihat di permukaan hanyalah\nawal dari bencana yang lebih besar.";
        dialogues[2][5] = "Ah, aku pasti terdengar seperti orang tua yang\nkebanyakan nonton film horor.";
        dialogues[2][6] = "Jangan khawatir. Selama kau masih bisa berdiri,\nitu tandanya kau masih kuat. Kau tidak akan\nmenjadi zombie! Mungkin...";
        dialogues[2][7] = "Pergilah, Nak. Jalani perjalananmu dengan\nhati-hati... dan kalau bisa, jangan tersandung\ndi jalan, ya? Aku tidak punya cukup plester\nuntuk menambal luka di tubuhmu.";
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
        if (Progress.cutsceneWarningFinished) {
            dialogueSet = 1;
        } else {
            dialogueSet++;
            if (dialogues[dialogueSet][0] == null) {
                // dialogueSet = 0; // Dialogue will be replayed again
                dialogueSet--; // Dialogue will be stuck in the end state
            }
        }
    }
}
