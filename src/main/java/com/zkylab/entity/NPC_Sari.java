package com.zkylab.entity;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.data.Progress;

public class NPC_Sari extends Entity {
    public static final String npcName = "Sari";

    public NPC_Sari(GamePanel gamePanel) {
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
        avatar = setup("/npc/sari_base_avatar", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/sari_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/sari_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/sari_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/sari_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/sari_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/sari_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/sari_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/sari_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Oh, kau di sini akhirnya.";
        dialogues[0][1] = "Lihatlah, barangku jatuh ke dalam patahan jalan itu.";
        dialogues[1][0] = "Aku tahu ini berbahaya, tapi barang itu\nterlalu berharga untuk ditinggalkan begitu saja.";
        dialogues[1][1] = "Dan aku tidak akan membiarkannya hilang\nbegitu saja, meskipun harus mengambil risiko.";
        dialogues[2][0] = "Namun, aku bukan orang bodoh yang\nmelompat ke dalam tanpa pikir panjang.";
        dialogues[2][1] = "Kau, bantu aku mengambilnya. Aku akan\nmemberimu sesuatu yang setimpal.";
        dialogues[3][0] = "Dan tolong, lakukan ini dengan hati-hati.\nAku tidak punya waktu untuk berurusan dengan\nlebih banyak masalah.";
        dialogues[4][0] = "Aku menghargai keberanianmu, tapi ingat,\nsatu langkah salah dan kau akan terjebak di sana.";
        dialogues[5][0] = "Baiklah, cepat selesaikan tugas ini, dan\nkita bisa kembali ke urusan masing-masing.";
        dialogues[6][0] = "Oh, jangan kira aku tidak tahu betapa\nberbahayanya ini. Tapi ada hal-hal yang\ntidak bisa dikorbankan, bahkan dalam situasi seperti ini.";
        dialogues[7][0] = "Terima kasih, kau benar-benar bisa diandalkan.\nMungkin aku akan mempertimbangkan untuk\nmempercayakan tugas penting lain padamu.";
        dialogues[8][0] = "Sekarang, pergilah. Aku punya hal lain yang\nharus dilakukan, dan waktu adalah uang, kau tahu.";
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
