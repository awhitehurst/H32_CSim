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

    /**
     * return the name of the value 
     * @return 
     */
    public Name getName() {
        return (Name) children.get(0);
    }

    /**
     * set the name of the value
     * @param name 
     */
    public void setName(Name name) {
        children.set(0, name);
    }

    /**
     * return the value from the expression list 
     * @return 
     */
    public ExprList getArgs() {
        return (ExprList) children.get(1);
    }

    /**
     * set the value in the expression list 
     * @param args 
     */
    public void setArgs(ExprList args) {
        children.set(1, args);
    }

    /**
     * check to see if args has a value 
     * @return 
     */
    public boolean hasArgs() {
        return children.get(1) != null;
    }

    /**
     * return the value of the scope 
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
