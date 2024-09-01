package com.zkylab.entity;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.data.Progress;

public class NPC_Genta extends Entity {
    public static final String npcName = "Genta";

    public NPC_Genta(GamePanel gamePanel) {
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
        avatar = setup("/npc/genta_base_avatar", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/genta_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/genta_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/genta_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/genta_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/genta_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/genta_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/genta_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/genta_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Kau datang tepat waktu!";
        dialogues[0][1] = "Kami sedang mencoba mengakses area basement,\nini salah satu bagian gedung yang paling rawan.";
        dialogues[1][0] = "Tantangan di sini berbeda, kami harus\nmemastikan tidak ada gas berbahaya atau\nkebocoran pipa air yang bisa membuat situasi\nlebih buruk.";
        dialogues[2][0] = "Lantai ini mungkin terlihat stabil,\ntapi kami harus memastikan tidak ada\nkerusakan struktur yang tersembunyi.";
        dialogues[2][1] = "Bangunan seperti ini bisa runtuh\nhanya karena getaran kecil.";
        dialogues[3][0] = "Kami juga harus bergerak cepat,\nkami belum tahu apakah ada orang\nterjebak di area yang sulit dijangkau.";
        dialogues[4][0] = "Setiap tim SAR punya tugas berbeda,\ndan kali ini, tugas kami adalah memastikan\nbagian terdalam gedung ini aman.";
        dialogues[4][1] = "Ini seperti mencari jarum dalam tumpukan jerami,\ntapi nyawa orang adalah taruhannya.";
        dialogues[5][0] = "Jika kau melihat ada tanda-tanda\ndinding atau lantai retak, segera beri tahu kami.";
        dialogues[5][1] = "Kami harus selalu waspada terhadap perubahan\nkecil sekalipun.";
        dialogues[6][0] = "Oh, satu lagi! Pastikan tidak ada yang\nmasuk ke area basement tanpa izin, ini\nsangat berbahaya.";
        dialogues[7][0] = "Terima kasih atas bantuanmu, kami harus\nmelanjutkan. Semoga kita bisa menemukan semua\norang yang mungkin terjebak di sini.";
        dialogues[7][1] = "Jaga keselamatanmu juga, jangan sampai\nkau yang butuh diselamatkan nanti!";
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
