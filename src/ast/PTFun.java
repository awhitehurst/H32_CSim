/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import lexer.Token;
import parser.Scope;

/**
 *
 * @author daugh
 */
public class PTFun extends VarDecl{

    private Name name;
    private TypeList args;
    private Scope scope;

     public PTFun(Token symbol) {
        super(symbol);
    }

    public PTFun() {
    }

    public PTFun(Token symbol, Name name, TypeList args) {
        super(symbol);
        this.name = name;
        this.args = args;
    }
      /**
     * Returns name of the variable 
     * @return 
     */
    public Name getName() {
        return name;
    }

    /**
     *Sets the name of the PTFun value .
     * @param name 
     */
    public void setName(Name name) {
        this.name = name;
    }
    
    public void setArgs(TypeList args){
    
    this.args = args;
    }
    public TypeList getArgs(){
    return args;
    }
    @Override
    public Type getType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void generate(ArrayList<String> code, boolean dynamic) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String format(int indent, boolean suppressNewline) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
