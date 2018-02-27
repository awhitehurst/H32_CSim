/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * ExprList is an ASTNode containing an ArrayList of Expressions.
 * @author Alan
 */
public class ExprList extends ASTNode {
    /**
     * Creates an empty ExprList.
     */
    public ExprList(){
        exprs = new ArrayList<>();
    }
    /**
     * Returns the size of the ExprList.
     * @return 
     */
    public int size(){
        return exprs.size();
    }
    /**
     * Adds a new expression.
     * @param expr the new expression.
     */
    public void add(Expression expr){
        exprs.add(expr);
    }
    /**
     * Get the expression at a specified index.
     * @param index the index to search at.
     * @return The requested Expression.
     */
    public Expression get(int index){
        return (Expression) exprs.get(index);
    }
    /**
     * Sets the Expression at a specified index to a new Expression
     * @param index the index to change
     * @param expr the new expression
     */
    public void set(int index, Expression expr){
        exprs.set(index, expr);
    }
    /**
     * Creates an iterator for the ExprList.
     * @return the iterator.
     */
    public Iterator<Expression> iterator(){
        return exprs.iterator();
    }
    /**
     * Returns a ListIterator for the ExprList.
     * @return 
     */
    public ListIterator<Expression> listIterator(){
        return exprs.listIterator();
    }
    
    public String toPolish() {
        StringBuilder sb = new StringBuilder("$");
        Iterator<Expression> expressions = exprs.iterator();
        while (expressions.hasNext()) {
            Expression exp = (Expression) expressions.next();
            sb.append(exp.toPolish());
        }
        return sb.toString();
    }
    
    @Override
    public void typeCheck(ArrayList<String> msgs) {
        Iterator<Expression> expressions = exprs.iterator();
        while(expressions.hasNext()){
            Expression exp = (Expression) expressions.next();
            exp.typeCheck(msgs);
        }
    }
    
    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        ListIterator<Expression> expressions = exprs.listIterator(exprs.size());
        while(expressions.hasPrevious()){
            Expression expr = expressions.previous();
            expr.generate(code, inFunction);
            code.add("\tpush");
        }
    }

    
    @Override
    public String format(int indent, boolean surpressNewline){
        StringBuilder sb = new StringBuilder();
        Iterator<Expression> expressions = exprs.iterator();
        while(expressions.hasNext()){
            sb.append(expressions.next().format(indent, true));
            if(expressions.hasNext()){
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    
    private final ArrayList<Expression> exprs;

    
    
}
