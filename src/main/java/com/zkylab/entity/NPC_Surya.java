package com.zkylab.entity;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.data.Progress;

public class NPC_Surya extends Entity {
    public static final String npcName = "Surya";

    public NPC_Surya(GamePanel gamePanel) {
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

        dialogueSet = -1;

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
        avatar = setup("/npc/surya_base_avatar", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/surya_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/surya_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/surya_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/surya_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/surya_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/surya_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/surya_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/surya_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Wah, kau harus melihat ini!";
        dialogues[0][1] = "Mobilku! Ini benar-benar hancur berantakan.";
        dialogues[1][0] = "Aku tahu gempa ini kuat, tapi siapa sangka\nmobilku bisa jadi korban begini.";
        dialogues[1][1] = "Hei, setidaknya ini jadi cerita yang menarik\nuntuk dibagikan nanti, kan?";
        dialogues[2][0] = "Aku selalu bilang, 'hidup penuh kejutan',\ntapi ini benar-benar di luar dugaanku.";
        dialogues[2][1] = "Kau tahu, aku sebenarnya cukup terikat\ndengan mobil ini. Tapi ya, apa boleh buat.";
        dialogues[3][0] = "Sekarang aku harus cari cara untuk\nkeluar dari sini tanpa mobil. Mungkin kau\npunya ide?";
        dialogues[4][0] = "Oh, hampir lupa! Jika kau butuh tumpangan,\nsepertinya kita sama-sama harus mencari\nalternatif karena... yah, lihatlah keadaan ini.";
        dialogues[5][0] = "Sial, aku baru saja mengganti bannya\ntiga hari yang lalu. Benar-benar waktu yang\ntidak tepat.";
        dialogues[6][0] = "Tapi, hei, aku tidak akan biarkan ini\nmerusak semangatku! Kita masih bisa\nbersenang-senang, kan?";
        dialogues[7][0] = "Jika kau butuh bantuan, aku siap\ndengan tangan terbuka. Kita harus tetap\nbersama dalam situasi seperti ini!";
        dialogues[8][0] = "Baiklah, ayo kita cari cara untuk\nmenyelesaikan masalah ini. Mobil bisa diganti,\ntapi persahabatan? Itu yang terpenting.";
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
