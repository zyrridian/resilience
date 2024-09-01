package com.zkylab.common;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import com.zkylab.data.Progress;
import com.zkylab.entity.Entity;
import com.zkylab.entity.NPC_Sastro;
import com.zkylab.entity.PlayerDummy;
import com.zkylab.object.OBJ_BlueHeart;
import com.zkylab.object.OBJ_Cloud_Concern;

public class CutsceneManager {

    GamePanel gamePanel;
    EventHandler eventHandler;
    public static Entity cutsceneMaster; // Entity responsible for dialogues and other events
    Graphics2D g2;
    public int sceneNumber;
    public int scenePhase;
    String endCredit;

    // Scene number
    public final int NA = 0;
    public final int opening = 1;
    public final int warning = 2;
    public final int water = 3;
    public final int skeletonLord = 88;
    public final int ending = 99;
    int counter = 0;
    float alpha = 0F;
    int y;

    public CutsceneManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        cutsceneMaster = new Entity(gamePanel);
        endCredit = "Program/Desain/Music/Skenario\n\n"
                + "Arye Burhanudin\n"
                + "Rafi Ramdhani\n"
                + "Rezky Aditia Fauzan\n"
                + "\n\n\n\n\n\n\n\n\n\n\n\n\n"
                + "Terimakasih telah bermain!";
        setDialogue();
    }

    public void setDialogue() {
        cutsceneMaster.dialogues[0][0] = "Ada orang yang terlihat kebingungan.";
        cutsceneMaster.dialogues[0][1] = "Bicaralah padanya.";
        cutsceneMaster.dialogues[0][2] = "Atau abaikan saja.";
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch (sceneNumber) {
            case opening:
                scene_opening();
                break;
            case warning:
                scene_warning();
                break;
            case water:
                scene_water();
                break;
            case ending:
                scene_ending();
                break;
        }
    }

    public void scene_opening() {
        // Search a vacant slot for the dummy
        if (scenePhase == 0) {
            gamePanel.player.sleep = true;
            for (int i = 0; i < gamePanel.npc[1].length; i++) {
                if (gamePanel.npc[gamePanel.currentMap][i] == null) {
                    gamePanel.npc[gamePanel.currentMap][i] = new PlayerDummy(gamePanel);
                    gamePanel.npc[gamePanel.currentMap][i].worldX = gamePanel.player.worldX;
                    gamePanel.npc[gamePanel.currentMap][i].worldY = gamePanel.player.worldY;
                    gamePanel.npc[gamePanel.currentMap][i].direction = gamePanel.player.direction;
                    gamePanel.npc[gamePanel.currentMap][i].sleep = true;
                    break;
                }
            }
            gamePanel.player.drawing = false;
            scenePhase++;
        }
        // Add earthquake sound effect
        if (scenePhase == 1) {
            gamePanel.playSoundEffect(GamePanel.SFX_EARTHQUAKE);
            scenePhase++;
        }
        // Shake the camera by altering the player's position significantly
        if (scenePhase == 2) {
            int shakeAmplitude = 10;
            gamePanel.player.worldX += (int) (Math.random() * shakeAmplitude * 2 - shakeAmplitude);
            gamePanel.player.worldY += (int) (Math.random() * shakeAmplitude * 2 - shakeAmplitude);
            if (counterReached(300)) {
                scenePhase++;
            }
        }
        // Set npc
        if (scenePhase == 3) {
            gamePanel.ui.npc = new PlayerDummy(gamePanel);
            scenePhase++;
        }
        // Show dialogue
        if (scenePhase == 4) {
            gamePanel.ui.drawDialogueScreen();
        }
        // Return camera to the player
        if (scenePhase == 5) {
            for (int i = 0; i < gamePanel.npc[1].length; i++) {
                if (gamePanel.npc[gamePanel.currentMap][i] != null &&
                        gamePanel.npc[gamePanel.currentMap][i].name.equals(PlayerDummy.npcName)) {
                    gamePanel.player.worldX = gamePanel.npc[gamePanel.currentMap][i].worldX;
                    gamePanel.player.worldY = gamePanel.npc[gamePanel.currentMap][i].worldY;
                    gamePanel.npc[gamePanel.currentMap][i] = null;
                    break;
                }
            }
            gamePanel.player.drawing = true;
            sceneNumber = NA;
            scenePhase = 0;
            gamePanel.player.sleep = false;
            gamePanel.gameState = GamePanel.PLAY_STATE;
            gamePanel.playMusic(GamePanel.MUSIC_INDOOR);
        }
    }

    public void scene_warning() {
        // set npc dialogue
        if (scenePhase == 0) {
            gamePanel.ui.npc = new NPC_Sastro(gamePanel);
            gamePanel.ui.npc.dialogueSet = 0;
            scenePhase++;
        }
        // start npc dialogue
        if (scenePhase == 1) {
            gamePanel.ui.drawDialogueScreen();
        }
        // npc turn left
        if (scenePhase == 2) {
            for (int i = 0; i < gamePanel.npc[1].length; i++) {
                if (gamePanel.npc[gamePanel.currentMap][i].name.equals(NPC_Sastro.npcName)) {
                    gamePanel.npc[gamePanel.currentMap][i].direction = "left";
                    break;
                }
            }
            scenePhase++;
        }
        // npc talk again
        if (scenePhase == 3) {
            gamePanel.ui.npc.dialogueSet = 1;
            gamePanel.ui.drawDialogueScreen();
        }
        // npc walk
        if (scenePhase == 4) {
            for (int i = 0; i < gamePanel.npc[1].length; i++) {
                if (gamePanel.npc[gamePanel.currentMap][i].name.equals(NPC_Sastro.npcName)) {
                    gamePanel.npc[gamePanel.currentMap][i].sleep = false;
                    gamePanel.npc[gamePanel.currentMap][i].onPath = true;
                    break;
                }
            }
            gamePanel.player.spriteNumber = 1;
        }
        // change sprite number and make it sleep
        if (scenePhase == 5) {
            for (int i = 0; i < gamePanel.npc[1].length; i++) {
                if (gamePanel.npc[gamePanel.currentMap][i].name.equals(NPC_Sastro.npcName)) {
                    gamePanel.npc[gamePanel.currentMap][i].spriteNumber = 1;
                    gamePanel.npc[gamePanel.currentMap][i].sleep = true;
                    break;
                }
            }
            gamePanel.player.spriteNumber = 1;
            gamePanel.player.direction = "right";
            scenePhase++;
        }
        // start another dialogue
        if (scenePhase == 6) {
            gamePanel.ui.npc.dialogueSet = 2;
            gamePanel.ui.drawDialogueScreen();
        }
        // reset everything then end the cutscene
        if (scenePhase == 7) {
            for (int i = 0; i < gamePanel.npc[1].length; i++) {
                if (gamePanel.npc[gamePanel.currentMap][i].name.equals(NPC_Sastro.npcName)) {
                    gamePanel.npc[gamePanel.currentMap][i].sleep = false;
                    break;
                }
            }
            sceneNumber = NA;
            scenePhase = 0;
            Progress.cutsceneWarningFinished = true;
            gamePanel.player.sleep = false;
            gamePanel.gameState = GamePanel.PLAY_STATE;
        }
    }

    public void scene_water() {
        // add cloud concern emoji
        if (scenePhase == 0) {
            for (int i = 0; i < gamePanel.obj.length; i++) {
                if (gamePanel.obj[gamePanel.currentMap][i] == null) {
                    gamePanel.obj[gamePanel.currentMap][i] = new OBJ_Cloud_Concern(gamePanel);
                    gamePanel.obj[gamePanel.currentMap][i].worldX = (int) (gamePanel.tileSize * (12 + 0.5));
                    gamePanel.obj[gamePanel.currentMap][i].worldY = (int) (gamePanel.tileSize * (17 - 0.5));
                    break;
                }
            }
            scenePhase++;
        }
        // change player sprite number
        if (scenePhase == 1) {
            gamePanel.player.spriteNumber = 1;
            scenePhase++;
        }
        // set npc dialogue
        if (scenePhase == 2) {
            gamePanel.ui.npc = new PlayerDummy(gamePanel);
            gamePanel.ui.npc.dialogueSet = 1;
            scenePhase++;
        }
        // start npc dialogue
        if (scenePhase == 3) {
            gamePanel.ui.drawDialogueScreen();
        }
        // search a vacant slot for the dummy
        if (scenePhase == 4) {
            gamePanel.player.sleep = true;
            for (int i = 0; i < gamePanel.npc[1].length; i++) {
                if (gamePanel.npc[gamePanel.currentMap][i] == null) {
                    gamePanel.npc[gamePanel.currentMap][i] = new PlayerDummy(gamePanel);
                    gamePanel.npc[gamePanel.currentMap][i].worldX = gamePanel.player.worldX;
                    gamePanel.npc[gamePanel.currentMap][i].worldY = gamePanel.player.worldY;
                    gamePanel.npc[gamePanel.currentMap][i].direction = gamePanel.player.direction;
                    gamePanel.npc[gamePanel.currentMap][i].sleep = true;
                    break;
                }
            }
            gamePanel.player.drawing = false;
            scenePhase++;
        }
        // moving camera up
        if (scenePhase == 5) {
            gamePanel.player.worldY -= 2;
            if (gamePanel.player.worldY < gamePanel.tileSize * 20) {
                scenePhase++;
            }
        }
        // npc talk again
        if (scenePhase == 6) {
            gamePanel.ui.npc.dialogueSet = 2;
            gamePanel.ui.drawDialogueScreen();
        }
        // reset everything then end the cutscene
        if (scenePhase == 7) {
            // Remove the dummy
            for (int i = 0; i < gamePanel.npc[1].length; i++) {
                if (gamePanel.npc[gamePanel.currentMap][i] != null &&
                        gamePanel.npc[gamePanel.currentMap][i].name.equals(PlayerDummy.npcName)) {
                    gamePanel.player.worldX = gamePanel.npc[gamePanel.currentMap][i].worldX;
                    gamePanel.player.worldY = gamePanel.npc[gamePanel.currentMap][i].worldY;
                    gamePanel.npc[gamePanel.currentMap][i] = null;
                    break;
                }
            }
            // Start drawing the player
            Progress.cutsceneWaterFinished = true;
            sceneNumber = NA;
            scenePhase = 0;
            gamePanel.player.drawing = true;
            gamePanel.player.sleep = false;
            gamePanel.gameState = GamePanel.PLAY_STATE;
            // gamePanel.playMusic(GamePanel.MUSIC_DUNGEON);
        }
    }

    // public void scene_skeletonLord() {

    // // Shut the iron door
    // if (scenePhase == 0) {
    // gamePanel.bossBattleOn = true;
    // for (int i = 0; i < gamePanel.obj[1].length; i++) {
    // if (gamePanel.obj[gamePanel.currentMap][i] == null) {
    // gamePanel.obj[gamePanel.currentMap][i] = new OBJ_DoorIron(gamePanel);
    // gamePanel.obj[gamePanel.currentMap][i].worldX = gamePanel.tileSize * 25;
    // gamePanel.obj[gamePanel.currentMap][i].worldY = gamePanel.tileSize * 28;
    // gamePanel.obj[gamePanel.currentMap][i].temp = true; // To delete temporary
    // door later
    // gamePanel.playSoundEffect(20);
    // break;
    // }
    // }
    // // Search a vacant slot for the dummy
    // for (int i = 0; i < gamePanel.npc[1].length; i++) {
    // if (gamePanel.npc[gamePanel.currentMap][i] == null) {
    // gamePanel.npc[gamePanel.currentMap][i] = new PlayerDummy(gamePanel);
    // gamePanel.npc[gamePanel.currentMap][i].worldX = gamePanel.player.worldX;
    // gamePanel.npc[gamePanel.currentMap][i].worldY = gamePanel.player.worldY;
    // gamePanel.npc[gamePanel.currentMap][i].direction =
    // gamePanel.player.direction;
    // break;
    // }
    // }

    // gamePanel.player.drawing = false;
    // scenePhase++;
    // }

    // // Moving the camera upward and use dummy player (above this code) image
    // because the current player image is invincible
    // if (scenePhase == 1) {
    // gamePanel.player.worldY -= 2;
    // if (gamePanel.player.worldY < gamePanel.tileSize * 16) {
    // scenePhase++;
    // }
    // }

    // // Wake up the boss and search the boss
    // if (scenePhase == 2) {
    // for (int i = 0; i < gamePanel.monster[1].length; i++) {
    // if (gamePanel.monster[gamePanel.currentMap][i] != null &&
    // gamePanel.monster[gamePanel.currentMap][i].name.equals(MON_SkeletonLord.monName)
    // ) {
    // gamePanel.monster[gamePanel.currentMap][i].sleep = false;
    // gamePanel.ui.npc = gamePanel.monster[gamePanel.currentMap][i];
    // scenePhase++;
    // break;
    // }
    // }

    // }

    // // Boss speak
    // if (scenePhase == 3) {
    // gamePanel.ui.drawDialogueScreen();
    // }

    // // Return camera to the player
    // if (scenePhase == 4) {

    // // Remove the dummy
    // for (int i = 0; i < gamePanel.npc[1].length; i++) {
    // if (gamePanel.npc[gamePanel.currentMap][i] != null &&
    // gamePanel.npc[gamePanel.currentMap][i].name == PlayerDummy.npcName
    // ) {
    // // Restore player position
    // gamePanel.player.worldX = gamePanel.npc[gamePanel.currentMap][i].worldX;
    // gamePanel.player.worldY = gamePanel.npc[gamePanel.currentMap][i].worldY;
    // gamePanel.npc[gamePanel.currentMap][i] = null;
    // break;
    // }
    // }

    // // Start drawing the player
    // gamePanel.player.drawing = true;

    // // Reset
    // sceneNumber = NA;
    // scenePhase = 0;
    // gamePanel.gameState = GamePanel.PLAY_STATE;

    // // Change the music
    // gamePanel.stopMusic();
    // gamePanel.playMusic(21);

    // }
    // }

    public void scene_ending() {

        // Stop music
        if (scenePhase == 0) {
            gamePanel.stopMusic(GamePanel.MUSIC_DUNGEON);
            gamePanel.ui.npc = new OBJ_BlueHeart(gamePanel);
            scenePhase++;
        }

        // Display dialogues
        if (scenePhase == 1) {
            gamePanel.ui.drawDialogueScreen();
        }

        // Play the fanfare
        if (scenePhase == 2) {
            gamePanel.playSoundEffect(GamePanel.SFX_FANFARE);
            scenePhase++;
        }

        // Wait until the sound effect ends
        if (scenePhase == 3) {
            if (counterReached(120)) {
                scenePhase++;
            }
        }

        // Scene gradually darker
        if (scenePhase == 4) {
            alpha += 0.005F;
            if (alpha > 1F) {
                alpha = 1F;
            }
            drawBlackBackground(alpha);
            if (alpha == 1F) {
                alpha = 0;
                scenePhase++;
            }
        }

        if (scenePhase == 5) {
            drawBlackBackground(1F);
            alpha += 0.005F;
            if (alpha > 1F) {
                alpha = 1F;
            }

            String text = "Setelah perjuangan berat melawan monster,\n"
                    + "akhirnya petualang berhasil menyelamatkan pulau.\n"
                    + "Tapi ini bukan akhir dari segalanya, karena penyebab\n"
                    + "gempa di kota ada hubungannya dengan para monster.\n"
                    + "Dan Krakatoa bukanlah satu-satunya monster raksasa.\n"
                    + "Petualangan Resilience baru saja dimulai.";
            drawString(alpha, 30F, (int) (gamePanel.tileSize * 2.5), text, 70);
            gamePanel.playMusic(GamePanel.MUSIC_INDOOR);

            if (counterReached(600)) {
                scenePhase++;
            }
        }

        if (scenePhase == 6) {
            drawBlackBackground(1F);
            drawString(1F, 100F, gamePanel.screenHeight / 2, "Resilience", 40);

            if (counterReached(480)) {
                scenePhase++;
            }
        }

        if (scenePhase == 7) {
            drawBlackBackground(1F);
            y = gamePanel.screenHeight / 2;
            drawString(1F, 30F, y, endCredit, 40);

            if (counterReached(120)) {
                scenePhase++;
            }
        }

        // Scroll credits
        if (scenePhase == 8) {
            drawBlackBackground(1F);
            y--;
            drawString(1F, 30F, y, endCredit, 40);
            if (counterReached(360)) {
                System.exit(0);
            }
        }

    }

    public boolean counterReached(int target) {
        boolean counterReached = false;
        counter++;
        if (counter > target) {
            counterReached = true;
            counter = 0;
        }
        return counterReached;
    }

    public void drawBlackBackground(float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }

    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));

        for (String line : text.split("\n")) {
            int x = gamePanel.ui.getXforCenteredText(line);
            g2.drawString(line, x, y);
            y += lineHeight;
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }

}
