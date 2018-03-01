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

    public Funcall() {
        super();
    }

    //return name 
    public Name getName() {
        return (Name) children.get(0);
    }

    //set name 
    public void setName(Name name) {
        children.set(0, name);
    }

    //return args value  
    public ExprList getArgs() {
        return (ExprList) children.get(1);
    }

    //set args value 
    public void setArgs(ExprList args) {
        children.set(1, args);
    }

    //does args have a value 
    public boolean hasArgs() {
        return children.get(1) != null;
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
