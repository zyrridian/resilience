package com.zkylab.entity;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.data.Progress;

public class NPC_Bumi extends Entity {
    public static final String npcName = "Bumi";

    public NPC_Bumi(GamePanel gamePanel) {
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
        avatar = setup("/npc/bumi_base_avatar", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/bumi_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/bumi_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/bumi_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/bumi_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/bumi_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/bumi_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/bumi_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/bumi_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Oh, kau datang pada waktu yang tepat!";
        dialogues[0][1] = "Aku sedang memeriksa retakan pada batuan\ndi sekitar sini.";
        dialogues[0][2] = "Gempa ini cukup kuat untuk memecah batuan,\ndan retakan ini bisa memberi tahu kita banyak hal.";
        dialogues[1][0] = "Tahukah kau? Retakan pada batuan bisa\nmemberi kita petunjuk tentang arah\npergerakan gempa.";
        dialogues[1][1] = "Ini seperti jejak kaki raksasa di tanah,\nsetiap retakan memiliki cerita tersendiri.";
        dialogues[2][0] = "Jika kau menemukan batu yang terlihat\nretak atau aneh, bawa padaku.\nAku ingin memeriksanya lebih lanjut.";
        dialogues[2][1] = "Batuan ini mungkin terlihat keras,\ntapi mereka juga bisa rapuh setelah gempa.";
        dialogues[3][0] = "Aku juga menemukan beberapa mineral baru\ndi sekitar sini, mungkin ini kesempatan baik\nuntuk belajar lebih banyak tentang bumi kita.";
        dialogues[4][0] = "Ingat, gempa bukan hanya tentang\ngetaran di tanah, tapi juga bagaimana\nbatuan meresponsnya.";
        dialogues[4][1] = "Sebenarnya, kita semua adalah bagian\ndari sejarah geologi yang sedang terjadi.";
        dialogues[5][0] = "Jika kau tertarik, aku bisa mengajarkanmu\nsedikit tentang bagaimana membaca batuan.";
        dialogues[5][1] = "Ini bukan hanya ilmu, tapi juga seni\nmembaca alam.";
        dialogues[6][0] = "Baiklah, aku harus kembali bekerja.\nIngat, setiap batu punya cerita yang harus\ndipahami!";
        dialogues[6][1] = "Sampai jumpa, dan tetap berhati-hati di luar\nsana!";
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
