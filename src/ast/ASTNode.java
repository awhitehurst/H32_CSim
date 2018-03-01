package ast;
import java.util.ArrayList;
import lexer.Token;

/**
 * A class representing the most basic node for the Abstract Syntax Tree.
 * Contains a single Token. Serves as a superclass for more specific types of ASTnodes.
 * @author Alan
 */
public abstract class ASTNode {
    /**
     * Sets the token to null
     */
    public ASTNode(){
        this(null);
    }
    /**
     * Constructor sets stored Token to provided token.
     * @param symbol the symbol to store.
     */
    public ASTNode(Token symbol){
        this.symbol = symbol;
    }
    /**
     * Returns the stored Token
     * @return the Token stored.
     */
    public Token getSymbol(){
        return symbol;
    }
    /**
     * Sets the symbol to the provided symbol.
     * @param s new symbol
     */
    public void setSymbol(Token s){
        symbol = s;
    }
    
    public abstract void typeCheck(ArrayList<String> msgs);
    public abstract void generate(ArrayList<String> code, boolean dynamic); 
    
    /**
     * Adds a number of spaces to indent a string.
     * @param amount the number of spaces to indent.
     * @return the indented string.
     */
    protected String indent(int amount){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<amount;++i){
            sb.append(" ");
        }
        return sb.toString();
    }
/**
 * Formats a string by indenting.
 * @param indent the number of indents.
 * @return formated line.
 */
    public String format(int indent){
        return format(indent, false);
    }
    
    public abstract String format(int indent, boolean suppressNewline);
    
    //
    protected Token symbol;

    
}
