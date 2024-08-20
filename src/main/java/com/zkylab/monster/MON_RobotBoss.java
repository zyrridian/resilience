package com.zkylab.monster;

import com.zkylab.common.GamePanel;
import com.zkylab.data.Progress;
import com.zkylab.entity.Entity;
import com.zkylab.object.OBJ_Coin_Red;

public class MON_RobotBoss extends Entity {
    
    GamePanel gamePanel;
    public static final String monName = "Robot Boss";

    public MON_RobotBoss(GamePanel gamePanel) {

        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        if (gamePanel.player.currentWeapon.type == type_sword_super) {
            maxLife = 1000;
        } else if (gamePanel.player.currentWeapon.type == type_sword_god) {
            maxLife = 100;
        } else {
            maxLife = 200;
        }
        life = maxLife;
        attack = 10;
        defense = 10;
        exp = 15000;
        knockBackPower = 15;
        sleep = false;

        int size = gamePanel.tileSize * 7;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48 * 2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 50;

        getImage();
        getAttackImage();
        setDialogue();
        
    }

    public void getImage() {

        int i = 7;

        if (!inRage) {
            up1 = setup("/monster/robot_boss_walk_up_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            up2 = setup("/monster/robot_boss_walk_up_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            // up3 = setup("/monster/robot_boss_walk_up_3", gamePanel.tileSize * i, gamePanel.tileSize * i);
            down1 = setup("/monster/robot_boss_walk_down_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            down2 = setup("/monster/robot_boss_walk_down_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            // down3 = setup("/monster/robot_boss_walk_down_3", gamePanel.tileSize * i, gamePanel.tileSize * i);
            left1 = setup("/monster/robot_boss_walk_left_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            left2 = setup("/monster/robot_boss_walk_left_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            // left3 = setup("/monster/robot_boss_walk_left_3", gamePanel.tileSize * i, gamePanel.tileSize * i);
            right1 = setup("/monster/robot_boss_walk_right_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            right2 = setup("/monster/robot_boss_walk_right_2", gamePanel.tileSize * i, gamePanel.tileSize * i);  
            // right3 = setup("/monster/robot_boss_walk_right_3", gamePanel.tileSize * i, gamePanel.tileSize * i);   
        }

        if (inRage) {
            up1 = setup("/monster/robot_boss_walk_up_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            up2 = setup("/monster/robot_boss_walk_up_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            // up3 = setup("/monster/robot_boss_walk_up_3", gamePanel.tileSize * i, gamePanel.tileSize * i);
            down1 = setup("/monster/robot_boss_walk_down_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            down2 = setup("/monster/robot_boss_walk_down_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            // down3 = setup("/monster/robot_boss_walk_down_3", gamePanel.tileSize * i, gamePanel.tileSize * i);
            left1 = setup("/monster/robot_boss_walk_left_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            left2 = setup("/monster/robot_boss_walk_left_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            // left3 = setup("/monster/robot_boss_walk_left_3", gamePanel.tileSize * i, gamePanel.tileSize * i);
            right1 = setup("/monster/robot_boss_walk_right_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            right2 = setup("/monster/robot_boss_walk_right_2", gamePanel.tileSize * i, gamePanel.tileSize * i);  
            // right3 = setup("/monster/robot_boss_walk_right_3", gamePanel.tileSize * i, gamePanel.tileSize * i);     
        }
        
    }

    public void getAttackImage() {

        int i = 7;
        
        if (!inRage) {
            attackUp1 = setup("/monster/robot_boss_attack_up_1", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackUp2 = setup("/monster/robot_boss_attack_up_2", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackDown1 = setup("/monster/robot_boss_attack_down_1", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackDown2 = setup("/monster/robot_boss_attack_down_2", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackLeft1 = setup("/monster/robot_boss_attack_left_1", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackLeft2 = setup("/monster/robot_boss_attack_left_2", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackRight1 = setup("/monster/robot_boss_attack_right_1", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackRight2 = setup("/monster/robot_boss_attack_right_2", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);     
        }

        if (inRage) {
            attackUp1 = setup("/monster/robot_boss_attack_up_1", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackUp2 = setup("/monster/robot_boss_attack_up_2", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackDown1 = setup("/monster/robot_boss_attack_down_1", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackDown2 = setup("/monster/robot_boss_attack_down_2", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackLeft1 = setup("/monster/robot_boss_attack_left_1", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackLeft2 = setup("/monster/robot_boss_attack_left_2", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackRight1 = setup("/monster/robot_boss_attack_right_1", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackRight2 = setup("/monster/robot_boss_attack_right_2", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);   
        }

    }

    public void setDialogue() {
        dialogues[0][0] = "No one can steal my treasure!";
        dialogues[0][1] = "You will die here!";
        dialogues[0][2] = "WELCOME TO MY DOOM!";
    }

    public void setAction() { // Monster simple ai algorithm

        // If skeleton hp decrease to 50% and not in rage, make it rage
        if (!inRage && life < maxLife / 2) {
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed *= 2;
            speed = defaultSpeed;
            attack *= 3;
        }

        // If player close to skeleton, move towards player, otherwise move randomly
        if (getTileDistance(gamePanel.player) < 10) { 
            moveTowardPlayer(60);
        } else {
            getRandomDirection(120);
        }

        // Check if it attacks
        if (!attacking) {
            checkAttackOrNot(60, gamePanel.tileSize * 7, gamePanel.tileSize * 5);
        }

    }

    public void damageReaction() {
        actionLockCounter = 0;
        // direction = gamePanel.player.direction;
        onPath = true;
    }

    public void checkDrop() {

        gamePanel.bossBattleOn = false;
        Progress.skeletonLordDefeated = true;
        
        // Restore the previous music
        // gamePanel.stopMusic();
        // gamePanel.playMusic(7);sssw

        // Remove the iron doors
        // for (int i = 0; i < gamePanel.obj[1].length; i++) {
        //     if (gamePanel.obj[gamePanel.currentMap][i] != null && gamePanel.obj[gamePanel.currentMap][i].name.equals(OBJ_DoorIron.objName)) {
                gamePanel.playSoundEffect(20);
                // gamePanel.obj[gamePanel.currentMap][i] = null;
        //     }
        // }
        
        // Cast a die
        // int i = new Random().nextInt(100) + 1;

        // Set the monster drop
        // if (i < 50) dropItem(new OBJ_Coin_Bronze(gamePanel));
        // if (i >= 50 && i < 75) dropItem(new OBJ_Heart(gamePanel));
        // if (i >= 75 && i < 100) dropItem(new OBJ_Mana_Crystal(gamePanel));
        dropItem(new OBJ_Coin_Red(gamePanel));

    }

}
