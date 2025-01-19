package ai;

import entity.Entity;
import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {
    GamePanel gamePanel;
    Entity entity;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;
    public PathFinder (GamePanel gamePanel){
        this.gamePanel = gamePanel;
        instantiateNodes();
    }
    public void instantiateNodes(){
        node = new Node[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        int col = 0;
        int row = 0;
        while ((col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow)){
            node[col] [row] = new Node (col,row);
            col++;
            if(col == gamePanel.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }
    public void resetNode(){
        int col = 0;
        int row = 0;
        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
            //Recent open, checked and solid state
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            col++;
            if(col == gamePanel.maxWorldCol){
                col = 0;
                row++;
            }
        }
        //Reset Other Setting
        openList.clear();
        pathList.clear();
        goalReached  = false;
        step = 0;
    }
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity){
        resetNode();

        //Set Start and Goal Node
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;
        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
            //Set Solid Node
            //Check Tiles
          int tileNum = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][col][row];
          if(gamePanel.tileManager.tiles[tileNum].collision == true){
              node[col][row].solid = true;
          }
            //Set cost
            getCost(node[col] [row]);
            col++;
            if(col == gamePanel.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }
    public void getCost(Node node){
        //G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        //H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        //F Cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while (goalReached == false && step < 1000) {
            int col = currentNode.col;
            int row = currentNode.row;
            //Check the Current Node
            currentNode.checked = true;
            openList.remove(currentNode);
            //Open tile Up node
            if (row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }
            //Open tile left node
            if (col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }
            //Open tile down node
            if (row + 1 < gamePanel.maxWorldRow) {
                openNode(node[col][row + 1]);
            }
            //Open tile right node
            if (col + 1 < gamePanel.maxWorldCol) {
                openNode(node[col + 1][row]);
            }
            //Find the best Node
            int bestNodeIndex = 0;
            int bestNodeCost = 999;

            for(int i = 0; i < openList.size(); i ++){
                //Check if this node's F cost is better
                if(openList.get(i).fCost < bestNodeCost){
                    bestNodeIndex = i;
                    bestNodeCost = openList.get(i).fCost;
                }
                //If F cost is equal, check G cost
                else if(openList.get(i).fCost == bestNodeCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            //If there is no node in the openList
            if(openList.size() == 0){
                break;
            }
            //After the loop, openList[bestNodeIndex] is the next step (= currentNode)
            currentNode = openList.get(bestNodeIndex);
            if (currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;

    }
    public void openNode(Node node){
        if(node.open== false && node.checked == false && node.solid == false){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath(){
        Node current = goalNode;
        while(current != startNode){
            pathList.add(0,current);
            current = current.parent;
        }
    }

}
