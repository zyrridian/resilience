package com.zkylab.tile_interactive;

import com.zkylab.common.GamePanel;

public class IT_StairRight extends InteractiveTile {

    GamePanel gamePanel;

    public IT_StairRight(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);

        this.gamePanel = gamePanel;
        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        down1 = setup("/tiles_interactive/stairs_right_1", gamePanel.tileSize, gamePanel.tileSize);
        // down2 = setup("/tiles_interactive/stairs_right_2.png", gamePanel.tileSize, gamePanel.tileSize);

        solidArea.x = 36;
        solidArea.y = 0;
        solidArea.width = 12;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

}