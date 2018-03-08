package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 *This class takes an expression and returns it to the correct type
 * @author Alan
 */
public class Literal extends Expression {
/**
 * Creates a Literal associated with a Token
 * @param symbol the Token associated with the Literal
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
     * Creates a Literal with a Token and a Type
     * @param symbol the Token associated with the literal
     * @param type the Type of the Literal
     */
    public Literal(Token symbol, Type type) {
        super(symbol);
        this.type = type;
    }

    /**
     * Translates a primitive data type to an Object of that type. 
     * @return the finished Object.
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

   /**
    * Passes back the symbol of the Token.
    * @return the the symbol of the associated Token.
    */
    public String toChar() {
        return symbol.getSymbol();
    }

    /**
     * Translates the content of the Token into Boolean form.
     * @return the Boolean representation of the symbol.
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
       // return;
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
