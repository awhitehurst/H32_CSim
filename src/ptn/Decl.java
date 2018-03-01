/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

import parser.Scope;

/**
 *
 * @author Alan
 */
public abstract class Decl extends PTNode {

    public Decl(int numChildren) {
        super(numChildren);
    }

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
