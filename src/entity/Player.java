package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

   GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
  public   int hashKey = 0;
    public Player (GamePanel gamePanel,KeyHandler keyHandler)
    {
        super(gamePanel);

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        screenX = gamePanel.screenWidth/2 - (gamePanel.tileSize/2);
        screenY = gamePanel.screenHeight/2- (gamePanel.tileSize/2);

        solidArea = new Rectangle(8,16,30,30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValue();
        getPlayerImage();
    }

    public void setDefaultValue()
    {
        worldX =  264;
        worldY = 329;
        speed = 3;
        direction = "down";

    }

    public void getPlayerImage()
    {
       up1 = setup("players/u1");
       up2 = setup("players/u2");
       up3 = setup("players/u3");
       up4 = setup("players/u4");
       up5 = setup("players/u5");
       up6 = setup("players/u6");

       down1 = setup("players/1");
       down2 = setup("players/2");
       down3 = setup("players/3");
       down4 = setup("players/4");
       down5 = setup("players/5");
       down6 = setup("players/6");

       left1 = setup("players/l1");
       left2 = setup("players/l2");
       left3 = setup("players/l3");
       left4 = setup("players/l4");
       left5 = setup("players/l5");
       left6 = setup("players/l6");

       right1 = setup("players/r1");
       right2 = setup("players/r2");
       right3 = setup("players/r3");
       right4 = setup("players/r4");
       right5 = setup("players/r5");
       right6 = setup("players/r6");
    }
    public void update() {


            if (keyHandler.upPressed) {
                direction = "up";

            } else if (keyHandler.downPressed) {
                direction = "down";

            } else if (keyHandler.leftPressed) {
                direction = "left";

            } else if (keyHandler.rightPressed) {
                direction = "right";
            }

        //Check Tile Collision

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);

        //Check Object Collision
      int objIndex =  gamePanel.collisionChecker.checkObject(this,true);
      pickUpObject(objIndex);

      //Check Npc Collision
        int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        interactNPC(npcIndex);


        //If collision is false Player can move
        if (collisionOn == false)
        {
            switch (direction)
            {
                     case "up":

                         if (keyHandler.upPressed) {
                             worldY -= speed;
                         }
                       break;
                    case "down":

                        if (keyHandler.downPressed) {
                            worldY += speed;
                        }
                        break;
                     case "left":

                         if (keyHandler.leftPressed) {
                             worldX -= speed;
                         }
                       break;
                     case "right":

                         if (keyHandler.rightPressed) {
                             worldX += speed;
                         }
                       break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 17) {
            switch (spriteNum) {
                case 1:
                    spriteNum = 2;
                    break;
                case 2:
                    spriteNum = 3;
                    break;
                case 3:
                    spriteNum = 4;
                    break;
                    case 4:
                    spriteNum = 5;
                    break;
                    case 5:
                    spriteNum = 6;
                    break;
                    case 6:
                    spriteNum = 1;
                    break;
            }
            spriteCounter = 0;
        }
    }

    public void pickUpObject(int i){
        if (i != 999){

        String objectName = gamePanel.object[i].name;
        switch (objectName) {
            case "Key":
                gamePanel.playSE(2);
                hashKey++;
                gamePanel.object[i] = null;
                gamePanel.ui.showMessage("You got a key!");
                break;
            case "Door1":
                if(hashKey > 0) {
                    gamePanel.playSE(1);
                    hashKey--;
                    gamePanel.object[i] = null;
                    gamePanel.ui.showMessage("You opened the Door!");
                }
                else {
                    gamePanel.ui.showMessage("Get the Key!");
                }
                break;

            case "Door":
                if (hashKey > 0) {
                    hashKey--;
                    gamePanel.ui.gameFinished = true;
                    gamePanel.stopMusic();
                    gamePanel.playSE(3);
                }
                else {
                    gamePanel.ui.showMessage("Get the Key!");
                }
                break;

            case "Potion":
                gamePanel.playSE(2);
                speed += 2;
                gamePanel.object[i] = null;
                gamePanel.ui.showMessage("Speed Up");

                break;

        }

        }
    }

    public void interactNPC(int i){
        if (i != 999) {
            if(gamePanel.keyHandler.enterPressed == true){
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[i].speak();
            }

        }
        gamePanel.keyHandler.enterPressed = false;
    }

    public void draw(Graphics2D graphics2D)
    {
        BufferedImage image = null;
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
                if (spriteNum == 3)
                {
                    image = up3;
                }
                  if (spriteNum == 4)
                {
                    image = up4;
                }
                  if (spriteNum == 5)
                {
                    image = up5;
                }
                  if (spriteNum == 6)
                {
                    image = up6;
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
                if (spriteNum == 3)
                {
                    image = down3;
                }
                 if (spriteNum == 4)
                {
                    image = down4;
                }
                 if (spriteNum == 5)
                {
                    image = down5;
                }
                 if (spriteNum == 6)
                {
                    image = down6;
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
                if (spriteNum == 3)
                {
                    image = left3;
                }
                   if (spriteNum == 4)
                {
                    image = left4;
                }
                if (spriteNum == 5)
                {
                    image = left5;
                }
                if (spriteNum == 6)
                {
                    image = left6;
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
                if (spriteNum == 3)
                {
                    image = right3;
                }
                 if (spriteNum == 4)
                {
                    image = right4;
                }
                if (spriteNum == 5)
                {
                    image = right5;
                }
                if (spriteNum == 6)
                {
                    image = right6;
                }
                break;
        }
        graphics2D.drawImage(image,screenX, screenY ,null);
    }
}

