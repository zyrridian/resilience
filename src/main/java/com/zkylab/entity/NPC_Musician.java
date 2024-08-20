package com.zkylab.entity;

import com.zkylab.common.GamePanel;

public class NPC_Musician extends Entity {

    public static final String npcName = "Musician";

    public NPC_Musician(GamePanel gamePanel) {
        super(gamePanel);

        name = npcName;
        direction = "right";
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
        avatar = setup("/npc/avatar_musician", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/musician_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/musician_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/musician_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/musician_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/musician_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/musician_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/musician_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/musician_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Aku sedang memikirkan konsep untuk konser\nmusikku 2 bulan lagi.";
        dialogues[0][1] = "Apa kau ingin datang?";
        dialogues[0][2] = "Tenang saja!";
        dialogues[0][3] = "Aku akan memberikanmu tiket gratis.";
        dialogues[1][0] = "Hey, apa kau paham mengenai AI?";
        dialogues[1][1] = "Aku tidak begitu mengerti, tapi gadis seniman\nitu memberitahuku mengenai hal ini.";
        dialogues[1][2] = "Dia menunjukkan padaku sebuah lagu, yang\nsepenuhnya dibuat oleh AI.";
        dialogues[1][3] = "Lagu itu memiliki vokal, bahkan aransemen musik\nyang beragam.";
        dialogues[1][4] = "Bahkan suara nyanyiannya bisa kita ganti\nmenggunakan suara tokoh terkenal.";
        dialogues[1][5] = "Walaupun belum sempurna, namun itu cukup\nmenyenangkan untuk hiburan.";
        dialogues[1][6] = "Aku akan menggunakan model AI suara presiden\nkita untuk menyanyikan laguku, dan\nmenunjukkannya kepada Librarian!";
        dialogues[1][7] = "Hahaha.";
        dialogues[1][8] = "Dia pasti tertipu dan menganggap itu nyata.";
        dialogues[2][0] = "Kemarin aku menggunakan AI mengoptimalkan\nkualitas suara dan mixing.";
        dialogues[2][1] = "Tidak sempurna, namun cukup membantu untuk\nmenghemat waktu.";
        dialogues[2][2] = "Dan terkadang, aku cukup ceroboh dan sering\nmelupakan detail kecil.";
        dialogues[2][3] = "AI membantuku menemukan kesalahan tersebut.";
        dialogues[3][0] = "Dari yang aku perhatikan, gadis seniman itu\ncukup skeptis bahwa AI dapat menggantikan pekerjaannya.";
        dialogues[3][1] = "Tapi bagiku sebagai seorang musisi, aku cukup\npercaya diri.";
        dialogues[3][2] = "AI tidak akan menggantikan pekerjaanku.";
        dialogues[3][3] = "Bayangkan saja orang lebih memilih menonton\nkonser musik AI daripada konser musikku.";
        dialogues[3][4] = "Itu adalah hal yang mustahil, dan sangat konyol\njika benar-benar terjadi.";
        dialogues[4][0] = "Hmm?";
        dialogues[4][1] = "Apa maksudmu?";
        dialogues[4][2] = "Jangan mudah percaya perkataan gadis seniman?";
        dialogues[4][3] = "Kenapa? Kelihatannya dia cukup bisa dipercaya.";
        dialogues[4][4] = "Apa mungkin aku tidak menyadari sesuatu?";
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
            dialogueSet = 0; // Dialogue will be replayed again
            // dialogueSet--; // Dialogue will be stuck in the end state
        }
    }
    
}
