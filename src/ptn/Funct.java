package ptn;

import parser.Scope;

/**
 * Represents a Function as a PTNode
 * @author Lab Admin
 */
public class Funct extends PTNode {
/**
 * Creates an empty Funct
 */
    public Funct() {
        super(4);
    }

    /**
     * return the value type 
     * @return 
     */
    public Type getType() {
        return (Type) children.get(0);
    }

    /**
     * set the values type 
     * @param t 
     */
    public void setType(Type t) {
        children.set(0, t);
    }

    /**
     * return the values name 
     * @return 
     */
    public Name getName() {
        return (Name) children.get(1);
    }

    /**
     * set the values name 
     * @param n 
     */
    public void setName(Name n) {
        children.set(1, n);
    }

    /**
     * 
     * @return 
     */
    public String getNameMangle() {
        if (getParamList() == null) {
            return getName().toString() + "$";
        } else {
            return getName().toString() + getParamList().toPolish();
        }
    }

    /**
     * return the paramerters list values
     * @return 
     */
    public ParamList getParamList() {
        return (ParamList) children.get(2);
    }

    /**
     * set the paramaters list vlaue 
     * @param p 
     */
    public void setParamList(ParamList p) {
        children.set(2, p);
    }

    /**
     * return the value of the block
     * @return 
     */
    public Block getBody() {
        return (Block) children.get(3);
    }

    /**
     * set the value of the block
     * @param b 
     */
    public void setBody(Block b) {
        children.set(3, b);
    }

    /**
     * return header if body is empty 
     * @return 
     */
    public boolean isHeader() {
        return getBody().isEmpty();
    }

    /**
     * return the scop value 
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

    @Override
    public String format(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent(indent));
        sb.append(getType().format(indent));
        sb.append(" ");
        //sb.append(getName().format(indent));
        sb.append(getName());
        sb.append("(");
        if (getParamList() != null) {
            sb.append(getParamList().format(indent));
        }
        sb.append(")");
        sb.append(getBody().format(indent + 5));
        return sb.toString();
    }

    @Override
    public ast.ASTNode toAST() {
        ast.Function f = new ast.Function();
        f.setName(getName().toAST());
        f.setType(getType().toAST());
        f.setScope(getScope());
        if (getParamList() != null) {
            f.setParams(getParamList().toParamList());
        }
        if (getBody().isEmpty()) {
            f.setHeader(true);
        } else {
            f.setBody(getBody().toAST());
        }
        return f;
    }
    //
    private Scope scope;
}
