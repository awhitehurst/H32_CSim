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
public class Funcall extends Expression {

    private String mangle;
    
    public Funcall() {
        super();
    }
    /**
     * Retrieves the stored mangle of the called function.
     * @return the mangle of the called function.
     */
    public String getMangle(){
    return mangle;
    
    }
    /**
     * Stores the name mangle of the called function. Sets it to the given value.
     * @param mangle the name mangle of stored function.
     */
    public void setMangle(String mangle){
    this.mangle = mangle;
    
    }

    public Name getName() {
        return (Name) children.get(0);
    }

    public void setName(Name name) {
        children.set(0, name);
    }

    public ExprList getArgs() {
        return (ExprList) children.get(1);
    }

    public void setArgs(ExprList args) {
        children.set(1, args);
    }

    public boolean hasArgs() {
        return children.get(1) != null;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override
    public String format(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(getName().format(indent));
        sb.append("(");
        if (getArgs() != null) {
            sb.append(getArgs().format(indent));
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public ast.Funcall toAST() {
        ast.Funcall funcall = new ast.Funcall();
        funcall.setName(getName().toAST());
        if (hasArgs()) {
            funcall.setArgs((ast.ExprList) getArgs().toAST());
        }
        funcall.setScope(scope);
        return funcall;
    }

    @Override
    public String toPolish() {
        return "_f";
    }
    
    private Scope scope;
}
