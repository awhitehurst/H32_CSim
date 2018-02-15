/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import lexer.Token;
import parser.Scope;
import parser.SymbolTable;

/**
 *
 * @author Alan
 */
public abstract class ASTNode {
    
    public ASTNode(){
        this(null);
    }
    
    public ASTNode(Token symbol){
        this.symbol = symbol;
    }
    
    public Token getSymbol(){
        return symbol;
    }
    
    public void setSymbol(Token s){
        symbol = s;
    }
    
    public abstract void typeCheck(ArrayList<String> msgs);
    public abstract void generate(ArrayList<String> code, boolean dynamic); 
    
    
    protected String indent(int amount){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<amount;++i){
            sb.append(" ");
        }
        return sb.toString();
    }
    
    public String format(int indent){
        return format(indent, false);
    }
    
    public abstract String format(int indent, boolean suppressNewline);
    
    //
    protected Token symbol;

    
}
