package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

public class Entity {
    GamePanel gamePanel;
    public int worldX,worldY;
    public int speed;

    public BufferedImage up1, up2, up3, up4, up5, up6, down1, down2, down3, down4, down5, down6, left1, left2, left3,
    left4, left5, left6,
    right1, right2, right3, right4, right5, right6;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48) ;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    String dialogues[] = new String[20];
    int dialogueIndex = 0;
    public boolean onPath = false;

    public Entity(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void speak(){
        if (dialogues[dialogueIndex] == null){
//
            dialogueIndex = 0;
        }
        gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gamePanel.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }

    }

    public void setAction(){

    }
    public void checkCollision(){
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this,false);
        gamePanel.collisionChecker.checkPlayer(this);
    }

    public void update(){
        setAction();
        checkCollision();
        if(collisionOn == false){
            switch (direction){
                case "up" : worldY -= speed; break;
                case "down" : worldY += speed; break;
                case "left" : worldX -= speed; break;
                case "right" : worldX += speed; break;
            }
        }
        spriteCounter++;
        if(spriteCounter > 12){
            if (spriteNum == 1){
                spriteNum = 2;
            }
            else if (spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            switch (direction)
            {
                case "up" :
                    if (spriteNum == 1)
                    {
                        image = up1;
                    }
                    if (spriteNum == 2)
                    {
                        image = up2;
                    }

                    break;

                case "down" :
                    if (spriteNum == 1)
                    {
                        image = down1;
                    }
                    if (spriteNum == 2)
                    {
                        image = down2;
                    }

                    break;

                case "left" :
                    if (spriteNum == 1)
                    {
                        image = left1;
                    }
                    if (spriteNum == 2)
                    {
                        image = left2;
                    }

                    break;

                case "right" :
                    if (spriteNum == 1)
                    {
                        image = right1;
                    }
                    if (spriteNum == 2)
                    {
                        image = right2;
                    }

                    break;
            }

            graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }

    }

    public BufferedImage setup(String imagePath){
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath+".png"));
            image = utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }


    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gamePanel.tileSize;
        int startRow = (worldY + solidArea.y) / gamePanel.tileSize;
        gamePanel.pathFinder.setNodes(startCol, startRow, goalCol, goalRow, this);

        if (gamePanel.pathFinder.search() == true) {
            long startTime = System.nanoTime();
            //Next worldX and worldY
            int nextX = gamePanel.pathFinder.pathList.get(0).col * gamePanel.tileSize;
            int nextY = gamePanel.pathFinder.pathList.get(0).row * gamePanel.tileSize;

            //Entity solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gamePanel.tileSize) {
                // left or right
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                //up or left
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                //up or right
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                //down or left
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            }
            else if (enTopY < nextY && enLeftX < nextX) {
                //down or right
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            }
            //If reach the goal, stop the search
             int nextCol = gamePanel.pathFinder.pathList.get(0).col;
             int nextRow = gamePanel.pathFinder.pathList.get(0).row;
             if(nextCol == goalCol && nextRow == goalRow){
                 onPath = false;
                 }


             }
        }
    }

