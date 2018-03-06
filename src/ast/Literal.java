/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 *This class takes an expression and returns it to the correct type
 * @author Alan
 */
public class Literal extends Expression {
    

    /**
     * Set the symbol for the binar
     * @param symbol 
     */
    public Literal(Token symbol) {
        super(symbol);
    }

    /**
     * creates an empty literal
     */
    public Literal() {
    }

   /**
    * 
    * @param symbol this is a token 
    * @param type this is a type 
    */
    public Literal(Token symbol, Type type) {
        super(symbol);
        this.type = type;
    }

    /**
     * creates cases depending on the type of the expression 
     * @return 
     */
    public Object toObject() {
        switch (type.getTypeCode()) {
            case Type.INT:
                return toInt();
            case Type.CHAR:
                return toChar();
            case Type.BOOL:
                return toBool();
            default:
                return symbol.getSymbol();
        }
    }

    /**
     * return parsed int value 
     * @return 
     */
    public int toInt() {
        return Integer.parseInt(symbol.getSymbol());
    }

    //return the char symbol  
    public String toChar() {
        return symbol.getSymbol();
    }

    /**
     * returned the parsed boolean expression 
     * @return 
     */
    public boolean toBool() {
        return Boolean.parseBoolean(symbol.getSymbol());
    }

    @Override
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return symbol.getSymbol();
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[Literal: ");
        sb.append(this.getType().format(indent, true));
        sb.append(" ");
        sb.append(this.toObject().toString());
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
    //
    private Type type;

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        return;
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        switch (type.getTypeCode()) {
            case Type.INT:
                code.add("\tldc "+toObject().toString());
                break;
            case Type.CHAR:
                code.add("\tldc " + symbol.getSymbol());
                break;
            case Type.BOOL:
                if(Boolean.parseBoolean(symbol.getSymbol())){
                    code.add("\tldc 1");
                } else {
                    code.add("\tldc 0");
                }
                break;
            default:
                // strings are different
        }
    }

}
