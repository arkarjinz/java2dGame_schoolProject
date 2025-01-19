package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Potion extends SuperObject{
    GamePanel gamePanel;
    public OBJ_Potion(GamePanel gamePanel){
        name = "Potion";
        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/Potion.png"));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
