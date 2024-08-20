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
        up1 = setup("/player/mc_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/player/mc_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/player/mc_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/player/mc_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/player/mc_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/player/mc_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/player/mc_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/player/mc_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogues() {
        dialogues[0][0] = "Ada orang yang terlihat kebingungan.";
        dialogues[0][1] = "Bicaralah padanya.";
        dialogues[0][2] = "Atau abaikan saja.";
    }
    
}
