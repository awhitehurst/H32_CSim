/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author daugh
 */
public class TypeList extends ASTNode{
     private final ArrayList<Type> types;
        /**
     * Creates an empty ExprList.
     */
    public TypeList(){
        types = new ArrayList<>();
    }
    /**
     * Returns the size of the ExprList.
     * @return 
     */
    public int size(){
        return types.size();
    }
    /**
     * Adds a new expression.
     * @param expr the new expression.    /**
     * Adds a new expression.
     * @param expr the new expression.
     */

    public void add(Type type){
        types.add(type);
    }
    /**
     * Get the expression at a specified index.
     * @param index the index to search at.
     * @return The requested Expression.
     */
    public Type get(int index){
        return (Type) types.get(index);
    }
    /**
     * Sets the Expression at a specified index to a new Expression
     * @param index the index to change
     * @param expr the new expression
     */
    public void set(int index, Type type){
        types.set(index, type);
    }
    /**
     * Creates an iterator for the ExprList.
     * @return the iterator.
     */
    public Iterator<Type> iterator(){
        return types.iterator();
    }
    /**
     * Returns a ListIterator for the ExprList.
     * @return 
     */
    public ListIterator<Type> listIterator(){
        return types.listIterator();
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
