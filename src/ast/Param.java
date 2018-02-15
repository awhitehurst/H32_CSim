/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import parser.Scope;

/**
 *
 * @author Alan
 */
public class Param extends Expression {

    public Param() {
    }

    @Override
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Name getVariable() {
        return variable;
    }

    public void setVariable(Name variable) {
        this.variable = variable;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[Param: ");
        sb.append(this.getType().format(indent, true));
        sb.append(" ");
        sb.append(this.getVariable().format(indent, true));
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }

    //
    private Type type;
    private Name variable;
    private Scope scope;

}
