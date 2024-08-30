package com.zkylab.entity;

import com.zkylab.common.GamePanel;

public class PlayerDummy extends Entity {

    public static final String npcName = "Dummy";

    public PlayerDummy(GamePanel gamePanel) {
        super(gamePanel);

        name = npcName;
        getImage();
        setDialogues();
    }

    public void getImage() {
        up1 = setup("/player/arjuna_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/player/arjuna_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/player/arjuna_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/player/arjuna_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/player/arjuna_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/player/arjuna_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/player/arjuna_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/player/arjuna_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogues() {
        dialogues[0][0] = "(menghela nafas)";
        dialogues[0][1] = "Phew...";
        dialogues[0][2] = "Selamat.";
        dialogues[0][3] = "Rumahnya masih utuh, dan kau... kau juga masih utuh.";
        dialogues[0][4] = "(melihat tangan yang mulai kehijauan)";
        dialogues[0][5] = "Hmm, atau mungkin tidak terlalu utuh...";
        dialogues[0][6] = "Sepertinya kau mulai berubah jadi zombie?";
        dialogues[0][7] = "Yah, setidaknya kau sudah siap untuk pesta Halloween lebih\nawal.";
        dialogues[1][0] = "Kamu mendengar seseorang yang sedang cemas.";
        dialogues[2][0] = "Sepertinya dia butuh bantuan. Bicaralah padanya.";
    }

}
