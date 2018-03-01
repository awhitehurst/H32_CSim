/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ptn;

import parser.Scope;

/**
 *
 * @author Lab Admin
 */
public class Block extends Statement {
    
    public Block(){
        super(1);
    }
    
    //return body of delecration state 
    public DeclStats getBody(){
        return (DeclStats) children.get(0);
    }
    
    //set body of decleration state 
    public void setBody(DeclStats s){
        children.set(0, s);
    }
    
    //check to see if body is empty 
    public boolean isEmpty(){
        return getBody().isEmpty();
    }

    //return scope value 
    public Scope getScope() {
        return scope;
    }

    //set scope value 
    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    @Override
    public String format(int indent){
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append(getBody().format(indent));
        sb.append(indent(Math.max(indent-5,0)));
        sb.append("}\n");
        return sb.toString();
    }
    
    @Override
    public ast.Block toAST(){
        ast.Block block = new ast.Block();
        block.setDecls(getBody().toDeclarations());
        block.setStats(getBody().toStatements());
        block.setScope(getScope());
        return block;
    }
    //
    private Scope scope;
}
