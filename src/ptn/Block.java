/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ptn;

import parser.Scope;

/**
 *Creates a block  for a declaration state 
 * @author Lab Admin
 */
public class Block extends Statement {
    
    public Block(){
        super(1);
    }
    
    /**
     * return the body of the decleration state 
     * @return 
     */
    public DeclStats getBody(){
        return (DeclStats) children.get(0);
    }
    
    /**
     * set the body of the decleration state 
     * @param s 
     */
    public void setBody(DeclStats s){
        children.set(0, s);
    }
    
    /**
     * check to see if the body is empty 
     * @return 
     */
    public boolean isEmpty(){
        return getBody().isEmpty();
    }

    /**
     * return the value of the scope of the block
     * @return 
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * set the value of the scope in the block
     * @param scope 
     */
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
