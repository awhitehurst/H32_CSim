/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

import ast.ASTNode;
import java.util.ArrayList;
import java.util.Iterator;
import lexer.Token;

/**
 * The PTNode forms the basis of the Parse Node Tree. Contains an arrayList of 
 * children PTNodes.
 * @author Lab Admin
 */
public abstract class PTNode {

    public PTNode() {
        this(0);
    }
/**
 * Constructor creates a new PTNode with a list of children.
 * @param numChildren the number of children.
 */
    public PTNode(int numChildren) {
        this(null, new ArrayList<PTNode>(), numChildren);
    }
/**
 * Creates a PTNode with a parent PTNode, and an ArrayList of PTNode children.
 * @param parent the parent of the PTNode
 * @param children ArrayList containing the children of the PTNode.
 */
    public PTNode(PTNode parent, ArrayList<PTNode> children) {
        this(parent, children, 0);
    }
/**
 * Creates a PTNode with a parent, ArrayList of PTNode children, and
 * the number of children.
 * @param parent Parent of the PTNode
 * @param children ArrayList containing the children of the PTNode.
 * @param numChildren the number of children of the PTNode.
 */
    public PTNode(PTNode parent, ArrayList<PTNode> children, int numChildren) {
        this.parent = parent;
        this.children = children;
        this.symbol = null;
        initChildren(numChildren);
    }
    /**
     * Creates a PTNode with a Token.
     * @param symbol the Token to be stored.
     */
    public PTNode(Token symbol){
        this.symbol = symbol;
        this.parent = null;
        this.children = null;
        
    }
/**
 * Retrieves the children of the PTNode.
 * @return an ArrayList containing the children of the PTNode.
 */
    public ArrayList<PTNode> getChildren() {
        return children;
    }
/**
 * Sets the children ArrayList to a new ArrayList.
 * @param children the new ArrayList.
 */
    public void setChildren(ArrayList<PTNode> children) {
        this.children = children;
    }
/**
 * Adds a child to the Node.
 * @param child the child of the PTNode.
 */
    public void addChild(PTNode child) {
        children.add(child);
    }
/**
 * Retrieves the parent of the node.
 * @return the parent of the Node.
 */
    public PTNode getParent() {
        return parent;
    }
/**
 * Sets the parent Node to a new parent.
 * @param parent the new parent of the Node.
 */
    public void setParent(PTNode parent) {
        this.parent = parent;
    }
/**
 * Retrieves the Token of the Node.
 * @return the Token associated with the Node.
 */
    public Token getSymbol() {
        return symbol;
    }
/**
 * Sets the Token to a new Token.
 * @param symbol the new Token.
 */
    public void setSymbol(Token symbol) {
        this.symbol = symbol;
    }
    /**
     * This method creates empty PTNodes that allows subclasses to set the contents to values.
     * Index 0 is reserved for the Type; Index 1, is reserved for the Name; Index 2
     * is reserved for the ParamList; Index 3 is reserved for a function Block;
     * @param numChilds 
     */
    private void initChildren(int numChilds) {
        for (int i = 0; i < numChilds; ++i) {
            boolean add = children.add(null);
        }
    }
    
    protected String indent(int amount){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<amount;++i){
            sb.append(" ");
        }
        return sb.toString();
    }
    
    public String format(int indent){
        return format(indent, false);
    }

    public String format(int indent, boolean suppressNewline) {
        StringBuilder sb = new StringBuilder();
        Iterator<PTNode> nodes = getChildren().iterator();
        if (nodes != null) {
            while (nodes.hasNext()) {
                sb.append(indent(indent));
                sb.append(nodes.next().format(indent));
                if(!suppressNewline){
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }
    
    public abstract ASTNode toAST();
    //
    protected PTNode parent;
    protected ArrayList<PTNode> children;
    protected Token symbol;
}
