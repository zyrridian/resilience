package com.zkylab.tile_interactive;

import com.zkylab.common.GamePanel;

public class IT_StairLeft extends InteractiveTile {

    GamePanel gamePanel;

    public IT_StairLeft(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);

        this.gamePanel = gamePanel;
        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        down1 = setup("/tiles_interactive/rock", gamePanel.tileSize, gamePanel.tileSize);
        // down1 = setup("/tiles_interactive/stairs_left_2.png", gamePanel.tileSize, gamePanel.tileSize);

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 3;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

}
