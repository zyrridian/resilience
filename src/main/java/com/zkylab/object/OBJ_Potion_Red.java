package com.zkylab.object;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class OBJ_Potion_Red extends Entity {
    
    GamePanel gamePanel;
    public static final String objName = "Potion Merah";

    public OBJ_Potion_Red(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        type = type_consumable;
        name = objName;
        value = 5;
        down1 = setup("/objects/potion_red", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nMemulihkan nyawa +" + value + ".";
        price = 25;
        stackable = true;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "Kamu meminum " + name + "!\n" + "Nyawa dipulihkan +" + value + ".";
    }

    public boolean use(Entity entity) {
        startDialogue(this, 0);
        entity.life += value;
        gamePanel.playSoundEffect(2);
        return true;
    }

}
