package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_OldMan extends Entity{

    KeyHandler keyHandler;
    public NPC_OldMan (GamePanel gamePanel){
        super(gamePanel);

        direction = "right";
        speed = 1;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;

        getImage();
        setDialogue();
    }
    public void getImage()
    {

        up1 = setup("npc/oldman_up_1");
        up2 = setup("npc/oldman_up_2");

        down1 = setup("npc/oldman_down_1");
        down2 = setup("npc/oldman_down_2");

        left1 = setup("npc/oldman_left_1");
        left2 = setup("npc/oldman_left_2");

        right1 = setup("npc/oldman_right_1");
        right2 = setup("npc/oldman_right_1");

    }
    public void setDialogue(){
        dialogues[0] = "Hello EveryNyan";
        dialogues[1] = "How Are You?";
        dialogues[2] = "Fine, Thank You";
        dialogues[3] = "I wish I were a bird";
    }

    public void setAction(){
        if (onPath == true){
            int goalCol = 16;
            int goalRow = 15;
            searchPath(goalCol,goalRow);


        }else {
            actionLockCounter ++;
            if (actionLockCounter == 120){

                Random random = new Random();
                int i = random.nextInt(100) + 1;

                if (i <= 25) {
                    direction = "up";
                }
                if(i > 25 && i <= 50 ){
                    direction = "down";
                }
                if(i > 50 && i <= 75){
                    direction = "left";
                }
                if (i > 75 && i <= 100){
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }
    public void speak(){
       super.speak();
       onPath = true;
    }


}
