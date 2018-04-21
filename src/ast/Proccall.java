/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 *
 * @author Alan
 */
public class Proccall extends Statement {

    /**
     * Set the symbol for the procall
     * @param symbol 
     */
    public Proccall(Token symbol) {
        super(symbol);
    }

    /**
     * creates an empty procall
     */
    public Proccall() {
    }

    /**
     * 
     * @param symbol is a token
     * @param name is a name 
     * @param args is the expression list 
     */
    public Proccall(Token symbol, Name name, ExprList args) {
        super(symbol);
        this.name = name;
        this.args = args;
    }

    /**
     * return the args expression list 
     * @return 
     */
    public ExprList getArgs() {
        return args;
    }

    /**
     * set the value of the expression list 
     * @param args 
     */
    public void setArgs(ExprList args) {
        this.args = args;
    }
/**
 * adds an args value to the expression list 
 * @param arg 
 */
    public void addArg(Expression arg) {
        args.add(arg);
    }

    /**
     * returns the name 
     * @return 
     */ 
    public Name getName() {
        return name;
    }

    /**
     * sets the value of the name 
     * @param name 
     */
    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if (args != null) {
            args.typeCheck(msgs);
        }
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction, int offset) {
        if(args!=null){
            args.generate(code, inFunction);
        }
        code.add("\tcall " + name.getName() + (args!=null?args.toPolish():"$"));
        if(args!=null){
            code.add("\tdloc " + args.size());
        }
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[Proccall: ");
        sb.append(this.getName().format(indent, true));
        sb.append(" Args: ");
        if (args != null) {
            sb.append(args.format(indent, true));
        } else {
            sb.append("()");
        }
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
    //
    private Name name;
    protected ExprList args;

}