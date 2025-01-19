package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door1 extends SuperObject{
    GamePanel gamePanel;
    public OBJ_Door1(GamePanel gamePanel){
        name = "Door1";
        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/Door1.png"));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
