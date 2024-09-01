package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_BlueHeart extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Blue Heart";

    public OBJ_BlueHeart(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_pickupOnly;
        name = objName;
        down1 = setup("/objects/blueheart", gamePanel.tileSize, gamePanel.tileSize);

        setDialogues();
    }

    public void setDialogues() {
        dialogues[0][0] = "Kamu mengambil benda merah.";
        dialogues[0][1] = "Sebuah batu misterius dari krakatoa.";
    }

    public boolean use(Entity entity) {
        gamePanel.gameState = GamePanel.CUTSCENE_STATE;
        gamePanel.cManager.sceneNumber = gamePanel.cManager.ending;
        return true;
    }

}
