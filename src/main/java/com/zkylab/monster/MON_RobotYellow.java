package com.zkylab.monster;

import java.util.Random;

import com.zkylab.common.GamePanel;
import com.zkylab.entity.Entity;
import com.zkylab.object.OBJ_Coin_Bronze;
import com.zkylab.object.OBJ_Heart;
import com.zkylab.object.OBJ_Mana_Crystal;
import com.zkylab.object.OBJ_Rock;

public class MON_RobotYellow extends Entity {

    GamePanel gamePanel;

    public MON_RobotYellow(GamePanel gamePanel) {

        super(gamePanel);

        this.gamePanel = gamePanel;

        type = type_monster;
        name = "Robot Yellow";
        defaultSpeed = 3;
        speed = defaultSpeed;
        maxLife = 15;
        life = maxLife;
        attack = 2;
        defense = 2;
        exp = 15;
        projectile = new OBJ_Rock(gamePanel);

        solidArea.x = 9;
        solidArea.y = 3;
        solidArea.width = 30;
        solidArea.height = 45;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        
    }

    public void getImage() {
        up1 = setup("/monster/robot_yellow_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monster/robot_yellow_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monster/robot_yellow_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monster/robot_yellow_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monster/robot_yellow_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monster/robot_yellow_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monster/robot_yellow_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monster/robot_yellow_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setAction() { // Monster simple ai algorithm
        getRandomDirection(10);
    }

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gamePanel.player.direction;
        // onPath = true;
    }

    public void checkDrop() {
        
        // Cast a die
        int i = new Random().nextInt(100) + 1;

        // Set the monster drop
        if (i < 50) dropItem(new OBJ_Coin_Bronze(gamePanel));
        if (i >= 50 && i < 75) dropItem(new OBJ_Heart(gamePanel));
        if (i >= 75 && i < 100) dropItem(new OBJ_Mana_Crystal(gamePanel));

    }
    
}
