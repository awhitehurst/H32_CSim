package csim;

import ast.ASTNode;
import java.util.ArrayList;
import parser.SymbolTable;

/**
 *Checker Class of csim package.
 * This class creates a checker the root ASTNode for error messages
 * Any error messages it finds it will store them in the Array List errorMessages
 * Makes use of the ASTNode.typeCheck(ArrayList<String>) in the check() method
 * @author Alan
 */

public class Checker {
    /**
     * errorMessages is set as an empty ArrayList<String>
     * @param ASTNode is used to set the root of the abstract syntax tree
     */
    public Checker(SymbolTable symtab, ASTNode root){
        this.symtab = symtab;
        this.root = root;
        errorMessages = new ArrayList<>();
    }
    /**
     * Uses typeCheck(Arraylist<String>) method.
     * The typeCheck methods that are implemented add to the errorMessages list.
     * If they are not implemented they Throw UnsupportedOperationException
     */
    public void check() {
        root.typeCheck(errorMessages);
    }
    /**
     * Returns true if there are more than 0 errors 
     */
    public boolean hasError() {
        return errorMessages.size()>0;
    }
    /**
     * Returns the list of error messages
     */
    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }
    //
    private SymbolTable symtab;
    private ASTNode root;
    private ArrayList<String> errorMessages;
}
