/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

/**
 *creates an array decleration that extends a decleration 
 * @author Alan
 */
public class ArrayDecl extends Decl {
   
    public ArrayDecl(){
        super(4);
    }
    
    /**
     * sets the type, name, and scope of the array delcaration 
     * @param v 
     */
    public ArrayDecl(VarDecl v){
        super(4);
        setType(v.getType());
        setName(v.getName());
        setScope(v.getScope());
    }
    
    /**
     * return the expression size 
     * @return 
     */
    public Expression getSize(){
        return (Expression) children.get(2);
    }
    
    /**
     * set the expression size 
     * @param expr 
     */
    public void setSize(Expression expr){
        children.set(2, expr);
    }
    
    /**
     * check to see if the expression is not equal to null
     * @return 
     */
    public boolean hasSize(){
        return children.get(2)!=null;
    }

    @Override
    public ExprList getInit() {
        return (ExprList) children.get(3);
    }

    @Override
    public void setInit(PTNode exprList) {
        if (exprList instanceof ExprList) {
            children.set(3, exprList);
        }
    }
    
    @Override
    public boolean hasInit(){
        return children.get(3)!=null;
    }

    @Override
    public String format(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent(indent));
        sb.append(getType().format(indent));
        sb.append(" ");
        sb.append(getName().format(indent));
        sb.append("[");
        if(hasSize()){
            sb.append(getSize().format(indent));
        }
        sb.append("]");
        if (hasInit()) {
            sb.append(" = ");
            sb.append("{ ");
            sb.append(getInit().format(indent));
            sb.append(" }");
        }
        sb.append(";\n");
        return sb.toString();
    }
    
    @Override
    public ast.ArrayDecl toAST(){
        ast.ArrayDecl arrayDecl = new ast.ArrayDecl();
        arrayDecl.setType(getType().toAST());
        arrayDecl.setVariable(getName().toAST());
        arrayDecl.setScope(getScope());
        if(hasSize()){
            arrayDecl.setSize(getSize().toAST());
        }
        if(hasInit()){
            arrayDecl.setInitList((ast.ExprList)getInit().toAST());
        }
        return arrayDecl;
    }

}
