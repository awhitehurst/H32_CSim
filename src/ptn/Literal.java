/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

/**
 *Creates cases depending on what symbol is retrieved 
 * @author Alan
 */
public class Literal extends Expression {
    
    /**
     * return proper format depending on case 
     * @return 
     */
    public String toPolish(){
        switch(symbol.getSType()){
            case CI:
                return "_i";
            case CB:
                return "_b";
            case CC:
                return "_c";
            case CS:
                return "_pc";
            default:
                return "_v";
        }
    }
    
    @Override
    public String format(int ident){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getSymbol().getSymbol());
        return sb.toString();
    }
    
    @Override
    public ast.Literal toAST(){
        ast.Literal value = new ast.Literal();
        value.setSymbol(symbol);
        value.setType(new ast.Type(symbol));
        return value;
    }
    
}
