/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import lexer.SType;
import lexer.Token;

/**
 * A Return is a special kind of statement that contains a Token, and may contain an Expression.
 * @author Alan
 */
public class Return extends Statement {
/**
 * Creates empty Return
 */
    public Return() {
    }
/**
 * Creates Return with an associated Token
 * @param symbol 
 */
    public Return(Token symbol) {
        super(symbol);
    }
/**
 * Creates Return with a Token and an Expression
 * @param symbol the Token to use
 * @param value the Expression to store.
 */
    public Return(Token symbol, Expression value) {
        super(symbol);
        this.value = value;
    }
/**
 * Retrieves the Expression of the Return
 * @return the Expression.
 */
    public Expression getValue() {
        return value;
    }
/**
 * Sets the value of the Expression.
 * @param value the new Expression.
 */
    public void setValue(Expression value) {
        this.value = value;
    }
    
     public Funcall hasFuncall(){ 
         if(value instanceof Expression){
                return (Funcall)value;
              
            }
             return null;
    }
     
    
    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if(getValue()!=null){
            getValue().typeCheck(msgs);
        }
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction, int offset) {
        if(getValue()!=null){
            getValue().generate(code, inFunction);
        }
        if(inFunction){
            code.add("\tret");
        }
    }
    
    @Override
    public String format(int indent, boolean suppressNL){
        StringBuilder sb = new StringBuilder();
        if(!suppressNL){
            sb.append(indent(indent));
        }
        sb.append("[Return: ");
        if(getValue()!=null){
            sb.append(this.getValue().format(indent,true));
        }
        sb.append(" ]");
        if(!suppressNL){
            sb.append("\n");
        }
        return sb.toString();
    }
    private Expression value;

   
    
}