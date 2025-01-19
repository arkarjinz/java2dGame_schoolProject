package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends SuperObject{
    GamePanel gamePanel;
    public OBJ_Key(GamePanel gamePanel){
        name = "Key";
        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/key.png"));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
