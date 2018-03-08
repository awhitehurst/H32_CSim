/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

import lexer.Token;

/**
 * Creates a PTNode with a Token as a Name.
 * @author Alan
 */
public class Name extends PTNode {
/**
 * Creates a Name with a Token
 * @param s the Token to be used.
 */
    public Name(Token s) {
       super(s);
    }
    
    @Override
    public String format(int indent){
        return toString();
    }
    
    @Override
    public ast.Name toAST(){
        return new ast.Name(symbol);
    }
    
    @Override
    public String toString(){
        return getSymbol().getSymbol();
    }
    
    
}
