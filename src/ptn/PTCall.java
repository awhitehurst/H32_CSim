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
public class PTCall extends Proccall{

    @Override
    public ast.PTCall toAST() {
        ast.PTCall ptcall = new ast.PTCall();
        ptcall.setName(getName().toAST());
        if (hasArgs()) {
            ptcall.setArgs((ast.ExprList) getArgs().toAST());
        }
        return ptcall;
    }

    
    
}
