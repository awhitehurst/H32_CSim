/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

/**
 *Creates a method to return the value of expressions 
 * @author Alan
 */
public class Expression extends PTNode {

    public Expression() {
        super(3);
    }

    /**
     * return Lhs expression value 
    */
    public Expression getLhs() {
        return (Expression) children.get(0);
    }

    /**
     * set Lhs expression value
     */
    public void setLhs(Expression e) {
        children.set(0, e);
    }

    /**
     * return operator value
     */
    public Operator getOp() {
        return (Operator) children.get(1);
    }

    /**
     * set operator value 
     * @param op 
     */
    public void setOp(Operator op) {
        children.set(1, op);
    }

    /**
     * return Rhs expression value 
     * @return 
     */
    public Expression getRhs() {
        return (Expression) children.get(2);
    }

    /**
     * set Rhs expression value 
     * @param e 
     */
    public void setRhs(Expression e) {
        children.set(2, e);
    }

    /**
     * check to see an operator has a value 
     * @return 
     */ 
    public boolean hasOp() {
        return children.get(1) != null;
    }

    /**
     * checks to see if Rhs has a value 
     * @return 
     */
    public boolean hasRhs() {
        return children.get(2) != null;
    }
    
    /**
     * check to see if an operator and a rhs exist  
     * @return 
     */
    private boolean isTerm(){
        return !hasOp() && !hasRhs();
    }
    
    private boolean isUnary(){
        return hasOp() && !hasRhs();
    }
    
    public String toPolish(){
        return "_e";
    }

    @Override
    public String format(int indent) {
        StringBuilder sb = new StringBuilder();
        if (isTerm()) {
            sb.append(getLhs().format(indent));
            return sb.toString();
        } else if (isUnary()) {
            sb.append(getOp().format(indent));
            sb.append(getLhs().format(indent));
            return sb.toString();
        } else {
            sb.append("(");
            sb.append(getLhs().format(indent));
            sb.append(" ");
            sb.append(getOp().format(indent));
            sb.append(" ");
            sb.append(getRhs().format(indent));
            sb.append(")");
            return sb.toString();
        }
    }
    
    @Override
    public ast.Expression toAST(){
        if(isTerm()){
            if(getLhs()==null){
                throw new RuntimeException("error converting expression");
            }
            return getLhs().toAST();
        } else if(isUnary()){
            ast.Unary unary = new ast.Unary();
            unary.setTerm(getLhs().toAST());
            unary.setOp(getOp().toAST());
            return unary;
        } else {
            ast.Binary binary = ast.binary.Factory.getExpression(getOp().getSymbol().getSymbol());
            binary.setLhs(getLhs().toAST());
            binary.setOp(getOp().toAST());
            binary.setRhs(getRhs().toAST());
            return binary;
        }
    }
    
}
