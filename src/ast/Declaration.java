/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import lexer.Token;
import parser.Scope;

/**
 *
 * @author Alan
 */
public abstract class Declaration extends ASTNode {

    /**
     * creates a empty declaration 
     */
    public Declaration() {
        
    }
    
    /**
     * Creates a declaration with an associated Token
     * @param symbol 
     */
    public Declaration(Token symbol){
        super(symbol);
    }
    
    public abstract int getAllocSize();

    /**
     * return the value of the scope 
     * @return 
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * sets the value of the scope 
     * @param scope 
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    public abstract Type getType();
    protected Scope scope;
    
}
