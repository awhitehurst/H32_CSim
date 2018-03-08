/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import java.util.Iterator;
import lexer.Token;
import parser.Scope;

/**
 * A function holds a Token, a Name, a Type, an ArrayList of parameters, and a Block. May be a header function located
 * at the top of the program, or a function with a block of statements.
 * @author Alan
 */
public class Function extends ASTNode {
/**
 * Constructor creates a Function with a Token.
 * @param symbol 
 */
    public Function(Token symbol) {
        super(symbol);
    }
/**
 * Creates an empty Function.
 */
    public Function() {
    }
/**
 * Fully specified Constructor.
 * @param symbol The Token of the Function
 * @param v The name of the Function
 * @param t The type of the Function
 * @param params ArrayList containing the parameters of the function
 * @param body The Block of the function
 */
    public Function(Token symbol, Name v, Type t, ArrayList<Param> params, Block body) {
        super(symbol);
        this.name = v;
        this.type = t;
        this.params = params;
        this.body = body;
    }

/**
 * Retrieves the parameters of the function
 * @return and ArrayList containing the parameters.
 */
    public ArrayList<Param> getParams() {
        return params;
    }

/**
 * Sets the Parameter ArrayList of the Function.
 * @param params 
 */

    public void setParams(ArrayList<Param> params) {
        this.params = params;
    }
/**
 * Adds a new Parameter to the list of parameters.
 * @param param The Parameter to be added
 */

    public void addParam(Param param) {
        params.add(param);
    }
/**
 * Retrieves the Type of the function.
 * @return The Type of the function.
 */
    public Type getType() {
        return type;
    }
/**
 * Sets the type of the function
 * @param t the new Type.
 */
    public void setType(Type t) {
        this.type = t;
    }
/**
 * Retrieves the name of the Function
 * @return the name of the function
 */
    public Name getName() {
        return name;
    }
/**
 * Sets the name of the function
 * @param v the new name.
 */
    public void setName(Name v) {
        this.name = v;
    }
/**
 * Asks whether this is a header declaration of a function
 * @return true if it is a header declaration, otherwise false.
 */
    public boolean isHeader() {
        return header;
    }
/**
 * Sets whether this Function is a header to a new value
 * @param header the new boolean value.
 */
    public void setHeader(boolean header) {
        this.header = header;
    }
/**
 * Retrieves the Block associated with the Function.
 * @return the Function's block.
 */
    public Block getBody() {
        return body;
    }
/**
 * Sets the body to a new body.
 * @param body 
 */
    public void setBody(Block body) {
        this.body = body;
    }
/**
 * Retrieves the Scope of the Function
 * @return the Scope associated with the function
 */
    public Scope getScope() {
        return scope;
    }
/**
 * Sets the scope to a new Scope.
 * @param scope the new Scope.
 */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if(!isHeader()){
            body.typeCheck(msgs);
        }
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        if (!isHeader()) {
            code.add(name.getName() + ":");
            code.add("\tesba");
            if (getParams() != null) {
                // due to the use of the base register in the optimal instruction
                // set of H1, parameters begin at offset 2 on the stack and
                // local variables have a negative offset
                int paramOffset = 2;
                for(Param param : getParams()){
                    param.getScope().setRelOffset(param.getVariable().getName(), paramOffset++);
                }
            }
            body.generate(code, true);
            code.add("\treba");
            code.add("\tret");
        } else {
            code.add("\textern " + name.getName());
        }
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[Function: ");
        sb.append(type.format(indent + 3, true));
        sb.append(name.format(indent + 3, true));
        if (getParams() != null) {
            Iterator<Param> theParams = params.iterator();
            while (theParams.hasNext()) {
                sb.append(theParams.next().format(indent + 3, false));
            }
        }
        if (!isHeader()) {
            sb.append("\n");
            sb.append(body.format(indent + 3, false));
        } else {
            sb.append("{}");
        }
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
    //
    private Name name;
    private Type type;
    private ArrayList<Param> params;
    private boolean header;
    private Block body;
    private Scope scope;

}