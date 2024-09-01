package com.zkylab.entity;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.data.Progress;

public class NPC_Lestari extends Entity {
    public static final String npcName = "Lestari";

    public NPC_Lestari(GamePanel gamePanel) {
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
        avatar = setup("/npc/lestari_base_avatar", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/lestari_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/lestari_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/lestari_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/lestari_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/lestari_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/lestari_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/lestari_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/lestari_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Oh, hai! Senang melihat ada yang datang\nuntuk memastikan keadaan kami.";
        dialogues[0][1] = "Desa ini mungkin kecil, tapi kami\npunya semangat yang besar!";
        dialogues[1][0] = "Gempa tadi cukup kuat, tapi syukurlah\nrumah-rumah di sini masih berdiri kokoh.";
        dialogues[1][1] = "Orang-orang desa ini sudah terbiasa\nmenghadapi tantangan alam.";
        dialogues[2][0] = "Tentu saja, ada beberapa kerusakan kecil,\nseperti genteng yang jatuh dan dinding retak,\ntapi itu sudah biasa bagi kami.";
        dialogues[3][0] = "Hal yang paling mengkhawatirkan\nadalah sumur di desa. Kadang-kadang\ngempa bisa mengubah jalur air bawah tanah.";
        dialogues[4][0] = "Kami harus memeriksa sumur untuk\nmemastikan airnya tetap bersih dan aman\nuntuk diminum.";
        dialogues[5][0] = "Selain itu, kami juga harus menjaga\npersediaan makanan. Jika ada ladang yang\nrusak, kami perlu bantuan untuk memulihkannya.";
        dialogues[6][0] = "Tapi jangan khawatir, dengan kerja sama,\nkami pasti bisa mengatasi semua ini.\nDesa ini sudah melalui banyak hal, dan\nkami selalu bangkit kembali.";
        dialogues[7][0] = "Jika kau punya waktu, mungkin bisa\nmembantu kami memperbaiki beberapa rumah\natau memeriksa sumur. Setiap bantuan sangat\nberharga bagi kami.";
        dialogues[8][0] = "Terima kasih sudah datang. Kau selalu\nselamat datang di desa ini. Kami mungkin\ndesa kecil, tapi hati kami besar!";
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
