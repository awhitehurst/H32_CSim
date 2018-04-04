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
 //System.out.println("Getting type of " + getMangledName() + " as " + scope.getType(getMangledName()).toAST());
        return (scope.getType(getName().toString())).toAST();
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        
    
    }

    @Override
    public void generate(ArrayList<String> code, boolean dynamic) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String format(int indent, boolean suppressNL) {
               StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[PTFun: ");
        sb.append(this.getName().format(indent, true));
        sb.append(" Args: ");
        if (getArgs() != null) {
            sb.append(this.getArgs().format(indent, true));
        }
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();    }
    
}
