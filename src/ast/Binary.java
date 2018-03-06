
package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 *
 * @author Alan
 */
public abstract class Binary extends Expression {

    public Binary() {
        
    }

    /**
     * Set the symbol for the binary 
     * @param symbol 
     */
    public Binary(Token symbol) {
        super(symbol);
    }

    /**
     * Creates a binary with a symbol, operator, lhs, and a rhs 
     * @param symbol
     * @param op
     * @param lhs
     * @param rhs 
     */
    public Binary(Token symbol, Operator op, Expression lhs, Expression rhs) {
        super(symbol);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * Returns the lhs expression of the array
     * @return 
     */
    public Expression getLhs() {
        return lhs;
    }

    /**
     * Sets the expression lhs
     * @param lhs 
     */
    public void setLhs(Expression lhs) {
        this.lhs = lhs;
    }

    /**
     * returns the operator 
     * @return 
     */
    public Operator getOp() {
        return op;
    }

    /**
     * sets the operator
     * @param op 
     */
    public void setOp(Operator op) {
        this.op = op;
    }

    /**
     * returns the rhs expression of the array
     * @return 
     */
    public Expression getRhs() {
        return rhs;
    }

    /**
     * sets the rhs expression
     * @param rhs 
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
