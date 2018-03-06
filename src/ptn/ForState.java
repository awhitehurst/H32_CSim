/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

/**
 *
 * @author Alan
 */
public class ForState extends Statement {
    
    public ForState(){
        super(4);
    }
    
    /**
     * return the assigned states init value 
     * @return 
     */
    public AssignState getInit(){
        return (AssignState) children.get(0);
    }
    
    /**
     * set the assigned state init value 
     * @param init 
     */
    public void setInit(AssignState init){
        children.set(0, init);
    }
    
    /**
     * return the test result for the expression 
     * @return 
     */
    public Expression getTest(){
        return (Expression) children.get(1);
    }
    
    /**
     * set the value of the test 
     * @param test 
     */ 
    public void setTest(Expression test){
        children.set(1,test);
    }
    
    /**
     * return the assigned state incr value 
     * @return 
     */
    public AssignState getIncr(){
        return (AssignState) children.get(2);
    }
    
    /**
     * set the assigned state incr value 
     * @param incr 
     */
    public void setIncr(AssignState incr){
        children.set(2,incr);
    }
    
    /**
     * return the block value 
     * @return 
     */
    public Block getBody(){
        return (Block) children.get(3);
    }

    /**
     * set the block value 
     * @param body 
     */
    public void setBody(Block body) {
        children.set(3,body);
    }
    
    @Override
    public String format(int indent){
        StringBuilder sb = new StringBuilder();
        sb.append(indent(indent));
        sb.append("for(");
        sb.append(getInit().format(indent,true));
        sb.append(";");
        sb.append(getTest().format(indent));
        sb.append(";");
        sb.append(getIncr().format(indent,true));
        sb.append(")");
        sb.append(getBody().format(indent+5));
        return sb.toString();
    }
    
    @Override
    public ast.Loop toAST(){
        ast.Loop loop = new ast.Loop();
        loop.setInit(getInit().toAST());
        loop.setTest(getTest().toAST());
        loop.setUpdate(getIncr().toAST());
        loop.setBody(getBody().toAST());
        return loop;
    }
    
}
