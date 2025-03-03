package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends SuperObject{
    GamePanel gamePanel;
    public OBJ_Door(GamePanel gamePanel){
        name = "Door";
        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/door.png"));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
