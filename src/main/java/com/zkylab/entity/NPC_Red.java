package com.zkylab.entity;

import java.util.Random;

import com.zkylab.common.GamePanel;

public class NPC_Red extends Entity {

    public static final String npcName = "Grandmaster";

    public NPC_Red(GamePanel gamePanel) {
        super(gamePanel);

        name = npcName;
        direction = "down";
        speed = 1;

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        dialogueSet = -1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        avatar = setup("/npc/avatar_red", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/red_walk_up_3", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/red_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/red_walk_left_3", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/red_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/red_walk_down_3", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/red_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/red_walk_right_3", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/red_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Halo, Bobi.";
        dialogues[0][1] = "Selamat datang di kota kami.";
        dialogues[0][2] = "...";
        dialogues[0][3] = ".........";
        dialogues[0][4] = "Tunggu.";
        dialogues[0][5] = "Namamu bukan Bobi.";
        dialogues[0][6] = "Aku bicara padamu.";
        dialogues[0][7] = "Orang di balik layar yang baru saja menekan\ntombol [Enter] pada keyboard.";
        dialogues[0][8] = "Atau kamu yang sedang menonton seseorang\nmemainkan game ini.";

        dialogues[1][0] = "Namun jika namamu adalah Bobi, kamu pasti\nterkejut sebelumnya.";

        dialogues[2][0] = "Aku adalah Grandmaster di kota ini.";
        dialogues[2][1] = "Silahkan jelajahi kota dan berinteraksi dengan\npara NPC.";
        dialogues[2][2] = "Beberapa gedung dapat dibuka, namun sisanya\ntidak.";
        dialogues[2][3] = "Sepertinya pencipta kami belum sempat membuat\ndesain interior untuk gedung tersebut.";

        dialogues[3][0] = "Aku memang NPC seperti yang lainnya.";
        dialogues[3][1] = "Tapi hanya aku yang mengetahui bahwa dunia\nini adalah sebuah game.";
        dialogues[3][2] = "Itulah sebabnya aku disebut Grandmaster.";

        dialogues[4][0] = "Keluarkan perisai tepat sebelum musuh menyerang,\nagar mereka terkena efek knockback.";
        dialogues[4][1] = "Dan meningkatkan damage-mu selama berberapa\ndetik.";
        dialogues[4][2] = "Aku memberitahu hal ini karena...";
        dialogues[4][3] = "Di arah timur, ada banyak robot yang bisa kamu\nlawan.";
        dialogues[4][4] = "Namun mereka tidak akan pernah habis.";
        dialogues[4][5] = "Selamat datang di game tanpa akhir!";

        dialogues[5][0] = "Jika kamu berjalan terus ke arah timur, kamu\nakan menemukan sebuah robot raksasa.";
        dialogues[5][1] = "Naikkan levelmu sebelum mengalahkan dia.";
        dialogues[5][2] = "Atau eksplorasi seluruh kota untuk menemukan\nitem legendaris.";
        dialogues[5][3] = "Atau...";
        dialogues[5][4] = "Temukanlah fungsi keyboard tersembunyi dari\ngame ini.";
        dialogues[5][5] = "Kamu akan mendapatkan akses pada kekuatanku.";

        dialogues[6][0] = "Jika kamu adalah orang malas membaca, dan\nmelewati percakapanku...";
        dialogues[6][1] = "Aku akan mengulanginya kembali.";

        dialogues[7][0] = "Halo, Bobi.";
        dialogues[7][1] = "Selamat datang di kota kami.";
        dialogues[7][2] = "...";
        dialogues[7][3] = ".........";
        dialogues[7][4] = "Tunggu.";
        dialogues[7][5] = "Namamu bukan Bobi.";
        dialogues[7][6] = "Aku bicara padamu.";
        dialogues[7][7] = "Orang di balik layar yang baru saja menekan\ntombol [Enter] pada keyboard.";
        dialogues[7][8] = "Atau kamu yang sedang menonton seseorang\nmemainkan game ini.";
        dialogues[7][9] = "Dan juga...";
        dialogues[7][10] = "Yang baru saja melewatkan percakapanku.";

        dialogues[8][0] = "Baiklah.";
        dialogues[8][1] = "Kali ini aku benar-benar akan mengulang\nkalimatku.";
        dialogues[8][2] = "Termasuk bagian ini juga.";
    }

    public void setAction() {
        if (onPath) {
            // NPC path with goal
            // int goalCol = 12;
            // int goalRow = 9;
            
            // NPC path follow player
            int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
            int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;
            // if (nextCol == goalCol && nextRow == goalRow) onPath = false;

            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;
            if (actionLockCounter == 120) { // Giving delay 2 second every movement
                Random random = new Random();
                int i = random.nextInt(100) + 1; // pick up a number from 1 to 100
                if (i <= 25) direction = "up";
                if (i > 25 && i <= 50) direction = "down";
                if (i > 50 && i <= 75) direction = "left";
                if (i > 75 && i <= 100) direction = "right";
                actionLockCounter = 0;
            }
        }
    }

    public void speak() {
        facePlayer();
        startDialogue(this, dialogueSet);
        dialogueSet++;
        if (dialogues[dialogueSet][0] == null) {
            dialogueSet = 0; // Dialogue will be replayed again
            // dialogueSet--; // Dialogue will be stuck in the end state
        }
    }
}
