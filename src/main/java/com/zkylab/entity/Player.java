package com.zkylab.entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.zkylab.common.GamePanel;
import com.zkylab.common.KeyHandler;
import com.zkylab.object.OBJ_Fireball;
import com.zkylab.object.OBJ_Key;
import com.zkylab.object.OBJ_Lantern;
import com.zkylab.object.OBJ_Shield_God;
import com.zkylab.object.OBJ_Shield_Normal;
import com.zkylab.object.OBJ_Sword_God;
import com.zkylab.object.OBJ_Sword_Normal;
import com.zkylab.object.OBJ_Tent;

public class Player extends Entity {

    // KeyHandler instance to manage player inputs
    KeyHandler keyHandler;

    // Animation and control variables
    int standCounter = 0;
    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;

    /**
     * Constructor for the Player class.
     * 
     * @param gamePanel  The main game panel.
     * @param keyHandler Handles player input.
     */
    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;

        // Initialize player position
        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        // Initialize player's solid area for collision detection
        solidArea = new Rectangle();
        solidArea.x = 9;
        solidArea.y = 18;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;

        setDefaultValues();
    }

    /**
     * Sets the default values for the player's attributes.
     */
    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 28;
        worldY = gamePanel.tileSize * 23;
        // worldX = gamePanel.tileSize * 10;
        // worldY = gamePanel.tileSize * 30;
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        // Player's initial status
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1;
        dexterity = 1;
        exp = 1;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gamePanel);
        currentShield = new OBJ_Shield_Normal(gamePanel);
        currentLight = null;
        projectile = new OBJ_Fireball(gamePanel);
        attack = getAttack();
        defense = getDefense();

        getImage();
        getAttackImage();
        getGuardImage();
        setItems();
        setDialogue();
    }

    /**
     * Resets the player's position and direction to default values.
     */
    public void setDefaultPosition() {
        gamePanel.currentMap = GamePanel.MAP_LIVING_ROOM;
        worldX = gamePanel.tileSize * 28;
        worldY = gamePanel.tileSize * 23;
        direction = "down";
    }

    /**
     * Sets the player's dialogue messages.
     */
    public void setDialogue() {
        dialogues[0][0] = "Sekarang levelmu adalah " + level + " ! Kamu bertambah kuat!";
    }

    /**
     * Restores the player's status to default values.
     */
    public void restoreStatus() {
        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockback = false;
        lightUpdated = true;
    }

    /**
     * Initializes the player's inventory with default items.
     */
    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Tent(gamePanel));
        inventory.add(new OBJ_Lantern(gamePanel));
        // inventory.add(new OBJ_Sword_God(gamePanel));
        // inventory.add(new OBJ_Shield_God(gamePanel));
    }

    /**
     * Calculates and returns the player's attack value.
     * 
     * @return The attack value.
     */
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }

    /**
     * Calculates and returns the player's defense value.
     * 
     * @return The defense value.
     */
    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

    /**
     * Finds the slot index of the current weapon in the inventory.
     * 
     * @return The index of the current weapon.
     */
    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentWeapon) {
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }

    /**
     * Finds the slot index of the current shield in the inventory.
     * 
     * @return The index of the current shield.
     */
    public int getCurrentShieldSlot() {
        int currentShieldSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentShield) {
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }

    /**
     * Loads the player's images for different actions.
     */
    public void getImage() {
        up1 = setup("/player/arjuna_base_walk_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/player/arjuna_base_walk_up_2", gamePanel.tileSize, gamePanel.tileSize);
        up3 = setup("/player/arjuna_base_walk_up_3", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/player/arjuna_base_walk_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/player/arjuna_base_walk_left_2", gamePanel.tileSize, gamePanel.tileSize);
        left3 = setup("/player/arjuna_base_walk_left_3", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/player/arjuna_base_walk_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/player/arjuna_base_walk_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down3 = setup("/player/arjuna_base_walk_down_3", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/player/arjuna_base_walk_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/player/arjuna_base_walk_right_2", gamePanel.tileSize, gamePanel.tileSize);
        right3 = setup("/player/arjuna_base_walk_right_3", gamePanel.tileSize, gamePanel.tileSize);
    }

    /**
     * Sets the player's sleeping image for all directions.
     * 
     * @param image The image to set.
     */
    public void getSleepingImage(BufferedImage image) {
        up1 = image;
        up2 = image;
        left1 = image;
        left2 = image;
        down1 = image;
        down2 = image;
        right1 = image;
        right2 = image;
    }

    /**
     * Loads the player's attack images based on the current weapon type.
     */
    public void getAttackImage() {
        if (currentWeapon.type == type_sword) {
            loadSwordAttackImages();
        }
        if (currentWeapon.type == type_sword_super) {
            loadSwordSuperImages();
        }
        if (currentWeapon.type == type_sword_god) {
            loadSwordGodImages();
        }
        if (currentWeapon.type == type_axe) {
            loadAxeAttackImages();
        }
        if (currentWeapon.type == type_pickaxe) {
            loadPickaxeAttackImages();
        }
    }

    private void loadSwordAttackImages() {
        attackUp1 = setup("/player/arjuna_base_attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackUp2 = setup("/player/arjuna_base_attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown1 = setup("/player/arjuna_base_attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown2 = setup("/player/arjuna_base_attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackLeft1 = setup("/player/arjuna_base_attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft2 = setup("/player/arjuna_base_attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight1 = setup("/player/arjuna_base_attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight2 = setup("/player/arjuna_base_attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
    }

    private void loadSwordSuperImages() {
        attackUp1 = setup("/player/mc_attacksuper_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackUp2 = setup("/player/mc_attacksuper_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown1 = setup("/player/mc_attacksuper_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown2 = setup("/player/mc_attacksuper_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackLeft1 = setup("/player/mc_attacksuper_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft2 = setup("/player/mc_attacksuper_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight1 = setup("/player/mc_attacksuper_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight2 = setup("/player/mc_attacksuper_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
    }

    private void loadSwordGodImages() {
        attackUp1 = setup("/player/arjuna_purple_attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackUp2 = setup("/player/arjuna_purple_attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown1 = setup("/player/arjuna_purple_attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown2 = setup("/player/arjuna_purple_attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackLeft1 = setup("/player/arjuna_purple_attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft2 = setup("/player/arjuna_purple_attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight1 = setup("/player/arjuna_purple_attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight2 = setup("/player/arjuna_purple_attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
    }

    private void loadAxeAttackImages() {
        attackUp1 = setup("/player/boy_axe_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackUp2 = setup("/player/boy_axe_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown1 = setup("/player/boy_axe_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown2 = setup("/player/boy_axe_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackLeft1 = setup("/player/boy_axe_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft2 = setup("/player/boy_axe_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight1 = setup("/player/boy_axe_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight2 = setup("/player/boy_axe_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
    }

    private void loadPickaxeAttackImages() {
        attackUp1 = setup("/player/boy_pick_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackUp2 = setup("/player/boy_pick_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown1 = setup("/player/boy_pick_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown2 = setup("/player/boy_pick_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackLeft1 = setup("/player/boy_pick_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft2 = setup("/player/boy_pick_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight1 = setup("/player/boy_pick_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight2 = setup("/player/boy_pick_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
    }

    /**
     * Loads the player's guarding images.
     */
    public void getGuardImage() {
        if (currentShield.type == type_shield) {
            loadShieldNormal();
        }
        if (currentShield.type == type_shield_super) {
            loadShieldSuper();
        }
        if (currentShield.type == type_shield_god) {
            loadShieldGod();
        }
    }

    public void loadShieldNormal() {
        guardUp = setup("/player/arjuna_base_guard_up", gamePanel.tileSize, gamePanel.tileSize);
        guardDown = setup("/player/arjuna_base_guard_down", gamePanel.tileSize, gamePanel.tileSize);
        guardLeft = setup("/player/arjuna_base_guard_left", gamePanel.tileSize, gamePanel.tileSize);
        guardRight = setup("/player/arjuna_base_guard_right", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void loadShieldSuper() {
        guardUp = setup("/player/mc_guardsuper_up", gamePanel.tileSize, gamePanel.tileSize);
        guardDown = setup("/player/mc_guardsuper_down", gamePanel.tileSize, gamePanel.tileSize);
        guardLeft = setup("/player/mc_guardsuper_left", gamePanel.tileSize, gamePanel.tileSize);
        guardRight = setup("/player/mc_guardsuper_right", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void loadShieldGod() {
        guardUp = setup("/player/arjuna_red_guard_up", gamePanel.tileSize, gamePanel.tileSize);
        guardDown = setup("/player/arjuna_red_guard_down", gamePanel.tileSize, gamePanel.tileSize);
        guardLeft = setup("/player/arjuna_red_guard_left", gamePanel.tileSize, gamePanel.tileSize);
        guardRight = setup("/player/arjuna_red_guard_right", gamePanel.tileSize, gamePanel.tileSize);
    }

    /**
     * Updates the player's actions based on key inputs and game state.
     */
    public void update() {

        if (!sleep) {

            if (knockback) {

                collisionOn = false;
                gamePanel.collisionChecker.checkTile(this);
                gamePanel.collisionChecker.checkObject(this, true);
                gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
                gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
                gamePanel.collisionChecker.checkEntity(this, gamePanel.iTile);

                if (collisionOn) {
                    knockBackCounter = 0;
                    knockback = false;
                    speed = defaultSpeed;
                } else if (!collisionOn) {
                    switch (knockbackDirection) {
                        case "up":
                            worldY -= speed;
                            break;
                        case "down":
                            worldY += speed;
                            break;
                        case "left":
                            worldX -= speed;
                            break;
                        case "right":
                            worldX += speed;
                            break;
                    }
                }

                knockBackCounter++;
                if (knockBackCounter == 10) {
                    knockBackCounter = 0;
                    knockback = false;
                    speed = defaultSpeed;
                }

            } else if (attacking) {
                attacking();
            } else if (keyHandler.spacePressed) {
                guarding = true;
                guardCounter++;
            } else if (keyHandler.upPressed || keyHandler.leftPressed ||
                    keyHandler.downPressed || keyHandler.rightPressed || keyHandler.enterPressed) { // Update image only
                                                                                                    // when key pressed

                // Determine the direction based on key pressed
                if (keyHandler.upPressed) {
                    direction = "up";
                } else if (keyHandler.leftPressed) {
                    direction = "left";
                } else if (keyHandler.downPressed) {
                    direction = "down";
                } else if (keyHandler.rightPressed) {
                    direction = "right";
                }

                // Check tile collision
                collisionOn = false;
                gamePanel.collisionChecker.checkTile(this);

                // Check object collision
                int objIndex = gamePanel.collisionChecker.checkObject(this, true);
                pickUpObject(objIndex);

                // Check NPC collision
                int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
                interactNPC(npcIndex);

                // Check monster collision
                int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
                contactMonster(monsterIndex);

                // Check interactive tile collision
                gamePanel.collisionChecker.checkEntity(this, gamePanel.iTile);

                // Check event
                gamePanel.eventHandler.checkEvent();

                // If collision is false, player can move
                if (!collisionOn && !keyHandler.enterPressed) {
                    switch (direction) {
                        case "up":
                            worldY -= speed;
                            break;
                        case "down":
                            worldY += speed;
                            break;
                        case "left":
                            worldX -= speed;
                            break;
                        case "right":
                            worldX += speed;
                            break;
                    }
                }

                if (keyHandler.enterPressed && !attackCanceled) {
                    gamePanel.playSoundEffect(GamePanel.SFX_SWORD_SLASH);
                    attacking = true;
                    spriteCounter = 0;
                }

                attackCanceled = false;
                gamePanel.keyHandler.enterPressed = false; // Player won't move when press enter
                guarding = false;
                guardCounter = 0;

                // Change walking image every 10 frames
                spriteCounter++;
                if (spriteCounter > 12) {
                    if (spriteNumber == 2 || spriteNumber == 1) {
                        spriteNumber = 3;
                    } else if (spriteNumber == 3 || spriteNumber == 1) {
                        spriteNumber = 2;
                    }
                    spriteCounter = 0;
                }

            } else {
                standCounter++;
                if (standCounter == 20) {
                    spriteNumber = 1;
                    standCounter = 0;
                }
                guarding = false;
                guardCounter = 0;
            }

            if (gamePanel.keyHandler.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30
                    && projectile.haveResource(this)) {

                // Set default coordinates, direction, and user
                projectile.set(worldX, worldY, direction, true, this);

                // Subtract the cost (mana, ammo, etc)
                projectile.subtractResource(this);

                // Check vacancy
                for (int i = 0; i < gamePanel.projectile[1].length; i++) {
                    if (gamePanel.projectile[gamePanel.currentMap][i] == null) {
                        gamePanel.projectile[gamePanel.currentMap][i] = projectile;
                        break;
                    }
                }

                shotAvailableCounter = 0;

                gamePanel.playSoundEffect(GamePanel.SFX_BURNING);

            }

            // This needs to be outside of key if statement!
            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 50) {
                    invincible = false;
                    transparent = false;
                    invincibleCounter = 0;
                }
            }

            // Prevent shooting multiple fireball when holding the key
            if (shotAvailableCounter < 30) {
                shotAvailableCounter++;
            }

            // Prevent life become larger than maxLife
            if (life > maxLife) {
                life = maxLife;
            }

            // Prevent mana become larger than maxMana
            if (mana > maxMana) {
                mana = maxMana;
            }

            // Game over only active if god mode off
            if (!keyHandler.godModeOn) {
                // Game over
                if (life <= 0) {
                    gamePanel.gameState = GamePanel.GAME_OVER_STATE;
                    gamePanel.ui.commandNumber = -1;
                    if (gamePanel.currentArea == GamePanel.OUTSIDE_AREA) {
                        gamePanel.stopMusic(22);
                    } else if (gamePanel.currentArea == GamePanel.INDOOR_AREA) {
                        gamePanel.stopMusic(23);
                    } else if (gamePanel.currentArea == GamePanel.DUNGEON_AREA) {
                        gamePanel.stopMusic(7);
                    }
                    gamePanel.playSoundEffect(GamePanel.SFX_DEAD);
                }
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            if (gamePanel.obj[gamePanel.currentMap][i].type == type_pickupOnly) {
                // Pickup only items
                gamePanel.obj[gamePanel.currentMap][i].use(this);
                gamePanel.obj[gamePanel.currentMap][i] = null;
            } else if (gamePanel.obj[gamePanel.currentMap][i].type == type_obstacle) {
                // Obstacle
                if (keyHandler.enterPressed) {
                    attackCanceled = true;
                    gamePanel.obj[gamePanel.currentMap][i].interact();
                }
            } else {
                // Inventory items
                String text;
                if (canObtainItem(gamePanel.obj[gamePanel.currentMap][i])) {
                    gamePanel.playSoundEffect(GamePanel.SFX_COIN);
                    text = "Mendapatkan " + gamePanel.obj[gamePanel.currentMap][i].name + ":";
                } else {
                    text = "Kamu tidak bisa membawa item lagi!";
                }
                gamePanel.ui.addMessage(text);
                gamePanel.obj[gamePanel.currentMap][i] = null;
            }
        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            if (gamePanel.keyHandler.enterPressed) {
                attackCanceled = true;
                gamePanel.npc[gamePanel.currentMap][i].speak();
            }
            gamePanel.npc[gamePanel.currentMap][i].move(direction);
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible && !gamePanel.monster[gamePanel.currentMap][i].dying) {

                gamePanel.playSoundEffect(GamePanel.SFX_RECEIVE_DAMAGE);

                int damage = gamePanel.monster[gamePanel.currentMap][i].attack - defense;
                if (damage < 1 && (currentShield.type == type_shield || currentShield.type == type_shield_super)) {
                    damage = 1;
                }

                // if shield god or super
                if (gamePanel.player.currentShield.type == type_shield_super) {
                    damage = 1;
                } else if (gamePanel.player.currentShield.type == type_shield_god) {
                    damage = 0;
                }

                life -= damage;
                invincible = true;
                transparent = true;

            }
        }
    }

    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {
        if (i != 999) {
            if (!gamePanel.monster[gamePanel.currentMap][i].invincible || currentWeapon.type == type_sword_god) {

                gamePanel.playSoundEffect(GamePanel.SFX_HIT_MONSTER);

                // If weapon or projectile have knockback power
                if (knockBackPower > 0) {
                    setKnockBack(gamePanel.monster[gamePanel.currentMap][i], attacker, knockBackPower);
                }

                // If monster offbalance, attack become critical
                if (gamePanel.monster[gamePanel.currentMap][i].offBalance) {
                    attack *= 5;
                }

                int damage = attack - gamePanel.monster[gamePanel.currentMap][i].defense;
                if (damage < 0)
                    damage = 0;

                if (currentWeapon.type == type_sword_god) {
                    damage = 1;
                }

                gamePanel.monster[gamePanel.currentMap][i].life -= damage;
                gamePanel.ui.addMessage(damage + " damage!");

                gamePanel.monster[gamePanel.currentMap][i].invincible = true;
                gamePanel.monster[gamePanel.currentMap][i].damageReaction();

                if (gamePanel.monster[gamePanel.currentMap][i].life <= 0) {
                    gamePanel.monster[gamePanel.currentMap][i].dying = true;
                    gamePanel.ui.addMessage(gamePanel.monster[gamePanel.currentMap][i].name + " telah dikalahkan!");
                    gamePanel.ui.addMessage("Exp + " + gamePanel.monster[gamePanel.currentMap][i].exp);
                    exp += gamePanel.monster[gamePanel.currentMap][i].exp;
                    checkLevelUp();
                }

            }
        }
    }

    public void damageInteractiveTile(int i) {

        if (i != 999 && gamePanel.iTile[gamePanel.currentMap][i].destructible
                && gamePanel.iTile[gamePanel.currentMap][i].isCorrectItem(this)
                && !gamePanel.iTile[gamePanel.currentMap][i].invincible) {

            gamePanel.iTile[gamePanel.currentMap][i].playSoundEffect();
            gamePanel.iTile[gamePanel.currentMap][i].life--;
            gamePanel.iTile[gamePanel.currentMap][i].invincible = true;

            // Generate particle
            generateParticle(gamePanel.iTile[gamePanel.currentMap][i], gamePanel.iTile[gamePanel.currentMap][i]);

            // When interactive tiles destroyed
            if (gamePanel.iTile[gamePanel.currentMap][i].life == 0) {
                gamePanel.iTile[gamePanel.currentMap][i].checkDrop();
                gamePanel.iTile[gamePanel.currentMap][i] = gamePanel.iTile[gamePanel.currentMap][i].getDestroyedForm();
            }

        }

    }

    public void damageProjectile(int i) {
        if (i != 999) {
            Entity projectile = gamePanel.projectile[gamePanel.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gamePanel.playSoundEffect(GamePanel.SFX_FANFARE);
            gamePanel.gameState = GamePanel.DIALOGUE_STATE;
            setDialogue();
            startDialogue(this, 0);
        }
    }

    public void selectItem() {
        int itemIndex = gamePanel.ui.getItemIndexOnSlot(gamePanel.ui.playerSlotCol, gamePanel.ui.playerSlotRow);
        if (itemIndex < inventory.size()) { // If user is not select a blank slot
            Entity selectedItem = inventory.get(itemIndex);
            if (selectedItem.type == type_sword || selectedItem.type == type_sword_super
                    || selectedItem.type == type_sword_god || selectedItem.type == type_axe
                    || selectedItem.type == type_pickaxe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }
            if (selectedItem.type == type_shield || selectedItem.type == type_shield_super
                    || selectedItem.type == type_shield_god) {
                currentShield = selectedItem;
                defense = getDefense();
                getGuardImage();
            }
            if (selectedItem.type == type_light) {
                if (currentLight == selectedItem) {
                    currentLight = null;
                } else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
            if (selectedItem.type == type_consumable) {
                if (selectedItem.use(this)) { // Remove only if it used
                    if (selectedItem.amount > 1) {
                        selectedItem.amount--;
                    } else {
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }

    public int searchItemInInventory(String itemName) {

        int itemIndex = 999;

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }

        return itemIndex;

    }

    public boolean canObtainItem(Entity item) {
        boolean canObtain = false;
        Entity newItem = gamePanel.eGenerator.getObject(item.name);
        // Check if stackable
        if (newItem.stackable) {
            int index = searchItemInInventory(newItem.name);
            if (index != 999) { // Increase stackable item
                inventory.get(index).amount++;
                canObtain = true;
            } else { // New item so need to check vacancy
                if (inventory.size() != maxInventorySize) {
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        } else { // Not stackable so check vacancy
            if (inventory.size() != maxInventorySize) {
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }

    /**
     * Draws the player on the screen.
     * 
     * @param g2 The Graphics2D object for drawing.
     */
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNumber == 1)
                        image = up1;
                    if (spriteNumber == 2)
                        image = up2;
                    if (spriteNumber == 3)
                        image = up3;
                }
                if (attacking) {
                    tempScreenY = screenY - gamePanel.tileSize;
                    if (spriteNumber == 1)
                        image = attackUp1;
                    if (spriteNumber == 2)
                        image = attackUp2;
                }
                if (guarding)
                    image = guardUp;
                break;
            case "down":
                if (!attacking) {
                    if (spriteNumber == 1)
                        image = down1;
                    if (spriteNumber == 2)
                        image = down2;
                    if (spriteNumber == 3)
                        image = down3;
                }
                if (attacking) {
                    if (spriteNumber == 1)
                        image = attackDown1;
                    if (spriteNumber == 2)
                        image = attackDown2;
                }
                if (guarding)
                    image = guardDown;
                break;
            case "left":
                if (!attacking) {
                    if (spriteNumber == 1)
                        image = left1;
                    if (spriteNumber == 2)
                        image = left2;
                    if (spriteNumber == 3)
                        image = left3;
                }
                if (attacking) {
                    tempScreenX = screenX - gamePanel.tileSize;
                    if (spriteNumber == 1)
                        image = attackLeft1;
                    if (spriteNumber == 2)
                        image = attackLeft2;
                }
                if (guarding)
                    image = guardLeft;
                break;
            case "right":
                if (!attacking) {
                    if (spriteNumber == 1)
                        image = right1;
                    if (spriteNumber == 2)
                        image = right2;
                    if (spriteNumber == 3)
                        image = right3;
                }
                if (attacking) {
                    if (spriteNumber == 1)
                        image = attackRight1;
                    if (spriteNumber == 2)
                        image = attackRight2;
                }
                if (guarding)
                    image = guardRight;
                break;
        }

        if (transparent)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
        if (drawing) {
            g2.drawImage(image, tempScreenX, tempScreenY, null); // Draw character
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F)); // Reset alpha

    }

}
