package com.zkylab.entity;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.data.Progress;

public class NPC_Bima extends Entity {
    public static final String npcName = "Bima";

    public NPC_Bima(GamePanel gamePanel) {
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
        avatar = setup("/npc/bima_base_avatar", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/bima_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/bima_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/bima_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/bima_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/bima_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/bima_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/bima_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/bima_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Hey, kau! Jangan mendekat terlalu dekat.";
        dialogues[0][1] = "Kami sedang memeriksa gedung ini untuk\nmemastikan tidak ada yang terjebak di dalam.";
        dialogues[1][0] = "Gempa ini cukup parah, beberapa bagian\ndari gedung bisa runtuh kapan saja.";
        dialogues[1][1] = "Kami harus sangat berhati-hati, satu langkah\nsalah bisa berakibat fatal.";
        dialogues[2][0] = "Kalau kau melihat ada tanda-tanda\nkehidupan di dalam reruntuhan, segera laporkan!";
        dialogues[2][1] = "Setiap detik sangat berharga untuk menyelamatkan nyawa.";
        dialogues[3][0] = "Kami dilatih untuk menghadapi situasi seperti ini,\ntapi tetap saja, melihat kehancuran ini\nselalu berat di hati.";
        dialogues[4][0] = "Jangan khawatir, kami sudah terbiasa\nbekerja dalam kondisi berbahaya.";
        dialogues[4][1] = "Tapi kau, lebih baik tetap di luar\narea berbahaya, biarkan kami yang menangani.";
        dialogues[5][0] = "Periksa sekitar sini, kadang-kadang\nada orang yang mencoba mencari barang\nmereka di dalam gedung yang rusak.";
        dialogues[5][1] = "Kami harus memastikan mereka tidak\nmemasuki area yang terlalu berbahaya.";
        dialogues[6][0] = "Jika kau punya masker atau sarung tangan,\nbawa untuk kami. Debu dan puing di sini\nbisa sangat berbahaya.";
        dialogues[7][0] = "Baiklah, kembali bekerja. Semoga kita\nbisa menemukan semua orang yang mungkin\nmasih terjebak.";
        dialogues[7][1] = "Tetap waspada dan jangan abaikan\nkeselamatanmu sendiri!";
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
