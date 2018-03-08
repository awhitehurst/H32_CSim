/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import lexer.Token;
import parser.Scope;

/**
 * Declaration is a kind of ASTNode, containing a Token called symbol and a Scope.
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
 * Returns the scope connected with this Declaration.
 * @return 
 */
    public Scope getScope() {
        return scope;
    }
  
/**
 * Sets the scope to a new scope.
 * @param scope the new scope.
 */
    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    public abstract Type getType();
    protected Scope scope;
    
}
