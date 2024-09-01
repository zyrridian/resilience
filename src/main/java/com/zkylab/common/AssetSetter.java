package com.zkylab.common;

import com.zkylab.entity.NPC_Aksa;
import com.zkylab.entity.NPC_Bayu;
import com.zkylab.entity.NPC_Bima;
import com.zkylab.entity.NPC_Bumi;
import com.zkylab.entity.NPC_Chakara;
import com.zkylab.entity.NPC_Genta;
import com.zkylab.entity.NPC_Lestari;
import com.zkylab.entity.NPC_Rangga;
import com.zkylab.entity.NPC_Rimba;
import com.zkylab.entity.NPC_Sari;
import com.zkylab.entity.NPC_Sastro;
import com.zkylab.entity.NPC_Surya;
import com.zkylab.entity.NPC_Tirta;
import com.zkylab.monster.MON_Glimp;
import com.zkylab.monster.MON_Mebot;
import com.zkylab.monster.MON_Nibby;
import com.zkylab.monster.MON_Toonamy;
import com.zkylab.object.OBJ_Door;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {

        int mapNumber = GamePanel.MAP_ROAD;
        int i = 0;

        gamePanel.obj[mapNumber][i] = new OBJ_Door(gamePanel); // if i press load game, it wont appear
        gamePanel.obj[mapNumber][i].worldX = gamePanel.tileSize * 26;
        gamePanel.obj[mapNumber][i].worldY = gamePanel.tileSize * 29;
        i++;


    }

    public void setNPC() {

        int mapNumber = GamePanel.MAP_HOUSE;
        int i = 0;

        gamePanel.npc[mapNumber][i] = new NPC_Sastro(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 30;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 23;
        i++;

        mapNumber = GamePanel.MAP_ROAD;
        i = 0;

        gamePanel.npc[mapNumber][i] = new NPC_Rimba(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 18;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 28;
        i++;

        mapNumber = GamePanel.MAP_RIVER;
        i = 0;

        gamePanel.npc[mapNumber][i] = new NPC_Bayu(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 18;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 27;
        i++;

        gamePanel.npc[mapNumber][i] = new NPC_Lestari(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 29;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 25;
        i++;

        gamePanel.npc[mapNumber][i] = new NPC_Tirta(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 18;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 21;
        i++;

        mapNumber = GamePanel.MAP_TOWN;
        i = 0;

        gamePanel.npc[mapNumber][i] = new NPC_Chakara(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 12;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 17;
        i++;

        gamePanel.npc[mapNumber][i] = new NPC_Aksa(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 29;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 22;
        i++;

        gamePanel.npc[mapNumber][i] = new NPC_Surya(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 37;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 32;
        i++;

        gamePanel.npc[mapNumber][i] = new NPC_Rangga(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 22;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 29;
        i++;

        gamePanel.npc[mapNumber][i] = new NPC_Sari(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 36;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 19;
        i++;

        gamePanel.npc[mapNumber][i] = new NPC_Bumi(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 19;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 33;
        i++;

        mapNumber = GamePanel.MAP_TOWER_1;
        i = 0;

        gamePanel.npc[mapNumber][i] = new NPC_Bima(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 24;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 21;
        i++;

        mapNumber = GamePanel.MAP_TOWER_2;
        i = 0;

        gamePanel.npc[mapNumber][i] = new NPC_Genta(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 27;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 22;
        i++;

    }

    public void setMonster() {

        int mapNumber = GamePanel.MAP_TOWN;
        int i = 0;

        gamePanel.monster[mapNumber][i] = new MON_Glimp(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.tileSize * 31;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.tileSize * 23;
        i++;

        gamePanel.monster[mapNumber][i] = new MON_Mebot(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.tileSize * 32;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.tileSize * 17;
        i++;

        gamePanel.monster[mapNumber][i] = new MON_Nibby(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.tileSize * 17;
        i++;

        gamePanel.monster[mapNumber][i] = new MON_Toonamy(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.tileSize * 27;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.tileSize * 26;
        i++;

    }

    public void setInteractiveTile() {


    }
}
