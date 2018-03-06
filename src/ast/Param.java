/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import parser.Scope;

/**
 * A Param is a kind of Expression with a Type, Name, and Scope
 * @author Alan
 */
public class Param extends Expression {
/**
 * Creates empty Param.
 */
    public Param() {
    }

    @Override
    public Type getType() {
        return type;
    }
/**
 * Sets the type of the Param to a given type.
 * @param type the new type.
 */
    public void setType(Type type) {
        this.type = type;
    }
/**
 * Returns the name of the variable in the Expression
 * @return the name of the variable.
 */
    public Name getVariable() {
        return variable;
    }
/**
 * Sets the variable to a new name.
 * @param variable the new name.
 */

    public void setVariable(Name variable) {
        this.variable = variable;
    }

/**
 * Gets the scope of the param 
 * @return 
 */

    public Scope getScope() {
        return scope;
    }

    /**
     * Sets the scope of the param to a new scope.
     * @param scope 
     */
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
