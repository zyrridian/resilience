package com.zkylab.entity;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.data.Progress;

public class NPC_Bayu extends Entity {
    
    public static final String npcName = "Bayu";

    public NPC_Bayu(GamePanel gamePanel) {
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
        avatar = setup("/npc/bayu_base_avatar", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/bayu_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/bayu_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/bayu_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/bayu_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/bayu_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/bayu_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/bayu_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/bayu_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Oh, kau datang untuk memeriksa keadaanku?";
        dialogues[0][1] = "Jangan khawatir, aku sudah terbiasa\nhidup di gunung berapi ini.";
        dialogues[1][0] = "Lucu, ya? Gempa kali ini bukan dari\nsi gunung tua ini, tapi dari tempat lain.";
        dialogues[1][1] = "Biasanya, aku mengaitkan getaran tanah\ndengan letusan, tapi kali ini berbeda.";
        dialogues[2][0] = "Banyak orang takut tinggal di dekat gunung berapi,\ntapi tanah di sini sangat subur, sempurna\nuntuk bertani.";
        dialogues[3][0] = "Gempa ini membuat beberapa jalur air\ntergeser. Aku harus mencari sumber air baru\nuntuk mengairi sawahku.";
        dialogues[4][0] = "Tapi, tahu tidak? Gempa ini mengingatkan kita\nbahwa bukan hanya gunung yang bisa\nmenyebabkan masalah. Alam punya banyak\ncara untuk menguji kita.";
        dialogues[5][0] = "Sejauh ini, ladangku baik-baik saja,\ntapi aku harus terus mengawasi,\nsiapa tahu ada tanah longsor kecil setelah ini.";
        dialogues[6][0] = "Jika kau membutuhkan hasil bumi,\naku punya beberapa hasil panen yang siap\ndiambil. Tidak peduli seberapa besar\ngempanya, hidup harus terus berjalan!";
        dialogues[7][0] = "Jangan ragu untuk datang kembali kalau\nbutuh bantuan atau hanya ingin berbicara.";
        dialogues[7][1] = "Dan ingat, gunung ini mungkin terlihat tenang,\ntapi dia selalu mengawasi!";
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
