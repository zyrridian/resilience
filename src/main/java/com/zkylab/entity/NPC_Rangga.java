package com.zkylab.entity;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.data.Progress;

public class NPC_Rangga extends Entity {
    public static final String npcName = "Rangga";

    public NPC_Rangga(GamePanel gamePanel) {
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
        avatar = setup("/npc/rangga_base_avatar", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/rangga_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/rangga_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/rangga_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/rangga_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/rangga_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/rangga_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/rangga_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/rangga_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Ah, kau datang tepat waktu!";
        dialogues[0][1] = "Aku senang melihat semua orang bekerja\ndengan baik setelah gempa ini.";
        dialogues[1][0] = "Meskipun ini adalah masa yang sulit,\nkita tidak boleh kehilangan fokus.";
        dialogues[1][1] = "Keamanan dan kesejahteraan semua orang\nadalah prioritas utama kita.";
        dialogues[2][0] = "Aku sudah menginstruksikan tim untuk\nmemeriksa seluruh bangunan, memastikan\nsemua struktur aman.";
        dialogues[2][1] = "Jika ada area yang terlihat tidak aman,\nsegera laporkan, kita akan menanganinya\nsecepat mungkin.";
        dialogues[3][0] = "Kita mungkin telah mengalami guncangan,\ntapi itu tidak boleh menghentikan langkah kita.";
        dialogues[3][1] = "Kita akan melewati ini bersama-sama,\nseperti yang selalu kita lakukan.";
        dialogues[4][0] = "Ingat, aku selalu terbuka untuk mendengar\nkekhawatiran atau ide dari kalian semua.";
        dialogues[4][1] = "Tidak ada yang lebih penting dari\nkeselamatan dan kolaborasi di sini.";
        dialogues[5][0] = "Oh, dan satu hal lagi, pastikan semua\npegawai telah dievakuasi dengan aman\nsebelum kita mulai bekerja kembali.";
        dialogues[6][0] = "Jika ada yang membutuhkan bantuan,\nbaik dalam pekerjaan atau hal lain, jangan ragu\nuntuk menyampaikan. Kita adalah satu tim.";
        dialogues[7][0] = "Sekarang, mari kita lanjutkan. Ada banyak\nhal yang perlu dilakukan, dan aku tahu kita\nbisa melakukannya dengan baik.";
        dialogues[8][0] = "Terima kasih atas kerja kerasmu.\nKita semua akan bangkit lebih kuat dari ini!";
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
