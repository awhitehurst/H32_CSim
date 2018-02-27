/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import lexer.Token;

/**
 * An Expression is an abstract class containing a Token.
 * @author Alan
 */
public abstract class Expression extends ASTNode {
/**
 * Creates an Expression with a Token.
 * @param symbol 
 */
    public Expression(Token symbol) {
        super(symbol);
    }
/**
 * Creates an empty Expression.
 */
    public Expression() {
        
    }
    
    public abstract Type getType();

    public String toPolish() {
        return "_" + getType().toPolish();
    }
    
    
    
}
