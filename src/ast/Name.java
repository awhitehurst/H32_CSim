/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 * Wrapper class for a token that we know is the name of a function or variable.
 * @author Alan
 */
public class Name extends ASTNode {
/**
 * Sets symbol to the provided symbol
 * @param symbol the symbol to be stored.
 */
    public Name(Token symbol) {
        super(symbol);
    }
/**
 * Unspecified Constructor.
 */
    public Name() {
    }
    /**
     * Returns the symbol stored in the Token.
     * @return the name
     */
    public String getName(){
        return symbol.getSymbol();
    }
    
    @Override
     public String format(int indent, boolean suppressNL){
        StringBuilder sb = new StringBuilder();
        if(!suppressNL){
            sb.append(indent(indent));
        }
        sb.append("[Name: ");
        sb.append(getName());
        sb.append(" ]");
        if(!suppressNL){
            sb.append("\n");
        }
        return sb.toString();
    }
     
    @Override
     public String toString(){
         return format(0,true);
     }
    //

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
