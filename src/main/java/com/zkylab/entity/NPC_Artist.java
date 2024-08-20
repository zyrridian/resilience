package com.zkylab.entity;

import com.zkylab.common.GamePanel;

public class NPC_Artist extends Entity {

    public static final String npcName = "Artist";

    public NPC_Artist(GamePanel gamePanel) {
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
        avatar = setup("/npc/avatar_artist", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/artist_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/artist_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/artist_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/artist_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/artist_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/artist_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/artist_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/artist_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Aku tidak percaya bahwa sekarang ada AI yang\nbisa menghasilkan gambar.";
        dialogues[0][1] = "Apa gunanya seniman seperti aku jika teknologi\nbisa melakukan semuanya?";
        dialogues[0][2] = "Aku merasa kreativitasku akan tergantikan oleh\nteknologi ini.";
        dialogues[0][3] = "Aku tidak tahu bagaimana harus bersaing.";

        dialogues[1][0] = "Memanfaatkannya? Tentu saja aku sudah mencoba\nAI sebagai sumber inspirasiku.";
        dialogues[1][1] = "Tapi aku kesal karena karya yang aku unggah di\ninternet bisa saja dicuri orang lain.";
        dialogues[1][2] = "Untuk melatih model AI mereka.";
        dialogues[1][3] = "(menghela nafas)";

        dialogues[2][0] = "Belakangan ini orang-orang menyebut karya yang\naku unggah merupakan hasil generate AI.";
        dialogues[2][1] = "Itu menunjukkan bahwa gambar buatanku sangat\nbagus.";
        dialogues[2][2] = "Haha! Aku memang seniman yang hebat.";

        dialogues[3][0] = "AI bisa membuat karya seni yang sempurna.";
        dialogues[3][1] = "Namun, kesempurnaan itu membosankan.";
        dialogues[3][2] = "Seni sejati ada di ketidaksempurnaan manusia.";

        dialogues[4][0] = "Aku bisa mengenali gambar buatan AI dalam\nsekejap mata.";
        dialogues[4][1] = "Kelemahan mereka adalah ketika membuat gambar\njari pada sebuah karakter.";
        dialogues[4][2] = "Mereka seperti anak kecil yang baru belajar\nmenggambar tangan.";
        dialogues[4][3] = "Itu sebabnya aku lebih hebat dari AI.";

        dialogues[5][0] = "Kadang aku merasa dunia seni sudah terlalu\nbergantung pada teknologi.";
        dialogues[5][1] = "Tapi aku juga penasaran sampai mana batasan\nkreativitas manusia bisa ditantang.";
        dialogues[5][2] = "Mungkin di sinilah letak keindahannya.";

        dialogues[6][0] = "Mungkin di masa depan, manusia dan AI bisa\nberkolaborasi untuk menciptakan sesuatu yang\nlebih besar.";
        dialogues[6][1] = "Bayangkan kombinasi antara keahlian kita dan\nkecepatan mereka.";
        dialogues[6][2] = "Itu akan menjadi sesuatu yang luar biasa.";

        dialogues[7][0] = "Tetapi, aku selalu ingat bahwa seni adalah\ntentang jiwa.";
        dialogues[7][1] = "Dan sejauh apapun teknologi berkembang, AI\ntidak akan pernah memiliki jiwa.";
        dialogues[7][2] = "Itu adalah kekuatan terbesarku.";
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

            // Random random = new Random();
            // int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

            // if (i <= 25) direction = "up";
            // if (i > 25 && i <= 50) direction = "down";
            // if (i > 50 && i <= 75) direction = "left";
            // if (i > 75 && i <= 100) direction = "right";

            // actionLockCounter = 0;

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
