package com.example.cs2340Project;

/**
 * Private node class to create a LinkedList-backed stack.
 *
 * @version 1.0
 */
public class Node {
    /** Next node being pointed to. */
    public Node next;
    /** The letter being held in the node. */
    public char let;
    /** Indice of the letter held in the ndoe. */
    public int pos;

    /** Constructore for the node.
     *
     * @param pos Indice of the letter's location.
     * @param let Letter held in the node.
     * @param next Next node being pointed to.
     */
    public Node (int pos, char let, Node next) {
        this.next = next;
        this.let = let;
        this.pos = pos;
    }

    /**
     * Secondary constructor of the node.
     *
     * @param pos Indice of the letter.
     * @param let Letter being represented.
     */
    public Node (int pos, char let) {
        this(pos, let, null);
    }

    /**
     * Equals method to check if the pos and letter passed
     * are equivalent to this node's pos and let.
     */
    public boolean equals(int pos, char let) {
        if (pos == this.pos && Character.toLowerCase(this.let) == Character.toLowerCase(let)) {
            return true;
        }
        return false;
    }
}
