package ptn;

import lexer.Token;
import parser.Scope;

/**
 *creates a declaration that extends PTNode that looks for the type and name
 * @author Alan
 */
public abstract class Decl extends PTNode {

    /**
     * creates a declaration based on the number of children 
     * @param numChildren 
     */
    public Decl(int numChildren) {
        super(numChildren);
    }

    /**
     * creates an empty declaration 
     */
    public Decl() {
    }
    
    /**
     * return the type value of the children 
     * @return 
     */
    public Type getType(){
        return (Type) children.get(0);
    }
    
    /**
     * set the type value of the children 
     * @param t 
     */
    public void setType(Type t){
        children.set(0,t);
    }
    
    /**
     * return the name of the children 
     * @return 
     */
    public Name getName(){
        return (Name) children.get(1);
    }
    
    /**
     * set the name of the children 
     * @param name 
     */
    public void setName(Name name){
        children.set(1, name);
    }
        @Override
    public Token getSymbol(){
    return children.get(1).getSymbol();
    
    }
    
    /**
     * check to see if a value exists from the PTNode list 
     * @return 
     */
    public boolean hasInit(){
        return children.get(2)!=null;
    }

    /**
     * return the value of the decl scope 
     * @return 
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * set the value of the scope  
     * @param scope 
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    public abstract PTNode getInit();
    public abstract void setInit(PTNode list);
    private Scope scope;
    
}
