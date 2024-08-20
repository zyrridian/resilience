package com.zkylab.ai;

/**
 * Represents a node in the grid used for pathfinding.
 * Each node contains information about its position, cost, and state.
 */
public class Node {

    Node parent; // Parent node in the path
    public int col; // Column position of the node
    public int row; // Row position of the node
    int gCost; // Cost from the start node to this node
    int hCost; // Heuristic cost from this node to the goal node
    int fCost; // Total cost (gCost + hCost)
    boolean solid; // Indicates if the node is obstructed
    boolean open; // Indicates if the node is open for exploration
    boolean checked; // Indicates if the node has been checked

    /**
     * Constructs a new node with the specified column and row.
     *
     * @param col The column position of the node.
     * @param row The row position of the node.
     */

    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }

}
