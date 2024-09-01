package com.zkylab.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.zkylab.Main;
import com.zkylab.ai.PathFinder;
import com.zkylab.data.SaveLoad;
import com.zkylab.entity.Entity;
import com.zkylab.entity.Player;
import com.zkylab.environment.EnvironmentManager;
import com.zkylab.tile.Map;
import com.zkylab.tile.TileManager;
import com.zkylab.tile_interactive.InteractiveTile;

/**
 * GamePanel is responsible for the main game rendering and logic,
 * including handling game states, updating entities, and drawing to the screen.
 */
public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    final int originalTileSize = 16; // 16 x 16 pixels
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48 x 48 pixels
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // World Settings
    public final int maxMap = 20;
    public int currentMap = MAP_LIVING_ROOM;
    // public int currentMap = MAP_TOWN;
    public final static int MAP_HOUSE = 0;
    public final static int MAP_LIVING_ROOM = 1;
    public final static int MAP_TOWN = 2;
    public final static int MAP_ROAD = 3;
    public final static int MAP_TOWER_1 = 4;
    public final static int MAP_TOWER_2 = 5;
    public final static int MAP_TOWER_3 = 6;
    public final static int MAP_RIVER = 7;
    public final static int MAP_CHALLENGE = 8;
    public int maxWorldCol;
    public int maxWorldRow;

    // Full Screen Settings
    public boolean fullscreenOn = false;
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    // FPS Settings
    int FPS = 60;

    // System Objects
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound sfx = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    public Config config = new Config(this);
    public PathFinder pathFinder = new PathFinder(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    Map map = new Map(this);
    public SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    public CutsceneManager cManager = new CutsceneManager(this);
    Thread gameThread;

    // Entity and Object Arrays
    public Player player = new Player(this, keyHandler);
    public Entity obj[][] = new Entity[maxMap][100];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][50];
    public Entity projectile[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    // public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // Game State Constants
    public int gameState;
    public final static int TITLE_STATE = 0;
    public final static int PLAY_STATE = 1;
    public final static int PAUSE_STATE = 2;
    public final static int DIALOGUE_STATE = 3;
    public final static int CHARACTER_STATE = 4;
    public final static int OPTIONS_STATE = 5;
    public final static int GAME_OVER_STATE = 6;
    public final static int TRANSITION_STATE = 7;
    public final static int TRADE_STATE = 8;
    public final static int SLEEP_STATE = 9;
    public final static int MAP_STATE = 10;
    public final static int CUTSCENE_STATE = 11;

    // Area Constants
    public int currentArea;
    public int nextArea;
    public final static int OUTSIDE_AREA = 50;
    public final static int INDOOR_AREA = 51;
    public final static int DUNGEON_AREA = 52;

    // Music Constants
    public final static int MUSIC_INDOOR = 0;
    public final static int MUSIC_OUTDOOR = 1;
    public final static int MUSIC_DUNGEON = 2;
    public final static int SFX_EARTHQUAKE = 50;
    public final static int SFX_POWER_UP = 51;
    public final static int SFX_SWORD_SLASH = 52;
    public final static int SFX_COIN = 53;
    public final static int SFX_UNLOCK = 54;
    public final static int SFX_FANFARE = 55;
    public final static int SFX_CURSOR = 56;
    public final static int SFX_HIT_MONSTER = 57;
    public final static int SFX_RECEIVE_DAMAGE = 58;
    public final static int SFX_BURNING = 59;
    public final static int SFX_CUT_TREE = 60;
    public final static int SFX_BLOCKED = 61;
    public final static int SFX_PARRY = 62;
    public final static int SFX_SPEAK = 63;
    public final static int SFX_DEAD = 64;
    public final static int SFX_GAME_OVER = 65;
    public final static int SFX_STAIRS = 66;
    public final static int SFX_SLEEP = 67;
    public final static int SFX_DOOR_OPEN = 68;



    // Others
    public boolean bossBattleOn = false;
    public int currentMusicIndex = 0;

    /**
     * Constructor for GamePanel.
     * Initializes the panel's settings and prepares it for game rendering.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // Improve game's rendering
        this.addKeyListener(keyHandler);
        this.setFocusable(true); // Game panel can be focused to receive input
    }

    /**
     * Sets up the game by initializing assets and game states.
     */
    public void setupGame() {
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        assetSetter.setInteractiveTile();
        eManager.setup();

        gameState = TITLE_STATE;
        currentArea = INDOOR_AREA;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullscreenOn) {
            setFullScreen();
        }
    }

    /**
     * Resets the game state, optionally restarting the game.
     *
     * @param restart Whether to restart the game or just reset the state.
     */
    public void resetGame(boolean restart) {
        // stopMusic();
        currentArea = INDOOR_AREA;
        removeTempEntity();
        bossBattleOn = false;
        player.restoreStatus();
        player.resetCounter();
        assetSetter.setNPC();
        assetSetter.setMonster();

        if (restart) {
            player.setDefaultValues();
            player.setDefaultPosition();
            assetSetter.setObject();
            assetSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }
    }

    /**
     * Starts the game thread which controls the game loop.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Sets the game panel to full-screen mode.
     */
    public void setFullScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        screenWidth2 = (int) width;
        screenHeight2 = (int) height;
    }

    /**
     * Main game loop. Updates game state and redraws the screen.
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta > 1) {
                update();
                drawToTempScreen(); // Draw everything to the buffered image
                drawToScreen(); // Draw the buffered image to the screen
                delta--;
            }
        }
    }

    /**
     * Updates the game state, including entities and game logic.
     */
    public void update() {
        if (gameState == PLAY_STATE) {
            player.update();
            updateEntities();
            updateParticles();
            updateInteractiveTiles();
            eManager.update(); // Upload lightning
        }
        if (gameState == CUTSCENE_STATE) {
            // // If collision is false, player can move
            // if (!player.collisionOn) {
            // switch (player.direction) {
            // case "up":
            // player.worldY -= player.speed;
            // break;
            // case "down":
            // player.worldY += player.speed;
            // break;
            // case "left":
            // player.worldX -= player.speed;
            // break;
            // case "right":
            // player.worldX += player.speed;
            // break;
            // }
            // }

            // // Change walking image every 10 frames
            // player.spriteCounter++;
            // if (player.spriteCounter > 12) {
            // if (player.spriteNumber == 2 || player.spriteNumber == 1) {
            // player.spriteNumber = 3;
            // } else if (player.spriteNumber == 3 || player.spriteNumber == 1) {
            // player.spriteNumber = 2;
            // }
            // player.spriteCounter = 0;
            // }

            // NPC
            // for (int i = 0; i < npc[1].length; i++) {
            // if (npc[currentMap][i] != null) {
            // npc[currentMap][i].setAction();
            // npc[currentMap][i].checkCollision();
            // // If collision is false, player can move
            // if (!npc[currentMap][i].collisionOn) {
            // switch (npc[currentMap][i].direction) {
            // case "up":
            // npc[currentMap][i].worldY -= npc[currentMap][i].speed;
            // break;
            // case "down":
            // npc[currentMap][i].worldY += npc[currentMap][i].speed;
            // break;
            // case "left":
            // npc[currentMap][i].worldX -= npc[currentMap][i].speed;
            // break;
            // case "right":
            // npc[currentMap][i].worldX += npc[currentMap][i].speed;
            // break;
            // }
            // }
            // }
            // }
            updateEntities();
        }
    }

    /**
     * Updates NPC, monster, and projectile entities.
     */
    private void updateEntities() {
        // NPC
        for (int i = 0; i < npc[1].length; i++) {
            if (npc[currentMap][i] != null) {
                npc[currentMap][i].update();
            }
        }

        // Monster
        for (int i = 0; i < monster[1].length; i++) {
            if (monster[currentMap][i] != null) {
                if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                    monster[currentMap][i].update();
                }
                if (!monster[currentMap][i].alive) {
                    monster[currentMap][i].checkDrop();
                    monster[currentMap][i] = null;
                }
            }
        }

        // Projectile
        for (int i = 0; i < projectile[1].length; i++) {
            if (projectile[currentMap][i] != null) {
                if (projectile[currentMap][i].alive) {
                    projectile[currentMap][i].update();
                }
                if (!projectile[currentMap][i].alive) {
                    projectile[currentMap][i] = null;
                }
            }
        }
    }

    /**
     * Updates particle entities.
     */
    private void updateParticles() {
        for (int i = 0; i < particleList.size(); i++) {
            if (particleList.get(i) != null) {
                if (particleList.get(i).alive) {
                    particleList.get(i).update();
                }
                if (!particleList.get(i).alive) {
                    particleList.remove(i);
                }
            }
        }
    }

    /**
     * Updates interactive tiles.
     */
    private void updateInteractiveTiles() {
        for (InteractiveTile tile : iTile[currentMap]) {
            if (tile != null) {
                tile.update();
            }
        }
    }

    /**
     * Draws the game scene to a temporary buffered image.
     */
    public void drawToTempScreen() {
        // Debug press T in play state
        long drawStart = keyHandler.showDebugText ? System.nanoTime() : 0;

        switch (gameState) {
            case TITLE_STATE:
                ui.draw(g2);
                break;
            case MAP_STATE:
                map.drawFullMapScreen(g2);
                break;
            default:
                drawMainScreen();
                break;
        }

        // Debug
        if (keyHandler.showDebugText) {
            drawDebugInfo(drawStart);
        }
    }

    public void drawMainScreen() {
        tileManager.draw(g2);

        for (InteractiveTile tile : iTile[currentMap]) {
            if (tile != null) {
                tile.draw(g2);
            }
        }

        entityList.add(player);
        addEntitiesToList();

        Collections.sort(entityList, new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                int result = Integer.compare(e1.worldY, e2.worldY);
                return result;
            }
        });

        for (Entity entity : entityList) {
            entity.draw(g2);
        }

        // for (InteractiveTile tile : iTile[currentMap]) {
        // if (tile != null) {
        // tile.draw(g2);
        // }
        // }

        entityList.clear();
        eManager.draw(g2);
        map.drawMiniMap(g2);
        cManager.draw(g2);
        ui.draw(g2);
    }

    /**
     * Adds npc, object, monster, projectile, and particle to the entity list.
     */
    private void addEntitiesToList() {
        for (int i = 0; i < npc[1].length; i++) {
            if (npc[currentMap][i] != null) {
                entityList.add(npc[currentMap][i]);
            }
        }
        for (int i = 0; i < obj[1].length; i++) {
            if (obj[currentMap][i] != null) {
                entityList.add(obj[currentMap][i]);
            }
        }
        for (int i = 0; i < monster[1].length; i++) {
            if (monster[currentMap][i] != null) {
                entityList.add(monster[currentMap][i]);
            }
        }
        for (int i = 0; i < projectile[1].length; i++) {
            if (projectile[currentMap][i] != null) {
                entityList.add(projectile[currentMap][i]);
            }
        }
        for (int i = 0; i < particleList.size(); i++) {
            if (particleList.get(i) != null) {
                entityList.add(particleList.get(i));
            }
        }
    }

    /**
     * Draws the debug information to the screen if debugging is enabled.
     *
     * @param drawStart The start time for the draw operation.
     */
    public void drawDebugInfo(long drawStart) {
        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.white);

        int x = 10;
        int y = 400;
        int lineHeight = 30;

        g2.drawString("WorldX: " + player.worldX, x, y);
        y += lineHeight;
        g2.drawString("WorldY: " + player.worldY, x, y);
        y += lineHeight;
        g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y);
        y += lineHeight;
        g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y);
        y += lineHeight;
        g2.drawString("Draw time: " + passed, x, y);
        y += lineHeight;
        g2.drawString("God mode: " + keyHandler.godModeOn, x, y);
        y += lineHeight;
    }

    /**
     * Draws the temporary screen image to the game panel.
     */
    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    /**
     * Plays background music.
     *
     * @param i The index of the music file.
     */
    public void playMusic(int i) {
        // music.setFile(i);
        music.play(i);
        music.loop(i);
    }

    /**
     * Stops the background music.
     */
    public void stopMusic(int i) {
        music.stop(i);
    }

    /**
     * Plays a sound effect.
     *
     * @param i The index of the sound effect file.
     */
    public void playSoundEffect(int i) {
        // sfx.setFile(i);
        sfx.play(i);
    }

    /**
     * Changes the game area and updates the music and assets accordingly.
     */
    public void changeArea() {
        if (nextArea != currentArea) {
            // Stop music
            if (currentArea == OUTSIDE_AREA) {
                stopMusic(MUSIC_OUTDOOR);
            } else if (currentArea == INDOOR_AREA) {
                stopMusic(MUSIC_INDOOR);
            } else if (currentArea == DUNGEON_AREA) {
                stopMusic(MUSIC_DUNGEON);
            }

            // Play new music
            if (nextArea == OUTSIDE_AREA)
                playMusic(MUSIC_OUTDOOR);
            if (nextArea == INDOOR_AREA)
                playMusic(MUSIC_INDOOR);
            if (nextArea == DUNGEON_AREA)
                playMusic(MUSIC_DUNGEON);
            assetSetter.setNPC();
        }
        currentArea = nextArea;
        assetSetter.setMonster();
    }

    /**
     * Removes temporary entities from the game.
     */
    public void removeTempEntity() {
        for (int mapNumber = 0; mapNumber < maxMap; mapNumber++) {
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[mapNumber][i] != null && obj[mapNumber][i].temp) {
                    obj[mapNumber][i] = null;
                }
            }
        }
    }
}
