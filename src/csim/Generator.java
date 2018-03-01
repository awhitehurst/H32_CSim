/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csim;

import ast.ASTNode;
import java.util.ArrayList;
import parser.SymbolTable;

/**
 *
 * @author alan.whitehurst
 */
public class Generator {
    /**
     * Builder method for Generator class with three parameters
     * @param filename this is the first parameter
     * @param SymbolTable this is th second parameter
     * @param ASTNode this is the third parameter
     */
    public Generator(String filename, SymbolTable stab, ASTNode astRoot){
        this.filename = filename;
        this.stab = stab;
        this.astRoot = astRoot;
    }
    /**
     * This method generates an ArrayList<String>.
     * Uses ASTNode.generate(ArrayList<String>,boolean).
     * @return ArrayList<String>.
     */
    public ArrayList<String> generate(){
        output = new ArrayList<>();
        astRoot.generate(output, false);
        return output;
    }
    /**
     * This method gets a label for the class.
     * @return String containing the total number of labels produced.
     */
    public static String getLabel(){
        return String.format("@L%03d", labelCount++);
    }
    //Variable Declarations
    private final String filename;
    private final SymbolTable stab;
    private final ASTNode astRoot;
    private ArrayList<String> output;
    private static int labelCount = 0;
}
