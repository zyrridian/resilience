package com.zkylab.entity;

import com.zkylab.common.GamePanel;

public class NPC_Professor extends Entity {

    public static final String npcName = "Professor";

    public NPC_Professor(GamePanel gamePanel) {
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
        avatar = setup("/npc/avatar_professor", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/npc/professor_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/professor_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/professor_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/professor_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/professor_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/professor_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/professor_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/professor_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Halo! Aku professor di kota ini.";
        dialogues[0][1] = "Dan aku juga seorang ahli teknologi, serta\ndokter.";
        dialogues[0][2] = "Senang bertemu denganmu.";
        dialogues[1][0] = "Apa kau dapat melihat gedung besar di kota?";
        dialogues[1][1] = "Itu adalah pusat teknologi tempatku bekerja\ndan meneliti.";
        dialogues[1][2] = "Tidak sembarang orang bisa masuk ke sana.";
        dialogues[1][3] = "Termasuk kamu juga. Maaf.";
        dialogues[2][0] = "Teknologi medis telah berkembang belakangan ini.";
        dialogues[2][1] = "Dengan AI, kita bisa mendiagnosis penyakit\nlebih cepat dan akurat.";
        dialogues[2][2] = "Misalnya, AI dapat mendeteksi kanker lebih awal\ndari manusia.";
        dialogues[2][3] = "Ini memberi pasien kesempatan yang lebih besar\nuntuk sembuh.";
        dialogues[3][0] = "Aku telah menciptakan robot bedah untuk\nmelakukan operasi secara presisi.";
        dialogues[3][1] = "Robot ini dikendalikan oleh ahli bedah, tetapi\nAI membantu memastikan setiap gerakannya tepat.";
        dialogues[3][2] = "Hasilnya, pasien mengalami pemulihan yang lebih\ncepat dan risiko komplikasi yang lebih rendah.";
        dialogues[3][3] = "Ini benar-benar revolusi dalam dunia medis.";
        dialogues[4][0] = "Hmm?";
        dialogues[4][1] = "Kau ingin mengetahui penemuanku yang lainnya?";
        dialogues[4][2] = "Tentu saja, aku akan memberitahumu.";
        dialogues[4][3] = "Maaf, ingatanku tidak begitu baik.";
        dialogues[4][4] = "...";
        dialogues[4][5] = "Oh! Ini dia. Aku telah memanfaatkan AI untuk\npengobatan personal dalam dunia medis.";
        dialogues[4][6] = "AI menganalisis data genetik pasien untuk\nmenentukan pengobatan yang paling efektif.";
        dialogues[4][7] = "Ini berarti setiap pasien mendapatkan perawatan\nyang disesuaikan dengan kebutuhan unik mereka.";
        dialogues[4][8] = "Tidak lagi ada pendekatan 'satu ukuran untuk\nsemua'.";
        dialogues[4][9] = "Tapi tentu saja, dibutuhkan seorang dokter\nprofessional untuk mengawasi penggunaan\npenemuanku.";
        dialogues[5][0] = "Haha. Sepertinya kamu tidak pernah lelah\nmendengarkan ceritaku. Aku menyukai\nkeingintahuan masa mudamu.";
        dialogues[5][1] = "Coba aku pikirkan...";
        dialogues[5][2] = "Ah... Telemidisin.";
        dialogues[5][3] = "Dengan bantuan AI, dokter dapat memantau\nkondisi pasien dari jarak jauh.";
        dialogues[5][4] = "Ini sangat berguna terutama di daerah terpencil.";
        dialogues[5][5] = "Pasien dapat menerima perawatan yang mereka\nbutuhkan tanpa harus melakukan perjalanan jauh.";
        dialogues[6][0] = "Bahkan, AI digunakan dalam pengembangan obat\nbaru.";
        dialogues[6][1] = "AI dapat memprediksi bagaimana obat baru akan\nbekerja sebelum uji klinis dilakukan.";
        dialogues[6][2] = "Ini mempercepat proses penelitian dan membawa\nobat baru ke pasar lebih cepat.";
        dialogues[6][3] = "Banyak nyawa yang bisa diselamatkan dengan cara\nini.";

        // dialogues[1][0] = "Aku penasaran dengan hutan di timur...";

        // dialogues[2][0] = "Beberapa orang pernah pergi ke sana";
        // dialogues[2][1] = "Namun belum pernah kembali.";

        // dialogues[3][0] = "Berhati-hatilah.";
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
