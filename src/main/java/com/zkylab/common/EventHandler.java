package com.zkylab.common;

import com.zkylab.data.Progress;
import com.zkylab.entity.Entity;

/**
 * Handles events such as dialogues, teleportation, and interactions in the
 * game.
 */
public class EventHandler {

    GamePanel gamePanel; // Game panel reference
    EventRect eventRect[][][]; // 3D array for event rectangles
    public static Entity eventMaster; // Entity responsible for dialogues and other events
    int previousEventX, previousEventY; // Previous event coordinates
    public boolean monsterMode = false;

    // Flags and temporary variables
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    /**
     * Constructor to initialize the event handler.
     *
     * @param gamePanel The GamePanel instance to interact with the game world.
     */
    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        eventMaster = new Entity(gamePanel);

        // Initialize the eventRect array
        eventRect = new EventRect[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        initializeEventRect();

        // Set default dialogues
        setDialogue();
    }

    public void initializeEventRect() {
        int map = 0, col = 0, row = 0;
        while (map < gamePanel.maxMap && col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
                if (row == gamePanel.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    /**
     * Sets default dialogues for various events.
     */
    public void setDialogue() {
        eventMaster.dialogues[0][0] = "Sebuah kursi kosong.";
        eventMaster.dialogues[1][0] = "Kamu meminum air. Energi dipulihkan.";
        eventMaster.dialogues[1][1] = "(Progress telah tersimpan)";
        eventMaster.dialogues[2][0] = "Pohon besar yang kokoh.";
        eventMaster.dialogues[3][0] = "Sebuah mobil tua terparkir di bawah tenda.";
        eventMaster.dialogues[3][1] = "Sepertinya masih bisa diperbaiki.";
        eventMaster.dialogues[4][0] = "Tempat sampah ini kosong";
        eventMaster.dialogues[5][0] = "Kompor tua yang tidak dapat digunakan.";
        eventMaster.dialogues[5][1] = "Namun masih panas.";
        eventMaster.dialogues[6][0] = "Seketika kota tiba-tiba menjadi gelap.";
        eventMaster.dialogues[6][1] = "Meskipun ini masih siang.";
        eventMaster.dialogues[6][2] = "Nyalakan lentera di inventorimu. Tekan [C].";
        eventMaster.dialogues[7][0] = "Aku tahu kamu butuh air...";
        eventMaster.dialogues[7][1] = "Tapi bicaralah dengan pemiliknya dulu!";
        eventMaster.dialogues[8][0] = "Hangatnya api ini terasa menenangkan di tengah kekacauan.";
        eventMaster.dialogues[9][0] = "Pintu ini sepertinya tidak akan pernah terbuka.";
        eventMaster.dialogues[10][0] = "Telepon berdering sekali... Lalu senyap.";
        eventMaster.dialogues[10][1] = "Saat diangkat, terdengar pesan suara yang samar:";
        eventMaster.dialogues[10][2] = "\"Hati-hati di jalan... tempat ini tidak seperti dulu lagi.\"";
        eventMaster.dialogues[10][3] = "...";
        eventMaster.dialogues[10][4] = "Entah siapa yang meninggalkan pesan itu.";
        eventMaster.dialogues[10][5] = "Mungkin lebih baik tetap waspada.";
        eventMaster.dialogues[11][0] = "Jam dinding berhenti.";
        eventMaster.dialogues[11][1] = "Waktu seolah terhenti sejak gempa itu...";
        eventMaster.dialogues[11][2] = "Atau mungkin baterainya habis.";
        eventMaster.dialogues[12][0] = "Sofa ini terlihat lebih nyaman dari kasurmu.";
        eventMaster.dialogues[13][0] = "Kipas ini tak lagi berfungsi.";
        eventMaster.dialogues[13][1] = "Debunya pun mulai menumpuk.";
        eventMaster.dialogues[14][0] = "Hey! Ini bukan rumahmu!";
        eventMaster.dialogues[14][1] = "Kau pikir bisa masuk begitu saja?";
        eventMaster.dialogues[15][0] = "Lemari ini tampak rapuh, hampir roboh.";
        eventMaster.dialogues[15][1] = "Di dalamnya ada tumpukan catatan lama...";
        eventMaster.dialogues[15][2] = "Catatan-catatan ini penuh dengan informasi tentang gempa\nbumi.";
        eventMaster.dialogues[15][3] = "\"Pastikan benda berat selalu diamankan,\" tertulis pada\nsalah satu catatan.";
        eventMaster.dialogues[15][4] = "Seseorang benar-benar berusaha untuk mengingatkan kita akan\nbahaya.";
        eventMaster.dialogues[16][0] = "Lemari tua ini penuh dengan kertas yang berserakan.";
        eventMaster.dialogues[16][1] = "Di antara kertas-kertas itu, ada catatan tentang jenis-jenis\ngempa.";
        eventMaster.dialogues[16][2] = "\"Gempa tektonik terjadi akibat pergerakan lempeng,\" tertulis\ndi salah satu catatan.";
        eventMaster.dialogues[16][3] = "\"Gempa vulkanik, di sisi lain, disebabkan oleh aktivitas\ngunung berapi.\"";
        eventMaster.dialogues[16][4] = "Sepertinya penulis catatan ini sangat memahami apa yang\nterjadi...";
        eventMaster.dialogues[17][0] = "Sebaiknya kamu tidak mengekplorasi gedung berbahaya ini.";
        eventMaster.dialogues[18][0] = "Layar televisi ini sudah rusak.";
        eventMaster.dialogues[18][1] = "Namun spare-part nya masih bagus.";
        eventMaster.dialogues[19][0] = "Mobil ini terlihat parah, penyok di sana-sini.";
        eventMaster.dialogues[19][1] = "Mesin mungkin tidak bisa dinyalakan lagi...";
        eventMaster.dialogues[19][2] = "Tapi siapa tahu, dengan sedikit usaha, mungkin masih bisa diperbaiki.";
        eventMaster.dialogues[20][0] = "Sebuah batu besar jatuh dari reruntuhan gedung.";
        eventMaster.dialogues[20][1] = "Berat dan kokoh, tampaknya mustahil untuk dipindahkan sendirian.";
        eventMaster.dialogues[20][2] = "Lebih baik mencari jalan lain, daripada mencoba menggesernya.";
    }

    /**
     * Checks for events and triggers the appropriate actions based on player
     * position.
     */
    public void checkEvent() {
        // Check if the player character is more than 1 tile away from the last event
        // int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        // int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        // int distance = Math.max(xDistance, yDistance);

        // if (distance > gamePanel.tileSize) {
        canTouchEvent = true;
        // }

        if (canTouchEvent) {
            handleEvents();
        }

    }

    /**
     * Handles specific events based on player interactions with event rectangles.
     */
    private void handleEvents() {

        // ========== EVENT TELEPORT ========== //

        // living room to house
        if (hit(GamePanel.MAP_LIVING_ROOM, 27, 30, "down"))
            teleport(GamePanel.MAP_HOUSE, 24, 23, GamePanel.OUTSIDE_AREA);
        else if (hit(GamePanel.MAP_HOUSE, 24, 23, "up"))
            openTheDoor(GamePanel.MAP_LIVING_ROOM, 27, 30, GamePanel.INDOOR_AREA);

        // house to road
        else if (hit(GamePanel.MAP_HOUSE, 24, 31, "down"))
            teleport(GamePanel.MAP_ROAD, 19, 9, GamePanel.OUTSIDE_AREA);
        else if (hit(GamePanel.MAP_ROAD, 19, 9, "up"))
            teleport(GamePanel.MAP_HOUSE, 24, 31, GamePanel.OUTSIDE_AREA);

        // road to river
        else if (hit(GamePanel.MAP_ROAD, 38, 28, "right"))
            teleport(GamePanel.MAP_RIVER, 17, 30, GamePanel.OUTSIDE_AREA);
        else if (hit(GamePanel.MAP_RIVER, 17, 30, "left"))
            teleport(GamePanel.MAP_ROAD, 38, 28, GamePanel.OUTSIDE_AREA);

        // river to town
        else if (hit(GamePanel.MAP_RIVER, 32, 30, "right"))
            teleport(GamePanel.MAP_TOWN, 10, 30, GamePanel.OUTSIDE_AREA);
        else if (hit(GamePanel.MAP_TOWN, 10, 29, "left") || hit(GamePanel.MAP_TOWN, 10, 30, "left")
                || hit(GamePanel.MAP_TOWN, 10, 31, "left"))
            teleport(GamePanel.MAP_RIVER, 32, 30, GamePanel.OUTSIDE_AREA);

        // town to gedung01
        else if (hit(GamePanel.MAP_TOWN, 12, 29, "up"))
            openTheDoor(GamePanel.MAP_TOWER_1, 25, 33, GamePanel.DUNGEON_AREA);
        else if (hit(GamePanel.MAP_TOWER_1, 25, 33, "down"))
            teleport(GamePanel.MAP_TOWN, 12, 29, GamePanel.OUTSIDE_AREA);

        // town to gedung02
        else if (hit(GamePanel.MAP_TOWN, 18, 29, "up"))
            openTheDoor(GamePanel.MAP_TOWER_2, 20, 37, GamePanel.DUNGEON_AREA);
        else if (hit(GamePanel.MAP_TOWER_2, 20, 37, "down"))
            teleport(GamePanel.MAP_TOWN, 18, 29, GamePanel.OUTSIDE_AREA);

        // town to gedung03
        else if (hit(GamePanel.MAP_TOWN, 24, 29, "up"))
            openTheDoor(GamePanel.MAP_TOWER_3, 28, 43, GamePanel.DUNGEON_AREA);
        else if (hit(GamePanel.MAP_TOWER_3, 28, 43, "down"))
            teleport(GamePanel.MAP_TOWN, 24, 29, GamePanel.OUTSIDE_AREA);

        // road to green
        else if (hit(GamePanel.MAP_ROAD, 26, 34, "down"))
            teleport(GamePanel.MAP_GREEN, 24, 17, GamePanel.OUTSIDE_AREA);

        // road to challenge
        else if (hit(GamePanel.MAP_GREEN, 24, 39, "down"))
            teleport(GamePanel.MAP_CHALLENGE, 30, 17, GamePanel.DUNGEON_AREA);

        // ========== INTERACT OBJECT ========= //

        // kursi
        else if (hit(GamePanel.MAP_HOUSE, 18, 25, "right") || hit(GamePanel.MAP_HOUSE, 19, 24, "down")
                || hit(GamePanel.MAP_HOUSE, 19, 26, "up") || hit(GamePanel.MAP_HOUSE, 20, 25, "left"))
            interactObject(0);

        // kolam
        else if (hit(GamePanel.MAP_HOUSE, 27, 28, "right") || hit(GamePanel.MAP_HOUSE, 27, 29, "right")
                || hit(GamePanel.MAP_HOUSE, 27, 30, "right")
                || hit(GamePanel.MAP_HOUSE, 27, 30, "right"))
            healingPool();

        // pohon
        else if (hit(0, 18, 21, "up") || hit(0, 19, 21, "up") || hit(0, 20, 21, "up")
                || hit(0, 21, 21, "up")
                || hit(0, 21, 18, "left") || hit(0, 21, 19, "left") || hit(0, 21, 20,
                        "left"))
            interactObject(2);

        // mobil
        else if (hit(GamePanel.MAP_HOUSE, 29, 23, "up") || hit(GamePanel.MAP_HOUSE, 30, 23, "up")
                || hit(GamePanel.MAP_HOUSE, 28, 20, "right") || hit(GamePanel.MAP_HOUSE, 28, 21, "right")
                || hit(GamePanel.MAP_HOUSE, 28, 22, "right") || hit(GamePanel.MAP_HOUSE, 29, 19, "down")
                || hit(GamePanel.MAP_HOUSE, 30, 19, "down"))
            interactObject(3);

        // tempat sampah
        else if (hit(GamePanel.MAP_HOUSE, 27, 23, "up") || hit(GamePanel.MAP_HOUSE, 28, 22, "left")
                || hit(GamePanel.MAP_HOUSE, 27, 21, "down"))
            interactObject(5);

        // kompor tua
        else if (hit(GamePanel.MAP_HOUSE, 27, 21, "up") || hit(GamePanel.MAP_HOUSE, 28, 20, "left")
                || hit(GamePanel.MAP_HOUSE, 27, 19, "down"))
            interactObject(6);

        // truk
        else if (hit(GamePanel.MAP_TOWN, 27, 19, "down") || hit(GamePanel.MAP_TOWN, 28, 19, "down"))
            interactObject(7);

        // perapian
        else if (hit(GamePanel.MAP_LIVING_ROOM, 26, 23, "up") || hit(GamePanel.MAP_LIVING_ROOM, 27, 23, "up"))
            interactObject(8);

        // pintu
        else if (hit(GamePanel.MAP_LIVING_ROOM, 23, 23, "up") || hit(GamePanel.MAP_LIVING_ROOM, 30, 23, "up"))
            interactObject(9);

        // telepon
        else if (hit(GamePanel.MAP_LIVING_ROOM, 24, 23, "up") || hit(GamePanel.MAP_LIVING_ROOM, 25, 23, "up"))
            interactObject(10);

        // jam
        else if (hit(GamePanel.MAP_LIVING_ROOM, 28, 23, "up") || hit(GamePanel.MAP_LIVING_ROOM, 29, 23, "up"))
            interactObject(11);

        // sofa
        else if (hit(GamePanel.MAP_LIVING_ROOM, 24, 24, "down") || hit(GamePanel.MAP_LIVING_ROOM, 30, 25, "left")
                || hit(GamePanel.MAP_LIVING_ROOM, 25, 26, "left") || hit(GamePanel.MAP_LIVING_ROOM, 25, 27, "left")
                || hit(GamePanel.MAP_LIVING_ROOM, 24, 28, "up") || hit(GamePanel.MAP_LIVING_ROOM, 23, 25, "right")
                || hit(GamePanel.MAP_LIVING_ROOM, 23, 26, "right") || hit(GamePanel.MAP_LIVING_ROOM, 23, 27, "right")
                || hit(GamePanel.MAP_LIVING_ROOM, 29, 24, "down") || hit(GamePanel.MAP_LIVING_ROOM, 25, 25, "left")
                || hit(GamePanel.MAP_LIVING_ROOM, 30, 26, "left") || hit(GamePanel.MAP_LIVING_ROOM, 30, 27, "left")
                || hit(GamePanel.MAP_LIVING_ROOM, 29, 28, "up") || hit(GamePanel.MAP_LIVING_ROOM, 28, 25, "right")
                || hit(GamePanel.MAP_LIVING_ROOM, 28, 26, "right") || hit(GamePanel.MAP_LIVING_ROOM, 28, 27, "right"))
            interactObject(12);

        // kipas
        else if (hit(GamePanel.MAP_LIVING_ROOM, 25, 25, "right") || hit(GamePanel.MAP_LIVING_ROOM, 25, 26, "right")
                || hit(GamePanel.MAP_LIVING_ROOM, 28, 25, "left") || hit(GamePanel.MAP_LIVING_ROOM, 28, 26, "left"))
            interactObject(13);

        // pintu tetangga
        else if (hit(GamePanel.MAP_RIVER, 23, 26, "up"))
            interactObject(14);

        // lemari tower 1
        else if (hit(GamePanel.MAP_TOWER_1, 18, 22, "up") || hit(GamePanel.MAP_TOWER_1, 19, 22, "up") || hit(GamePanel.MAP_TOWER_1, 20, 22, "up"))
            interactObject(15);

        // lemari tower 1
        else if (hit(GamePanel.MAP_TOWER_1, 18, 28, "up") || hit(GamePanel.MAP_TOWER_1, 19, 28, "up") || hit(GamePanel.MAP_TOWER_1, 30, 25, "up") || hit(GamePanel.MAP_TOWER_1, 31, 25, "up"))
            interactObject(16);

        // pintu tower 2
        else if (hit(GamePanel.MAP_TOWER_1, 22, 21, "up") || hit(GamePanel.MAP_TOWER_1, 23, 21, "up") || hit(GamePanel.MAP_TOWER_1, 28, 21, "up") || hit(GamePanel.MAP_TOWER_1, 29, 21, "up"))
            interactObject(17);

        // pintu tower 4
        else if (hit(GamePanel.MAP_TOWN, 37, 29, "up"))
            interactObject(9);
        
        // tv tower 3
        else if (hit(GamePanel.MAP_TOWER_3, 24, 25, "up") || hit(GamePanel.MAP_TOWER_3, 36, 25, "up"))
            interactObject(18);
        
        // mobil rusak town
        else if (hit(GamePanel.MAP_TOWN, 17, 19, "right") || hit(GamePanel.MAP_TOWN, 17, 20, "right") || hit(GamePanel.MAP_TOWN, 18, 18, "down") || hit(GamePanel.MAP_TOWN, 19, 18, "down") || hit(GamePanel.MAP_TOWN, 21, 19, "left") || hit(GamePanel.MAP_TOWN, 21, 20, "left") ||
                hit(GamePanel.MAP_TOWN, 33, 31, "right") || hit(GamePanel.MAP_TOWN, 33, 32, "right") || hit(GamePanel.MAP_TOWN, 34, 30, "down") || hit(GamePanel.MAP_TOWN, 35, 30, "down") || hit(GamePanel.MAP_TOWN, 37, 31, "left") || hit(GamePanel.MAP_TOWN, 34, 33, "up") || hit(GamePanel.MAP_TOWN, 35, 33, "up")
        )
            interactObject(19);

        // batu town
        else if (hit(GamePanel.MAP_TOWN, 12, 18, "right") || hit(GamePanel.MAP_TOWN, 12, 19, "right") || hit(GamePanel.MAP_TOWN, 13, 20, "up") || hit(GamePanel.MAP_TOWN, 15, 20, "left") || hit(GamePanel.MAP_TOWN, 15, 20, "up") || hit(GamePanel.MAP_TOWN, 16, 17, "left") || hit(GamePanel.MAP_TOWN, 16, 18, "left"))
            interactObject(20);

        // temp challenge map
        // else if (hit(GamePanel.MAP_LIVING_ROOM, 30, 23, "up"))
        // openTheDoor(GamePanel.MAP_CHALLENGE, 30, 17, GamePanel.OUTSIDE_AREA);
        // else if (hit(GamePanel.MAP_CHALLENGE, 30, 17, "up"))
        // teleport(GamePanel.MAP_LIVING_ROOM, 30, 23, GamePanel.INDOOR_AREA);

        // ========== Cutscenes ========== //
        else if (hit(GamePanel.MAP_HOUSE, 24, 25, "any"))
            sceneWarning();

        // else if (hit(GamePanel.MAP_TOWN, 11, 30, "right"))
        // lanternEvent(GamePanel.MAP_TOWN, 11, 30, 6); // lantern

        else if (hit(GamePanel.MAP_TOWN, 14, 29, "any") || hit(GamePanel.MAP_TOWN, 14, 30, "any")
                || hit(GamePanel.MAP_TOWN, 14, 31, "any") || hit(GamePanel.MAP_TOWN, 14, 32, "any")
                || hit(GamePanel.MAP_TOWN, 14, 33, "any"))
            sceneWater();   

    }

    /**
     * Checks if the player is interacting with a specific event rectangle.
     *
     * @param map          The map index.
     * @param col          The column index.
     * @param row          The row index.
     * @param reqDirection The required direction for the interaction.
     * @return true if the player is interacting with the event rectangle; false
     *         otherwise.
     */
    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gamePanel.currentMap) {
            gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
            gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
            eventRect[map][col][row].x = col * gamePanel.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gamePanel.tileSize + eventRect[map][col][row].y;

            if (gamePanel.player.solidArea.intersects(eventRect[map][col][row])
                    && !eventRect[map][col][row].eventDone) {
                if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                    previousEventX = gamePanel.player.worldX;
                    previousEventY = gamePanel.player.worldY;

                }
            }

            // Reset areas to default
            gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
            gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;

        }

        return hit;
    }

    public boolean hit(int map, int col, int row, int col2, int row2, String reqDirection) {
        boolean hit = false;
        // col1 = 14, col2 = 16
        if (map == gamePanel.currentMap) {
            gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
            gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

            int tempCol = col2 - col;
            int tempRow = row2 - row;

            if (tempCol == 0) {
                eventRect[map][col][row].x = col * gamePanel.tileSize + eventRect[map][col][row].x;
            } else {
                int multiply = tempCol + 1;
                eventRect[map][col][row].x = (col * multiply) * gamePanel.tileSize + eventRect[map][col][row].x;
            }

            if (tempRow == 0) {
                eventRect[map][col][row].y = row * gamePanel.tileSize + eventRect[map][col][row].y;
            } else {
                int multiply = tempRow + 1;
                eventRect[map][col][row].y = (row * multiply) * gamePanel.tileSize + eventRect[map][col][row].y;
            }

            if (gamePanel.player.solidArea.intersects(eventRect[map][col][row])
                    && !eventRect[map][col][row].eventDone) {
                if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                    previousEventX = gamePanel.player.worldX;
                    previousEventY = gamePanel.player.worldY;

                }
            }

            // Reset areas to default
            gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
            gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;

        }

        return hit;
    }

    public void autoDialog(int dialogue) {
        gamePanel.gameState = GamePanel.DIALOGUE_STATE;
        gamePanel.player.attackCanceled = true;
        eventMaster.startDialogue(eventMaster, dialogue);
    }

    public void openTheDoor(int map, int col, int row, int gameState) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.player.attackCanceled = true;
            teleport(map, col, row, gameState);
        }
    }

    /**
     * Handles interactions with objects.
     *
     * @param col       The column index.
     * @param row       The row index.
     * @param gameState The game state to transition to.
     * @param dialog    The dialogue index to display.
     */
    public void interactObject(int dialog) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = GamePanel.DIALOGUE_STATE;
            gamePanel.player.attackCanceled = true;
            gamePanel.playSoundEffect(GamePanel.SFX_CUT_TREE);
            eventMaster.startDialogue(eventMaster, dialog);
        }
    }

    /**
     * Handles damage pit interactions.
     *
     * @param col       The column index.
     * @param row       The row index.
     * @param gameState The game state to transition to.
     */
    public void damagePit(int col, int row, int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.playSoundEffect(GamePanel.SFX_COIN);
        eventMaster.startDialogue(eventMaster, 0);
        gamePanel.player.life -= 1;
        eventRect[0][col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void lanternEvent(int map, int col1, int row1, int dialogue) {
        gamePanel.gameState = GamePanel.DIALOGUE_STATE;
        eventMaster.startDialogue(eventMaster, dialogue);
        eventRect[map][col1][row1].eventDone = true;
        canTouchEvent = false;
    }

    /**
     * Handles healing pool interactions.
     *
     * @param col       The column index.
     * @param row       The row index.
     *                  s
     * @param gameState The game state to transition to.
     */
    public void healingPool() {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = GamePanel.DIALOGUE_STATE;
            gamePanel.player.attackCanceled = true;
            gamePanel.playSoundEffect(GamePanel.SFX_POWER_UP);
            eventMaster.startDialogue(eventMaster, 1);
            gamePanel.player.life = gamePanel.player.maxLife;
            gamePanel.player.mana = gamePanel.player.maxMana;
            // gamePanel.assetSetter.setMonster(); // optional for testing purpose. reset
            // monster
            gamePanel.saveLoad.save();
        }
    }

    /**
     * Handles teleportation events.
     *
     * @param map  The target map index.
     * @param col  The target column index.
     * @param row  The target row index.
     * @param area The target area to transition to.
     */
    public void teleport(int map, int col, int row, int area) {
        gamePanel.gameState = GamePanel.TRANSITION_STATE;
        gamePanel.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gamePanel.playSoundEffect(GamePanel.SFX_STAIRS);
    }

    /**
     * Initiates dialogue with an entity.
     *
     * @param entity The entity to speak with.
     */
    public void speak(Entity entity) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = GamePanel.DIALOGUE_STATE;
            gamePanel.player.attackCanceled = true;
            entity.speak();
        }
    }

    /**
     * Handles the skeleton lord cutscene event.
     */
    public void skeletonLord() {
        if (!gamePanel.bossBattleOn && !Progress.skeletonLordDefeated) {
            gamePanel.gameState = GamePanel.CUTSCENE_STATE;
            gamePanel.cManager.sceneNumber = gamePanel.cManager.skeletonLord;
        }
    }

    /**
     * Handles the warning cutscene event.
     */
    public void sceneWarning() {
        if (!Progress.cutsceneWarningFinished) {
            gamePanel.gameState = GamePanel.CUTSCENE_STATE;
            gamePanel.cManager.sceneNumber = gamePanel.cManager.warning;
        }
    }

    public void sceneWater() {
        if (!Progress.cutsceneWaterFinished) {
            gamePanel.gameState = GamePanel.CUTSCENE_STATE;
            gamePanel.cManager.sceneNumber = gamePanel.cManager.water;
        }
    }

}
