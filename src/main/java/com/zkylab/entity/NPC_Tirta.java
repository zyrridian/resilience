package com.zkylab.entity;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.data.Progress;

public class NPC_Tirta extends Entity {
    public static final String npcName = "Tirta";

    public NPC_Tirta(GamePanel gamePanel) {
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
        avatar = setup("/npc/tirta_base_avatar", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/tirta_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/tirta_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/tirta_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/tirta_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/tirta_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/tirta_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/tirta_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/tirta_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Oh, hai!";
        dialogues[0][1] = "Aku sedang memeriksa kondisi air di daerah ini.";
        dialogues[0][2] = "Gempa ini benar-benar mengubah jalur air,\nkita harus hati-hati!";
        dialogues[0][3] = "Hmm, siapa sangka air bisa lebih berbahaya\ndari gempa itu sendiri?";
        dialogues[1][0] = "Tapi tenang, air ini masih aman... Sejauh ini.";
        dialogues[1][1] = "Kamu butuh air minum? Jangan khawatir,\nini dari sumber yang sudah kuperiksa.";
        dialogues[2][0] = "Oh, kau ingin tahu lebih banyak tentang air?";
        dialogues[2][1] = "Kau tahu, air bisa menjadi sahabat atau musuh\ndalam situasi seperti ini.";
        dialogues[3][0] = "Sekarang, tugasmu adalah memastikan\norang-orang tidak mengambil air dari sumber\nyang terkontaminasi.";
        dialogues[3][1] = "Dan ingat, jangan berenang di genangan air\npasca gempa, kau tidak akan tahu apa yang\nbersembunyi di bawahnya!";
        dialogues[4][0] = "Baiklah, aku harus kembali bekerja. Sampai jumpa!";
        dialogues[5][0] = "Ingat, jika kau melihat sesuatu yang aneh\ndengan air, laporkan padaku segera.";
        dialogues[6][0] = "Kau kekurangan peralatan? Mungkin\nkau bisa menemukannya di toko di seberang jalan.";
        dialogues[7][0] = "Sampai nanti, tetaplah berhati-hati!";
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
