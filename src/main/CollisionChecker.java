package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gamePanel;
    public CollisionChecker(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public void checkTile(Entity entity){

           int entityLeftWorldX = entity.worldX + entity.solidArea.x;
           int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
           int entityTopWorldY = entity.worldY + entity.solidArea.y;
           int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

           int entityLeftCol = entityLeftWorldX/gamePanel.tileSize;
           int entityRightCol = entityRightWorldX/gamePanel.tileSize;
           int entityTopRow = entityTopWorldY/gamePanel.tileSize;
           int entityBottomRow = entityBottomWorldY/gamePanel.tileSize;

           int tileNum1, tileNum2;

           switch (entity.direction)
           {
               case "up":
                   entityTopRow = ((entityTopWorldY - entity.speed)/gamePanel.tileSize) ;
                   tileNum1 = gamePanel.tileManager.mapTileNum [gamePanel.currentMap][entityLeftCol][entityTopRow];
                   tileNum2 = gamePanel.tileManager.mapTileNum [gamePanel.currentMap][entityRightCol][entityTopRow];
                   if(gamePanel.tileManager.tiles[tileNum1].collision == true || gamePanel.tileManager.tiles[tileNum2].collision == true)
                   {
                       entity.collisionOn = true;
                   }
                   break;
               case "down":
                   entityBottomRow = ((entityBottomWorldY + entity.speed)/gamePanel.tileSize) ;
                   tileNum1 = gamePanel.tileManager.mapTileNum [gamePanel.currentMap][entityLeftCol][entityBottomRow];
                   tileNum2 = gamePanel.tileManager.mapTileNum [gamePanel.currentMap][entityRightCol][entityBottomRow];
                   if(gamePanel.tileManager.tiles[tileNum1].collision == true || gamePanel.tileManager.tiles[tileNum2].collision == true)
                   {
                       entity.collisionOn = true;
                   }
                   break;
               case "left":
                   entityLeftCol = ((entityLeftWorldX - entity.speed)/gamePanel.tileSize) ;
                   tileNum1 = gamePanel.tileManager.mapTileNum [gamePanel.currentMap][entityLeftCol][entityTopRow];
                   tileNum2 = gamePanel.tileManager.mapTileNum [gamePanel.currentMap][entityLeftCol][entityBottomRow];
                   if(gamePanel.tileManager.tiles[tileNum1].collision == true || gamePanel.tileManager.tiles[tileNum2].collision == true)
                   {
                       entity.collisionOn = true;
                   }
                   break;
               case "right":
                   entityRightCol = ((entityRightWorldX + entity.speed)/gamePanel.tileSize) ;
                   tileNum1 = gamePanel.tileManager.mapTileNum [gamePanel.currentMap][entityRightCol][entityTopRow];
                   tileNum2 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityRightCol][entityBottomRow];
                   if(gamePanel.tileManager.tiles[tileNum1].collision == true || gamePanel.tileManager.tiles[tileNum2].collision == true)
                   {
                       entity.collisionOn = true;
                   }
                   break;
           }
    }

    public int checkObject(Entity entity, boolean player){
        int index = 999;

        for (int i = 0; i < gamePanel.object.length; i++){
            if (gamePanel.object[i] != null){
                //Get Entity Solid Area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Get the object's solid area position
                gamePanel.object[i].solidArea.x = gamePanel.object[i].worldX + gamePanel.object[i].solidArea.x;
                gamePanel.object[i].solidArea.y = gamePanel.object[i].worldY + gamePanel.object[i].solidArea.y;

                switch (entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(gamePanel.object[i].solidArea)){
                            if (gamePanel.object[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if (player == true){
                                index = i;
                            }

                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(gamePanel.object[i].solidArea)){
                            if (gamePanel.object[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if (player == true){
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(gamePanel.object[i].solidArea)){
                            if (gamePanel.object[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if (player == true){
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x  += entity.speed;
                        if(entity.solidArea.intersects(gamePanel.object[i].solidArea)){
                            if (gamePanel.object[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if (player == true){
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.object[i].solidArea.x = gamePanel.object[i].solidAreaDefaultX;
                gamePanel.object[i].solidArea.y = gamePanel.object[i].solidAreaDefaultY;
            }
        }

        return index;
    }
    public  int checkEntity(Entity entity, Entity[] target){
        int index = 999;

        for (int i = 0; i <target.length; i++){
            if (target[i] != null){
                //Get Entity Solid Area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Get the object's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y =target[i].worldY + target[i].solidArea.y;

                switch (entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)){
                                entity.collisionOn = true;
                                index = i;


                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            entity.collisionOn = true;
                                index = i;

                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x  += entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;

                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
               target[i].solidArea.x = target[i].solidAreaDefaultX;
               target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }

        return index;
    }
    public void checkPlayer(Entity entity){
        //Get Entity Solid Area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        //Get the object's solid area position
        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y =gamePanel.player.worldY + gamePanel.player.solidArea.y;

        switch (entity.direction){
            case "up":
                entity.solidArea.y -= entity.speed;
                if(entity.solidArea.intersects(gamePanel.player.solidArea)){
                    entity.collisionOn = true;



                }
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                if(entity.solidArea.intersects(gamePanel.player.solidArea)){
                    entity.collisionOn = true;


                }
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                if(entity.solidArea.intersects(gamePanel.player.solidArea)){
                    entity.collisionOn = true;

                }
                break;
            case "right":
                entity.solidArea.x  += entity.speed;
                if(entity.solidArea.intersects(gamePanel.player.solidArea)) {
                    entity.collisionOn = true;

                }
                break;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

    }

}
