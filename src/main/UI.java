package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;


public class UI {
    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font  maruMonica;
   // Font arial_40, arial_80;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.0");
    public String currentDialogue = "";
    public int commandNum = 0;
    public UI(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        try{
            InputStream is = getClass().getClassLoader().getResourceAsStream("font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
//        arial_40 = new Font("Arial",Font.PLAIN,17);
//        arial_80 = new Font("Arial",Font.BOLD,50);
        OBJ_Key key = new OBJ_Key(gamePanel);
        keyImage = key.image;
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D graphics2D){

        this.graphics2D = graphics2D;
        graphics2D.setFont(maruMonica);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setColor(Color.white);

        if(gamePanel.gameState == gamePanel.titleState){
            drawTitleScreen();
        }

        if(gameFinished == true){
            graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics2D.setColor(Color.white);

            String text;
            int textLength, x , y;

            text = "You found the exit";
            textLength = (int)graphics2D.getFontMetrics().getStringBounds(text,graphics2D).getWidth();
             x = gamePanel.screenWidth/2 - textLength/2 ;
             y = gamePanel.screenHeight/2 - (gamePanel.tileSize * 3);
             graphics2D.drawString(text,x,y);

            text = "Your Time Is : " + decimalFormat.format(playTime) + "!";
            textLength = (int)graphics2D.getFontMetrics().getStringBounds(text,graphics2D).getWidth();
            x = gamePanel.screenWidth/2 - textLength/2 ;
            y = gamePanel.screenHeight/2 + (gamePanel.tileSize * 4);
            graphics2D.drawString(text,x,y);

//            graphics2D.setFont(arial_80);
            graphics2D.setColor(Color.CYAN);
            text = "CONGRATULATION!!!!!";
            textLength = (int)graphics2D.getFontMetrics().getStringBounds(text,graphics2D).getWidth();
            x = gamePanel.screenWidth/2 - textLength/2 ;
            y = gamePanel.screenHeight/2 + (gamePanel.tileSize * 2);
            graphics2D.drawString(text,x,y);

            gamePanel.gameThread = null;
        }
        else {
            graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics2D.setColor(Color.white);
            graphics2D.drawImage(keyImage, gamePanel.tileSize/2, gamePanel.tileSize/2, gamePanel.tileSize - 10, gamePanel.tileSize - 10, null);
            graphics2D.drawString("x "+ gamePanel.player.hashKey,74,60);
            this.graphics2D = graphics2D;
            graphics2D.setFont(maruMonica);
            graphics2D.setColor(Color.white);
            if(gamePanel.gameState == gamePanel.playState){
                //DO
            }
            if(gamePanel.gameState == gamePanel.pauseState){
                   drawPauseScreen();
            }
            if(gamePanel.gameState == gamePanel.dialogueState){
                drawDialogueScreen();
            }


            //TIME
            playTime += (double)1/60;
//            graphics2D.drawString("Time : " + decimalFormat.format(playTime) , (gamePanel.tileSize * 14) - 8, 45);

            //Message
            if (messageOn == true){
                graphics2D.setFont(graphics2D.getFont().deriveFont(20F));
                graphics2D.drawString(message,gamePanel.tileSize/2,gamePanel.tileSize*5);

                messageCounter++;
                if(messageCounter > 120){
                    messageCounter = 0;
                    messageOn = false;
                }



            }


        }
    }

    public void drawTitleScreen(){
        graphics2D.setColor(new Color(70,120,80));
        graphics2D.fillRect(0,0, gamePanel.screenWidth, gamePanel.screenHeight);

        //Title Name

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 96F));
        String text = "ESCAPE!";
        int x = getXForCenteredText(text);
        int y = gamePanel.tileSize * 3;

        //Shadow
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(text, x+5, y+5);
        //Main Color
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(text, x, y);

        //Character Image
        x = gamePanel.screenWidth / 2 - (gamePanel.tileSize * 2)/2;
        y += gamePanel.tileSize*2;
        graphics2D.drawImage(gamePanel.player.down1, x , y, gamePanel.tileSize*2, gamePanel.tileSize*2, null);

        //Menu
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48F));
        text = "START";
        x = getXForCenteredText(text);
        y +=gamePanel.tileSize * 3.5;
        graphics2D.drawString(text, x, y);
        if(commandNum == 0){
            graphics2D.drawString(">", x-gamePanel.tileSize, y);
        }

        text = "QUIT";
        x = getXForCenteredText(text);
        y +=gamePanel.tileSize;
        graphics2D.drawString(text, x, y);
        if(commandNum == 1){
            graphics2D.drawString(">", x-gamePanel.tileSize, y);
        }
    }
        public void drawPauseScreen(){
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN,25F));
        String text = "PAUSE";
        int x = getXForCenteredText(text);

        int y = gamePanel.screenHeight/2;
        graphics2D.drawString(text,x,y);
        }
        public void drawDialogueScreen(){
             //Window
            int x = gamePanel.tileSize *2;
            int y = gamePanel.tileSize/2;
            int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
            int height = gamePanel.tileSize * 4;
            drawSubWindow(x, y, width, height);

            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD,25F));
            x += gamePanel.tileSize;
            y += gamePanel.tileSize;
            for (String line : currentDialogue.split("\n")){
                graphics2D.drawString(line, x, y);
                y+= 40;
            }

        }

        public void drawSubWindow(int x, int y, int width, int height){
             Color color = new Color(0,0,0, 210);
             graphics2D.setColor(color);
             graphics2D.fillRoundRect(x, y, width, height, 35, 35);

             color = new Color(255, 255, 255);
             graphics2D.setColor(color);
             graphics2D.setStroke(new BasicStroke(5));
             graphics2D.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

        }

        public int getXForCenteredText(String text){
            int length = (int)graphics2D.getFontMetrics().getStringBounds(text,graphics2D).getWidth();
           int x=gamePanel.screenWidth/2 - length/2;
            return x;
        }
}
