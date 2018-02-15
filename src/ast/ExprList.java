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
 *
 * @author Alan
 */
public class ExprList extends ASTNode {
    public ExprList(){
        exprs = new ArrayList<>();
    }
    
    public int size(){
        return exprs.size();
    }
    
    public void add(Expression expr){
        exprs.add(expr);
    }
    
    public Expression get(int index){
        return (Expression) exprs.get(index);
    }
    
    public void set(int index, Expression expr){
        exprs.set(index, expr);
    }
    
    public Iterator<Expression> iterator(){
        return exprs.iterator();
    }
    
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
