/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import lexer.Token;
import parser.Scope;

/**
 * A Funcall is a special kind of Expression, containing a Token, a name, and an ExprList of arguments.
 * @author Alan
 */
public class Funcall extends Expression {

    public Funcall(Token symbol) {
        super(symbol);
    }

    public Funcall() {
    }

    public Funcall(Token symbol, Name name, ExprList args) {
        super(symbol);
        this.name = name;
        this.args = args;
    }

    /**
     * Returns the value from the expression list 
     * @return 
     */
    public ExprList getArgs() {
        return args;
    }

    /**
     * Sets the value of args in the expression list 
     * @param args 
     */
    public void setArgs(ExprList args) {
        this.args = args;
    }

    /**
     * Adds a value to the expression list 
     * @param arg 
     */
    public void addArg(Expression arg) {
        args.add(arg);
    }

    /**
     * Returns name of the variable 
     * @return 
     */
    public Name getName() {
        return name;
    }

    /**
     *Sets the name of the funcall value .
     * @param name 
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     *Returns the scope of the funcall
     * @return 
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Sets the scope of the funcall to a new scope.
     * @param scope 
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    private String getMangledName(){
        return name.getName() + (args!=null?getArgs().toPolish():"$");
    }
    

    @Override
    public Type getType() {
        System.out.println("Getting type of " + getMangledName() + " as " + scope.getType(getMangledName()).toAST());
        return (scope.getType(getMangledName())).toAST();
    }

    @Override
    public Token getSymbol(){
    return name.symbol;
    }
    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if(args!=null){
            args.typeCheck(msgs);
        }
        
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        if (args != null) {
            args.generate(code, inFunction);
            code.add("\tcall " + name.getName() + args.toPolish());
            code.add("\tdloc " + args.size());
        } else {
            code.add("\tcall " + name.getName() + "$");
        }
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[Funcall: ");
        sb.append(this.getName().format(indent, true));
        sb.append(" Args: ");
        if (getArgs() != null) {
            sb.append(this.getArgs().format(indent, true));
        }
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
    //
    private Name name;
    private ExprList args;
    private Scope scope;

}