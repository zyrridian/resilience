package com.zkylab.common;

import com.zkylab.entity.Entity;
import com.zkylab.object.OBJ_Axe;
import com.zkylab.object.OBJ_Boots;
import com.zkylab.object.OBJ_Chest;
import com.zkylab.object.OBJ_Coin_Bronze;
import com.zkylab.object.OBJ_Coin_Red;
import com.zkylab.object.OBJ_Door;
import com.zkylab.object.OBJ_DoorIron;
import com.zkylab.object.OBJ_Fireball;
import com.zkylab.object.OBJ_Heart;
import com.zkylab.object.OBJ_Key;
import com.zkylab.object.OBJ_Lantern;
import com.zkylab.object.OBJ_Mana_Crystal;
import com.zkylab.object.OBJ_Pickaxe;
import com.zkylab.object.OBJ_Potion_Red;
import com.zkylab.object.OBJ_Rock;
import com.zkylab.object.OBJ_Shield_God;
import com.zkylab.object.OBJ_Shield_Super;
import com.zkylab.object.OBJ_Sword_God;
import com.zkylab.object.OBJ_Shield_Normal;
import com.zkylab.object.OBJ_Sword_Normal;
import com.zkylab.object.OBJ_Sword_Super;
import com.zkylab.object.OBJ_Tent;

public class EntityGenerator {

    GamePanel gamePanel;

    public EntityGenerator(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public Entity getObject(String itemName) {
        Entity obj = null;
        switch (itemName) {
            case OBJ_Axe.objName: obj = new OBJ_Axe(gamePanel); break;
            case OBJ_Boots.objName: obj = new OBJ_Boots(gamePanel); break;
            case OBJ_Chest.objName: obj = new OBJ_Chest(gamePanel); break;
            case OBJ_Coin_Bronze.objName: obj = new OBJ_Coin_Bronze(gamePanel); break;
            case OBJ_Coin_Red.objName: obj = new OBJ_Coin_Red(gamePanel); break;
            case OBJ_Door.objName: obj = new OBJ_Door(gamePanel); break;
            case OBJ_DoorIron.objName: obj = new OBJ_DoorIron(gamePanel); break;
            case OBJ_Fireball.objName: obj = new OBJ_Fireball(gamePanel); break;
            case OBJ_Heart.objName: obj = new OBJ_Heart(gamePanel); break;
            case OBJ_Key.objName: obj = new OBJ_Key(gamePanel); break;
            case OBJ_Lantern.objName: obj = new OBJ_Lantern(gamePanel); break;
            case OBJ_Mana_Crystal.objName: obj = new OBJ_Mana_Crystal(gamePanel); break;
            case OBJ_Pickaxe.objName: obj = new OBJ_Pickaxe(gamePanel); break;
            case OBJ_Potion_Red.objName: obj = new OBJ_Potion_Red(gamePanel); break;
            case OBJ_Rock.objName: obj = new OBJ_Rock(gamePanel); break;
            case OBJ_Shield_Normal.objName: obj = new OBJ_Shield_Normal(gamePanel); break;
            case OBJ_Shield_Super.objName: obj = new OBJ_Shield_Super(gamePanel); break;
            case OBJ_Shield_God.objName: obj = new OBJ_Shield_God(gamePanel); break;
            case OBJ_Sword_Normal.objName: obj = new OBJ_Sword_Normal(gamePanel); break;
            case OBJ_Sword_Super.objName: obj = new OBJ_Sword_Super(gamePanel); break;
            case OBJ_Sword_God.objName: obj = new OBJ_Sword_God(gamePanel); break;
            case OBJ_Tent.objName: obj = new OBJ_Tent(gamePanel); break;
        }
        return obj;
    }
    
}
