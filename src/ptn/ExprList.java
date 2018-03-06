
package ptn;

import ast.ASTNode;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *creates an array list for expressions that extends the PTNode 
 * @author Alan
 */
public class ExprList extends PTNode {
    
    /**
     * creates an empty expression list 
     */
    public ExprList(){
    }
    
    /**
     * add an expression to the list 
     * @param expr 
     */
    public void addExpr(Expression expr){
        children.add(expr);
    }
    
    /**
     * return an expression at a given point 
     * @param index
     * @return 
     */
    public Expression getExpr(int index){
        return (Expression) children.get(index);
    }
    
    /**
     * set an expression value at a given point 
     * @param index
     * @param expr 
     */
    public void setExpr(int index, Expression expr){
        children.set(index, expr);
    }
    
     public String toPolish() {
        StringBuilder sb = new StringBuilder("$");
        Iterator<PTNode> expressions = children.iterator();
        while (expressions.hasNext()) {
            Expression arg = (Expression) expressions.next();
            sb.append(arg.toPolish());
        }
        return sb.toString();
    }
    
    @Override
    public String format(int indent){
        StringBuilder sb = new StringBuilder();
        Iterator<PTNode> expressions = children.iterator();
        while(expressions.hasNext()){
            sb.append(expressions.next().format(indent));
            if(expressions.hasNext()){
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public ASTNode toAST() {
        ast.ExprList exprList = new ast.ExprList();
        Iterator<PTNode> expressions = children.iterator();
        while(expressions.hasNext()){
            exprList.add((ast.Expression)expressions.next().toAST());
        }
        return exprList;
    }

}
