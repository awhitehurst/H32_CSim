/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import csim.Generator;
import java.util.ArrayList;
import lexer.Token;

/**
 *
 * @author Alan
 */
public class Loop extends Statement {

    /**
     * creates an empty loop
     */
    public Loop() {
    }

    /**
     * Set the symbol for the loop
     * @param symbol 
     */
    public Loop(Token symbol) {
        super(symbol);
    }

    /**
     * return the block body value 
     * @return 
     */
    public Block getBody() {
        return body;
    }

   
    /**
     * set the block body value 
     * @param body 
     */
    public void setBody(Block body) {
        this.body = body;
    }

    /**
     * return the expression of the test 
     * @return 
     */
    public Expression getTest() {
        return test;
    }

    /**
     * set the expression test value 
     * @param test 
     */
    public void setTest(Expression test) {
        this.test = test;
    }

    /**
     * check to see if the init assignment value is null 
     * @return 
     */
    public boolean hasInit() {
        return init != null;
    }

    /**
     * return the init assignment value 
     * @return 
     */
    public Assignment getInit() {
        return init;
    }

    /**
     * set the value of the init 
     * @param init 
     */
    public void setInit(Assignment init) {
        this.init = init;
    }

    /**
     * check to see if an update in the assignment has taken place 
     * @return 
     */
    public boolean hasUpdate() {
        return update != null;
    }

    /**
     *return the updated assignment value
     * @return 
     */
    public Assignment getUpdate() {
        return update;
    }

    /**
     * set the value of the assignment to be updated
     * @param update 
     */
    public void setUpdate(Assignment update) {
        this.update = update;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if (hasInit()) {
            init.typeCheck(msgs);
        }
        getTest().typeCheck(msgs);
        if (!getTest().getType().isTypeCompatible(Type.BOOL_TYPE)) {
            msgs.add(
                    String.format("Loop test (%s) is not bool at line %d, column %d.",
                            getTest().getType(),
                            getTest().getSymbol().getLine(),
                            getTest().getSymbol().getCol()));
        }
        if (hasUpdate()) {
            update.typeCheck(msgs);
        }
        body.typeCheck(msgs);
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction, int baseOffset) {
        code.add("; Loop");
        if (getInit() != null) {
            getInit().generate(code, inFunction);
        }
        String testLabel = Generator.getLabel();
        code.add(testLabel + ":");
        getTest().generate(code, inFunction);
        String endLabel = Generator.getLabel();
        code.add("\tjz " + endLabel);
        getBody().generate(code, inFunction, baseOffset);
        if (getUpdate() != null) {
            getUpdate().generate(code, inFunction);
        }
        code.add("\tja " + testLabel);
        code.add(endLabel + ":");

    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[Loop:\n");
        if (hasInit()) {
            sb.append(getInit().format(indent + 3, false));
        }
        sb.append(getTest().format(indent + 3, false));
        if (hasUpdate()) {
            sb.append(getUpdate().format(indent + 3, false));
        }
        sb.append(getBody().format(indent + 3, false));
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
    //
    private Assignment init = null;
    private Expression test = null;
    private Assignment update = null;
    private Block body = null;

}
