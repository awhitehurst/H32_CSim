/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

/**
 *Creates an assign state that extends a statement 
 * @author Alan
 */
public class AssignState extends Statement {
    
    public AssignState(){
        super(2);
    }
    
    /**
     * get the indent value of the variable reference 
     * @return 
     */
    public VarRef getIdent(){
        return (VarRef) children.get(0);
    }

    /**
     * set the indent value of the variable reference 
     * @param ident 
     */
    public void setIdent(VarRef ident) {
        children.set(0,ident);
    }
    
    /**
     * return the expression in the assign state 
     * @return 
     */
    public Expression getExpression(){
        return (Expression) children.get(1);
    }

    /**
     * set the value of the expression in the assign state
     * @param expr 
     */
    public void setExpression(Expression expr) {
        children.set(1, expr);
    }
    
    @Override
    public String format(int indent, boolean suppressNewline){
        StringBuilder sb = new StringBuilder();
        if(!suppressNewline){
            sb.append(indent(indent));
        }
        sb.append(getIdent().format(indent));
        sb.append(" = ");
        sb.append(getExpression().format(indent));
        if(!suppressNewline){
            sb.append(";\n");
        }
        return sb.toString();
    }
    
    @Override
    public ast.Assignment toAST(){
        ast.Assignment assignment = new ast.Assignment();
        assignment.setVarRef(getIdent().toAST());
        assignment.setExpr(getExpression().toAST());
        return assignment;
    }
    
}
