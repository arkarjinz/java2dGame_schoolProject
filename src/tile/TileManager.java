package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamePanel;
  public   Tile[] tiles;
  public   int mapTileNum [] [] [];
  boolean drawPath = true;

    public TileManager (GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        tiles = new Tile[100];
        mapTileNum = new int[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        getTileImage();
        loadMap("maps/20maze.txt", 0);
        loadMap("maps/anomap.txt",1);
    }
    public void getTileImage() {
        setup(0,"000",false);
        setup(1,"001",true);
        setup(2,"002", false);
        setup(3,"003",false);
        setup(4,"004",false);
        setup(5,"005",false);
        setup(6,"006",false);
        setup(7,"007",false);
        setup(8,"008",false);
        setup(9,"009",false);
        setup(10,"010",false);
        setup(11,"011",true);
        setup(12,"012",true);
        setup(13,"013",false);
        setup(14,"014",true);
        setup(15,"015",true);
        setup(16,"016",true);
        setup(17,"017",true);
        setup(18, "018",true);
        setup(19, "019",true);
        setup(20,"020",true);
        setup(21,"021",true);
        setup(22,"022",true);
        setup(23,"023",true);
        setup(24,"024",true);
        setup(25,"025",false);

    }

    public void setup(int index, String imagePath, boolean collision){
        UtilityTool utilityTool = new UtilityTool();
        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/" + imagePath + ".png"));
            tiles[index].image = utilityTool.scaleImage(tiles[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tiles[index].collision = collision;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int col = 0;
            int row = 0;

            while (row < gamePanel.maxWorldRow) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    // Handle the case where there are fewer rows in the file than expected
                    break;
                }

                String numbers[] = line.split(" ");
                col = 0; // Reset col for each row

                while (col < gamePanel.maxWorldCol) {
                    if (col >= numbers.length) {
                        // Handle the case where there are fewer columns in the file than expected
                        break;
                    }

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum [map] [col][row] = num;
                    col++;
                }

                row++;
            }

            bufferedReader.close();
        } catch (Exception e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        }
    }

    public void draw (Graphics2D graphics2D)
    {
        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow)
        {
             int tileNum = mapTileNum[gamePanel.currentMap][worldCol][worldRow];
             int worldX = worldCol  * gamePanel.tileSize;
             int worldY = worldRow * gamePanel.tileSize;
             int screenX = worldX - gamePanel.player.worldX  + gamePanel.player.screenX;
             int screenY = worldY - gamePanel.player.worldY  + gamePanel.player.screenY;
             if(worldX + gamePanel.tileSize >  gamePanel.player.worldX - gamePanel.player.screenX &&
             worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
              worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
              worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
             ){
                 graphics2D.drawImage(tiles[tileNum].image,screenX,screenY,null);
             }


             worldCol++;


            if (worldCol == gamePanel.maxWorldCol)
            {
                worldCol = 0;

                worldRow++;

            }
        }
        if(drawPath == true){
            graphics2D.setColor(new Color(255, 0,0,70));
            for (int i = 0; i < gamePanel.pathFinder.pathList.size(); i++){
                int worldX = gamePanel.pathFinder.pathList.get(i).col * gamePanel.tileSize;
                int worldY = gamePanel.pathFinder.pathList.get(i).row * gamePanel.tileSize;
                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

                graphics2D.fillRect(screenX,screenY,gamePanel.tileSize, gamePanel.tileSize);
            }
        }

    }
}
