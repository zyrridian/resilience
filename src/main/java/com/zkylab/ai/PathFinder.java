package com.zkylab.ai;

import java.util.ArrayList;

import com.zkylab.common.GamePanel;

/**
 * Manages the pathfinding algorithm using A* search.
 * Handles node initialization, pathfinding, and path tracking.
 */
public class PathFinder {

    GamePanel gamePanel; // Reference to the game panel for accessing game state
    Node[][] nodes; // Grid of nodes representing the world
    ArrayList<Node> openList = new ArrayList<>(); // List of nodes to be evaluated
    public ArrayList<Node> pathList = new ArrayList<>(); // List of nodes forming the final path
    Node startNode, goalNode, currentNode; // Start, goal, and current nodes
    boolean goalReached = false; // Flag indicating whether the goal has been reached
    int step = 0; // Counter for the number of steps taken

    /**
     * Constructs a new PathFinder instance.
     *
     * @param gamePanel The game panel used for accessing the game state.
     */
    public PathFinder(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        instantiateNodes();
    }

    /**
     * Initializes the grid of nodes based on the dimensions of the game world.
     */
    public void instantiateNodes() {
        nodes = new Node[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        int col = 0;
        int row = 0;
        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            nodes[col][row] = new Node(col, row);
            col++;
            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Resets the state of all nodes and clears the open and path lists.
     */
    public void resetNodes() {
        int col = 0;
        int row = 0;
        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            // Reset node properties
            nodes[col][row].open = false;
            nodes[col][row].checked = false;
            nodes[col][row].solid = false;
            col++;
            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }

        // Reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    /**
     * Sets the start and goal nodes and initializes the nodes based on game state.
     *
     * @param startCol The column of the start node.
     * @param startRow The row of the start node.
     * @param goalCol  The column of the goal node.
     * @param goalRow  The row of the goal node.
     */
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();
        // Set start and goal nodes
        startNode = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            // Set solid nodes based on game tile properties
            int tileNumber = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][col][row];
            if (gamePanel.tileManager.tile[tileNumber].collision) {
                nodes[col][row].solid = true;
            }

            // Set solid nodes based on interactive tiles
            for (int i = 0; i < gamePanel.iTile[1].length; i++) {
                if (gamePanel.iTile[gamePanel.currentMap][i] != null
                        && gamePanel.iTile[gamePanel.currentMap][i].destructible) {
                    int itemCol = gamePanel.iTile[gamePanel.currentMap][i].worldX / gamePanel.tileSize;
                    int itemRow = gamePanel.iTile[gamePanel.currentMap][i].worldY / gamePanel.tileSize;
                    nodes[itemCol][itemRow].solid = true;
                }
            }

            // Calculate costs for each node
            getCost(nodes[col][row]);

            col++;
            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Calculates the cost values for a given node.
     *
     * @param node The node for which costs are to be calculated.
     */
    public void getCost(Node node) {
        // G cost (distance from the start node)
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);

        // H cost (heuristic distance to the goal node)
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F cost (total cost)
        node.fCost = node.gCost + node.hCost;
    }

    /**
     * Executes the A* search algorithm to find the shortest path.
     *
     * @return True if the goal is reached, false otherwise.
     */
    public boolean search() {
        while (!goalReached && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            // Mark the current node as checked
            currentNode.checked = true;
            openList.remove(currentNode);

            // Explore neighboring nodes
            if (row - 1 >= 0) openNode(nodes[col][row - 1]); // Up
            if (col - 1 >= 0) openNode(nodes[col - 1][row]); // Left
            if (row + 1 < gamePanel.maxWorldRow) openNode(nodes[col][row + 1]); // Down
            if (col + 1 < gamePanel.maxWorldCol) openNode(nodes[col + 1][row]); // Right

            // Find the best node in the open list
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            // If no nodes left to evaluate, exit
            if (openList.size() == 0) {
                break;
            }

            // Set the best node as the current node
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }

            step++;

        }

        return goalReached;
    }

     /**
     * Adds a node to the open list for evaluation if it is valid.
     *
     * @param node The node to be added to the open list.
     */
    public void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    /**
     * Traces the path from the goal node to the start node.
     */
    public void trackThePath() {
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }

}
