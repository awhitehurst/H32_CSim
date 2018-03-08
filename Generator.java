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
 * Generates the assembly code of the program.
 * Uses the generate(ArrayList<String>,boolean) method of ASTNode subclasses
 * The assembly code is stored in output list
 * @author alan.whitehurst
 */
public class Generator {
    /** 
     * @param: ASTNode astRoot, root of the abstract syntax tree
     * 
     */
    public Generator(String filename, SymbolTable stab, ASTNode astRoot){
        this.filename = filename;
        this.stab = stab;
        this.astRoot = astRoot;
    }
    /**
     * Calls the generate methods of ASTNode subclasses.
     * ASTNode.generate methods add the output list
     * @returns: filled output list with assembly code
     */
    public ArrayList<String> generate(){
        output = new ArrayList<>();
        astRoot.generate(output, false);
        return output;
    }
    /**
     * @returns: label of @L%03d with the total of labels generated next to it.
     */
    public static String getLabel(){
        return String.format("@L%03d", labelCount++);
    }
    //
    private final String filename;
    private final SymbolTable stab;
    private final ASTNode astRoot;
    private ArrayList<String> output;
    private static int labelCount = 0;
}
