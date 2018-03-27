
package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 * A binary expression is an abstract class representing any kind of expression
 * that has an operator and two subjects of that operator. 
 * @author Alan
 */
public abstract class Binary extends Expression {
/**
 * Creates an empty Binary.
 */
    public Binary() {
        
    }
/**
 * Creates a Binary associated with a given Token
 * @param symbol the Token of the Binary
 */
    public Binary(Token symbol) {
        super(symbol);
    }
/**
 * Fully specified constructor contains a Token, an Operator, an Expression on
 * the lefthand side of the operator, and an Expression on the righthand side
 * of the Operator
 * @param symbol the Token of the Binary
 * @param op the Operator of the Binary
 * @param lhs the Expression on the lefthand side of the Operator
 * @param rhs the Expression on the righthand side of the Operator.
 */
    public Binary(Token symbol, Operator op, Expression lhs, Expression rhs) {
        super(symbol);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * Retrieves the Expression on the lefthand side of the Operator
     * @return the Expression on the lefthand side of the Operator
     */
    public Expression getLhs() {
        return lhs;
    }

   /**
    * Sets the Expression on the lefthand side of the Operator to a new Expression.
    * @param lhs the new Expression.
    */
    public void setLhs(Expression lhs) {
        this.lhs = lhs;
    }

   /**
    * Retrieves the Operator of the Binary.
    * @return the Operator.
    */
    public Operator getOp() {
        return op;
    }

    /**
     * Sets the Operator to a new Operator.
     * @param op the new Operator.
     */
    public void setOp(Operator op) {
        this.op = op;
    }

    /**
     * Retrieves the Expression on the righthand side of the Operator
     * @return the Expression on the righthand side of the Operator
     */
    public Expression getRhs() {
        return rhs;
    }
/**
 * Retrieves the Expression on the righthand side of the Operator
 * @param rhs the Expression on the righthand side of the Operator
 */
    public void setRhs(Expression rhs) {
        this.rhs = rhs;
    }
    
    @Override
    public String format(int indent, boolean suppressNL){
        StringBuilder sb = new StringBuilder();
        if(!suppressNL){
            sb.append(indent(indent));
        }
        sb.append("[Binary ");
        sb.append(this.getLhs().format(indent,true));
        sb.append(" ");
        sb.append(this.getOp().format(indent,true));
        sb.append(" ");
        sb.append(this.getRhs().format(indent,true));
        sb.append(" ]");
        if(!suppressNL){
            sb.append("\n");
        }
        return sb.toString();
    }
    //    
    private Operator op;
    private Expression lhs;
    private Expression rhs;
    
}
