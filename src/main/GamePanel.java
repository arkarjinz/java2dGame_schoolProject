package main;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.Tile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class GamePanel extends JPanel implements Runnable {
       Entity entity;
    public final long startTime = System.nanoTime();

    //SCREEN SETTING
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    //fps
    int FPS = 60;

    //World Setting
    public final int maxWorldCol = 20;
    public final int maxWorldRow = 20;

    public final int maxMap = 10;
    public int currentMap = 0;

 //  public final int worldWidth = tileSize * maxScreenCol;
  // public final int worldHeight = tileSize * maxScreenRow;


    //System
   public TileManager tileManager = new TileManager(this);
  public   KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();



  public CollisionChecker collisionChecker = new CollisionChecker(this);
  public AssetSetter assetSetter = new AssetSetter(this);
  public UI ui = new UI(this);
//  public PathFinder pathFinder = new PathFinder(this);
    public PathFinder pathFinder = new PathFinder(this);
    Thread gameThread;

  //Entity and Object
   public Player player = new Player(this,keyHandler);

   public SuperObject object[] = new SuperObject[10];
   public Entity npc[] = new Entity[10];

   //Game State
    public int gameState;

    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState =  3;


    public  GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame(){

        assetSetter.setObject();
        assetSetter.setNPC();
        playMusic(0);
        stopMusic();
        gameState  = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run()
    {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        int timer = 0;
        int drawCount = 0;
        while (gameThread != null)
      {
          currentTime = System.nanoTime();
          delta += (currentTime - lastTime) / drawInterval;
          timer += (currentTime - lastTime);
          lastTime = currentTime;
          if (delta > 1)
          {
              update();
              repaint();
              delta--;
              drawCount++;
          }

          if (timer >= 1000000000)
          {
              System.out.println("FPS: " + drawCount);
              drawCount = 0;
              timer = 0;
          }
      }


    }

    //Sleep Method
//    public void run() {
//
//        double drawInterval = 1000000000/FPS;
//        double nextDrawTime = System.nanoTime() + drawInterval;
//        while (gameThread != null)
//        {
//
//            update();
//            repaint();
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime/1000000;
//                if (remainingTime < 0)
//                {
//                    remainingTime = 0;
//                }
//
//                Thread.sleep((long) remainingTime);
//                nextDrawTime += drawInterval;
//            }
//            catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }

    public void update(){

        if(gameState == playState){
            player.update();

            for (int i = 0; i< npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
        }
        if(gameState == pauseState){
            //nothing
        }


    }

    public void paintComponent(Graphics graphics){

        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        //Debug
        long drawStart = 0;
        if(keyHandler.showDebugText == true){
            drawStart = System.nanoTime();
        }
        //Title Screen
        if (gameState == titleState){
            ui.draw(graphics2D);
        }else {


            tileManager.draw(graphics2D);
            for (int i = 0; i < object.length; i++) {
                if (object[i] != null) {
                    object[i].draw(graphics2D, this);
                }
            }
            player.draw(graphics2D);
            //NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].draw(graphics2D);
                }
            }

            //UI
            ui.draw(graphics2D);
        }
        //Debug
        if(keyHandler.showDebugText == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            graphics2D.setFont(new Font("Arial",Font.PLAIN,20));
            graphics2D.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            graphics2D.drawString("WorldX"+ player.worldX, x, y); y += lineHeight;
            graphics2D.drawString("WorldY"+ player.worldY, x, y); y += lineHeight;
            graphics2D.drawString("Col"+ (player.worldX + player.solidArea.x) / tileSize, x, y); y += lineHeight;
            graphics2D.drawString("Row"+ (player.worldY + player.solidArea.y) / tileSize , x, y); y+= lineHeight;
//            if(entity.ok == true)
//            {
//                graphics2D.drawString("PathFind"+ (entity.duration) , x, y); y+= lineHeight;
//            }

            graphics2D.drawString("Draw Time: " + passed, x, y);
            System.out.println("Draw Time: " + passed );
        }

        graphics2D.dispose();
    }
     public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
     }
     public void stopMusic(){
        music.stop();
     }
     public void playSE(int i){
        se.setFile(i);
        se.play();
     }
}
