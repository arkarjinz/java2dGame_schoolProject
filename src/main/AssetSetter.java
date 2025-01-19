package main;

import entity.NPC_OldMan;
import object.OBJ_Door;
import object.OBJ_Door1;
import object.OBJ_Key;
import object.OBJ_Potion;

public class AssetSetter {
    GamePanel gamePanel;
    public AssetSetter(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void setObject(){

        gamePanel.object[0] = new OBJ_Key(gamePanel);
        gamePanel.object[0].worldX = 569;
        gamePanel.object[0].worldY = 289;

        gamePanel.object[1] = new OBJ_Door(gamePanel);
        gamePanel.object[1].worldX = 629;
        gamePanel.object[1].worldY = 859;

        gamePanel.object[2] = new OBJ_Potion(gamePanel);
        gamePanel.object[2].worldX = 9 * gamePanel.tileSize;
        gamePanel.object[2].worldY = 5 * gamePanel.tileSize;

//        gamePanel.object[3] = new OBJ_Door1(gamePanel);
//        gamePanel.object[3].worldX = 9 * gamePanel.tileSize;
//        gamePanel.object[3].worldY = 3 * gamePanel.tileSize;

//        gamePanel.object[4] = new OBJ_Key(gamePanel);
//        gamePanel.object[4].worldX = 20 * gamePanel.tileSize;
//        gamePanel.object[4].worldY = 12 * gamePanel.tileSize;

    }

    public void setNPC(){
        gamePanel.npc[0] = new NPC_OldMan(gamePanel);
        gamePanel.npc[0].worldX = 199;
        gamePanel.npc[0].worldY = 479;
    }
}
