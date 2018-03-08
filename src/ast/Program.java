/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import java.util.Iterator;
import lexer.Token;

/**
 *
 * @author Alan
 */
public class Program extends ASTNode {

    /**
     * Set the symbol for the program
     * @param symbol 
     */
    public Program(Token symbol) {
        super(symbol);
    }

    /**
     * creates an empty program
     */
    public Program() {
    }

    /**
     * 
     * @param symbol is a token
     * @param functs is an array list 
     * @param body is a block
     */
    public Program(Token symbol, ArrayList<Function> functs, Block body) {
        super(symbol);
        this.functs = functs;
        this.body = body;
    }

    /**
     * add a fuction value to the array list 
     * @param f 
     */
    public void addFunct(Function f) {
        functs.add(f);
    }

    /**
     * set the value of the function in the array list 
     * @param functs 
     */
    public void setFuncts(ArrayList<Function> functs) {
        this.functs = functs;
    }

    /**
     * add a declaration to the body 
     * @param d 
     */
    public void addDecl(Declaration d) {
        body.addDecl(d);
    }

    /**
     * add a statement to the body 
     * @param s 
     */
    public void addStat(Statement s) {
        body.addStat(s);
    }

    /**
     * set the value of the body block 
     * @param body 
     */
    public void setBody(Block body) {
        this.body = body;
    }

    /**
     * return the block body value 
     * @return 
     */
    public Block getBody() {
        return body;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if (functs != null) {
            Iterator<Function> functions = functs.iterator();
            while (functions.hasNext()) {
                functions.next().typeCheck(msgs);
            }
        }
        body.typeCheck(msgs);
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        code.add("!o");
        if (functs!=null && !functs.isEmpty()) {
            code.add("; functions");
            for (Function f : functs) {
                f.generate(code, true);
            }
        }
        code.add("@temp:\tdw 0");
        code.add("main$:");
        body.generate(code, true);
        code.add("\thalt");
        code.add("\tend main$");
    }

    @Override
    public String format(int indent, boolean suppressNewline) {
        StringBuilder sb = new StringBuilder();
        if (functs != null) {
            Iterator<Function> theFuncts = functs.iterator();
            while (theFuncts.hasNext()) {
                sb.append(theFuncts.next().format(indent, false));
                sb.append("\n");
            }
        }
        sb.append(indent(indent));
        sb.append("[Program:\n");
        sb.append(getBody().format(indent + 3, false));
        sb.append("\n");
        sb.append(indent(indent));
        sb.append("]\n");
        return sb.toString();
    }
    //
    private ArrayList<Function> functs;
    private Block body;

}
