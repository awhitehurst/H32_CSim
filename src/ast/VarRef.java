package ast;

import java.util.ArrayList;
import lexer.Token;
import parser.Scope;

/**
 *creates a variable reference that contains a expression, name, and a token symbol
 * @author Alan
 */
public class VarRef extends Expression {

    
    protected Name v;
    protected Expression index;
    protected int indirect = 0;
    protected Scope scope;

    /**
     * creates an empty variable reference 
     */
    public VarRef() {
    }

    /**
     * 
     * @param symbol is a token
     * @param v is a name
     * @param expr is a expression 
     */
    public VarRef(Token symbol, Name v, Expression expr) {
        super(symbol);
        this.v = v;
        this.index = expr;
    }

    /**
     * check to if the expression index value is not equal to null 
     * @return 
     */
    public boolean isArrayRef() {
        return index != null;
    }

    /**
     * return the expression index value 
     * @return 
     */
    public Expression getIndex() {
        return index;
    }

    /**
     * set the value of the expression index 
     * @param expr 
     */
    public void setIndex(Expression expr) {
        this.index = expr;
    }

    /**
     * return the name of the variable 
     * @return 
     */
    public Name getVariable() {
        return v;
    }

    /**
     * set the name of the variable 
     * @param v 
     */
    public void setVariable(Name v) {
        this.v = v;
    }

    /**
     * check to make sure the indirect value is greater then zero 
     * @return 
     */
    public boolean isIndirect() {
        return indirect > 0;
    }

    /**
     * set the indirect value 
     * @param indirect 
     */
    public void setIndirect(int indirect) {
        this.indirect = indirect;
    }

    /**
     * increase the indirect value by one 
     */
    public void incIndirect() {
        this.indirect++;
    }

    /**
     * return the value of the scope 
     * @return 
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * set the scope value 
     * @param scope 
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * return the correct variable name 
     * @return 
     */
    public String getQualifiedVariableName() {
        return "@" + scope.label() + this.getVariable().getName();
    }
    
    /**
     * return the offset value of the scope 
     * @return 
     */
    public int getRelativeOffset(){
        return scope.getRelOffset(getVariable().getName());
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[VarRef: ");
        if (isIndirect()) {
            for (int i = 0; i < indirect; ++i) {
                sb.append("*");
            }
        }
        sb.append(this.getVariable().format(indent, true));
        sb.append(" ");
        if (isArrayRef()) {
            sb.append("index: ");
            sb.append(index.format(indent, true));
        }
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<VarRef: ");
        sb.append(v);
        sb.append(" (");
        sb.append(indirect);
        sb.append(")");
        if (index != null) {
            sb.append(" = ");
            sb.append(index);
        }
        sb.append(" >");
        return sb.toString();
    }

    @Override
    public Type getType() {
        return scope.getType(v.getName()).toAST();
    }
    
    
    public Type getType(boolean isTarget){
              Type t = new Type(scope.getType(v.getName()).toAST());
        if(isTarget && this.isIndirect()){
            t.setPointer(t.getPointer()-1);
        }
        if(t.isPointer() && isArrayRef()){
            t.setPointer(t.getPointer()-1);
        }
        System.out.println("Getting type of " + this + " as " + t);
        return t;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        return;
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        if (!getType().isStatic() && inFunction) {
            if (isArrayRef()) {
                getIndex().generate(code, inFunction);
                code.add("\taddc " + scope.getRelOffset(getVariable().getName()));
                code.add("\tcora");
                code.add("\tldi");
            } else {
                code.add("\tldr " + scope.getRelOffset(getVariable().getName()));
            }
        } else if (isArrayRef()) {
            getIndex().generate(code, inFunction);
            code.add("\taddc " + getQualifiedVariableName());
            code.add("\tldi");
//        } else if (getType().isPointer()) {
//            code.add("\tldc " + getQualifiedVariableName());
        } else {
            code.add("\tld " + getQualifiedVariableName());
        }
    }

}
