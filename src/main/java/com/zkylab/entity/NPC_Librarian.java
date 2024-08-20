package com.zkylab.entity;

import com.zkylab.common.GamePanel;

public class NPC_Librarian extends Entity {

    public static final String npcName = "Librarian";

    public NPC_Librarian(GamePanel gamePanel) {
        super(gamePanel);

        name = npcName;
        direction = "down";
        speed = 0;

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
        avatar = setup("/npc/avatar_librarian", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/librarian_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/librarian_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/librarian_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/librarian_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/librarian_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/librarian_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/librarian_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/librarian_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Selamat datang di Perpustakaan Kota. Apa yang\nbisa saya bantu?";
        dialogues[0][1] = "Hm? Jadi kamu mencari 'Buku Inspirasi Teknologi'\ndan 'Pena Kreativitas'?";
        dialogues[0][2] = "Barang-barang itu tersebar di seluruh\nperpustakaan.";
        dialogues[0][3] = "Tapi aku lupa dimana menyimpannya.";
        dialogues[0][4] = "Apa kamu bisa mencarinya sendiri?";
        dialogues[0][5] = "...";
        dialogues[0][6] = "...";
        dialogues[0][7] = "Kenapa kamu masih berdiri di sana?";
        dialogues[0][8] = "Ah! Jadi kamu tidak sedang mencari pena\nkreativitas dan juga satu barang lainnya yang\naku sangat malas menyebutkan karena namanya\nterlalu panjang namun sekarang aku terlalu-";
        dialogues[0][9] = "...";
        dialogues[0][10] = "Maaf jika aku mengganggumu.";
        dialogues[1][0] = "AI?";
        dialogues[1][1] = "Tentu saja aku tahu, aku tidak bodoh.";
        dialogues[1][2] = "Itu sebabnya aku bekerja di perpustakaan-";
        dialogues[1][3] = "...";
        dialogues[1][4] = "Maaf jika kata-kataku tadi memiliki kesan\nnegatif.";
        dialogues[1][5] = "Aku tidak bermaksud seperti itu.";
        dialogues[2][0] = "Apa kau tahu? Profesor sedang mengembangkan\nsebuah komputer kuantum yang dapat bekerja\njauh lebih cepat daripada komputer\nkonvensional.";
        dialogues[2][1] = "Komputer ini menggunakan konsep mekanika\nkuantum, dimana partikel bisa berada di dua\ntempat sekaligus.";
        dialogues[2][2] = "Jika hal ini berhasil, maka teknologi AI akan\nsemakin berkembang.";
        dialogues[2][3] = "Dan dunia medis pun akan terkena dampaknya,\nmembawa perubahan besar dalam diagnosis dan\nperawatan.";
        dialogues[3][0] = "Silahkan baca buku di sana untuk menambah\nwawasanmu.";
        dialogues[3][1] = "Berhentilah berbicara denganku.";
        dialogues[4][0] = "Silahkan baca buku di rak.";
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

            // actionLockCounter++;

            // if (actionLockCounter == 120) { // Giving delay 2 second every movement

            //     Random random = new Random();
            //     int i = random.nextInt(100) + 1; // pick up a number from 1 to 100
        
            //     if (i <= 25) direction = "up";
            //     if (i > 25 && i <= 50) direction = "down";
            //     if (i > 50 && i <= 75) direction = "left";
            //     if (i > 75 && i <= 100) direction = "right";
                
            //     actionLockCounter = 0;
                
            // }

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
