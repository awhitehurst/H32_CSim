/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

import parser.Scope;

/**
 * Abstract class representing various forms of Declarations as a PTNode.
 * @author Alan
 */
public abstract class Decl extends PTNode {
/**
 * Creates a Decl with the specified number of children.
 * @param numChildren the number of children.
 */
    public Decl(int numChildren) {
        super(numChildren);
    }
/**
 * Creates an empty Decl.
 */
    public Decl() {
    }
    
    //return type value 
    public Type getType(){
        return (Type) children.get(0);
    }
    
    //set value type 
    public void setType(Type t){
        children.set(0,t);
    }
    
    //return name 
    public Name getName(){
        return (Name) children.get(1);
    }
    
    //set name of value 
    public void setName(Name name){
        children.set(1, name);
    }
    /**
     * Returns whether the children ArrayList has been Initialized.
     * @return true or false.
     */
    public boolean hasInit(){
        return children.get(2)!=null;
    }

    //return scope value 
    public Scope getScope() {
        return scope;
    }

    //set value of scope
    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    public abstract PTNode getInit();
    public abstract void setInit(PTNode list);
    private Scope scope;
    
}
