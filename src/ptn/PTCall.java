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
private Proccall call;
    public PTCall(Proccall call){
    this.call = call;
    }

    public Proccall getCall() {
        return call;
    }

    public void setCall(Proccall call) {
        this.call = call;
    }
        public Name getName() {
        return call.getName();
    }
    public void setIdent(Name name) {
        call.setIdent(name);
    }

    public ExprList getArgs() {
        return call.getArgs();
    }

    public void setArgs(ExprList args) {
        call.setArgs(args);
    }
      public boolean hasArgs() {
        return call.hasArgs();
    }

    public String getNameMangle() {
        return call.getNameMangle();
    }

    
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
