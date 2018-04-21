/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

/**
 *
 * @author daugh
 */
public class PTRef extends VarRef{
    
        @Override
    public ast.PTRef toAST(){
        ast.PTRef ptRef = new ast.PTRef();
        ptRef.setVariable(getName().toAST());
        if(this.isArrayRef()){
            ptRef.setIndex(this.getIndex().toAST());
        }
        ptRef.setIndirect(this.indirect);
        ptRef.setScope(getScope());
        return ptRef;
    }
}
