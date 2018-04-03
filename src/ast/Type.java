package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 * Contains information about a type of variable, including its Token, its level of pointer it is, the type of the symbol, and whether the variable is static.
 * Needs work
 * @author Alan
 */
public class Type extends ASTNode implements Comparable {

    public static final int VOID = 0;
    public static final int BOOL = 1;
    public static final int CHAR = 2;
    public static final int INT = 3;
    public static final int CHARP = 4;

    public static final Type VOID_TYPE = new Type(VOID, null);
    public static final Type BOOL_TYPE = new Type(BOOL, null);
    public static final Type CHAR_TYPE = new Type(CHAR, null);
    public static final Type INT_TYPE = new Type(INT, null);
    public static final Type CHARP_TYPE = new Type(CHAR, 1, null);
/**
 * Uses a Token to construct a Type.
 * @param symbol the symbol to be contained in the type.
 */
    public Type(Token symbol) {
        super(symbol);
        this.typeCode = toTypeCode(symbol);
        this.pointer = 0;
        if (typeCode == CHARP) {
            typeCode = CHAR;
            pointer = 1;
        }
    }
/**
 * Uses a typeCode and a Token to construct a type.
 * @param typeCode
 * @param symbol 
 */
    public Type(int typeCode, Token symbol) {
        this(typeCode, 0, symbol);
    }
    /**
     * Uses a typeCode, pointer and Token to construct a type.
     * @param typeCode
     * @param pointer
     * @param symbol 
     */
    public Type(int typeCode, int pointer, Token symbol){
        this(typeCode, pointer, symbol, false);
    }
 /**
  * Uses a specified type to create an identical type.
  * @param t 
  */   
    public Type(Type t){
        this(t.typeCode, t.pointer, t.symbol, t.stat);
    }
/**
 * Fully specified Constructor.
 * @param typeCode
 * @param pointer
 * @param symbol
 * @param stat 
 */
    public Type(int typeCode, int pointer, Token symbol, boolean stat) {
        super(symbol);
        this.typeCode = typeCode;
        this.pointer = pointer;
        this.symbol = symbol;
        this.stat = stat;
    }
/**
 * Sets the typeCode to provided typeCode
 * @param typeCode 
 */
    public void setType(int typeCode) {
        this.typeCode = typeCode;
    }
/**
 * Returns the typeCode
 * @return typeCode
 */
    public int getTypeCode() {
        return typeCode;
    }
/**
 * Returns the pointer
 * @return pointer
 */
    public int getPointer() {
        return pointer;
    }
/**
 * Sets the pointer to provided value
 * @param pointer the new value.
 */
    public void setPointer(int pointer) {
        this.pointer = pointer;
    }
/**
 * Checkers whether this type is a pointer.
 * @return true if it is, else false.
 */
    public boolean isPointer() {
        return pointer > 0;
    }
/**
 * Translates the symbol of a Token from an SType to a TypeCode
 * @param symbol the Token to be identified
 * @return the appropriate identification.
 */
    public static int toTypeCode(Token symbol) {
        lexer.SType stype = symbol.getSType();
        switch (stype) {
            case CI:
                return INT;
            case CC:
                return CHAR;
            case CB:
                return BOOL;
            case CS:
                return CHARP;
            default:
                return VOID;
        }
    }
    /**
     * Returns 1.
     * @return 1.
     */
    public int getAllocSize(){
        return 1;
    }
    /**
     * Checks whether the Type is static.
     * @return true if it is static, otherwise false.
     */
    public boolean isStatic(){
        return stat;
    }
    
    public void setStatic(boolean aStatic) {
        stat = aStatic;
    }
    /**
     * Checks whether another type is compatible
     * @param t the Type to be checked.
     * @return true if compatible, or false if not compatible.
     */
    public boolean isTypeCompatible(Type t) {
        if(typeCode == 0){ ///CHECK THIS.
        return true;
        }
        return typeCode == t.typeCode && pointer == t.pointer;
    }

    public String toPolish() {
        StringBuilder sb = new StringBuilder();
        if (isPointer()) {
            for (int i = 0; i < pointer; ++i) {
                sb.append("p");
            }
        }
        sb.append(TYPENAME[typeCode].substring(0,1));
        return sb.toString();
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[Type: ");
        sb.append(TYPENAME[getTypeCode()]);
        for (int i = 0; i < pointer; ++i) {
            sb.append("*");
        }
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(isStatic()){
            sb.append("static ");
        }
        for(int i=0;i<pointer;++i){
            sb.append("*");
        }
        sb.append(TYPENAME[typeCode]);
        return sb.toString();
    }
    
    @Override
    public int compareTo(Object o) {
        if(o==null){
            return 1;
        }
        if(!(o instanceof Type)){
            return 1;
        }
        Type ot = (Type)o;
        return typeCode-ot.typeCode;
    }
    
    @Override
    public void typeCheck(ArrayList<String> msgs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//
    private int typeCode;
    private int pointer;
    private boolean stat;
    private static final String[] TYPENAME = {"void", "bool", "char", "int", "char*",};
    
    

    

    
}