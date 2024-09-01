package com.zkylab.common;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.zkylab.entity.Entity;
import com.zkylab.object.OBJ_Background;
import com.zkylab.object.OBJ_Coin_Bronze;
import com.zkylab.object.OBJ_Heart;
import com.zkylab.object.OBJ_Mana_Crystal;
import com.zkylab.object.OBJ_Moon;
import com.zkylab.object.OBJ_Sun;

public class UI {

    GamePanel gamePanel;
    Graphics2D g2;
    public Font maruMonica, purisaBold, yoster, november;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin, bgchallenge;
    public BufferedImage day, night;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNumber = 0;
    public int titleScreenState = 0; // sub class of title, for example: menu, options, opening, etc.
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int counter = 0;
    int titleScreenCounter = 0;
    public Entity npc;
    int charIndex = 0;
    String combinedText = "";

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        try {
            InputStream inputStream = getClass().getResourceAsStream("/font/maru-monica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            inputStream = getClass().getResourceAsStream("/font/purisa-bold.ttf");
            purisaBold = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            inputStream = getClass().getResourceAsStream("/font/yoster.ttf");
            yoster = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            inputStream = getClass().getResourceAsStream("/font/november.ttf");
            november = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create life object in HUD
        Entity heart = new OBJ_Heart(gamePanel);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        // Create mana object in HUD
        Entity crystal = new OBJ_Mana_Crystal(gamePanel);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;

        Entity bronzeCoin = new OBJ_Coin_Bronze(gamePanel);
        coin = bronzeCoin.down1;

        Entity bg = new OBJ_Background(gamePanel);
        bgchallenge = bg.down1;

        Entity sun = new OBJ_Sun(gamePanel);
        day = sun.down1;

        Entity moon = new OBJ_Moon(gamePanel);
        night = moon.down1;

    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        // g2.setFont(maruMonica);
        g2.setFont(november);
        g2.setColor(Color.white);

        // Title State
        if (gamePanel.gameState == GamePanel.TITLE_STATE) {
            drawTitleScreen();
        }

        // Play State
        if (gamePanel.gameState == GamePanel.PLAY_STATE) {
            drawPlayerLifeScreen();
            drawMonsterLife();
            drawMessage();
        }

        // Pause State
        if (gamePanel.gameState == GamePanel.PAUSE_STATE) {
            drawPlayerLifeScreen();
            drawPauseScreen();
        }

        // Dialog State
        if (gamePanel.gameState == GamePanel.DIALOGUE_STATE) {
            drawPlayerLifeScreen();
            drawDialogueScreen();
        }

        // Character State
        if (gamePanel.gameState == GamePanel.CHARACTER_STATE) {
            drawPlayerLifeScreen();
            drawCharacterScreen();
            drawInventory(gamePanel.player, true);
        }

        // Options State
        if (gamePanel.gameState == GamePanel.OPTIONS_STATE) {
            drawPlayerLifeScreen();
            drawOptionsScreen();
        }

        // Game Over State
        if (gamePanel.gameState == GamePanel.GAME_OVER_STATE) {
            drawGameOverScreen();
        }

        // Transition State
        if (gamePanel.gameState == GamePanel.TRANSITION_STATE) {
            drawTransitionScreen(gamePanel.currentMusicIndex);
        }

        // Trade State
        if (gamePanel.gameState == GamePanel.TRADE_STATE) {
            drawPlayerLifeScreen();
            drawTradeScreen();
        }

        // Sleep State
        if (gamePanel.gameState == GamePanel.SLEEP_STATE) {
            drawPlayerLifeScreen();
            drawSleepScreen();
        }
    }

    public void drawPlayerLifeScreen() {

        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        int i = 0;
        int iconSize = 32;
        int manaStartX = (gamePanel.tileSize / 2) - 5;
        int manaStartY = 0;

        // Draw Max Life
        while (i < gamePanel.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, iconSize, iconSize, null);
            i++;
            x += iconSize;
            manaStartY = y + 32;

            // if (i % 8 == 0) {
            // x = gamePanel.tileSize / 2;
            // y += iconSize;
            // }
        }

        // Reset
        x = gamePanel.tileSize / 2;
        y = gamePanel.tileSize / 2;
        i = 0;

        // Draw Current Life
        while (i < gamePanel.player.life) {
            g2.drawImage(heart_half, x, y, iconSize, iconSize, null);
            i++;
            if (i < gamePanel.player.life) {
                g2.drawImage(heart_full, x, y, iconSize, iconSize, null);
            }
            i++;
            x += iconSize;
        }

        // Draw max mana
        x = (gamePanel.tileSize / 2) - 5;
        y = manaStartY;
        i = 0;
        while (i < gamePanel.player.maxMana) {
            g2.drawImage(crystal_blank, x, y, iconSize, iconSize, null);
            i++;
            x += manaStartX;
        }

        // Draw mana
        x = (gamePanel.tileSize / 2) - 5;
        y = manaStartY;
        i = 0;
        while (i < gamePanel.player.mana) {
            g2.drawImage(crystal_full, x, y, iconSize, iconSize, null);
            i++;
            x += manaStartX;
        }
    }

    public void drawMonsterLife() {

        for (int i = 0; i < gamePanel.monster[1].length; i++) {

            Entity monster = gamePanel.monster[gamePanel.currentMap][i];

            if (monster != null && monster.inCamera()) {
                if (monster.hpBarOn && !monster.boss) {

                    double oneScale = (double) gamePanel.tileSize / monster.maxLife;
                    double hpBarValue = oneScale * monster.life;

                    if (hpBarValue < 0)
                        hpBarValue = 0;

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(monster.getScreenX() - 1, monster.getScreenY() - 16, gamePanel.tileSize + 2, 12);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int) hpBarValue, 10);

                    monster.hpBarCounter++;

                    if (monster.hpBarCounter > 600) {
                        monster.hpBarCounter = 0;
                        monster.hpBarOn = false;
                    }

                } else if (monster.boss) {

                    double oneScale = (double) gamePanel.tileSize * 8 / monster.maxLife;
                    double hpBarValue = oneScale * monster.life;

                    int x = gamePanel.screenWidth / 2 - gamePanel.tileSize * 4;
                    int y = gamePanel.tileSize * 10;
                    if (hpBarValue < 0)
                        hpBarValue = 0;

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(x - 1, y - 1, gamePanel.tileSize * 8 + 2, 22);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(x, y, (int) hpBarValue, 20);

                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
                    g2.setColor(Color.white);
                    g2.drawString(monster.name, x + 4, y - 10);

                }
            }
        }
    }

    public void drawMessage() {

        int messageX = gamePanel.tileSize;
        int messageY = gamePanel.tileSize * 4;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {

                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 100) {
                    message.remove(i);
                    messageCounter.remove(i);
                }

            }
        }

    }

    public void drawTitleScreen() {

        if (titleScreenState == 0) {

            titleScreenCounter++;

            if (titleScreenCounter < 120) {
                g2.setColor(new Color(0, 0, 0));
                g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

                // Title Name
                g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
                String text = "Mandala Tech";
                int x = getXforCenteredText(text);
                int y = gamePanel.tileSize * 6 + gamePanel.tileSize / 2;

                // Shadow
                g2.setColor(Color.gray);
                g2.drawString(text, x + 3, y + 3);

                // Main Color
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
            }

            if (titleScreenCounter >= 120) {
                titleScreenState++;
                titleScreenCounter = 0;
            }

        } else if (titleScreenState == 1) { // MENU

            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            // Title Name
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
            String text = "Resilience";
            int x = getXforCenteredText(text);
            int y = (int) (gamePanel.tileSize * 2.5);

            // Shadow
            g2.setColor(Color.gray);
            g2.drawString(text, x + 3, y + 3);

            // Main Color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // Character Image
            x = gamePanel.screenWidth / 2 - (gamePanel.tileSize * 2) / 2;
            y += gamePanel.tileSize * 1;
            g2.drawImage(gamePanel.player.down1, x, y, gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);

            // Menu
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize * 3.5;
            g2.drawString(text, x, y);
            if (commandNumber == 0) {
                g2.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
            if (commandNumber == 1) {
                g2.drawString(">", x - gamePanel.tileSize, y);
            }

            // text = "CHALLENGE MODE";
            // x = getXforCenteredText(text);
            // y += gamePanel.tileSize;
            // g2.drawString(text, x, y);
            // if (commandNumber == 2) {
            //     g2.drawString(">", x - gamePanel.tileSize, y);
            // }

            text = "CREDITS";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
            if (commandNumber == 2) {
                g2.drawString(">", x - gamePanel.tileSize, y);
            }

            text = "EXIT";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
            if (commandNumber == 3) {
                g2.drawString(">", x - gamePanel.tileSize, y);
            }

        } else if (titleScreenState == 2) {

            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            // Title Name
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
            String text = "Fungsi Tombol Keyboard:";
            int x = getXforCenteredText(text);
            int y = gamePanel.tileSize * 3;

            // Shadow
            g2.setColor(Color.gray);
            g2.drawString(text, x + 3, y + 3);

            // Main Color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // Menu
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15F));

            text = "W/UP    - Bergerak ke atas";
            x = gamePanel.tileSize * 3;
            y += gamePanel.tileSize * 2;
            g2.drawString(text, x, y);

            text = "A/LEFT  - Bergerak ke kiri";
            x = gamePanel.tileSize * 3;
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "S/DOWN  - Bergerak ke bawah";
            x = gamePanel.tileSize * 3;
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "D/RIGHT - Bergerak ke kanan";
            x = gamePanel.tileSize * 3;
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "J/ENTER - Berinteraksi dan menyerang";
            x = gamePanel.tileSize * 3;
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "K/SPACE - Menahan serangan";
            x = gamePanel.tileSize * 3;
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "L/CTRL  - Menembak";
            x = gamePanel.tileSize * 12;
            y = gamePanel.tileSize * 5;
            g2.drawString(text, x, y);

            text = "I/C     - Membuka inventory";
            x = gamePanel.tileSize * 12;
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "M       - Membuka peta";
            x = gamePanel.tileSize * 12;
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "X       - Membuka mini map";
            x = gamePanel.tileSize * 12;
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "ESC     - Pause atau kembali";
            x = gamePanel.tileSize * 12;
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));
            text = "[Enter] - Mulai";
            x = (int) (gamePanel.tileSize * 16.5);
            y = gamePanel.tileSize * 11;
            g2.drawString(text, x, y);

            text = "[ESC] - Kembali";
            x = (int) (gamePanel.tileSize * 1);
            y = (int) (gamePanel.tileSize * 1.25);
            g2.drawString(text, x, y);

        } else if (titleScreenState == 3) {

            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            // Title Name
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
            String text = "Credits";
            int x = getXforCenteredText(text);
            int y = gamePanel.tileSize * 2;

            // Shadow
            g2.setColor(Color.gray);
            g2.drawString(text, x + 3, y + 3);

            // Main Color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // Menu
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));

            text = "CHARACTER SPRITE SHEET DESIGN";
            x = getXforHalfCenteredText(text);
            y += gamePanel.tileSize * 2;
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15F));
            text = "Arye Burhanudin";
            x = getXforHalfCenteredText(text);
            y += gamePanel.tileSize * 0.75;
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
            text = "MAP TILESET DESIGN";
            x = getXforHalfCenteredText(text);
            y += gamePanel.tileSize * 1.5;
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15F));
            text = "Rafi Ramdhani";
            x = getXforHalfCenteredText(text);
            y += gamePanel.tileSize * 0.75;
            g2.drawString(text, x, y);

            text = "Rezky Aditia Fauzan";
            x = getXforHalfCenteredText(text);
            y += gamePanel.tileSize / 2;
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
            text = "STORY / SCENARIO";
            x = getXforHalfCenteredText(text);
            y += gamePanel.tileSize * 1.5;
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15F));
            text = "Rafi Ramdhani";
            x = getXforHalfCenteredText(text);
            y += gamePanel.tileSize * 0.75;
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
            text = "PROGRAMMING & MUSIC";
            x = gamePanel.screenWidth / 2 + getXforHalfCenteredText(text);
            y = gamePanel.tileSize * 4;
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15F));
            text = "Rezky Aditia Fauzan";
            x = gamePanel.screenWidth / 2 + getXforHalfCenteredText(text);
            y += gamePanel.tileSize * 0.75;
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
            text = "TEAM";
            x = gamePanel.screenWidth / 2 + getXforHalfCenteredText(text);
            y += (int) (gamePanel.tileSize * 1.5);
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15F));
            text = "Mandala Tech: STT Mandala Bandung";
            x = gamePanel.screenWidth / 2 + getXforHalfCenteredText(text);
            y += gamePanel.tileSize * 0.75;
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15F));
            text = "Copyright 2024 Mandala Tech";
            x = gamePanel.screenWidth / 2 + getXforHalfCenteredText(text);
            y += gamePanel.tileSize * 0.5;
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));
            text = "[ESC] - Kembali";
            x = gamePanel.tileSize;
            y = (int) (gamePanel.tileSize * 1.25);
            g2.drawString(text, x, y);

        } else if (titleScreenState == 4) { // Title screen prolog 1

            // Set background
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            // Set font and color
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
            g2.setColor(Color.white);

            // ========== Prolog text ========== //
            String text = "Di sebuah desa kecil yang damai,";
            int x = getXforCenteredText(text);
            int y = gamePanel.tileSize * 2;
            g2.drawString(text, x, y);

            text = "hiduplah seorang pemuda bernama Arjuna";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "yang lebih suka tidur siang daripada menyelamatkan dunia.";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "Setiap pagi, dia terbangun dengan sinar matahari yang masuk";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize * 2;
            g2.drawString(text, x, y);

            text = "melalui jendela rumahnya yang sederhana.";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "Hari ini, seperti hari-hari lainnya, dia berencana untuk";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize * 2;
            g2.drawString(text, x, y);

            text = "tidak melakukan apa-apa, dan dia sangat berbakat dalam hal itu.";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

        } else if (titleScreenState == 5) { // Title screen prolog 2

            // Set background
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            // Set font and color
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
            g2.setColor(Color.white);

            // ========== Prolog text ========== //
            String text = "Namun, takdir tampaknya bosan";
            int x = getXforCenteredText(text);
            int y = gamePanel.tileSize * 3;
            g2.drawString(text, x, y);

            text = "dengan rutinitasnya.";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "Tanpa peringatan";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize * 2;
            g2.drawString(text, x, y);

            text = "bumi mulai bergemuruh,";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "seakan ada raksasa yang sedang";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize * 2;
            g2.drawString(text, x, y);

            text = "mencari sandal hilang di bawah tanah.";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

        } else if (titleScreenState == 99) {
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            g2.drawImage(bgchallenge, null, gamePanel);

            // Title Name
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 38F));
            String text = "Start Challenge";
            int x = getXforCenteredText(text);
            int y = gamePanel.tileSize * 2;

            // Shadow
            g2.setColor(Color.gray);
            g2.drawString(text, x + 3, y + 3);

            // Main Color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // Menu
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));
            text = "[ESC] - Kembali";
            x = gamePanel.tileSize;
            y = (int) (gamePanel.tileSize * 1.25);
            g2.drawString(text, x, y);
        }

    }

    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight / 2;

        g2.drawString(text, x, y);

    }

    public void drawDialogueScreen() {

        int x;
        int y;
        int width;
        int height;

        if (npc != null && npc.name != null && npc.avatar != null) {
            // Picture box
            x = gamePanel.tileSize * 2;
            y = gamePanel.screenHeight - (gamePanel.tileSize / 2) - gamePanel.tileSize * 4;
            width = gamePanel.tileSize * 3;
            height = gamePanel.tileSize * 3;
            drawSubWindow(x, y, width, height);
            g2.drawImage(npc.avatar, x + 15, y + 15, width - 25, height - 25, null);

            // Name box
            x = gamePanel.tileSize * 2;
            y = y + height;
            width = gamePanel.tileSize * 3;
            height = gamePanel.tileSize * 1;
            drawSubWindow(x, y, width, height);

            // Set Name box Text (centered)
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
            int length = (int) g2.getFontMetrics().getStringBounds(npc.name, g2).getWidth();
            g2.drawString(npc.name, x + (width / 2 - length / 2), y + (gamePanel.tileSize - 14));
            // g2.drawString(npc.name, x + (gamePanel.tileSize - 32), y +
            // (gamePanel.tileSize - 14));

            // Dialog box
            x = x + width;
            y = gamePanel.screenHeight - (gamePanel.tileSize / 2) - gamePanel.tileSize * 4;
            width = gamePanel.screenWidth - (gamePanel.tileSize * 7);
            height = gamePanel.tileSize * 4;
            drawSubWindow(x, y, width, height);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
            x += gamePanel.tileSize - (16);
            y += gamePanel.tileSize;
        } else {
            // Dialog box
            x = gamePanel.tileSize * 2;
            y = gamePanel.screenHeight - gamePanel.tileSize * 4;
            width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
            height = gamePanel.tileSize * 4 - (gamePanel.tileSize / 2);
            drawSubWindow(x, y, width, height);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
            x += gamePanel.tileSize - (16);
            y += gamePanel.tileSize;
        }

        if (npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
            // Display text letter by letter
            char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
            if (charIndex < characters.length) {
                gamePanel.playSoundEffect(GamePanel.SFX_SPEAK);
                String text = String.valueOf(characters[charIndex]);
                combinedText = combinedText + text;
                currentDialogue = combinedText;
                charIndex++;
            }
            if (gamePanel.keyHandler.enterPressed) {
                charIndex = 0;
                combinedText = "";
                if (gamePanel.gameState == GamePanel.DIALOGUE_STATE
                        || gamePanel.gameState == GamePanel.CUTSCENE_STATE) {
                    npc.dialogueIndex++;
                    gamePanel.keyHandler.enterPressed = false;
                }
            }
        } else { // If no text is in the array
            npc.dialogueIndex = 0;
            if (gamePanel.gameState == GamePanel.DIALOGUE_STATE) {
                gamePanel.gameState = GamePanel.PLAY_STATE;
            }
            if (gamePanel.gameState == GamePanel.CUTSCENE_STATE) {
                gamePanel.cManager.scenePhase++;
            }
        }

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawChoiceScreen() {
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.screenHeight - (gamePanel.tileSize * 8) - gamePanel.tileSize / 2;
        int width = gamePanel.tileSize * 6;
        int height = gamePanel.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        g2.drawString("Terima Quest?", x + (gamePanel.tileSize - 16), y + gamePanel.tileSize + 16);

        y += gamePanel.tileSize - 10;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.drawString("Ya", x + (gamePanel.tileSize - 16) + gamePanel.tileSize / 2, y + gamePanel.tileSize + 28);
        if (commandNumber == 0) {
            g2.drawString(">", x + (gamePanel.tileSize - 16), y + gamePanel.tileSize + 28);
        }

        y += gamePanel.tileSize - 10;
        g2.drawString("Tidak", x + (gamePanel.tileSize - 16) + gamePanel.tileSize / 2, y + gamePanel.tileSize + 30);
        if (commandNumber == 1) {
            g2.drawString(">", x + (gamePanel.tileSize - 16), y + gamePanel.tileSize + 30);
        }
    }

    public void drawCharacterScreen() {
        // Create a Frame
        final int frameX = gamePanel.tileSize * 2;
        final int frameY = gamePanel.tileSize;
        final int frameWidth = gamePanel.tileSize * 5;
        final int frameHeight = gamePanel.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20F));

        int textX = frameX + 20;
        int textY = frameY + gamePanel.tileSize;
        final int lineHeight = 35;

        // Names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Nyawa", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Kekuatan", textX, textY);
        textY += lineHeight;
        g2.drawString("Kelincahan", textX, textY);
        textY += lineHeight;
        g2.drawString("Atk", textX, textY);
        textY += lineHeight;
        g2.drawString("Def", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Koin", textX, textY);
        textY += lineHeight + 8;
        g2.drawString("Senjata", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Perisai", textX, textY);
        textY += lineHeight;

        // Values
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gamePanel.tileSize; // Reset textY
        String value;

        value = String.valueOf(gamePanel.player.level);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.life + "/" + gamePanel.player.maxLife);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.mana + "/" + gamePanel.player.maxMana);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.strength);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.dexterity);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.attack);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.defense);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.exp);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.nextLevelExp);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.coin);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gamePanel.player.currentWeapon.down1, tailX - gamePanel.tileSize, textY - 24, null);
        textY += gamePanel.tileSize;

        g2.drawImage(gamePanel.player.currentShield.down1, tailX - gamePanel.tileSize, textY - 24, null);
        textY += gamePanel.tileSize;

    }

    public void drawInventory(Entity entity, boolean cursor) {

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gamePanel.player) { // Draw player inventory window
            frameX = gamePanel.tileSize * 12;
            frameY = gamePanel.tileSize;
            frameWidth = gamePanel.tileSize * 6;
            frameHeight = gamePanel.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else { // Draw npc or trader inventory window
            frameX = gamePanel.tileSize * 2;
            frameY = gamePanel.tileSize;
            frameWidth = gamePanel.tileSize * 6;
            frameHeight = gamePanel.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // Frame
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gamePanel.tileSize + 3;

        // Draw Player's Items
        for (int i = 0; i < entity.inventory.size(); i++) {

            // Equip Cursor
            if (entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield ||
                    entity.inventory.get(i) == entity.currentLight) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            // Display stackable item amount
            if (entity == gamePanel.player && entity.inventory.get(i).amount > 1) {
                g2.setFont(g2.getFont().deriveFont(24F));
                int amountX;
                int amountY;

                // Amount
                String text = "" + entity.inventory.get(i).amount;
                amountX = getXforAlightToRightText(text, slotX + 44);
                amountY = slotY + gamePanel.tileSize;

                // Shadow
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(text, amountX, amountY);

                // Number
                g2.setColor(Color.white);
                g2.drawString(text, amountX - 3, amountY - 3);
            }

            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // Cursor
        if (cursor) {

            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gamePanel.tileSize;
            int cursorHeight = gamePanel.tileSize;

            // Draw Cursor
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // Description Frame
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gamePanel.tileSize * 3;

            // Draw Description text
            int textX = dFrameX + 20;
            int textY = dFrameY + gamePanel.tileSize;
            g2.setFont(g2.getFont().deriveFont(20F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if (itemIndex < entity.inventory.size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }

        }

    }

    public void drawOptionsScreen() {

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20F));

        // Sub window
        int frameX = gamePanel.tileSize * 6;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 8;
        int frameHeight = gamePanel.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                options_fullScreenNotification(frameX, frameY);
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3:
                options_endGameConfirmation(frameX, frameY);
                break;
        }

        gamePanel.keyHandler.enterPressed = false;

    }

    public void drawGameOverScreen() {

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        int x;
        int y;
        String text;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

        text = "Game Over";

        // Shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gamePanel.tileSize * 4;
        g2.drawString(text, x, y);

        // Main
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // Retry
        g2.setFont(g2.getFont().deriveFont(30F));
        text = "Mulai Ulang";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNumber == 0) {
            g2.drawString(">", x - 40, y);
        }

        // Back to the title screen
        text = "Keluar";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNumber == 1) {
            g2.drawString(">", x - 40, y);
        }

    }

    public void options_top(int frameX, int frameY) {

        int textX;
        int textY;

        // Title
        String text = "Pengaturan";
        textX = getXforCenteredText(text);
        textY = frameY + gamePanel.tileSize;
        g2.drawString(text, textX, textY);

        // Fullscreen on/off
        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize * 2;
        g2.drawString("Layar Penuh", textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                if (!gamePanel.fullscreenOn) {
                    gamePanel.fullscreenOn = true;
                } else if (gamePanel.fullscreenOn) {
                    gamePanel.fullscreenOn = false;
                }
                subState = 1;
            }
        }

        // Music
        textY += gamePanel.tileSize;
        g2.drawString("Musik", textX, textY);
        if (commandNumber == 1)
            g2.drawString(">", textX - 25, textY);

        // Sound Effect
        textY += gamePanel.tileSize;
        g2.drawString("SFX", textX, textY);
        if (commandNumber == 2)
            g2.drawString(">", textX - 25, textY);

        // Control
        textY += gamePanel.tileSize;
        g2.drawString("Kontrol", textX, textY);
        if (commandNumber == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 2;
                commandNumber = 0;
            }
        }

        // End game
        textY += gamePanel.tileSize;
        g2.drawString("Akhiri Permainan", textX, textY);
        if (commandNumber == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 3;
                commandNumber = 0;
            }
        }

        // Back
        textY += gamePanel.tileSize * 2;
        g2.drawString("Kembali", textX, textY);
        if (commandNumber == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                gamePanel.gameState = GamePanel.PLAY_STATE;
                commandNumber = 0;
            }
        }

        // Fullscreen checkbox
        textX = (int) (frameX + gamePanel.tileSize * 4.5);
        textY = frameY + gamePanel.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gamePanel.fullscreenOn) {
            g2.fillRect(textX, textY, 24, 24);
        }

        // Music volume
        textY += gamePanel.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gamePanel.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // Sound effect volume
        textY += gamePanel.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gamePanel.sfx.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gamePanel.config.saveConfig();

    }

    public void options_fullScreenNotification(int frameX, int frameY) {

        int textX = frameX + gamePanel.tileSize;
        int textY = frameY + gamePanel.tileSize * 3;

        currentDialogue = "Perubahan akan terjadi\nsetelah memulai ulang\npermainan.";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Back
        textY = frameY + gamePanel.tileSize * 9;
        g2.drawString("Kembali", textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 0;
            }
        }

    }

    public void options_control(int frameX, int frameY) {

        int textX;
        int textY;

        // Title
        String text = "Kontrol";
        textX = getXforCenteredText(text);
        textY = frameY + gamePanel.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize;
        g2.drawString("Bergerak", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("Aksi", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("Menyerang", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("Bertahan", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("Menembak", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("Inventori", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("Pause", textX, textY);
        textY += gamePanel.tileSize;

        textX = (int) (frameX + gamePanel.tileSize * 5.7);
        textY = frameY + gamePanel.tileSize * 2;
        g2.drawString("WASD", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("ENTER", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("J", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("K", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("L", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("I", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("ESC", textX, textY);
        textY += gamePanel.tileSize;

        // Back
        textX = frameX + gamePanel.tileSize;
        textY = frameY + gamePanel.tileSize * 9;
        g2.drawString("Kembali", textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 0;
                commandNumber = 3;
            }
        }

    }

    public void options_endGameConfirmation(int frameX, int frameY) {

        int textX = frameX + gamePanel.tileSize;
        int textY = frameY + gamePanel.tileSize * 3;

        currentDialogue = "Keluar dari game dan kembali\nke menu utama?";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Yes
        String text = "Ya";
        textX = getXforCenteredText(text);
        textY += gamePanel.tileSize * 2;
        g2.drawString(text, textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 0;
                if (gamePanel.currentArea == GamePanel.OUTSIDE_AREA) {
                    gamePanel.stopMusic(GamePanel.MUSIC_OUTDOOR);
                } else if (gamePanel.currentArea == GamePanel.INDOOR_AREA) {
                    gamePanel.stopMusic(GamePanel.MUSIC_INDOOR);
                } else if (gamePanel.currentArea == GamePanel.DUNGEON_AREA) {
                    gamePanel.stopMusic(GamePanel.MUSIC_DUNGEON);
                }
                titleScreenState = 1;
                gamePanel.gameState = GamePanel.TITLE_STATE;
                gamePanel.resetGame(true);
            }
        }

        // No
        text = "Tidak";
        textX = getXforCenteredText(text);
        textY += gamePanel.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNumber == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 0;
                commandNumber = 4;
            }
        }

    }

    public void drawTransitionScreen(int currentMusic) {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        if (counter == 50) { // The transition is done
            counter = 0;
            gamePanel.gameState = GamePanel.PLAY_STATE;
            gamePanel.currentMap = gamePanel.eventHandler.tempMap;
            gamePanel.player.worldX = gamePanel.tileSize * gamePanel.eventHandler.tempCol;
            gamePanel.player.worldY = gamePanel.tileSize * gamePanel.eventHandler.tempRow;
            gamePanel.eventHandler.previousEventX = gamePanel.player.worldX;
            gamePanel.eventHandler.previousEventY = gamePanel.player.worldY;
            gamePanel.changeArea();
        }
    }

    public void drawTradeScreen() {
        switch (subState) {
            case 0:
                trade_select();
                break;
            case 1:
                trade_buy();
                break;
            case 2:
                trade_sell();
                break;
        }
        gamePanel.keyHandler.enterPressed = false;
    }

    public void trade_select() {
        npc.dialogueSet = 4;
        drawDialogueScreen();

        // Draw window
        int x = gamePanel.tileSize * 15;
        int y = gamePanel.tileSize * 4;
        int width = (int) (gamePanel.tileSize * 3.5);
        int height = (int) (gamePanel.tileSize * 3.6);
        drawSubWindow(x, y, width, height);

        // Draw texts
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        g2.drawString("Beli", x, y);
        if (commandNumber == 0) {
            g2.drawString(">", x - 24, y);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 1;
            }
        }
        y += gamePanel.tileSize;

        g2.drawString("Jual", x, y);
        if (commandNumber == 1) {
            g2.drawString(">", x - 24, y);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 2;
            }
        }
        y += gamePanel.tileSize;

        g2.drawString("Keluar", x, y);
        if (commandNumber == 2) {
            g2.drawString(">", x - 24, y);
            if (gamePanel.keyHandler.enterPressed) {
                commandNumber = 0;
                npc.startDialogue(npc, 5);
            }
        }
        y += gamePanel.tileSize;
    }

    public void trade_buy() {

        // Draw player inventory
        drawInventory(gamePanel.player, false);

        // Draw npc inventory
        drawInventory(npc, true);

        // Draw hint window
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize * 9;
        int width = gamePanel.tileSize * 6;
        int height = gamePanel.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Kembali", x + 24, y + 60);

        // Draw player coin window
        x = gamePanel.tileSize * 12;
        y = gamePanel.tileSize * 9;
        width = gamePanel.tileSize * 6;
        height = gamePanel.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Jumlah Koinmu: " + gamePanel.player.coin, x + 24, y + 60);

        // Draw price window
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.inventory.size()) {
            x = (int) (gamePanel.tileSize * 5.5);
            y = (int) (gamePanel.tileSize * 5.5);
            width = (int) (gamePanel.tileSize * 2.5);
            height = gamePanel.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlightToRightText(text, gamePanel.tileSize * 8 - 20);
            g2.drawString(text, x, y + 34);

            // Buy an item
            if (gamePanel.keyHandler.enterPressed) {
                if (npc.inventory.get(itemIndex).price > gamePanel.player.coin) { // Coin is not enough
                    subState = 0;
                    npc.startDialogue(npc, 6);
                } else { // Coin is enough
                    if (gamePanel.player.canObtainItem(npc.inventory.get(itemIndex))) { // Item slot available
                        gamePanel.player.coin -= npc.inventory.get(itemIndex).price;
                    } else { // Item slot is full
                        subState = 0;
                        npc.startDialogue(npc, 7);
                    }
                }
            }
        }

    }

    public void trade_sell() {

        // Draw player inventory
        drawInventory(gamePanel.player, true);

        int x;
        int y;
        int width;
        int height;

        // Draw hint window
        x = gamePanel.tileSize * 2;
        y = gamePanel.tileSize * 9;
        width = gamePanel.tileSize * 6;
        height = gamePanel.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Kembali", x + 24, y + 60);

        // Draw player coin window
        x = gamePanel.tileSize * 12;
        y = gamePanel.tileSize * 9;
        width = gamePanel.tileSize * 6;
        height = gamePanel.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Jumlah Koinmu: " + gamePanel.player.coin, x + 24, y + 60);

        // Draw price window
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if (itemIndex < gamePanel.player.inventory.size()) {
            x = (int) (gamePanel.tileSize * 15.5);
            y = (int) (gamePanel.tileSize * 5.5);
            width = (int) (gamePanel.tileSize * 2.5);
            height = gamePanel.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = gamePanel.player.inventory.get(itemIndex).price / 2;
            String text = "" + price;
            x = getXforAlightToRightText(text, gamePanel.tileSize * 18 - 20);
            g2.drawString(text, x, y + 34);

            // Sell an item
            if (gamePanel.keyHandler.enterPressed) {
                if (gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentWeapon
                        || gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentShield) {
                    commandNumber = 0;
                    subState = 0;
                    npc.startDialogue(npc, 8);
                } else {
                    if (gamePanel.player.inventory.get(itemIndex).amount > 1) {
                        gamePanel.player.inventory.get(itemIndex).amount--;
                    } else {
                        gamePanel.player.inventory.remove(itemIndex);
                    }

                }
            }
        }

    }

    public void drawSleepScreen() {
        counter++;
        if (counter < 120) {
            gamePanel.eManager.lighting.filterAlpha += 0.01F;
            if (gamePanel.eManager.lighting.filterAlpha > 1F) {
                gamePanel.eManager.lighting.filterAlpha = 1F;
            }
        }
        if (counter >= 120) {
            gamePanel.eManager.lighting.filterAlpha -= 0.01F;
            if (gamePanel.eManager.lighting.filterAlpha <= 0F) {
                gamePanel.eManager.lighting.filterAlpha = 0F;
                counter = 0;
                gamePanel.eManager.lighting.dayState = gamePanel.eManager.lighting.day;
                gamePanel.eManager.lighting.dayCounter = 0;
                gamePanel.gameState = GamePanel.PLAY_STATE;
                gamePanel.player.getImage();
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 210);
        g2.setColor(color);
        g2.fillRect(x, y, width, height);

        color = new Color(255, 255, 255);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(5));
        g2.drawRect(x + 5, y + 5, width - 10, height - 10);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.screenWidth / 2 - length / 2;
    }

    public int getXforHalfCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.screenWidth / 4 - length / 2;
    }

    public int getXforAlightToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }

}
