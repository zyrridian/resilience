package com.zkylab.pet;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;

public class PET_Dog extends Entity {

    public static final String npcName = "Dog";
    GamePanel gamePanel;

    public PET_Dog(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = npcName;
        direction = "left";
        speed = 3;

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
        avatar = setup("/pet/avatar_dog", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        up1 = setup("/pet/dog_walk_left_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        up2 = setup("/pet/dog_walk_left_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        left1 = setup("/pet/dog_walk_left_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        left2 = setup("/pet/dog_walk_left_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        down1 = setup("/pet/dog_walk_right_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        down2 = setup("/pet/dog_walk_right_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        right1 = setup("/pet/dog_walk_right_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
        right2 = setup("/pet/dog_walk_right_1", (int) (gamePanel.tileSize / 1), (int) (gamePanel.tileSize / 1));
    }

    public void setDialogue() {
        dialogues[0][0] = "Woof, woof!";
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


            searchPath(goalCol, goalRow, "any");

        } else {

            actionLockCounter++;

            if (actionLockCounter == 120) { // Giving delay 2 second every movement

                Random random = new Random();
                int i = random.nextInt(100) + 1; // pick up a number from 1 to 100
        
                if (i <= 25) direction = "left";
                if (i > 25 && i <= 50) direction = "right";
                if (i > 50 && i <= 75) direction = "left";
                if (i > 75 && i <= 100) direction = "right";
                
                actionLockCounter = 0;
                
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
