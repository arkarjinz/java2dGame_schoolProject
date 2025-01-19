package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
  public  boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
//Debug
    boolean showDebugText = false;
  public KeyHandler(GamePanel gamePanel){
      this.gamePanel = gamePanel;
  }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        //TITLE State
        if(gamePanel.gameState == gamePanel.titleState){
            if(code == KeyEvent.VK_W){
                gamePanel.ui.commandNum--;
                if(gamePanel.ui.commandNum < 0){
                    gamePanel.ui.commandNum = 1;
                }
            }
            if (code == KeyEvent.VK_S){
                gamePanel.ui.commandNum++;
                if(gamePanel.ui.commandNum > 1){
                    gamePanel.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gamePanel.ui.commandNum == 0){
                    gamePanel.gameState = gamePanel.playState;
                }
                if(gamePanel.ui.commandNum == 1){
                    System.exit(0);
                }
            }
        }

        //Play State
        if(gamePanel.gameState == gamePanel.playState){
            if (code == KeyEvent.VK_W){
                upPressed = true;
            }
            if (code == KeyEvent.VK_S){
                downPressed = true;
            }
            if (code == KeyEvent.VK_A){
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D){
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P){
                gamePanel.gameState = gamePanel.pauseState;
        }   if (code == KeyEvent.VK_M){
                gamePanel.playMusic(0);
        }if (code == KeyEvent.VK_N){
                gamePanel.stopMusic();
        }
            //Debug
            if (code == KeyEvent.VK_T){
               if(showDebugText == false){
                   showDebugText = true;
               } else if (showDebugText == true) {
                   showDebugText = false;
               }
            }
            if (code == KeyEvent.VK_R){
                switch (gamePanel.currentMap){
                    case 0:  gamePanel.tileManager.loadMap("maps/20maze.txt",0);
                    break;
                    case 1 : gamePanel.tileManager.loadMap("maps/anomap.txt",1);
                    break;
                }

            }
            if (code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }

        }
        //Dialogue State
        else if (gamePanel.gameState == gamePanel.dialogueState){
            if (code == KeyEvent.VK_ENTER){
                gamePanel.gameState = gamePanel.playState;
            }
        }

        //Pause State
     else if (gamePanel.gameState == gamePanel.pauseState){
            if (code == KeyEvent.VK_P){
                gamePanel.gameState = gamePanel.playState;
            }
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W){
            upPressed = false;
        }
        if (code == KeyEvent.VK_S){
            downPressed = false;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = false;
        }


    }
}
