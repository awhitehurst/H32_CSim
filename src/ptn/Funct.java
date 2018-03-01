package ptn;

import parser.Scope;

/**
 *
 * @author Lab Admin
 */
public class Funct extends PTNode {

    public Funct() {
        super(4);
    }

    //return value type
    public Type getType() {
        return (Type) children.get(0);
    }

    //set value type 
    public void setType(Type t) {
        children.set(0, t);
    }

    //return value name 
    public Name getName() {
        return (Name) children.get(1);
    }

    //set value name 
    public void setName(Name n) {
        children.set(1, n);
    }

    public String getNameMangle() {
        if (getParamList() == null) {
            return getName().toString() + "$";
        } else {
            return getName().toString() + getParamList().toPolish();
        }
    }

    //return param list values 
    public ParamList getParamList() {
        return (ParamList) children.get(2);
    }

    //set param laist vlaues 
    public void setParamList(ParamList p) {
        children.set(2, p);
    }

    //return block 
    public Block getBody() {
        return (Block) children.get(3);
    }

    //set block value 
    public void setBody(Block b) {
        children.set(3, b);
    }

    //return header if body is empty 
    public boolean isHeader() {
        return getBody().isEmpty();
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
