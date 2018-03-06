package csim;

import ast.ASTNode;
import java.util.ArrayList;
import parser.SymbolTable;

/**
 *
 * @author Alan
 */

public class Checker {
    /**
     * Builder for Checker Class with two parameters
     * @param SymbolTable this is the first paramater for Checker
     * @param ASTNode this is the second parameter for Checker
     */
    public Checker(SymbolTable symtab, ASTNode root){
        this.symtab = symtab;
        this.root = root;
        errorMessages = new ArrayList<>();
    }
    
    public void check() {
        root.typeCheck(errorMessages);
    }

    public boolean hasError() {
        return errorMessages.size()>0;
    }

    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }
    //
    private SymbolTable symtab;
    private ASTNode root;
    private ArrayList<String> errorMessages;
}
